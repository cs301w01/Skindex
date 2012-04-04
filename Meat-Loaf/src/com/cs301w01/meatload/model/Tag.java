package com.cs301w01.meatload.model;

import java.io.Serializable;

/**
 * Object used to store reference data for Tags.
 * @author Derek Dowling
 */
public class Tag implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private int pictureCount;

	/**
	 * Default constructor for Tag, with a Name and Picture Count
	 * @param name Name of tag to be created
	 * @param pictureCount Number of pictures associated with this tag
	 */
	public Tag(String name, int pictureCount) {
		this.name = name;
		this.pictureCount = pictureCount;
	}

	/**
	 * Returns the name of the current Tag object.
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the number of pictures associated with this tag.
	 * @return int
	 */
	public int getPictureCount() {
		return this.pictureCount;
	}

}
