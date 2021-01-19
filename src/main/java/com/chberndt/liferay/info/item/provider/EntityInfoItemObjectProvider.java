package com.chberndt.liferay.info.item.provider;

import com.chberndt.liferay.info.item.Entity;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.GroupUrlTitleInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Christian Berndt
 */
@Component(
	immediate = true, property = "service.ranking:Integer=100",
	service = InfoItemObjectProvider.class
)
public class EntityInfoItemObjectProvider
	implements InfoItemObjectProvider<Entity> {

	@Override
	public Entity getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		System.out.println(
			"EntityInfoItemObjectProvider.getInfoItem(InfoItemIdentifier infoItemIdentifier)");

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier) &&
			!(infoItemIdentifier instanceof GroupUrlTitleInfoItemIdentifier)) {

			throw new NoSuchInfoItemException("Invalid infoItemIdentifier");
		}

		Entity entity = null;

		//		if (entity == null) {
		//			throw new NoSuchInfoItemException("Invalid infoItemIdentifier");
		//		}

		return entity;
	}

	@Override
	public Entity getInfoItem(long classPK) throws NoSuchInfoItemException {
		System.out.println(
			"EntityInfoItemObjectProvider.getInfoItem(long classPK)");

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			new ClassPKInfoItemIdentifier(classPK);

		return getInfoItem(classPKInfoItemIdentifier);
	}

}