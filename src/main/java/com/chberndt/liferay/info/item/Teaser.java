package com.chberndt.liferay.info.item;

public interface Teaser {

	public String getClassName();

	public long getClassPK();

	public String getDescription();

	public String getImageURL();

	public String getTitle();

	public void setClassName(String className);

	public void setClassPK(long classPK);

	public void setDescription(String description);

	public void setImageURL(String imageURL);

	public void setTitle(String title);

}