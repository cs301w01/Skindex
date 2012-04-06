package com.cs301w01.meatload.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.cs301w01.meatload.model.Tag;

/**
 * Stores temporary values to be permanently stored in a database or used by one
 * of the View classes.
 * 
 * @author Derek Dowling
 */
public class Picture implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String albumName;
	private String path;
	private int id;
	private Date date;
	private ArrayList<Tag> tags;

	public Picture(String name, String path, String album, Date date, ArrayList<Tag> tags) {
		this.name = name;
		this.albumName = album;
		this.path = path;
		this.date = date;
		this.tags = tags;
	}
	
	/**
	 * Constructor, creates a Picture with the specified Name and Album Name
	 * @param name The name of the picture
	 * @param album The name of the album to add it to
	 */
	public Picture(String name, String album) {
		this.name = name;
		this.albumName = album;
	}

	/**
	 * Returns the ID of the current Picture object.
	 * @return int
	 */
	public int getPictureID() {
		return this.id;
	}

	/**
	 * Sets the id of the current Picture to the provided int.
	 * @param id new Picture ID.
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Returns the name of the current Picture object.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the Path of the current Picture object.
	 * @return String
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Returns the Date of the current Picture object.
	 * @return Date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Returns a collection of the tags from the current Picture object.
	 * @return Arraylist<Tag>
	 */
	public ArrayList<Tag> getTags() {
		return tags;
	}

	/**
	 * Returns the Album Name of the current Picture object.
	 * @return String
	 */
	public String getAlbumName() {
		return this.albumName;
	}

	/**
	 * Returns a formatted String representing the current Picture object.
	 */
	public String toString() {
		String output = "Picture, id = " + getPictureID() + " " + " name = "
				+ getName() + " date =  " + getDate().toString() + " tags = ( ";
		for (Tag tag : getTags()) {
			output.concat(tag.getName() + ", ");
		}
		output.concat(").");
		return output;
	}

}
