package com.cs301w01.meatload.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import com.cs301w01.meatload.model.Album;
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

	Context context;

	public MainManager() {
		// dbMan = new DBManager(context);
	}

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
		
		//TODO: BRING UP A LIST OF ALL ALBUMS TO TEST IN BELOW CODE (JOEL)
		ArrayList<Album> testList = getAllAlbums();
		Album testAlbum;	
		Iterator<Album> listIter = testList.iterator();
		
		
		albumName = albumName.trim();
		if(albumName.length() > MAX_ALBUM_NAME_LENGTH){
			albumName = albumName.substring(0,MAX_ALBUM_NAME_LENGTH);
		} else if (albumName.length() == 0){
			//handle empty album name
			return"";
		}

		
		while (listIter.hasNext()){
			testAlbum = listIter.next();
			if (testAlbum.getName().equals(albumName)){
				return "";
				//handle Album Name Already Exists
				//Test returned result with: if (result.length() == 0)
			}
		}
		
		new AlbumQueryGenerator(context).insertAlbum(albumName);
		
		return albumName;
		//length() == 0 if adding album failed, else returns name of added album truncated to max Album name # chars
	}

	public Album getAlbumByName(String albumName) {
		return new AlbumQueryGenerator(context).getAlbumByName(albumName);
	}
	
	public int getMaxAlbumName(){
		return MAX_ALBUM_NAME_LENGTH;
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
	
    public void errorDialog(String err, Activity a){
    	AlertDialog.Builder alert = new AlertDialog.Builder(a);
		alert.setTitle("Error");
		alert.setMessage(err);
		alert.show();
    }
}
