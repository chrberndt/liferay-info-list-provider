package com.chberndt.liferay.info.item.provider;

import com.chberndt.liferay.info.field.TeaserInfoItemFields;
import com.chberndt.liferay.info.item.Teaser;

import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Christian Berndt
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class TeaserInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<Teaser> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(Teaser teaser) {
		System.out.println(
			"TeaserInfoItemFieldValuesProvider.getInfoItemFieldValues()");
		System.out.println("teaser.getTeaserId() = " + teaser.getClassPK());
		System.out.println(
			"Teaser.class.getName() = " + Teaser.class.getName());

		System.out.println("teaser = " + teaser);

		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getInfoFieldValues(teaser)
		).infoItemReference(
			new InfoItemReference(Teaser.class.getName(), teaser.getClassPK())
		).build();
	}

	private List<InfoFieldValue<Object>> _getInfoFieldValues(Teaser teaser) {
		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		infoFieldValues.add(
			new InfoFieldValue<>(
				TeaserInfoItemFields.titleInfoField, teaser.getTitle()));

		// teaser.getTitle(LocaleUtil.getDefault())));

		infoFieldValues.add(
			new InfoFieldValue<>(
				TeaserInfoItemFields.descriptionInfoField,
				teaser.getDescription()));

		// teaser.getDescription(LocaleUtil.getDefault())));

		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay != null) {
			infoFieldValues.add(
				new InfoFieldValue<>(
					TeaserInfoItemFields.imageInfoField, null));
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