package com.cs301w01.meatload.controllers;

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
public class GalleryManager implements FController {

	private Context context;
	private Album album;
	private boolean isAlbum = true;
    
    public static final String ALL_PICTURES_ALBUM_NAME = "ALL PICTURES";
	
    public GalleryManager(Context context) {

    	isAlbum = false;

        this.context = context;
    }
    
    /**
     * Constructs the GalleryManager with an Album of Pictures (or All Pictures) as opposed to
     * Pictures with a set of tags.
     * @param album String representation of album name to be associated with this object
     */
    public GalleryManager(Album album, Context context) {

        this.album = album;

        if (album.getName().equals(ALL_PICTURES_ALBUM_NAME)) {
        	isAlbum = false;
    	}

        this.context = context;

    }
    
    /**
     * Constructs the GalleryManager with all Pictures with a set of Tags as opposed to with an
     * Album.
     * @param tags Collection of tags as Strings
     */
    public GalleryManager(Collection<String> tags, Context context) {
    	this.album = createTagAlbum(tags);
    	isAlbum = false;

        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    
    public void storePhoto(Picture picture) {
    	new PictureQueryGenerator(context).insertPicture(picture);
    }
    
    public Picture getPhoto(int pid) {
    	return new PictureQueryGenerator(context).selectPictureByID(pid);
    }
    
    public void changeAlbumName(String newAlbumName, Album a) {
        AlbumQueryGenerator aG = new AlbumQueryGenerator(this.context);
        aG.updateAlbumName(a.getName(), newAlbumName);
    }
    
    /**
     * Invokes the DBManager to return a set of Picture objects based on whether GalleryManager
     * was contructed with an album name or a set of tags. 
     * @return ArrayList of HashMaps representing a set of Picture objects
     */
    public Collection<Picture> getPictureGallery() {
    	if (isAlbum)
    		return new PictureQueryGenerator(context).selectPicturesFromAlbum(album.getName());
    	else if (album.getName().equals(ALL_PICTURES_ALBUM_NAME))
    		return new PictureQueryGenerator(context).selectAllPictures();
    	else
    		return null;
    		// TODO: I removed the following code.  Now that album tags do not have tags
    		// this should never be the case, correct?  I am leaving it in a mess here
    		// because I would like someone to confirm this behaviour.
    		//new PictureQueryGenerator(context).selectPicturesByTag(album.getAlbumTags());
    }
    
    public void deletePicture(int pid) {
    	new PictureQueryGenerator(context).deletePictureByID(pid);
    }
    
    public void deleteAlbum(String name) {
    	new AlbumQueryGenerator(context).deleteAlbumByName(name);
    }
    
    public boolean isAlbum() {
    	return isAlbum;
    }

    public String getAlbumName() {
    	return album.getName();
    }
    
    public Date getDate() {
    	return album.getDate();
    }
    
    public String getTitle() {
    	
    	if (isAlbum)
    		return album.getName();
    	
    	else if (album.getName().equals(ALL_PICTURES_ALBUM_NAME))
    		return ALL_PICTURES_ALBUM_NAME;
    	
    	else
    		return null;
    	// I am removing the following code as it is no longer needed I think.
    	// Joel was here.
    	//new TagQueryGenerator(context).stringJoin(album.getAlbumTags(), ", ");
    }

    /**
     * Method for creating a temporary album for the gallery manager.
     *
     * @param tags, Collection of strings representing selected tags
     */
    private Album createTagAlbum(Collection<String> tags) {
    	PictureQueryGenerator pictureQueryGenerator = new PictureQueryGenerator(context);
        Collection<Picture> pictures = pictureQueryGenerator.selectPicturesByTag(tags);

        return new Album("Tag Album", pictures.size(), pictures, -1);
    }
    
    public Album getAlbum() {
        return album;
    }

    public void updateAlbum() {
        long albumId = album.getID();
        this.album = new AlbumQueryGenerator(context).getAlbumByID(albumId);
    }
}
