package com.cs301w01.meatload.model;

/**
 * Object used to store reference data for Tags.
 * @author Derek Dowling
 */
public class Tag {

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
	 * @return The name of this tag
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return The number of pictures associated with this tag
	 */
	public int getPictureCount() {
		return this.pictureCount;
	}

}
