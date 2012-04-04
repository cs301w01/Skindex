package com.cs301w01.meatload.model;
import java.io.Serializable;
import java.util.Collection;

import android.content.Context;

import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.Picture;

/**
 * Mediates between the GalleryActivity and the DBManager by creating HashMaps of pictures to be
 * displayed in the Gallery View.
 * <p>
 * Can be constructed using an album name as a String or a Collection of tags as Strings. 
 * @author Isaac Matichuk
 * @see SQLiteDBManager
 * @see com.cs301w01.meatload.activities.GalleryActivity
 */
public abstract class GalleryData implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Invokes the DBManager to return a set of Picture objects based on whether GalleryManager
     * was contructed with an album name or a set of tags. 
     * @return ArrayList of HashMaps representing a set of Picture objects
     */
    public abstract Collection<Picture> getPictureGallery(Context context);
    
    /**
     * Creates a title to display in the Gallery Activity based on the type
     * of Gallery
     */
    public abstract String getTitle(Context context);
    
    /**
     * By default, a Gallery is not an album. But if the given gallery is
     * an album, it should override this function and return true
     * @return boolean of whether or not this object is an Album Gallery
     */
    public boolean isAlbum() {
    	return false;
    }
    
    // TODO: (Isaac) Implement or get rid of stillValid method in GalleryData
    public boolean stillValid() {
    	return true;
    }
}
