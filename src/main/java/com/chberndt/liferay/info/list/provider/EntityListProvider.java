package com.chberndt.liferay.info.list.provider;

import com.chberndt.liferay.info.item.Entity;

import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Christian Berndt
 */
@Component(immediate = true, service = InfoListProvider.class)
public class EntityListProvider implements InfoListProvider<Entity> {

	@Override
	public List<Entity> getInfoList(
		InfoListProviderContext infoListProviderContext) {

		return getInfoList(infoListProviderContext, null, null);
	}

	@Override
	public List<Entity> getInfoList(
		InfoListProviderContext infoListProviderContext, Pagination pagination,
		Sort sort) {

		List<Entity> entities = new ArrayList<>();

		entities.add(new Entity("My First Entity", "My first description"));
		entities.add(new Entity("My Second Entity", "My second description"));

		return entities;
	}

	@Override
	public int getInfoListCount(
		InfoListProviderContext infoListProviderContext) {

		return 2;
	}

	@Override
	public String getLabel(Locale locale) {
		return "Entity Info List Provider";
	}

}