package com.cs301w01.meatload.controllers;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;

import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.Tag;
import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.TagQueryGenerator;

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
    public ArrayList<Album> getAllAlbums() {

    	ArrayList<Album> albums = new ArrayList<Album>();

        Album allPics = new Album("All Pictures", new PictureQueryGenerator(context).getPictureCount(), -1);

    	albums.add(allPics);
    	albums.addAll(new AlbumQueryGenerator(context).selectAllAlbums());

    	return albums;
    }
    
    public ArrayList<Tag> getAllTags(){
    	return new TagQueryGenerator(context).selectAllTags();
    }
    
    public void addAlbum(String albumName, Collection<String> tags) {
    	new AlbumQueryGenerator(context).insertAlbum(albumName);
    }
}
