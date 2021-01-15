package com.chberndt.liferay.info.item.provider;

import com.chberndt.liferay.info.field.LayoutSEOEntryInfoItemFields;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.layout.seo.model.LayoutSEOEntry;

import org.osgi.service.component.annotations.Component;

@Component(service = InfoItemFormProvider.class)
public class LayoutSEOEntryInfoItemFormProvider
	implements InfoItemFormProvider<LayoutSEOEntry> {

	@Override
	public InfoForm getInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			LayoutSEOEntryInfoItemFields.titleInfoField
		).infoFieldSetEntry(
			LayoutSEOEntryInfoItemFields.descriptionInfoField
		).infoFieldSetEntry(
			LayoutSEOEntryInfoItemFields.imageInfoField
		).name(
			LayoutSEOEntry.class.getName()
		).build();
	}

}
