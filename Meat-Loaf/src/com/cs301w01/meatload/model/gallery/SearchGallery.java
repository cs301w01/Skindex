package com.cs301w01.meatload.model.gallery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import android.content.Context;

import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;

/**
 * Used to create a Gallery Activity using a search criteria based on Dates or Tags.
 * 
 * @author Isaac Matichuk
 * @see SQLiteDBManager
 * @see GalleryActivity
 */
public class SearchGallery extends GalleryData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String picName;
	Date startDate;
	Date endDate;
	ArrayList<String> tags;

	/**
	 * Constructor, creates a TagGallery with the given list of tags.
	 * 
	 * @param tags
	 */
	public SearchGallery(String picName, Date startDate, Date endDate, ArrayList<String> tags) {

		this.picName = picName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.tags = tags;
	}

	/**
	 * Invokes the DBManager to return a set of Picture objects based on whether
	 * GalleryManager was contructed with an album name or a set of tags.
	 * 
	 * @return ArrayList of HashMaps representing a set of Picture objects
	 */
	public Collection<Picture> getPictureGallery(Context context) {
		return new PictureQueryGenerator(context).selectPicturesBySearch(picName, startDate,
				endDate, tags);
	}

	/**
	 * Creates a title to display in the Gallery Activity based on the type of
	 * TagsGallery.
	 * 
	 * @param Context
	 *            To be used in DB operations.
	 * @return String
	 */
	public String getTitle(Context context) {
		return "Search";
	}
}
