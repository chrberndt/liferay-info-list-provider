package com.chberndt.liferay.info.list.provider;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.util.AssetHelper;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Christian Berndt
 */
@Component(immediate = true, service = InfoListProvider.class)
public class CustomInfoListProvider implements InfoListProvider<AssetEntry> {

	@Override
	public List<AssetEntry> getInfoList(
		InfoListProviderContext infoListProviderContext) {

		return getInfoList(infoListProviderContext, null, null);
	}

	@Override
	public List<AssetEntry> getInfoList(
		InfoListProviderContext infoListProviderContext, Pagination pagination,
		Sort sort) {

		long[] audienceCategoryIds = null;

		User user = infoListProviderContext.getUser();

		// Only authenticated users

		if (!user.isDefaultUser()) {
			try {
				AssetEntry assetEntry = _assetEntryLocalService.getEntry(
					User.class.getName(), user.getUserId());

				List<AssetCategory> assetCategories =
					assetEntry.getCategories();

				for (AssetCategory assetCategory : assetCategories) {

					// TODO: Filter user categories by audience vocabulary

					System.out.println(assetCategory.getVocabularyId());
				}
			}
			catch (PortalException pe) {
				_log.error("Unable to get asset entry for user", pe);
			}
		}

		// TODO: Filter assets by audience category ids

		AssetEntryQuery assetEntryQuery = _getAssetEntryQuery(
			infoListProviderContext, Field.MODIFIED_DATE, "DESC", pagination);

		try {
			Hits hits = _assetHelper.search(
				_getSearchContext(infoListProviderContext), assetEntryQuery,
				assetEntryQuery.getStart(), assetEntryQuery.getEnd());

			return _assetHelper.getAssetEntries(hits);
		}
		catch (Exception exception) {
			_log.error("Unable to get asset entries", exception);
		}

		return Collections.emptyList();
	}

	@Override
	public int getInfoListCount(
		InfoListProviderContext infoListProviderContext) {

		Company company = infoListProviderContext.getCompany();

		return _assetEntryLocalService.getCompanyEntriesCount(
			company.getCompanyId());
	}

	@Override
	public String getLabel(Locale locale) {

		// TODO: Enable ResourceBundleLoader

		//		ResourceBundle resourceBundle =
		//			_resourceBundleLoader.loadResourceBundle(locale);

		//		return LanguageUtil.get(resourceBundle, "my-most-viewed-content");

		return "Category matcher";
	}

	// Based on com.liferay.asset.internal.info.list.provider.BaseAssetsInfoListProvider

	private AssetEntryQuery _getAssetEntryQuery(
		InfoListProviderContext infoListProviderContext, String orderByCol,
		String orderByType, Pagination pagination) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		Company company = infoListProviderContext.getCompany();

		// TODO: Filter by JournalArticle

		long[] availableClassNameIds =
			AssetRendererFactoryRegistryUtil.getClassNameIds(
				company.getCompanyId(), true);

		availableClassNameIds = ArrayUtil.filter(
			availableClassNameIds,
			availableClassNameId -> {
				String className = _portal.getClassName(availableClassNameId);

				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(className);

				if (indexer == null) {
					return false;
				}

				if (Objects.equals(className, DLFileEntry.class.getName())) {
					className = FileEntry.class.getName();
				}

				InfoDisplayContributor<?> infoDisplayContributor =
					_infoDisplayContributorTracker.getInfoDisplayContributor(
						className);

				if (infoDisplayContributor == null) {
					return false;
				}

				return true;
			});

		assetEntryQuery.setClassNameIds(availableClassNameIds);

		assetEntryQuery.setEnablePermissions(true);

		// TODO: Set relevant groupIds (AssetLibraries, Sites, Global, etc.)

		Optional<Group> groupOptional =
			infoListProviderContext.getGroupOptional();

		if (groupOptional.isPresent()) {
			Group group = groupOptional.get();

			assetEntryQuery.setGroupIds(new long[] {group.getGroupId()});
		}

		if (pagination != null) {
			assetEntryQuery.setStart(pagination.getStart());
			assetEntryQuery.setEnd(pagination.getEnd());
		}

		assetEntryQuery.setOrderByCol1(orderByCol);
		assetEntryQuery.setOrderByType1(orderByType);

		assetEntryQuery.setOrderByCol2(Field.CREATE_DATE);
		assetEntryQuery.setOrderByType2("DESC");

		return assetEntryQuery;
	}

	// From com.liferay.asset.internal.info.list.provider.RecentContentInfoListProvider

	private SearchContext _getSearchContext(
			InfoListProviderContext infoListProviderContext)
		throws Exception {

		Company company = infoListProviderContext.getCompany();

		long groupId = company.getGroupId();

		Optional<Group> groupOptional =
			infoListProviderContext.getGroupOptional();

		if (groupOptional.isPresent()) {
			Group group = groupOptional.get();

			groupId = group.getGroupId();
		}

		User user = infoListProviderContext.getUser();

		Optional<Layout> layoutOptional =
			infoListProviderContext.getLayoutOptional();

		SearchContext searchContext = SearchContextFactory.getInstance(
			new long[0], new String[0], new HashMap<>(), company.getCompanyId(),
			null, layoutOptional.orElse(null), null, groupId, null,
			user.getUserId());

		searchContext.setSorts(
			SortFactoryUtil.create(
				Field.MODIFIED_DATE,
				com.liferay.portal.kernel.search.Sort.LONG_TYPE, true),
			SortFactoryUtil.create(
				Field.CREATE_DATE,
				com.liferay.portal.kernel.search.Sort.LONG_TYPE, true));

		return searchContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomInfoListProvider.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private Portal _portal;

	// TODO: Enable ResourceBundleLoader

	//	@Reference(
	//		target = "(bundle.symbolic.name=com.chberndt.liferay.info.list.provider)"
	//	)
	//	private ResourceBundleLoader _resourceBundleLoader;

}