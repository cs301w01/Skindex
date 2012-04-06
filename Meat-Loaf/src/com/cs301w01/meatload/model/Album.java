package com.cs301w01.meatload.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Stores temporary values representing an Album to facilitate reading and
 * writing album details from the database.
 */
public class Album implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private int pictureCount;
	private long id;
	private Date date;

	/**
	 * Constructor, creates an Album object with the given name, # of pictures,
	 * id, and date.
	 * 
	 * @param name
	 * @param pictureCount
	 * @param id
	 * @param date
	 */
	public Album(String name, int pictureCount, long id, Date date) {
		this.name = name;
		this.pictureCount = pictureCount;
		this.id = id;
		this.date = date;
	}

	/**
	 * Constructor, creates an Album object with a given Name, # of pictures,
	 * and id
	 */
	public Album(String name, int pictureCount, long id) {
		this.name = name;
		this.pictureCount = pictureCount;
		this.id = id;
		this.date = Calendar.getInstance().getTime();
	}

	/**
	 * Returns the current Album's Name
	 * 
	 * @return String of current album's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the current Album's picture count
	 * 
	 * @return The number of pictures in the album as an int
	 */
	public int getPictureCount() {
		return pictureCount;
	}

	/**
	 * Returns the current Album's ID
	 * 
	 * @return long
	 */
	public long getID() {
		return id;
	}

	/**
	 * Returns the current Album's Date
	 * 
	 * @return Date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Returns a formatted string representing the current Album Name.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return getName();
	}
}
