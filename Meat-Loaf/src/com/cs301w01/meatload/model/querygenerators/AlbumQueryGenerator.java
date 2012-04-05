package com.cs301w01.meatload.model.querygenerators;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.cs301w01.meatload.model.Album;

/**
 * Creates queries dealing with albums in the database.
 * <p>
 * Converts values from the database to usable data by the rest of the application.
 * @author Derek Dowling
 */
public class AlbumQueryGenerator extends QueryGenerator {
	
	//vars for albums table
    public static final String TABLE_NAME = "albums";
	public static final String ALBUMS_COL_DATE = "date";
    public static final String CREATE_TABLE_QUERY =
        						"CREATE TABLE " + TABLE_NAME + 
        						" (" + COL_ID + " INTEGER PRIMARY KEY, " +
								ALBUMS_COL_DATE + " Date, " + 
        						COL_NAME + " TEXT);";

	/**
	 * Constructor, creates an AlbumQueryGenerator with the given Context
	 * @param context Used for DB
	 */
	public AlbumQueryGenerator(Context context) {
		super(context);
	}
	
	
    
    /**
     * Creates a new album in the database with an albumName and a Collection of tags.
     * <p>
     * Does not insert any pictures into the newly created album.
     * @param albumName
     * @param tags
     */
    public long insertAlbum(String albumName) {

        ContentValues cv = new ContentValues();

        // Add album info to cv
        cv.put(COL_NAME, albumName);
        cv.put(ALBUMS_COL_DATE, dateToString(Calendar.getInstance().getTime()));

        // Insert picture into picture tables
        long aid = db.insert(TABLE_NAME, COL_ID, cv);
        
        // Get newly inserted picture's id from pictures table
        //int aid = selectAlbumIDByName(albumName);
        
        Log.d(TABLE_NAME, "Album inserted: " + albumName + " w/ aid " + aid);

        return aid;
    }
    
    /**
     * Returns the Album ID of the album in the database with the given Name.
     * @param name The Album Name to find in the DB.
     * @return int
     */
    public int selectAlbumIDByName (String name) {
        return selectIDByName(name, TABLE_NAME);
    }
    
    /**
     * Deletes the album with the given name in the database.
     * @param name Name of the album to be deleted.
     */
    public void deleteAlbumByName(String name) {
    	deleteAlbumByID(selectIDByName(name, TABLE_NAME));
    }
    
    /**
     * Deletes the album with the given ID in the database.
     * @param ID The ID of the Album to delete.
     */
    public void deleteAlbumByID(int albumID) {
    	new PictureQueryGenerator(this.context).deletePicturesFromAlbum(albumID);
    	deleteByID(albumID, TABLE_NAME);
    }
    
    public ArrayList<Album> selectAllAlbums() {
    	
    	String albumQuery = "SELECT a." + COL_ID + " AS " + COL_ID + ", " + 
    						"a." + COL_NAME + " AS " + COL_NAME + ", " + 
	    					"a." + ALBUMS_COL_DATE + " AS " + ALBUMS_COL_DATE + ", " +
    						"COUNT(p." + COL_ID + ") AS numPictures" +
    						" FROM " + TABLE_NAME + 
    						" a LEFT OUTER JOIN " + PictureQueryGenerator.TABLE_NAME +
    						" p ON (a." + COL_ID + " = p." + COL_ALBUMID + ")" + 
    						" GROUP BY a." + COL_NAME;
    	
    	return selectAlbumsByQuery(albumQuery);
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
        
        String aName = c.getString(c.getColumnIndex(COL_NAME));
        
        c.close();
        
        return aName;
        
    }
    
    /**
     * Opens the album with the old name in the database and updates the name
     * to the new one provided.
     * @param oldAlbumName
     * @param newAlbumName
     */
    public void updateAlbumName(String oldAlbumName, String newAlbumName) {

        int aID = selectAlbumIDByName(oldAlbumName);

        String query = "UPDATE " + TABLE_NAME + " " +
                       "SET " + COL_NAME + " = '" + newAlbumName + "' " +
                       "WHERE " + COL_ID + " = '" + aID +"'";
        
        db.performRawQuery(query).close();
        Log.d(TABLE_NAME, "Updated name of Album: " + query);
        
        setAlbumModified((long) aID);
        
    }

