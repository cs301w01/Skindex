package com.cs301w01.meatload.model;

import java.io.Serializable;
import java.util.Collection;

import android.content.Context;

import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;

/**
 * Mediates between the GalleryActivity and the DBManager by creating HashMaps
 * of pictures to be displayed in the Gallery View.
 * <p>
 * Can be constructed using an album name as a String or a Collection of tags as
 * Strings.
 * 
 * @author Isaac Matichuk
 * @see SQLiteDBManager
 * @see GalleryActivity
 */
public class AllPicturesGallery extends GalleryData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ALL_PICTURES_ALBUM_NAME = "All Pictures";

	public AllPicturesGallery() {

	}

	/**
	 * Invokes the DBManager to return a set of Picture objects based on whether
	 * GalleryManager was contructed with an album name or a set of tags.
	 * 
	 * @return ArrayList of HashMaps representing a set of Picture objects
	 */
	public Collection<Picture> getPictureGallery(Context context) {
		return new PictureQueryGenerator(context).selectAllPictures();
	}

	/**
	 * Creates a title to display in the Gallery Activity based on the type of
	 * Gallery
	 */
	public String getTitle(Context context) {
		return ALL_PICTURES_ALBUM_NAME;
	}
}
