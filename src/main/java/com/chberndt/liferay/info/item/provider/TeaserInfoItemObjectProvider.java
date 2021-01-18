package com.chberndt.liferay.info.item.provider;

import com.chberndt.liferay.info.item.Teaser;
import com.chberndt.liferay.info.item.TeaserDTO;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.GroupUrlTitleInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
	immediate = true, property = "service.ranking:Integer=100",
	service = InfoItemObjectProvider.class
)
public class TeaserInfoItemObjectProvider
	implements InfoItemObjectProvider<Teaser> {

	@Override
	public Teaser getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		System.out.println(
			"TeaserInfoItemObjectProvider.getInfoItem(InfoItemIdentifier infoItemIdentifier)");

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier) &&
			!(infoItemIdentifier instanceof GroupUrlTitleInfoItemIdentifier)) {

			throw new NoSuchInfoItemException("Invalid infoItemIdentifier");
		}

		Layout layout = null;

		if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)infoItemIdentifier;

			layout = _layoutLocalService.fetchLayout(
				classPKInfoItemIdentifier.getClassPK());
		}
		else if (infoItemIdentifier instanceof
					GroupUrlTitleInfoItemIdentifier) {

			GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier =
				(GroupUrlTitleInfoItemIdentifier)infoItemIdentifier;

			try {

				// TODO: obtain private / publicLayout info from context

				boolean privateLayout = false;
				layout = _layoutLocalService.getLayoutByFriendlyURL(
					groupUrlTitleInfoItemIdentifier.getGroupId(), privateLayout,
					groupUrlTitleInfoItemIdentifier.getUrlTitle());
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException, portalException);
				}
			}
		}

		if (layout == null) {
			throw new NoSuchInfoItemException("Invalid infoItemIdentifier");
		}
		
		Teaser teaser = new TeaserDTO();
		
		teaser.setClassName(Layout.class.getName());
		teaser.setClassPK(layout.getLayoutId());
		teaser.setTitle(layout.getTitle(LocaleUtil.getDefault()));
		teaser.setDescription(layout.getDescription(LocaleUtil.getDefault()));

		return teaser;
	}

	@Override
	public Teaser getInfoItem(long classPK) throws NoSuchInfoItemException {
		System.out.println(
			"TeaserInfoItemObjectProvider.getInfoItem(long classPK)");

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			new ClassPKInfoItemIdentifier(classPK);

		return getInfoItem(classPKInfoItemIdentifier);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TeaserInfoItemObjectProvider.class);

	@Reference
	private LayoutLocalService _layoutLocalService;

}