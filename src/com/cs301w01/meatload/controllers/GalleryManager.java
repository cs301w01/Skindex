package com.cs301w01.meatload.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.DBManager;
import com.cs301w01.meatload.model.Photo;


/**
 * Created by IntelliJ IDEA.
 * User: Derek
 * Date: 3/3/12
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */


public class GalleryManager implements FController, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//DBManager dbMan;
	String albumName = null;
	Collection<String> tags;
	boolean isAlbum = false;
	
    public GalleryManager(Context context){
    	//dbMan = new DBManager(context);
    	tags = new ArrayList<String>();
    	isAlbum = false;
    }
    
    public GalleryManager(String albumName, Context context){
    	if(albumName.equals("All Photos"))
    	{
    		this.albumName = albumName;
        	isAlbum = true;
    	}
    	tags = new ArrayList<String>();
    	//dbMan = new DBManager(context);
    }
    
    public GalleryManager(Collection<String> tags, Context context){
    	this.tags = tags;
    	//dbMan = new DBManager(context);
    	isAlbum = false;
    }
    
    /**TODO
     * Should I make this store a file path too?
     * @param photo
     */
    public void storePhoto(Photo photo){
    	new DBManager().insertPhoto(photo);
    }
    
    public Photo getPhoto(String name){
    	return new DBManager().selectPhotoByName(name);
    }
    
    public Collection<Photo> getPhotoGallery(){
    	if(isAlbum)
    		return new DBManager().selectPhotosFromAlbum(albumName);
    	else if(tags.isEmpty())
    		return new DBManager().selectAllPhotos();
    	else
    		return new DBManager().selectPhotosByTag(tags);
    }
    
    public void deletePhoto(String name){
    	new DBManager().deletePhotoByName(name);
    }
    
    public void deleteAlbum(String name){
    	new DBManager().deleteAlbumByName(name);
    }
    
    public boolean isAlbum(){
    	return isAlbum;
    }

    public String getAlbumName(){
    	return albumName;
    }
    
    public String getTitle(){
    	if(isAlbum)
    		return albumName;
    	else if(tags.isEmpty())
    		return "All Photos";
    	else
    		return new DBManager().stringJoin(tags, ", ");
    }
}