    /**
     * Returns an album object based on the name of the album.
     * 
     * @param albumName of album
     * @return Album
     */
    public Album getAlbumByName(String albumName) {
    	
	    String albumQuery = "SELECT a." + COL_ID + " AS " + COL_ID + ", " + 
	    					"a." + COL_NAME + " AS " + COL_NAME + ", " + 
	    					"a." + ALBUMS_COL_DATE + " AS " + ALBUMS_COL_DATE + ", " +
							"COUNT(p." + COL_ID + ") AS numPictures" +
							" FROM " + TABLE_NAME + 
							" a LEFT OUTER JOIN " + PictureQueryGenerator.TABLE_NAME +
							" p ON (a." + COL_ID + " = p." + COL_ALBUMID + ")" + 
							" GROUP BY a." + COL_NAME + 
							" HAVING a." + COL_NAME + " = '" + albumName + "'";
	    
	    Cursor c = db.performRawQuery(albumQuery);

    	if (c == null) {
    		return null;
    	}
    	String id = c.getString(c.getColumnIndex(COL_ID));
    	Date date = stringToDate(c.getString(c.getColumnIndex(ALBUMS_COL_DATE)));
        String numPictures = c.getString(c.getColumnIndex("numPictures"));

        c.close();
        
        return new Album(albumName, Integer.parseInt(numPictures), Long.parseLong(id), date);

    }

    /**
     * Finds an Album in the database with the given ID and returns it.
     * @param albumId The desired Album's ID.
     * @return Album
     */
    public Album getAlbumByID(long albumId) {
    	
    	String albumQuery = "SELECT a." + COL_ID + " AS " + COL_ID + ", " + 
							"a." + COL_NAME + " AS " + COL_NAME + ", " + 
	    					"a." + ALBUMS_COL_DATE + " AS " + ALBUMS_COL_DATE + ", " +
							"COUNT(p." + COL_ID + ") AS numPictures" +
							" FROM " + TABLE_NAME + 
							" a LEFT OUTER JOIN " + PictureQueryGenerator.TABLE_NAME +
							" p ON (a." + COL_ID + " = p." + COL_ALBUMID + ")" + 
							" GROUP BY a." + COL_NAME + 
							" HAVING a." + COL_ID + " = '" + albumId + "'";

		Cursor c = db.performRawQuery(albumQuery);
		
		if (c == null) {
			return null;
		}
		String albumName = c.getString(c.getColumnIndex(COL_NAME));
    	Date date = stringToDate(c.getString(c.getColumnIndex(ALBUMS_COL_DATE)));
		String numPictures = c.getString(c.getColumnIndex("numPictures"));
		
		c.close();
		
		return new Album(albumName, Integer.parseInt(numPictures), albumId, date);

    }

    public String getAlbumNameById(long albumId) {
        
        String query = "SELECT " + COL_NAME + " " +
                       "FROM " + TABLE_NAME + " " +
                       "WHERE " + COL_ID + " = '" + albumId + "'";
        
        Cursor c = db.performRawQuery(query);
        Log.d(TABLE_NAME, "Fetching Album Name for: " + query);
               
        String aName = c.getString(c.getColumnIndex(COL_NAME));

        c.close();
        
        return aName;

    }
    
    public void setAlbumModified(long albumId) {
    	String date = dateToString(Calendar.getInstance().getTime());
    	String query = "UPDATE " + TABLE_NAME + " " +
        				"SET " + ALBUMS_COL_DATE + " = '" + date + "' " +
        				"WHERE " + COL_ID + " = '" + albumId +"'";

    	db.performRawQuery(query).close();
    	Log.d(TABLE_NAME, "Updated date modified of Album: " + query);
    }
    
    private ArrayList<Album>selectAlbumsByQuery(String albumQuery){
    	Cursor c = db.performRawQuery(albumQuery);
    	
    	ArrayList<Album> albums = new ArrayList<Album>();

    	if (c == null) {
    		return albums;
    	}

        while(!c.isAfterLast()) {
        	String id = c.getString(c.getColumnIndex(COL_ID));
            String albumName = c.getString(c.getColumnIndex(COL_NAME));
        	Date date = stringToDate(c.getString(c.getColumnIndex(ALBUMS_COL_DATE)));
            String numPictures = c.getString(c.getColumnIndex("numPictures"));

            Album a = new Album(albumName, Integer.parseInt(numPictures), Long.parseLong(id), date);

            albums.add(a);

            c.moveToNext();
        }

        c.close();
        
        return albums;
    }
}
