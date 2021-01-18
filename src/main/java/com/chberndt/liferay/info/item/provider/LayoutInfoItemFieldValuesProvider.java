package com.chberndt.liferay.info.item.provider;

import com.chberndt.liferay.info.field.LayoutInfoItemFields;

import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(service = InfoItemFieldValuesProvider.class)
public class LayoutInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<Layout> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(Layout layout) {
		
		System.out.println("LayoutInfoItemFieldValuesProvider.getInfoItemFieldValues()"); 
		System.out.println("layout.getLayoutId() = " + layout.getLayoutId());
		System.out.println("Layout.class.getName() = " + Layout.class.getName());
		
		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getInfoFieldValues(layout)
		).infoItemReference(
			new InfoItemReference(Layout.class.getName(), layout.getLayoutId())
		).build();
	}

	private List<InfoFieldValue<Object>> _getInfoFieldValues(Layout layout) {
		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		infoFieldValues.add(
			new InfoFieldValue<>(
				LayoutInfoItemFields.titleInfoField,
				layout.getTitle(LocaleUtil.getDefault())));
		infoFieldValues.add(
			new InfoFieldValue<>(
				LayoutInfoItemFields.descriptionInfoField,
				layout.getDescription(LocaleUtil.getDefault())));

		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay != null) {
			infoFieldValues.add(
				new InfoFieldValue<>(
					LayoutInfoItemFields.imageInfoField, null));
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