package com.cs301w01.meatload.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.cs301w01.meatload.model.Photo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * This class is helper for dealing with SQLite in Android. It provides a variety of useful methods 
 * for creating, updating, deleting, and selecting data. 
 * @see <a href=http://www.codeproject.com/Articles/119293/Using-SQLite-Database-with-Android>
 * http://www.codeproject.com/Articles/119293/Using-SQLite-Database-with-Android
 * </a>
 */
public class DBManager extends SQLiteOpenHelper implements Serializable {

	private static final long serialVersionUID = 1L;
	//private SQLiteDatabase db;
    private static String logTag = "DBMANAGER";
    private static final String DB_NAME = "skindexDB";

    //common db vars
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_PHOTOID = "photoID";
    public static final String COL_ALBUMID = "albumID";
    
    //vars for photos table
    public static final String TABLE_NAME_PHOTOS = "photos";
    public static final String PHOTOS_COL_PATH = "path";
    public static final String PHOTOS_COL_DATE = "date";
    
    //vars for tags table
    public static final String TABLE_NAME_TAGS = "tags";
    
    //vars for albums table
    public static final String TABLE_NAME_ALBUMS = "albums";
    
    //vars for album tags table
    public static final String TABLE_NAME_ALBUMTAGS = "albumTags";

    private static final int DATABASE_VERSION = 2;
    
    private static final String CREATE_TABLE_PHOTOSTABLE =
            "CREATE TABLE " + TABLE_NAME_PHOTOS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    PHOTOS_COL_DATE + " Date, " +
                    PHOTOS_COL_PATH + " TEXT, " +
                    COL_NAME + " TEXT, " +
                    COL_ALBUMID + " INTEGER, " +
                    "FOREIGN KEY(" + COL_ALBUMID + ") REFERENCES " +
                    TABLE_NAME_ALBUMS + "( " + COL_ID + "));";
    
    private static final String CREATE_TABLE_TAGSTABLE =
            "CREATE TABLE " + TABLE_NAME_TAGS + " (" +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL_PHOTOID + " INTEGER, " +
                COL_NAME + " TEXT, " +
                "FOREIGN KEY(" + COL_PHOTOID + ") REFERENCES " +
                                                    TABLE_NAME_PHOTOS + "( " + COL_ID + "));";

    private static final String CREATE_TABLE_ALBUMSTABLE =
            "CREATE TABLE " + TABLE_NAME_ALBUMS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    COL_NAME + " TEXT);";
    
    private static final String CREATE_TABLE_ALBUMTAGSTABLE =
        "CREATE TABLE " + TABLE_NAME_ALBUMTAGS + " (" +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_ALBUMID + " INTEGER, " +
            COL_NAME + " TEXT, " +
            "FOREIGN KEY(" + COL_ALBUMID + ") REFERENCES " +
            										TABLE_NAME_ALBUMS + "( " + COL_ID + "));";
    
    private static Context myContext;


    public DBManager(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        myContext = context;
    }
    
    public DBManager() {
    	super(myContext, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase newDB) {

        createTables(newDB);

    }

    private void createTables(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_PHOTOSTABLE);
        Log.d(logTag, TABLE_NAME_PHOTOS + " generated.");

        db.execSQL(CREATE_TABLE_ALBUMSTABLE);
        Log.d(logTag, TABLE_NAME_ALBUMS + " generated.");

        db.execSQL(CREATE_TABLE_TAGSTABLE);
        Log.d(logTag, TABLE_NAME_TAGS + " generated.");
        
        db.execSQL(CREATE_TABLE_ALBUMTAGSTABLE);
        Log.d(logTag, TABLE_NAME_ALBUMTAGS + " generated.");

        Log.d(logTag, "DB generated.");

    }

