package com.cs301w01.meatload.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.TagQueryGenerator;

/**
 * This class is helper for dealing with SQLite in Android. It provides a
 * variety of useful methods for creating, updating, deleting, and selecting
 * data.
 * 
 * @see <a
 *      href="http://www.codeproject.com/Articles/119293/Using-SQLite-Database-with-Android">
 *      http://www.codeproject.com/Articles/119293/Using-SQLite-Database-with-Android

 *      </a>
 */
public class SQLiteDBManager extends SQLiteOpenHelper implements DBManager {

	private static String logTag = "DBMANAGER";
	private static final String DB_NAME = "skindexDB";

	private static final int DATABASE_VERSION = 2;

	// private Context myContext;

	/**
	 * Constructor, creates an SQLiteDBManager with the given Context.
	 * 
	 * @param context
	 */
	public SQLiteDBManager(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);

		// myContext = context;
		// uncomment if any changes have been made to tables to reset
		// resetDB();
	}

	/**
	 * This method will be called the first time a Database is instantiated by
	 * this app on this particular phone. Once created, unless reset or
	 * uninstalled, this method will not run again. Android calls this method
	 * automatically.
	 * 
	 * @param newDB
	 */
	@Override
	public void onCreate(SQLiteDatabase newDB) {

		createTables(newDB);

	}

	/**
	 * Creates the tables needed for the current database.
	 * 
	 * @param db
	 *            The database to create tables in
	 */
	private void createTables(SQLiteDatabase db) {

		db.execSQL(PictureQueryGenerator.CREATE_TABLE_QUERY);
		Log.d(logTag, PictureQueryGenerator.TABLE_NAME + " generated.");

		db.execSQL(AlbumQueryGenerator.CREATE_TABLE_QUERY);
		Log.d(logTag, AlbumQueryGenerator.TABLE_NAME + " generated.");

		db.execSQL(TagQueryGenerator.CREATE_TABLE_QUERY);
		Log.d(logTag, TagQueryGenerator.TABLE_NAME + " generated.");

		Log.d(logTag, "DB generated.");

	}

	/**
	 * Drops the specified tables in the given database.
	 * 
	 * @param db
	 *            DB to drop from
	 */
	private void dropTables(SQLiteDatabase db) {

		db.execSQL("DROP TABLE IF EXISTS " + PictureQueryGenerator.TABLE_NAME);
		Log.d(logTag, PictureQueryGenerator.TABLE_NAME + " dropped.");

		db.execSQL("DROP TABLE IF EXISTS " + AlbumQueryGenerator.TABLE_NAME);
		Log.d(logTag, AlbumQueryGenerator.TABLE_NAME + " dropped.");

		db.execSQL("DROP TABLE IF EXISTS " + TagQueryGenerator.TABLE_NAME);
		Log.d(logTag, TagQueryGenerator.TABLE_NAME + " dropped.");

		Log.d(logTag, "DB generated.");

	}

	/**
	 * Used to force changes to update in the sql table during testing
	 */
	public void resetDB() {

		SQLiteDatabase db = this.getWritableDatabase();
		dropTables(db);
		createTables(db);
		db.close();
		Log.d(logTag, "TABLES RESET.");
	}

	/**
	 * When the version of database is increase this method will be called by
	 * Android, reseting the database with any new changes made to the tables.
	 * 
	 * @param sqLiteDatabase
	 *            The DB to update
	 * @param i
	 *            the old version
	 * @param i1
	 *            the new version
	 */
	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		dropTables(sqLiteDatabase);
		onCreate(sqLiteDatabase);
	}

	/**
	 * Performs a raw SQL Select query. Returns a cursor set to the first
	 * result.
	 * 
	 * @param query
	 *            Query to be sent to database
	 * @return Cursor to first result of query, or null if query is empty
	 */
	public Cursor performRawQuery(String query) {

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(query, null);
		Log.d(logTag, "Raw query executed: " + query);

		if (c.getCount() == 0) {
			db.close();
			return null;
		}

		c.moveToFirst();
		db.close();
		return c;
	}

	
	/**
	 * This method is used to update tuples in the database.
	 * 
	 * @param tableName name of the table you are updating a table for
	 * @param cv ContentValue object which contains the changed values
	 * @param whereClause the tuple column comparison for determining which rows to update
	 * @param whereArgs[] args for the where clause
	 */
	public int update(String tableName, ContentValues cv, String whereClause, String whereArgs[]) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		int id = db.update(tableName, cv, whereClause, whereArgs);
		db.close();
		
		return id;

	}
 
	/**
	 * This method is used to insert new tuples into a database.
	 * 
	 * @param tableName String name of the table inserting on
	 * 
	 * @param colId String representation of the primary key column id as
	 * 			represented in the create table statement, example: "id_"
	 * 
	 * @param cv ContentValues a content values object hash which contains
	 * 				a key for the column name, and a value to insert for that column
	 * 
	 * @return id long value of the row id that was created by the insert
	 */
	public long insert(String tableName, String colId, ContentValues cv) {

		SQLiteDatabase db = this.getWritableDatabase();
		long id = db.insert(tableName, colId, cv);
		db.close();
		return id;

	}

}