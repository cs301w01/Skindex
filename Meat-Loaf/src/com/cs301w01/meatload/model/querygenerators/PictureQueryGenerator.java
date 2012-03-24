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

import com.cs301w01.meatload.model.Picture;

public class PictureQueryGenerator extends QueryGenerator {

	//vars for pictures table
    public static final String TABLE_NAME = "pictures";
    public static final String PICTURES_COL_PATH = "path";
    public static final String PICTURES_COL_DATE = "date";
	
	private static final String CREATE_TABLE_QUERY =
        "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY, " +
                PICTURES_COL_DATE + " Date, " +
                PICTURES_COL_PATH + " TEXT, " +
                COL_NAME + " TEXT, " +
                COL_ALBUMID + " INTEGER, " +
                "FOREIGN KEY(" + COL_ALBUMID + ") REFERENCES " +
                AlbumQueryGenerator.getTableName() + "( " + COL_ID + "));";
	
	public PictureQueryGenerator(Context context) {
		super(context);

	}
	
	/**
     * Takes a Picture object and pushes it to the database.
     * @param p 		Picture object to be pushed to database
     * @param tableName Table storing the tuple to be updated
     * @param id 		ID value of 
     * @return
     */
    public int updatePictureByID(Picture p, String tableName, int id) {
        
        ContentValues cv = new ContentValues();

        //add picture info to cv
        cv.put(PICTURES_COL_DATE, dateToString(p.getDate()));
        cv.put(PICTURES_COL_PATH, p.getPath());
        cv.put(COL_NAME, p.getName());

        int uVal = db.update(tableName, cv, COL_ID + "=" + id, null);

        return uVal;
    }
    
    /**
     * Used to find the id of a picture in the pictures table for insertion into the albums and 
     * tags tables.
     * @param name
     * @return
     */
    private int selectPictureIDByName (String name) {
        return selectIDByName(name, TABLE_NAME);
    }
    
    /**
     * Inserts a picture into the pictures table and corresponding tags into the tag table.
     * @param p Picture object
     */
    public long insertPicture(Picture p) {

        ContentValues cv = new ContentValues();

        AlbumQueryGenerator albumGen = new AlbumQueryGenerator(this.context);
        int albumID = albumGen.selectAlbumIDByName(p.getAlbumName());
        
        //add picture info to cv
        cv.put(PICTURES_COL_DATE, dateToString(p.getDate()));
        cv.put(PICTURES_COL_PATH, p.getPath());
        cv.put(COL_ALBUMID, albumID);
        cv.put(COL_NAME, p.getName());
        Log.d(TABLE_NAME, "ALBUM NAME OF Picture: " + p.getAlbumName());

        //insert picture into picture tables
        long pid = db.insert(TABLE_NAME, COL_ID, cv);

        //insert picture's tags into tags table
        for(String tag : p.getTags()){
           
            ContentValues tcv = new ContentValues();
            
            cv.put(COL_NAME, tag);
            cv.put(COL_PICTUREID, pid);

            //insert tag tuple into tags table
            db.insert(TagQueryGenerator.getTableName(), COL_ID, tcv);
            
            Log.d(TABLE_NAME, "Tag inserted: " + tag + " w/ pid: " + pid);
            
        }

        return pid;
    }
    
    public int getPictureCount() {
    	
    	Cursor c = db.performRawQuery("SELECT COUNT(*) AS numPictures FROM " + TABLE_NAME);
    	
    	if (c == null) {
    		return 0;
    	}
    	
    	return new Integer(c.getString(c.getColumnIndex("numPictures")));
    }
    
    
    /**
    *
    * @param pictureQuery
    * @return
    */
   private ArrayList<HashMap<String,String>> selectPicturesByQuery(String pictureQuery) {
       
       Cursor c = db.performRawQuery(pictureQuery);
       ArrayList<HashMap<String,String>> pictures = new ArrayList<HashMap<String,String>>();
       
       if (c == null){
   		return pictures;
   	}

       while(!c.isAfterLast()) {
    	   
    	   AlbumQueryGenerator albumGen = new AlbumQueryGenerator(this.context);
    	   String albumName = albumGen.getAlbumNameOfPicture(c.getInt(c.getColumnIndex(COL_ID)));
    	   
           HashMap<String,String> map = new HashMap<String,String>();
           map.put("id", c.getString(c.getColumnIndex(COL_ID)));
           map.put("albumName", albumName);
           map.put("path", c.getString(c.getColumnIndex(PICTURES_COL_PATH)));
           map.put("date", c.getString(c.getColumnIndex(PICTURES_COL_DATE)));
           
           pictures.add(map);
           
           /*
           String photoName = c.getString(c.getColumnIndex(COL_NAME));
           String path = c.getString(c.getColumnIndex(PHOTOS_COL_PATH));
           String albumName = getAlbumNameOfPicture(c.getInt(c.getColumnIndex(COL_ID)));
           Date date = stringToDate(c.getString(c.getColumnIndex(PHOTOS_COL_DATE)));
                   
           Photo p = new Photo(photoName, path, albumName, date, 
           		selectPhotoTags(c.getInt(c.getColumnIndex(COL_ID))));
           
           photos.add(p);
           */

           c.moveToNext();
       }

       return pictures;
   }
   
