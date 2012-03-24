package com.cs301w01.meatload.model.querygenerators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.Picture;

public class AlbumQueryGenerator extends QueryGenerator {
	
	public AlbumQueryGenerator(Context context) {
		super(context);
	}

	//vars for albums table
    public static final String TABLE_NAME = "albums";
    
    private static final String CREATE_TABLE_QUERY =
        "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL_NAME + " TEXT);";

    /**
     * 
     * @param albumName
     * @param tags
     */
    public long insertAlbum(String albumName, Collection<String> tags) {

        ContentValues cv = new ContentValues();

        // Add album info to cv
        cv.put(COL_NAME, albumName);

        // Insert picture into picture tables
        long aid = db.insert(TABLE_NAME, COL_ID, cv);
        
        // Get newly inserted picture's id from pictures table
        //int aid = selectAlbumIDByName(albumName);
        
        Log.d(TABLE_NAME, "Album inserted: " + albumName + " w/ aid " + aid);

        // Insert picture's tags into tags table
        for(String tag : tags){
           
            ContentValues tcv = new ContentValues();
            
            cv.put(COL_NAME, tag);
            cv.put(COL_ALBUMID, aid);

            //insert tag tuple into tags table
            db.insert(TABLE_NAME, COL_ID, tcv);
            
            Log.d(TABLE_NAME, "Tag inserted: " + tag + " w/ pid: " + aid);
            
        }

        return aid;
    }
    
    public int selectAlbumIDByName (String name) {
        return selectIDByName(name, TABLE_NAME);
    }
    
    public void deleteAlbumByName(String name) {
    	deleteByID(selectIDByName(name, TABLE_NAME), TABLE_NAME);
    }
    
    public ArrayList<HashMap<String,String>> selectAllAlbums(){
    	
    	String albumQuery = "SELECT a." + COL_NAME + " AS " + COL_NAME + ", " + 
    							"COUNT(p." + COL_ID + ") AS numPictures" +
    						" FROM " + TABLE_NAME + 
    						" a LEFT OUTER JOIN " + PictureQueryGenerator.TABLE_NAME +
    						" p ON (a." + COL_ID + " = p." + COL_ALBUMID + ")" + 
    						" GROUP BY a." + COL_NAME;
    	
    	Cursor c = db.performRawQuery(albumQuery);
    	
    	ArrayList<HashMap<String,String>> albums = new ArrayList<HashMap<String,String>>();
    	if (c == null){
    		return albums;
    	}

        while(!c.isAfterLast()){
        	HashMap<String,String> map = new HashMap<String,String>();
            String albumName = c.getString(c.getColumnIndex(COL_NAME));
            String numPictures = c.getString(c.getColumnIndex("numPictures"));
            
            map.put("name", albumName);
            map.put("numPictures", numPictures);
            albums.add(map);

            c.moveToNext();
        }

        return albums;
    }
    
    /**
     * Use this to find the name of the album that a picture is contained in by passing the 
     * picture's id as an input parameter.
     * @param pictureID
     * @return albumName as a string
     */
    public String getAlbumNameOfPicture(int pictureID) {
        
        Cursor c = db.performRawQuery("SELECT a." + COL_NAME + " AS " + COL_NAME +
                                   " FROM " + PictureQueryGenerator.TABLE_NAME + " p LEFT JOIN " +
                                   TABLE_NAME + " a ON (a." + COL_ID + " = p." +
                                   COL_ALBUMID + ")" + 
                                   " WHERE p." + COL_ID + " = " +
                                   "'" + pictureID + "'");
        
        return c.getString(c.getColumnIndex(COL_NAME));
        
    }

    /**
     * Returns an album object based on the name of the album.
     * 
     * @param albumName of album
     * @return an album object
     */
    public Album getAlbumByName(String albumName) {

        Collection<Picture> pictures = new ArrayList<Picture>();
        
        Collection<Picture> hashPicture = new PictureQueryGenerator(context).selectPicturesFromAlbum(albumName);

        for(Picture picture : hashPicture) {
            
            int id = new PictureQueryGenerator(context).selectIDByName(picture.getName(),
                    PictureQueryGenerator.TABLE_NAME);
            String name = picture.getName();
            String path = picture.getPath();

            Date date = picture.getDate();

            Collection<String> tags = new TagQueryGenerator(context).selectPictureTags(id);
           
            Picture p = new Picture(name, path, albumName, date, tags);
            p.setID(id);
            
            pictures.add(p);
            
        }

        Album album = new Album(albumName, hashPicture.size(),pictures);

        return album;

    }
    
}
