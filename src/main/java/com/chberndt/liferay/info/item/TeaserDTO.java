package com.chberndt.liferay.info.item;

/**
 *
 * @author Christian Berndt
 *
 */
public class TeaserDTO implements Teaser {

	public String getClassName() {
		return className;
	}

	public long getClassPK() {
		return classPK;
	}

	public String getDescription() {
		return description;
	}

	public String getImageURL() {
		return imageURL;
	}

	public String getTitle() {
		return title;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setClassPK(long classPK) {
		this.classPK = classPK;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String className;
	private long classPK;
	private String description;
	private String imageURL;
	private String title;

}