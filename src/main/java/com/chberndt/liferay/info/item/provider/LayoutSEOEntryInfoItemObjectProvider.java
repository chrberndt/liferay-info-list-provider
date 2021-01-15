package com.chberndt.liferay.info.item.provider;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.GroupKeyInfoItemIdentifier;
import com.liferay.info.item.GroupUrlTitleInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	immediate = true, property = "service.ranking:Integer=100",
	service = InfoItemObjectProvider.class
)
public class LayoutSEOEntryInfoItemObjectProvider
	implements InfoItemObjectProvider<LayoutSEOEntry> {

	@Override
	public LayoutSEOEntry getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier) &&
			!(infoItemIdentifier instanceof GroupUrlTitleInfoItemIdentifier)) {

			throw new NoSuchInfoItemException("Invalid infoItemIdentifier");
		}

		LayoutSEOEntry layoutSEOEntry = null;

		if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)infoItemIdentifier;

			layoutSEOEntry = _layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				classPKInfoItemIdentifier.getClassPK());
		}
		else if (infoItemIdentifier instanceof
					GroupUrlTitleInfoItemIdentifier) {

			ClassPKInfoItemIdentifier classPKInfoItemIdentifier = 
				(ClassPKInfoItemIdentifier)infoItemIdentifier;
//			GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier = 
//				(GroupKeyInfoItemIdentifier)infoItemIdentifier;
//			GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier =
//				(GroupUrlTitleInfoItemIdentifier)infoItemIdentifier;

			try {
				
				// TODO
				layoutSEOEntry = _layoutSEOEntryLocalService.getLayoutSEOEntry(
					classPKInfoItemIdentifier.getClassPK());
				
//				layoutSEOEntry = _layoutSEOEntryLocalService.getLayoutSEOEntry(
//					groupUrlTitleInfoItemIdentifier.getGroupId(),
//					groupUrlTitleInfoItemIdentifier.getUrlTitle());
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException, portalException);
				}
			}
		}

		if (layoutSEOEntry == null) {
			throw new NoSuchInfoItemException("Invalid infoItemIdentifier");
		}

		return layoutSEOEntry;
	}

	@Override
	public LayoutSEOEntry getInfoItem(long classPK)
		throws NoSuchInfoItemException {

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			new ClassPKInfoItemIdentifier(classPK);

		return getInfoItem(classPKInfoItemIdentifier);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSEOEntryInfoItemObjectProvider.class);

	@Reference
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

}
