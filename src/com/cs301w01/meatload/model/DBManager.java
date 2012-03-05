package com.cs301w01.meatload.model;

import com.cs301w01.meatload.activities.FView;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.cs301w01.meatload.model.Photo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is helper for dealing with SQLite in Android. It provides a variety of useful methods for
 * creating, updating, deleting, and selecting data.
 *
 * Great resource: http://www.codeproject.com/Articles/119293/Using-SQLite-Database-with-Android
 */
public class DBManager extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static String tag = "DBMANAGER";
    private static final String DB_NAME = "skindexDB";

    //common db vars
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_PHOTOID = "photoID";
    
    //vars for photos table
    public static final String TABLE_NAME_PHOTOS = "photos";
    public static final String PHOTOS_COL_PATH = "path";
    public static final String PHOTOS_COL_DATE = "date";
    
    //vars for tags table
    public static final String TABLE_NAME_TAGS = "tags";
    
    //vars for albums table
    public static final String TABLE_NAME_ALBUMS = "albums";

    private static final int DATABASE_VERSION = 2;
    
    private static final String CREATE_TABLE_PHOTOSTABLE =
            "CREATE TABLE " + TABLE_NAME_PHOTOS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY," +
                    PHOTOS_COL_DATE + " Date," +
                    PHOTOS_COL_PATH + " TEXT," +
                    COL_NAME + " TEXT);";
    
    private static final String CREATE_TABLE_TAGSTABLE =
            "CREATE TABLE " + TABLE_NAME_TAGS + " (" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_PHOTOID + " INTEGER," +
                COL_NAME + " TEXT," +
                "FOREIGN KEY(" + COL_PHOTOID + ") REFERENCES " +
                                                    TABLE_NAME_PHOTOS + "( " + COL_ID + "));";

    private static final String CREATE_TABLE_ALBUMSTABLE =
            "CREATE TABLE " + TABLE_NAME_ALBUMS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY," +
                    COL_PHOTOID + " INTEGER," +
                    COL_NAME + " TEXT," +
                    "FOREIGN KEY(" + COL_PHOTOID + ") REFERENCES " +
                    TABLE_NAME_PHOTOS + "( " + COL_ID + "));";


    public DBManager(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase newDB) {

        db = newDB;

    }

    private void createTables(){

        db.execSQL(CREATE_TABLE_PHOTOSTABLE);
        Log.d(tag, CREATE_TABLE_PHOTOSTABLE + " generated.");

        db.execSQL(CREATE_TABLE_ALBUMSTABLE);
        Log.d(tag, CREATE_TABLE_ALBUMSTABLE + " generated.");

        db.execSQL(CREATE_TABLE_TAGSTABLE);
        Log.d(tag, CREATE_TABLE_TAGSTABLE + " generated.");

        Log.d(tag, "DB generated.");

    }

    private void dropTables(){

        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_PHOTOSTABLE);
        Log.d(tag, TABLE_NAME_PHOTOS + " dropped.");

        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_ALBUMSTABLE);
        Log.d(tag, TABLE_NAME_ALBUMS + " dropped.");

        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_TAGSTABLE);
        Log.d(tag, TABLE_NAME_TAGS + " dropped.");

        Log.d(tag, "DB generated.");    
        
    }
    
    /**
     * Used to force changes to update in the sql table during testing
     */
    public void resetDB() {

        SQLiteDatabase db = this.getWritableDatabase();

        dropTables();
        createTables();
        
        Log.d(tag, "TABLES RESET.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        dropTables();
        onCreate(sqLiteDatabase);

    }

    /**
     * This method is used to select rows from a table where a specific id is known. Returns a cursor
     * that is preset to the first tuple.
     *
     * @param selectColumns array of columns you wish to have returned
     * @param tableName table name
     * @param idValue
     * @return
     */
    public Cursor selectByID(String[] selectColumns, String tableName, String idValue) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor =
                db.query(true, from, selectColumns, COL_ID + " = " + idValue, null,null,null,null,null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        mCursor.moveToFirst();

        return mCursor;

    }

    /**
     * Use this method if you want to do a "SELECT *" style query on a table for a specific ID
     * @param tableName name of table
     * @param id row in table
     * @return
     */
    public Cursor selectAllColsByID(String tableName, long id){

        //build the query
        String selectionQuery = "SELECT * FROM " + tableName + " WHERE " + DBManager.COL_ID +
                " = '" + id + "'";

        return performRawQuery(selectionQuery);

    }

    /**
     * Removes a tuple based on its id value.
     * @param id tuple to delete
     * @param tableName
     */
    public void deleteByID(long id, String tableName){

        SQLiteDatabase db = this.getWritableDatabase();

        String dQuery = "DELETE FROM " + tableName + " WHERE " + COL_ID  + " = '" + id + "'";

        Log.d(tag, "Performing delete: " + dQuery);

        db.execSQL(dQuery);

        db.close();

    }

    /**
     * The update method has the following parameters:
     String Table: The table to update a value in
     ContentValues cv: The content values object that has the new values
     String where clause: The WHERE clause to specify which record to update
     String[] args: The arguments of the WHERE clause
     * @param p Fuel Entry object
     * @param tableName
     * @param id
     * @return
     */
    public int updatePhotoByID(Photo p, String tableName, int id)
    {
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
     * Pass in Object you would like to insert, and the name of the table you are inserting into.
     * @param p Fuel Entry object
     * @param tableName
     */
    public void insertPhoto(Photo p, String tableName) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //insert data
        cv.put(COL_DATE, dateToString(p.getDate()));
        cv.put(COL_FUELAMOUNT, p.getFuelAmount());
        cv.put(COL_DISTANCE, p.getDistance());
        cv.put(COL_CENTSPERLITRE, p.getCost());
        cv.put(COL_FUELGRADE, p.getFuelGrade());
        cv.put(COL_STATION, p.getStation());
        cv.put(COL_COST, p.getCost());

        db.insert(tableName, COL_ID, cv);
        db.close();

    }

    public void insertTag(){
        //todo
    }

    public void insertAlbum(){
        //todo
    }



    /**
     * Useful method for converting from date to String for db insertion
     * @param date
     * @return
     */
    public String dateToString(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);

    }

    /**
     * Performs a raw query, where the where args are null
     * @param query
     * @return
     */
    public Cursor performRawQuery(String query)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        Log.d(tag, "Raw query executed: " + query);

        c.moveToFirst();

        return c;

    }

    /**
     * Use this method if you wish to return all results and all columns from a table.
     *
     * @param tableName
     * @return
     */
    public Cursor getAllRows(String tableName) {

        //build the query
        String selectionQuery = "SELECT * FROM " + tableName;

        return performRawQuery(selectionQuery);

    }
}