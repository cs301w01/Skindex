package com.cs301w01.meatload.model;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import android.content.Context;

import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.TagQueryGenerator;

/**
 * Mediates between the GalleryActivity and the DBManager by creating HashMaps of pictures to be
 * displayed in the Gallery View.
 * <p>
 * Can be constructed using an album name as a String or a Collection of tags as Strings. 
 * @author Isaac Matichuk
 * @see SQLiteDBManager
 * @see com.cs301w01.meatload.activities.GalleryActivity
 */
public class AlbumGallery extends GalleryData implements Serializable {
    
	Album album;
	
	public AlbumGallery(Album album){
		this.album = album;
	}
	
    /**
     * Invokes the DBManager to return a set of Picture objects based on whether GalleryManager
     * was contructed with an album name or a set of tags. 
     * @return ArrayList of HashMaps representing a set of Picture objects
     */
    public Collection<Picture> getPictureGallery(Context context){
    	return new PictureQueryGenerator(context).selectPicturesFromAlbum(album.getName());
    }
    
    /**
     * Creates a title to display in the Gallery Activity based on the type
     * of Gallery
     */
    public String getTitle(){
    	return album.getName();
    }
    
    public boolean isAlbum() {
    	return true;
    }
    
    public String getName(){
    	return album.getName();
    }
    
    public Album getAlbum() {
    	return album;
    }
}
