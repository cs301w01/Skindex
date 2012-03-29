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

	public GalleryManager(GalleryData gallery) {
		this.gallery = gallery;
	}

	/**
	 * gets the gallery currently being used by this gallerymanager
	 * 
	 * @return GalleryData
	 */
	public GalleryData getGallery() {
		return gallery;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void storePhoto(Picture picture) {
		new PictureQueryGenerator(context).insertPicture(picture);
	}

	public Picture getPhoto(int pid) {
		return new PictureQueryGenerator(context).selectPictureByID(pid);
	}

	public void changeAlbumName(String newAlbumName, Album a) {
		AlbumQueryGenerator aG = new AlbumQueryGenerator(this.context);
		aG.updateAlbumName(a.getName(), newAlbumName);
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

	public void deletePicture(int pid) {
		new PictureQueryGenerator(context).deletePictureByID(pid);
	}

	public void deleteAlbum(String name) {
		new AlbumQueryGenerator(context).deleteAlbumByName(name);
	}

	public boolean isAlbum() {
		return gallery.isAlbum();
	}

	public String getTitle() {
		return gallery.getTitle();
	}
}
