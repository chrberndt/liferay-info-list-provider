package com.chberndt.liferay.info.item;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ClassedModel;

import java.io.Serializable;

/**
 * @author Christian Berndt
 */
public class TeaserDTO implements ClassedModel, Teaser {

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public String getDescription() {
		return _description;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {

		// TODO Auto-generated method stub

		return null;
	}

	public String getImageURL() {
		return _imageURL;
	}

	@Override
	public Class<?> getModelClass() {
		return Teaser.class;
	}

	@Override
	public String getModelClassName() {
		return Teaser.class.getName();
	}

	@Override
	public Serializable getPrimaryKeyObj() {

		// TODO Auto-generated method stub

		return null;
	}

	public String getTitle() {
		return _title;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setImageURL(String imageURL) {
		_imageURL = imageURL;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {

		// TODO Auto-generated method stub

	}

	public void setTitle(String title) {
		_title = title;
	}

	private String _className;
	private long _classPK;
	private String _description;
	private String _imageURL;
	private String _title;

}