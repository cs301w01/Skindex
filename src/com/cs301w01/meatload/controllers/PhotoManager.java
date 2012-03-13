package com.cs301w01.meatload.controllers;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;

import com.cs301w01.meatload.model.DBManager;
import com.cs301w01.meatload.model.Photo;


/**
 * Created by IntelliJ IDEA.
 * User: Derek
 * Date: 3/3/12
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */


public class PhotoManager implements FController{

	DBManager dbMan;
	String albumName = null;
	Collection<String> tags;
	boolean isAlbum;
	
    public PhotoManager(Context context){
    	dbMan = new DBManager(context);
    	tags = new ArrayList<String>();
    }
    
    public PhotoManager(String albumName, Context context){
    	this.albumName = albumName;
    	dbMan = new DBManager(context);
    	isAlbum = true;
    }
    
    public PhotoManager(Collection<String> tags, Context context){
    	this.tags = tags;
    	dbMan = new DBManager(context);
    	isAlbum = false;
    }
    
    /**TODO
     * Should I make this store a file path too?
     * @param photo
     */
    public void storePhoto(Photo photo){
    	dbMan.insertPhoto(photo);
    }
    
    public Photo getPhoto(String name){
    	return dbMan.selectPhotoByName(name);
    }
    
    public Collection<Photo> getPhotoGallery(){
    	if(isAlbum)
    		return dbMan.selectPhotosFromAlbum(albumName);
    	else if(tags.isEmpty())
    		return dbMan.selectAllPhotos();
    	else
    		return dbMan.selectPhotosByTag(tags);
    }
    
    public void deletePhoto(String name){
    	dbMan.deletePhotoByName(name);
    }
    
    public void deleteAlbum(String name){
    	dbMan.deleteAlbumByName(name);
    }
    
    public void storeTag(){
    	
    }
    
    public void deleteTag(){
    	
    }
    
    /**When the user presses the new album button it should bring up a window
     * where they can enter data such as a name and tags. Then when they press
     * OK, this function should get called and is passed the info the user created.
     */
    private void addNewAlbum(String name, Collection<String> tags){
    	
    }
    
    /**TODO
     * Not sure how we're going to pass around DBManager... is it a singleton?
     * @return
     */
    public Collection<String> getAlbumNames(){
    	return dbMan.selectAllAlbums();
    }
    
    public boolean isAlbum(){
    	return isAlbum;
    }

    public String getAlbumName(){
    	return albumName;
    }
}