    private void dropTables(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PHOTOS);
        Log.d(logTag, TABLE_NAME_PHOTOS + " dropped.");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ALBUMS);
        Log.d(logTag, TABLE_NAME_ALBUMS + " dropped.");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TAGS);
        Log.d(logTag, TABLE_NAME_TAGS + " dropped.");
        
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ALBUMTAGS);
        Log.d(logTag, TABLE_NAME_ALBUMTAGS + " dropped.");

        Log.d(logTag, "DB generated.");
        
    }
    
    /**
     * Used to force changes to update in the sql table during testing
     */
    public void resetDB() {

        SQLiteDatabase db = this.getWritableDatabase();

        dropTables(db);
        createTables(db);
        
        Log.d(logTag, "TABLES RESET.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        dropTables(sqLiteDatabase);
        onCreate(sqLiteDatabase);

    }

    /**
     * This method is used to select rows from a table where a specific id is known. Returns a 
     * cursor that is preset to the first tuple.
     * @param selectColumns array of columns you wish to have returned
     * @param tableName table name
     * @param idValue
     * @return
     */
    private Cursor selectRowByID(String[] selectColumns, String tableName, String idValue) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor =
                db.query(true, tableName, selectColumns, COL_ID + " = " + idValue, 
                		null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        mCursor.moveToFirst();

        return mCursor;

    }

    /**
     * Use this method if you want to do a "SELECT *" style query on a table for a specific ID
     * @param tableName Name of table
     * @param id Row in table
     * @return
     */
    private Cursor selectAllColsByID(String tableName, long id) {

        //build the query
        String selectionQuery = "SELECT * FROM " + tableName + " WHERE " + DBManager.COL_ID +
                " = '" + id + "'";

        return performRawQuery(selectionQuery);

    }

    /**
     * Removes a tuple based on its id value.
     * @param id Tuple to delete
     * @param tableName
     */
    private void deleteByID(long id, String tableName) {

        SQLiteDatabase db = this.getWritableDatabase();

        String dQuery = "DELETE FROM " + tableName + " WHERE " + COL_ID  + " = '" + id + "'";

        Log.d(logTag, "Performing delete: " + dQuery);

        db.execSQL(dQuery);

        db.close();

    }

    /**
     * Takes a Picture object and pushes it to the database.
     * @param p 		Picture object to be pushed to database
     * @param tableName Table storing the tuple to be updated
     * @param id 		ID value of 
     * @return
     */
    public int updatePhotoByID(Photo p, String tableName, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //add photo info to cv
        cv.put(PHOTOS_COL_DATE, dateToString(p.getDate()));
        cv.put(PHOTOS_COL_PATH, p.getPath());
        cv.put(COL_NAME, p.getName());

        int uVal = db.update(tableName, cv, COL_ID + "=" + id, null);

        db.close();

        return uVal;
    }
    
    /**
     * Returns the int value corresponding to the tuple ID by the tuple name.
     * @param name
     * @param tableName
     * @return
     * @link selectAlbumIDByName
     */
    private int selectIDByName(String name, String tableName) {
    	//query to execute
        String select = "SELECT " + COL_ID + 
                        " FROM " + tableName + 
                        " WHERE " + COL_NAME + 
                        " = '" + name + "'";
        
        //execute query and get cursor
        Cursor c = performRawQuery(select);
        
        //return id of photo
        return c.getInt(c.getColumnIndex(COL_ID));
    }

    /**
     * Used to find the id of a photo in the photos table for insertion into the albums and tags tables.
     * @param name
     * @return
     */
    private int selectPhotoIDByName (String name) {
        return selectIDByName(name, TABLE_NAME_PHOTOS);
    }
    
    private int selectAlbumIDByName (String name) {
        return selectIDByName(name, TABLE_NAME_ALBUMS);
    }
    

    /**
     * Inserts a picture into the photos table and corresponding tags into the tag table.
     * @param p Picture object
     */
    public void insertPhoto(Photo p) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //add photo info to cv
        cv.put(PHOTOS_COL_DATE, dateToString(p.getDate()));
        cv.put(PHOTOS_COL_PATH, p.getPath());
        cv.put(COL_ALBUMID, selectAlbumIDByName(p.getAlbumName()));
        cv.put(COL_NAME, p.getName());

        //insert photo into photo tables
        db.insert(TABLE_NAME_PHOTOS, COL_ID, cv);

        //get newly inserted photo's id from photos table
        int pid = selectPhotoIDByName(p.getName());

        //insert photo's tags into tags table
        for(String tag : p.getTags()){
           
            ContentValues tcv = new ContentValues();
            
            cv.put(COL_NAME, tag);
            cv.put(COL_PHOTOID, pid);

            //insert tag tuple into tags table
            db.insert(TABLE_NAME_TAGS, COL_ID, tcv);
            
            Log.d(logTag, "Tag inserted: " + tag + " w/ pid: " + pid);
            
        }

        db.close();

    }
    
    /**
     * 
     * @param albumName
     * @param tags
     */
    public void insertAlbum(String albumName, Collection<String> tags) {
    	SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Add album info to cv
        cv.put(COL_NAME, albumName);

        // Insert photo into photo tables
        db.insert(TABLE_NAME_ALBUMS, COL_ID, cv);
        
        // Get newly inserted photo's id from photos table
        int aid = selectAlbumIDByName(albumName);
        
        Log.d(logTag, "Album inserted: " + albumName + " w/ aid " + aid);

        // Insert photo's tags into tags table
        for(String tag : tags){
           
            ContentValues tcv = new ContentValues();
            
            cv.put(COL_NAME, tag);
            cv.put(COL_ALBUMID, aid);

            //insert tag tuple into tags table
            db.insert(TABLE_NAME_ALBUMTAGS, COL_ID, tcv);
            
            Log.d(logTag, "Tag inserted: " + tag + " w/ pid: " + aid);
            
        }

        db.close();
    }
    
    public int getTotalPhotos() {
    	Cursor c = performRawQuery("SELECT COUNT(*) AS numPhotos FROM " + TABLE_NAME_PHOTOS);
    	
    	if (c == null) {
    		return 0;
    	}
    	
    	return new Integer(c.getString(c.getColumnIndex("numPhotos")));
    }
    
    private Collection<String> selectTagsByQuery(String tagQuery) {
    	Cursor c = performRawQuery(tagQuery);
    	Collection<String> tags = new ArrayList<String>();
    	
    	if (c == null) {
    		return tags;
    	}

    	while(!c.isAfterLast()) {
    		tags.add(c.getString(c.getColumnIndex(COL_NAME)));
    		c.moveToNext();
    	}

    	return tags;
    }

    public Collection<Tag> selectAllTags() {
    	
    	String tagQuery = "SELECT t." + COL_NAME + " AS " + COL_NAME + ", COUNT(*) AS numPhotos" +
    						" FROM " + TABLE_NAME_TAGS + 
    						" t LEFT JOIN " + TABLE_NAME_PHOTOS +
    						" p ON (t." + COL_PHOTOID + " = p." + COL_ID + ")" + 
    						" GROUP BY t." + COL_NAME;
    	
    	Cursor c = performRawQuery(tagQuery);
    	
    	Collection<Tag> tags = new ArrayList<Tag>();
    	
    	if (c == null) {
    		return tags;
    	}

        while(!c.isAfterLast()) {
            
            String albumName = c.getString(c.getColumnIndex(COL_NAME));
            int numPhotos = new Integer(c.getString(c.getColumnIndex("numPhotos")));
            
            tags.add(new Tag(albumName, numPhotos));

            c.moveToNext();
        }

        return tags;
    }

    public Collection<String> selectPhotoTags(int photoID) {
        
        String getTags = "SELECT " + COL_NAME +
        					" FROM " + TABLE_NAME_TAGS +
        					" WHERE " + COL_PHOTOID + " = '" + photoID + "'";
        
        return selectTagsByQuery(getTags);
        
    }
    
    private ArrayList<HashMap<String,String>> selectPhotosByQuery(String photoQuery) {
        
        Cursor c = performRawQuery(photoQuery);
        ArrayList<HashMap<String,String>> photos = new ArrayList<HashMap<String,String>>();
        
        if (c == null){
    		return photos;
    	}

        while(!c.isAfterLast()) {
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("id", c.getString(c.getColumnIndex(COL_ID)));
            map.put("albumName", getAlbumNameOfPhoto(c.getInt(c.getColumnIndex(COL_ID))));
            map.put("path", c.getString(c.getColumnIndex(PHOTOS_COL_PATH)));
            map.put("date", c.getString(c.getColumnIndex(PHOTOS_COL_DATE)));
            
            photos.add(map);
            
            /*
            String photoName = c.getString(c.getColumnIndex(COL_NAME));
            String path = c.getString(c.getColumnIndex(PHOTOS_COL_PATH));
            String albumName = getAlbumNameOfPhoto(c.getInt(c.getColumnIndex(COL_ID)));
            Date date = stringToDate(c.getString(c.getColumnIndex(PHOTOS_COL_DATE)));
                    
            Photo p = new Photo(photoName, path, albumName, date, 
            		selectPhotoTags(c.getInt(c.getColumnIndex(COL_ID))));
            
            photos.add(p);
            */

            c.moveToNext();
        }

        return photos;
    }
    
    public ArrayList<HashMap<String,String>> selectAllPhotos() {
    	return selectPhotosByQuery("SELECT * FROM " + 
    								TABLE_NAME_PHOTOS + 
    								" ORDER BY " + PHOTOS_COL_DATE);
    }
    
    /**TODO: selectPhotoByName
     * add the getting album name to the query
     * @param name
     * @return
     */
    public Photo selectPhotoByID(int photoID) {
    	Cursor c = performRawQuery("SELECT * FROM " +
									TABLE_NAME_PHOTOS +
									" WHERE " + COL_ID + " = '" + photoID + "'");
    	
    	if (c == null){
    		return null;
    	}
    	

        String photoName = c.getString(c.getColumnIndex(COL_NAME));
        String path = c.getString(c.getColumnIndex(PHOTOS_COL_PATH));
        String albumName = getAlbumNameOfPhoto(c.getInt(c.getColumnIndex(COL_ID)));
        Date date = stringToDate(c.getString(c.getColumnIndex(PHOTOS_COL_DATE)));
                
        return new Photo(photoName, path, albumName, date, selectPhotoTags(c.getInt(c.getColumnIndex(COL_ID))));
    }
    
    public void deletePhotoByID(int photoID) {
    	deleteByID(photoID, TABLE_NAME_PHOTOS);
    }
    
    public void deleteAlbumByName(String name) {
    	deleteByID(selectIDByName(name, TABLE_NAME_ALBUMS), TABLE_NAME_ALBUMS);
    }
    
    public ArrayList<HashMap<String,String>> selectAllAlbums(){
    	
    	String albumQuery = "SELECT a." + COL_NAME + " AS " + COL_NAME + ", COUNT(*) AS numPhotos" +
    						" FROM " + TABLE_NAME_ALBUMS + 
    						" a LEFT OUTER JOIN " + TABLE_NAME_PHOTOS +
    						" p ON (a." + COL_ID + " = p." + COL_ALBUMID + ")" + 
    						" GROUP BY a." + COL_NAME;
    	
    	Cursor c = performRawQuery(albumQuery);
    	
    	ArrayList<HashMap<String,String>> albums = new ArrayList<HashMap<String,String>>();
    	if (c == null){
    		return albums;
    	}

        while(!c.isAfterLast()){
        	HashMap<String,String> map = new HashMap<String,String>();
            String albumName = c.getString(c.getColumnIndex(COL_NAME));
            String numPhotos = c.getString(c.getColumnIndex("numPhotos"));
            
            map.put("name", albumName);
            map.put("numPhotos", numPhotos);
            albums.add(map);

            c.moveToNext();
        }

        return albums;
    }

    /**
     * Use this to find the name of the album that a photo is contained in by passing the photo's 
     * id as an input parameter.
     * @param photoID
     * @return albumName as a string
     */
    public String getAlbumNameOfPhoto(int photoID) {
        
        Cursor c = performRawQuery("SELECT " + COL_NAME +
                                   " FROM " + TABLE_NAME_ALBUMS +
                                   " WHERE " + COL_PHOTOID + " = " +
                                   "'" + photoID + "'");
        
        return c.getString(c.getColumnIndex(COL_NAME));
        
    }
    
    // TODO: fix this function to work properly once the table set ups have been finalized
    /**
     * Gets all Pictures from an album from database.
     * @param albumName
     * @return
     */
    public ArrayList<HashMap<String, String>> selectPhotosFromAlbum(String albumName) {
    	int albumID = selectAlbumIDByName(albumName);
    	String query = "SELECT * FROM " + 
    					TABLE_NAME_PHOTOS + 
    					" WHERE " + 
    					COL_ALBUMID + " = " + "'" + albumID + "'" + 
    					" ORDER BY " + PHOTOS_COL_DATE;
    	return selectPhotosByQuery(query);
    }
    
    // TODO: fix this function to work properly once the table set ups have been finalized
    /**
     * Gets all pictures with the given tags from the database.
     * @param tags A collection of Tags represented as Strings to be used in the query
     * @return ArrayList of HashMaps representing all Pictures with the given tag
     */
    public ArrayList<HashMap<String, String>> selectPhotosByTag(Collection<String> tags) {
    	String query = "SELECT p." + COL_NAME + " AS " + COL_NAME + ", p." + 
    					PHOTOS_COL_PATH + " AS " + PHOTOS_COL_PATH + ", p." + 
    					COL_ID + " AS " + COL_ID + ", p." + PHOTOS_COL_DATE + " AS " + 
    					PHOTOS_COL_DATE + " FROM " + 
    					TABLE_NAME_PHOTOS + " p LEFT JOIN " + 
    					TABLE_NAME_TAGS + " t ON (p." + COL_ID + " = t." + COL_PHOTOID + ") " +
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
    			" ORDER BY COUNT(*), " + PHOTOS_COL_DATE;
    	return selectPhotosByQuery(query);
    }

    /**
     * Useful method for converting from date to String for db insertion.
     * @param date Date object to be converted to String object
     * @return String object from converted Date object
     */
    public String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
    
    /**
     * Parses a string into a usable Date object.
     * @param date String representation of Date to be parsed
     * @return Parsed Date object
     */
    public Date stringToDate(String date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();  
        }

        return null;
    }
    
    /**
     * Performs a raw SQL Select query. Returns a cursor set to the first result.
     * @param query Query to be sent to database
     * @return Cursor to first result of query, or null if query is empty
     */
    private Cursor performRawQuery(String query) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        Log.d(logTag, "Raw query executed: " + query);
        	
        if (c.getCount() == 0) {
        	return null;
        }
        c.moveToFirst();

        return c;

    }
    
    /**
     * Performs a join on a collection of Strings with a delimiter between each String.
     * @param strings Collection of Strings to be joined
     * @param delimiter String to be placed between each String
     * @return String representation of joined Strings
     */
    public String stringJoin(Collection<String> strings, String delimiter) {
    	String newString = "";
    	boolean isFirst = true;
    	for (String curr : strings){
    		newString += curr;
    		if(isFirst){
    			isFirst = false;
    			newString += delimiter;
    		}
    	}
    	return newString;
    }
}