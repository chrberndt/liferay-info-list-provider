package com.chberndt.liferay.info.item.provider;

import com.chberndt.liferay.info.field.EntityInfoItemFields;
import com.chberndt.liferay.info.item.Entity;

import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Christian Berndt
 */
@Component(service = InfoItemFormProvider.class)
public class EntityInfoItemFormProvider
	implements InfoItemFormProvider<Entity> {

	@Override
	public InfoForm getInfoForm() {
		return InfoForm.builder(
		).infoFieldSetEntry(
			EntityInfoItemFields.titleInfoField
		).infoFieldSetEntry(
			EntityInfoItemFields.descriptionInfoField
		).infoFieldSetEntry(
			EntityInfoItemFields.imageInfoField
		).name(
			Entity.class.getName()
		).build();
	}

}