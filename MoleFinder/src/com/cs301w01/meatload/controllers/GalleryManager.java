package com.cs301w01.meatload.controllers;

import java.util.Collection;

import android.content.Context;

import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.GalleryData;
import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;
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
 * @see com.cs301w01.meatload.activities.GalleryActivity
 */
public class GalleryManager implements FController {

	private Context context;
	private GalleryData gallery;
	
	/**
	 * Constructor, sets current GalleryData and initializes the context
	 * This GalleryData is used for querying the database for a gallery of pictures
	 * @param gallery
	 */
	public GalleryManager(Context context, GalleryData gallery) {
		setContext(context);
		this.gallery = gallery;
	}

	/**
	 * Sets the current GalleryManager's Context.  A context is necessary if
	 * the database is going to be used.
	 * The context is set upon creation of the manager object
	 * but this context is invalidated as soon as the user leaves the screen
	 * so every activity that stores a manager needs to update the context
	 * on resume
	 * @param Context
	 */
	public void setContext(Context context) {
		this.context = context;
	}
	
	/**
	 * gets the GalleryData object currently being used by this GalleryManager
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
	 * @return Picture object
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
	 * Deletes the album specified by album id.  Will delete all associated photos.
	 * @param aid ID of album to delete.
	 */
	public void deleteAlbum(int aid) {
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
}
