package com.cs301w01.meatload.model;
import java.io.Serializable;
import java.util.Collection;

import android.content.Context;

import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;

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
    
	private static final long serialVersionUID = 9038977214143165620L;
	int albumID;
	
	public AlbumGallery(Album album){
		this.albumID = (int) album.getID();
	}
	
    /**
     * Invokes the DBManager to return a set of Picture objects based on whether GalleryManager
     * was contructed with an album name or a set of tags. 
     * @return ArrayList of HashMaps representing a set of Picture objects
     */
    public Collection<Picture> getPictureGallery(Context context){
    	return new PictureQueryGenerator(context).selectPicturesFromAlbum(albumID);
    }
    
    /**
     * Creates a title to display in the Gallery Activity based on the type
     * of Gallery
     */
    public String getTitle(Context context){
    	return new AlbumQueryGenerator(context).getAlbumByID((long) albumID).getName();
    }
    
    /**
     * Returns a boolean specifying whether the current AlbumGallery represents a valid
     * album in the database.
     * @return boolean
     */
    public boolean isAlbum() {
    	return true;
    }
    
    /**
     * Returns the Album object represented by albumID in the current AlbumGallery.
     * @param context
     * @return Album
     */
    public Album getAlbum(Context context) {
    	return new AlbumQueryGenerator(context).getAlbumByID((long)albumID);
    }
    
    // TODO: (Isaac) Implement or get rid of stillValid method in AlbumGallery
    /**
     * @deprecated
     */
    public boolean stillValid() {
    	return true;
    }
}
