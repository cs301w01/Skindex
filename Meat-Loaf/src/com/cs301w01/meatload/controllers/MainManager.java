package com.cs301w01.meatload.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import android.content.Context;

import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;

/**
 * Mediates between DBManager and Skindex by providing lists of Albums and Tags.
 * @author Isaac Matichuk
 * @see SQLiteDBManager
 * @see Skindex
 */
public class MainManager implements FController {

	Context context;
	
    public MainManager() {
    	//dbMan = new DBManager(context);
    }
    
    public void setContext(Context context){
    	this.context = context;
    }
    
    /**
     * Creates and invokes an instance of the DBManager class to return HashMaps corresponding to
     * the table of albums in the database.  
     * @return All albums as ArrayList of HashMaps
     */
    public ArrayList<HashMap<String, String>> getAllAlbums() {

    	ArrayList<HashMap<String, String>> albums = new ArrayList<HashMap<String,String>>();
    	HashMap<String, String> map = new HashMap<String,String>();
    	map.put("name", "All Pictures");
    	map.put("numPictures", Integer.toString(new PictureQueryGenerator(context).getPictureCount()));
    	albums.add(map);
    	albums.addAll(new AlbumQueryGenerator(context).selectAllAlbums());
    	return albums;
    }
    
    /**
     * @deprecated
     * @return ArrayList of HashMaps containing tags as Strings
     */
    public ArrayList<HashMap<String, String>> getAllTags() {
    	return null;
    }
    
    public void addAlbum(String albumName, Collection<String> tags) {
    	new AlbumQueryGenerator(context).insertAlbum(albumName, tags);
    }
}
