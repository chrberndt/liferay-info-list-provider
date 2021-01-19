package com.chberndt.liferay.info.field;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Christian Berndt
 */
public interface EntityInfoItemFields {

	public static final InfoField<TextInfoFieldType> descriptionInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"description"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				EntityInfoItemFields.class, "description")
		).build();
	public static final InfoField<ImageInfoFieldType> imageInfoField =
		InfoField.builder(
		).infoFieldType(
			ImageInfoFieldType.INSTANCE
		).name(
			"image"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(EntityInfoItemFields.class, "image")
		).build();
	public static final InfoField<TextInfoFieldType> titleInfoField =
		InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"title"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(EntityInfoItemFields.class, "title")
		).build();

}