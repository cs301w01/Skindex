package com.cs301w01.meatload.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.cs301w01.meatload.model.DBManager;
import com.cs301w01.meatload.model.Photo;

/**
 * Mediates between the GalleryActivity and the DBManager by creating HashMaps of pictures to be
 * displayed in the Gallery View.
 * <p>
 * Can be constructed using an album name as a String or a Collection of tags as Strings. 
 * @author Isaac Matichuk
 * @see DBManager
 * @see GalleryActivity
 */
public class GalleryManager implements FController, Serializable {

	private static final long serialVersionUID = 1L;
	//DBManager dbMan;
	String albumName = null;
	Collection<String> tags;
	boolean isAlbum = false;
	
    public GalleryManager() {
    	//dbMan = new DBManager(context);
    	tags = new ArrayList<String>();
    	isAlbum = false;
    }
    
    /**
     * Constructs the GalleryManager with an Album of Pictures (or All Pictures) as opposed to
     * Pictures with a set of tags.
     * @param albumName String representation of album name to be associated with this object
     */
    public GalleryManager(String albumName) {
    	if (albumName.equals("All Photos")) {
    		this.albumName = albumName;
        	isAlbum = true;
    	}
    	tags = new ArrayList<String>();
    	//dbMan = new DBManager(context);
    }
    
    /**
     * Constructs the GalleryManager with all Pictures with a set of Tags as opposed to with an
     * Album.
     * @param tags Collection of tags as Strings
     */
    public GalleryManager(Collection<String> tags) {
    	this.tags = tags;
    	//dbMan = new DBManager(context);
    	isAlbum = false;
    }
    
    public void storePhoto(Photo picture) {
    	new DBManager().insertPhoto(picture);
    }
    
    public Photo getPhoto(int pid) {
    	return new DBManager().selectPhotoByID(pid);
    }
    
    /**
     * Invokes the DBManager to return a set of Picture objects based on whether GalleryManager
     * was contructed with an album name or a set of tags. 
     * @return ArrayList of HashMaps representing a set of Picture objects
     */
    public ArrayList<HashMap<String, String>> getPhotoGallery() {
    	if (isAlbum())
    		return new DBManager().selectPhotosFromAlbum(albumName);
    	else if (tags.isEmpty())
    		return new DBManager().selectAllPhotos();
    	else
    		return new DBManager().selectPhotosByTag(tags);
    }
    
    public void deletePhoto(int pid) {
    	new DBManager().deletePhotoByID(pid);
    }
    
    public void deleteAlbum(String name) {
    	new DBManager().deleteAlbumByName(name);
    }
    
    public boolean isAlbum() {
    	return isAlbum;
    }

    public String getAlbumName() {
    	return albumName;
    }
    
    public String getTitle() {
    	if (isAlbum)
    		return albumName;
    	else if (tags.isEmpty())
    		return "All Photos";
    	else
    		return new DBManager().stringJoin(tags, ", ");
    }
}
