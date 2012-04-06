package com.cs301w01.meatload.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.Tag;
import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.TagQueryGenerator;

/**
 * Mediates between DBManager and Skindex by providing lists of Albums and Tags.
 * 
 * @author Isaac Matichuk
 * @see SQLiteDBManager
 * @see Skindex
 */
public class MainManager implements FController {
	final private int MAX_ALBUM_NAME_LENGTH = 20;
	final private int MAX_TAG_NAME_LENGTH = 20;
	Context context;

	public MainManager(Context context) {
		setContext(context);
	}

	/**
	 * Sets the current GalleryManager's Context. A context is necessary if the
	 * database is going to be used. The context is set upon creation of the
	 * manager object but this context is invalidated as soon as the user leaves
	 * the screen so every activity that stores a manager needs to update the
	 * context on resume
	 * 
	 * @param Context
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * Creates and invokes an instance of the DBManager class to return HashMaps
	 * corresponding to the table of albums in the database.
	 * 
	 * @return All albums as ArrayList of HashMaps
	 */
	public ArrayList<Album> getAllAlbums() {
		return new AlbumQueryGenerator(context).selectAllAlbums();
	}

	public int getPictureCount() {
		return new PictureQueryGenerator(context).getPictureCount();
	}

	public ArrayList<Tag> getAllTags() {
		return new TagQueryGenerator(context).selectAllTags();
	}

	public String addAlbum(String albumName, Collection<String> tags) {

		albumName = albumName.trim();
		if (albumName.length() > MAX_ALBUM_NAME_LENGTH) {
			albumName = albumName.substring(0, MAX_ALBUM_NAME_LENGTH);
		} else if (albumName.length() == 0) {
			// handle empty album name
			return "";
		}

		if (albumExists(albumName)) {
			// album name already exists
			return "";
		}

		new AlbumQueryGenerator(context).insertAlbum(albumName);
		return albumName;
		// length() == 0 if adding album failed, else returns name of added
		// album truncated to max Album name # chars
	}

	public Album getAlbumByName(String albumName) {
		return new AlbumQueryGenerator(context).getAlbumByName(albumName);
	}

	public int getMaxAlbumName() {
		return MAX_ALBUM_NAME_LENGTH;
	}

	public int getMaxTagName() {
		return MAX_TAG_NAME_LENGTH;
	}

	/**
	 * Takes an ArrayList of albums and returns an array containing the names of
	 * all the albums
	 * 
	 * @param albums
	 *            an ArrayList of albums to be converted
	 * @return Returns an array of strings of all the names of the albums given
	 */
	public String[] albumsToStrings(ArrayList<Album> albums) {
		String albumNames[] = new String[albums.size()];
		int i = 0;
		for (Album currAlbum : albums) {
			albumNames[i] = currAlbum.getName();
			i++;
		}
		return albumNames;
	}

	public boolean albumExists(String name) {
		ArrayList<Album> testList = getAllAlbums();
		Album testAlbum;
		Iterator<Album> listIter = testList.iterator();

		name = name.trim();

		if (name.length() > MAX_ALBUM_NAME_LENGTH) {
			name = name.substring(0, MAX_ALBUM_NAME_LENGTH);
		}

		while (listIter.hasNext()) {
			testAlbum = listIter.next();
			if (testAlbum.getName().equals(name)) {
				return true;
			}
		}

		return false;
	}
}
