package com.chberndt.liferay.info.item.provider;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.layout.seo.model.LayoutSEOEntry;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

/**
 * @author Christian Berndt
 */
@Component(
	immediate = true, property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemDetailsProvider.class
)
public class LayoutSEOEntryInfoItemDetailsProvider
	implements InfoItemDetailsProvider<LayoutSEOEntry> {

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(LayoutSEOEntry.class.getName());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(LayoutSEOEntry layoutSEOEntry) {
		return new InfoItemDetails(
			getInfoItemClassDetails(),
			new InfoItemReference(
				LayoutSEOEntry.class.getName(),
				layoutSEOEntry.getLayoutSEOEntryId()));
	}

}