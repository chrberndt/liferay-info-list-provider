package com.chberndt.liferay.info.item.provider;

import com.chberndt.liferay.info.field.LayoutInfoItemFields;

import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.portal.kernel.model.Layout;

import org.osgi.service.component.annotations.Component;

/**
 * @author Christian Berndt
 */
@Component(service = InfoItemFormProvider.class)
public class LayoutInfoItemFormProvider
	implements InfoItemFormProvider<Layout> {

	@Override
	public InfoForm getInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			LayoutInfoItemFields.titleInfoField
		).infoFieldSetEntry(
			LayoutInfoItemFields.descriptionInfoField
		).infoFieldSetEntry(
			LayoutInfoItemFields.imageInfoField
		).name(
			Layout.class.getName()
		).build();
	}

}