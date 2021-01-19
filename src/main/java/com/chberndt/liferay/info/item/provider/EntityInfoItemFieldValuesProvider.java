package com.chberndt.liferay.info.item.provider;

import com.chberndt.liferay.info.field.EntityInfoItemFields;
import com.chberndt.liferay.info.item.Entity;

import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Christian Berndt
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class EntityInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<Entity> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(Entity entity) {
		System.out.println(
			"EntityInfoItemFieldValuesProvider.getInfoItemFieldValues()");
		System.out.println("entity = " + entity);
		System.out.println(
			"entity.getClass.getName() = " +
				entity.getClass(
				).getName());

		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getInfoFieldValues(entity)
		).infoItemReference(
			new InfoItemReference(Entity.class.getName(), 0)
		).build();
	}

	private List<InfoFieldValue<Object>> _getInfoFieldValues(Entity entity) {
		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		infoFieldValues.add(
			new InfoFieldValue<>(
				EntityInfoItemFields.titleInfoField, entity.getTitle()));
		infoFieldValues.add(
			new InfoFieldValue<>(
				EntityInfoItemFields.descriptionInfoField,
				entity.getDescription()));

		return infoFieldValues;
	}

}