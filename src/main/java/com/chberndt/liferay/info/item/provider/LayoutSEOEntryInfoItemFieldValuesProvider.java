package com.chberndt.liferay.info.item.provider;

import com.chberndt.liferay.info.field.LayoutSEOEntryInfoItemFields;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(service = InfoItemFieldValuesProvider.class)
public class LayoutSEOEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<LayoutSEOEntry> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		LayoutSEOEntry layoutSEOEntry) {

		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getInfoFieldValues(layoutSEOEntry)
		).infoItemReference(
			new InfoItemReference(
				LayoutSEOEntry.class.getName(),
				layoutSEOEntry.getLayoutSEOEntryId())
		).build();
	}

	private List<InfoFieldValue<Object>> _getInfoFieldValues(
		LayoutSEOEntry layoutSEOEntry) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		infoFieldValues.add(
			new InfoFieldValue<>(
				LayoutSEOEntryInfoItemFields.titleInfoField,
				layoutSEOEntry.getOpenGraphTitle()));
		infoFieldValues.add(
			new InfoFieldValue<>(
				LayoutSEOEntryInfoItemFields.descriptionInfoField,
				layoutSEOEntry.getOpenGraphDescription()));

		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay != null) {
			infoFieldValues.add(
				new InfoFieldValue<>(
					LayoutSEOEntryInfoItemFields.imageInfoField,
					layoutSEOEntry.getOpenGraphImageFileEntryId()));
		}

		return infoFieldValues;
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

}