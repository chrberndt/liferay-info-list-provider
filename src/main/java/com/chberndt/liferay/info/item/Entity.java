package com.chberndt.liferay.info.item;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ClassedModel;

import java.io.Serializable;

/**
 * @author Christian Berndt
 */
public class Entity implements ClassedModel {

	public Entity(String title, String description) {
		_title = title;
		_description = description;
	}

	public String getDescription() {
		return _description;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {

		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public Class<?> getModelClass() {
		return Entity.class;
	}

	@Override
	public String getModelClassName() {
		return Entity.class.getName();
	}

	// Implementation of ClassedModel interface is required in
	// order to display configured field values in view mode

	@Override
	public Serializable getPrimaryKeyObj() {

		// TODO Auto-generated method stub

		return null;
	}

	public String getTitle() {
		return _title;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {

		// TODO Auto-generated method stub

	}

	private String _description;
	private String _title;

}