   public ArrayList<HashMap<String,String>> selectAllPictures() {
   	
	   return selectPicturesByQuery("SELECT * FROM " + 
   								TABLE_NAME + 
   								" ORDER BY " + PICTURES_COL_DATE);
   }
   
   /**TODO: selectPhotoByName
    * add the getting album name to the query
    * @param pictureID
    * @return Picture object
    */
   public Picture selectPictureByID(int pictureID) {
			
		Cursor c = db.performRawQuery("SELECT * " +
							   		  "FROM " + TABLE_NAME +
							   		  " WHERE " + COL_ID + " = '" + pictureID + "'");
		
		if (c == null){
			return null;
		}
	
	   AlbumQueryGenerator albumGen = new AlbumQueryGenerator(this.context);
	   String albumName = albumGen.getAlbumNameOfPicture(c.getInt(c.getColumnIndex(COL_ID)));
	   	
	   String pictureName = c.getString(c.getColumnIndex(COL_NAME));
	   String path = c.getString(c.getColumnIndex(PICTURES_COL_PATH));
	   Date date = stringToDate(c.getString(c.getColumnIndex(PICTURES_COL_DATE)));
	    
	   TagQueryGenerator tagGen = new TagQueryGenerator(this.context);
	   Collection<String> tags = tagGen.selectPictureTags(c.getInt(c.getColumnIndex(COL_ID)));
	   
	   return new Picture(pictureName, path, albumName, date, tags);
	   
   }
   
   
   public void deletePictureByID(int pictureID) {
	   
   		deleteByID(pictureID, TABLE_NAME);
   		
   }
   
   // TODO: fix this function to work properly once the table set ups have been finalized
   /**
    * Gets all Pictures from an album from database.
    * @param albumName
    * @return
    */
   public ArrayList<HashMap<String, String>> selectPicturesFromAlbum(String albumName) {
		
	   AlbumQueryGenerator albumGen = new AlbumQueryGenerator(this.context);
	   
	   int albumID = albumGen.selectAlbumIDByName(albumName);
	   	    
		String query = "SELECT * FROM " + 
						TABLE_NAME + 
						" WHERE " + 
						COL_ALBUMID + " = " + "'" + albumID + "'" + 
						" ORDER BY " + PICTURES_COL_DATE;
		return selectPicturesByQuery(query);
   	
   }
   
// TODO: fix this function to work properly once the table set ups have been finalized
   /**
    * Gets all pictures with the given tags from the database.
    * @param tags A collection of Tags represented as Strings to be used in the query
    * @return ArrayList of HashMaps representing all Pictures with the given tag
    */
   public ArrayList<HashMap<String, String>> selectPicturesByTag(Collection<String> tags) {
   	String query = "SELECT p." + COL_NAME + " AS " + COL_NAME + ", p." + 
   					PICTURES_COL_PATH + " AS " + PICTURES_COL_PATH + ", p." + 
   					COL_ID + " AS " + COL_ID + ", p." + PICTURES_COL_DATE + " AS " + 
   					PICTURES_COL_DATE + " FROM " + 
   					TABLE_NAME + " p LEFT JOIN " + 
   					TagQueryGenerator.getTableName() + " t ON (p." + COL_ID + " = t." + COL_PICTUREID + ") " +
   					"WHERE ";
   	boolean loopedOnce = false;
   	for (String tag : tags) {
   		if (loopedOnce) {
   			query += " OR ";
   			loopedOnce = true;
   		}
   		query += "t." + COL_NAME + " = '" + tag + "'";
   	}
   	query += " GROUP BY p." + COL_ID +
   			" ORDER BY COUNT(*), " + PICTURES_COL_DATE;
   	return selectPicturesByQuery(query);
   	
   }

}
