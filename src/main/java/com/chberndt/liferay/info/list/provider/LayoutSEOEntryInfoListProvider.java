package com.chberndt.liferay.info.list.provider;

import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Christian Berndt
 */
@Component(immediate = true, service = InfoListProvider.class)
public class LayoutSEOEntryInfoListProvider
	implements InfoListProvider<LayoutSEOEntry> {

	@Override
	public List<LayoutSEOEntry> getInfoList(
		InfoListProviderContext infoListProviderContext) {

		return getInfoList(infoListProviderContext, null, null);
	}

	@Override
	public List<LayoutSEOEntry> getInfoList(InfoListProviderContext infoListProviderContext, Pagination pagination,
			Sort sort) {

		// Obtain a list of layouts
		List<Layout> layouts = _layoutLocalService.dynamicQuery(_getDynamicQuery(infoListProviderContext, PRIVATE_LAYOUT), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		// TODO: Add paging
		// TODO: Filter by categories
		// TODO: Filter by parentlayoutId

		System.out.println("layouts.size() = " + layouts.size());

		Group group = null;

		Optional<Group> groupOptional = infoListProviderContext.getGroupOptional();

		if (groupOptional.isPresent()) {
			group = groupOptional.get();
		}

		// Obtain custom title / description / image from related LayoutSEOEntry
		
		// TODO: How to handle layouts without custom SEO entries?
		// TODO: How to obtain "default teaser" data (from fragment / WC)
		List<LayoutSEOEntry> layoutSEOEntries = new ArrayList<LayoutSEOEntry>(); 
				
		for (Layout layout : layouts) {
			
			LayoutSEOEntry layoutSEOEntry = _layoutSEOEntryLocalService.fetchLayoutSEOEntry(group.getGroupId(), PRIVATE_LAYOUT, layout.getLayoutId()); 
			
			if (layoutSEOEntry != null) {
				layoutSEOEntries.add(layoutSEOEntry); 
			}
			
		}

		System.out.println("layoutSEOEntries.size() = " + layoutSEOEntries.size());

		return layoutSEOEntries;

	}

	@Override
	public int getInfoListCount(
		InfoListProviderContext infoListProviderContext) {
		
		int total = (int) _layoutLocalService.dynamicQueryCount(_getDynamicQuery(infoListProviderContext, false));

		System.out.println("total = " + total); 
		
		return total;
	}
	
	private DynamicQuery _getDynamicQuery(InfoListProviderContext infoListProviderContext, boolean privateLayout) {

		Group group = null;

		Optional<Group> groupOptional = infoListProviderContext.getGroupOptional();

		if (groupOptional.isPresent()) {
			group = groupOptional.get();
		}
		
		// Get all private / public Content Pages of the current group
		// Filter out "editing" layouts marked as "system"
		return _layoutLocalService.dynamicQuery().add(RestrictionsFactoryUtil.eq("groupId", group.getGroupId()))
				.add(RestrictionsFactoryUtil.eq("type", "content"))
				.add(RestrictionsFactoryUtil.eq("privateLayout", privateLayout))
				.add(RestrictionsFactoryUtil.eq("system", PRIVATE_LAYOUT));
	}

	@Override
	public String getLabel(Locale locale) {
		return "LayoutSEOEntry Info List Provider";
	}
	
	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;
	
	@Reference
	private Portal _portal;
	
	// TODO: Obtain public / private layout from the info list context
	private static boolean PRIVATE_LAYOUT = false;

}
