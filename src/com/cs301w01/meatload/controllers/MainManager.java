package com.cs301w01.meatload.controllers;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;

import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.DBManager;
import com.cs301w01.meatload.model.Photo;
import com.cs301w01.meatload.model.Tag;


/**
 * Created by IntelliJ IDEA.
 * User: Derek
 * Date: 3/3/12
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */


public class MainManager implements FController{

	DBManager dbMan;
	
    public MainManager(Context context){
    	dbMan = new DBManager(context);
    }
    
    public Collection<Album> getAllAlbums(){
    	Collection<Album> albums = new ArrayList<Album>();
    	albums.add(new Album("All Photos", dbMan.getTotalPhotos()));
    	albums.addAll(dbMan.selectAllAlbums());
    	return albums;
    }
    
    public Collection<Tag> getAllTags(){
    	return dbMan.selectAllTags();
    }
}
