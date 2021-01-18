package com.chberndt.liferay.info.list.provider;

import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Christian Berndt
 */
@Component(immediate = true, service = InfoListProvider.class)
public class LayoutInfoListProvider implements InfoListProvider<Layout> {

	@Override
	public List<Layout> getInfoList(
		InfoListProviderContext infoListProviderContext) {

		return getInfoList(infoListProviderContext, null, null);
	}

	@Override
	public List<Layout> getInfoList(
		InfoListProviderContext infoListProviderContext, Pagination pagination,
		Sort sort) {

		System.out.println("TeaserInfoListProvider.getInfoList()");

		// Obtain a list of layouts

		List<Layout> layouts = _layoutLocalService.dynamicQuery(
			_getDynamicQuery(infoListProviderContext, PRIVATE_LAYOUT),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		System.out.println("layouts.size() = " + layouts.size());

		// TODO: Add paging
		// TODO: Filter by categories
		// TODO: Filter by parentlayoutId

		return layouts;
	}

	@Override
	public int getInfoListCount(
		InfoListProviderContext infoListProviderContext) {

		int total = (int)_layoutLocalService.dynamicQueryCount(
			_getDynamicQuery(infoListProviderContext, false));

		System.out.println("total = " + total);

		return total;
	}

	@Override
	public String getLabel(Locale locale) {
		return "Layout Info List Provider";
	}

	private DynamicQuery _getDynamicQuery(
		InfoListProviderContext infoListProviderContext,
		boolean privateLayout) {

		Group group = null;

		Optional<Group> groupOptional =
			infoListProviderContext.getGroupOptional();

		if (groupOptional.isPresent()) {
			group = groupOptional.get();
		}

		// Get all private / public Content Pages of the current group
		// Filter out "editing" layouts marked as "system"

		return _layoutLocalService.dynamicQuery(
		).add(
			RestrictionsFactoryUtil.eq("groupId", group.getGroupId())
		).add(
			RestrictionsFactoryUtil.eq("type", "content")
		).add(
			RestrictionsFactoryUtil.eq("privateLayout", privateLayout)
		).add(
			RestrictionsFactoryUtil.eq("system", PRIVATE_LAYOUT)
		);
	}

	private static boolean PRIVATE_LAYOUT = false;

	@Reference
	private LayoutLocalService _layoutLocalService;

	// TODO: Obtain public / private layout from the info list context

	@Reference
	private Portal _portal;

}