package com.cs301w01.meatload.controllers;

import java.util.Collection;
import java.util.Date;

import android.content.Context;

import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.AllPicturesGallery;
import com.cs301w01.meatload.model.GalleryData;
import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.TagQueryGenerator;

/**
 * Mediates between the GalleryActivity and the DBManager by creating HashMaps
 * of pictures to be displayed in the Gallery View.
 * <p>
 * Can be constructed using an album name as a String or a Collection of tags as
 * Strings.
 * 
 * @author Isaac Matichuk
 * @see SQLiteDBManager
 * @see com.cs301w01.meatload.activities.GalleryActivity
 */
public class GalleryManager implements FController {

	private Context context;
	private GalleryData gallery;
	
	/**
	 * Constructor, sets current DalleryData
	 * @param gallery
	 */
	public GalleryManager(GalleryData gallery) {
		this.gallery = gallery;
	}

	/**
	 * Sets the current context.  Must be set before any database
	 * calls are made or GalleryManager will error.
	 * @param context Current context for database.
	 */
	public void setContext(Context context) {
		this.context = context;
	}
	
	/**
	 * Stores a given photo into the database.
	 * @param picture Picture to be stored.
	 */
	public void storePhoto(Picture picture) {
		new PictureQueryGenerator(context).insertPicture(picture);
	}
	
	/**
	 * gets the gallery currently being used by this gallerymanager
	 * @return GalleryData
	 */
	public GalleryData getGallery() {
		return gallery;
	}
	
	/**
	 * Returns the title of the current GalleryManager, as to be displayed
	 * in the Gallery screen.
	 * @return String
	 */
	public String getTitle() {
		return gallery.getTitle(context);
	}
		
	/**
	 * Finds a Picture with the given pid in the database and returns it.
	 * @param pid ID of picture to be returned.
	 * @return
	 */
	public Picture getPhoto(int pid) {
		return new PictureQueryGenerator(context).selectPictureByID(pid);
	}

	/**
	 * Invokes the DBManager to return a set of Picture objects based on whether
	 * GalleryManager was contructed with an album name or a set of tags.
	 * 
	 * @return ArrayList of HashMaps representing a set of Picture objects
	 */
	public Collection<Picture> getPictureGallery() {
		return gallery.getPictureGallery(context);
	}
	
	/**
	 * In the database changes the name of an old album to a newly provided string.
	 * @param newAlbumName new name
	 * @param album The old album to be modified
	 */
	public void changeAlbumName(String newAlbumName, Album a) {
		AlbumQueryGenerator aG = new AlbumQueryGenerator(this.context);
		aG.updateAlbumName(a.getName(), newAlbumName);
	}

	/**
	 * Deletes the Picture with a given pid from the database.
	 * @param pid ID of Picture to be deleted.
	 */
	public void deletePicture(int pid) {
		new PictureQueryGenerator(context).deletePictureByID(pid);
	}
	
	/**
	 * Deletes the album specified by album id.  Will delete all associated photos.
	 * @param aid ID of album to delete.
	 */
	public void deleteAlbum(int aid) {
		new PictureQueryGenerator(context).deletePicturesFromAlbum(aid);
		new AlbumQueryGenerator(context).deleteAlbumByID(aid);
	}

	/**
	 * Determines whether the current Gallery Manager represents an album or is a 
	 * collection of related Pictures that are not in the same album.
	 * @return boolean
	 */
	public boolean isAlbum() {
		return gallery.isAlbum();
	}

	/**
	 * Deprecated, waiting for Isaac to delete.
	 * @return
	 */
	public boolean stillValid() {
		return gallery.stillValid();
	}
}
