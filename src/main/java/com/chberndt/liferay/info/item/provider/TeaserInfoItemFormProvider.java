package com.chberndt.liferay.info.item.provider;

import com.chberndt.liferay.info.field.TeaserInfoItemFields;
import com.chberndt.liferay.info.item.Teaser;

import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Christian Berndt
 */
@Component(service = InfoItemFormProvider.class)
public class TeaserInfoItemFormProvider
	implements InfoItemFormProvider<Teaser> {

	@Override
	public InfoForm getInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			TeaserInfoItemFields.titleInfoField
		).infoFieldSetEntry(
			TeaserInfoItemFields.descriptionInfoField
		).infoFieldSetEntry(
			TeaserInfoItemFields.imageInfoField
		).name(
			Teaser.class.getName()
		).build();
	}

}