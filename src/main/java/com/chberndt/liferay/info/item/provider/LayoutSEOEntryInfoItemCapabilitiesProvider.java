package com.chberndt.liferay.info.item.provider;

import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.provider.InfoItemCapabilitiesProvider;
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Christian Berndt
 */
@Component(service = InfoItemCapabilitiesProvider.class)
public class LayoutSEOEntryInfoItemCapabilitiesProvider
	implements InfoItemCapabilitiesProvider<LayoutSEOEntry> {

	@Override
	public List<InfoItemCapability> getInfoItemCapabilities() {
		return ListUtil.fromArray(_displayPageInfoItemCapability);
	}

	@Reference
	private DisplayPageInfoItemCapability _displayPageInfoItemCapability;

}