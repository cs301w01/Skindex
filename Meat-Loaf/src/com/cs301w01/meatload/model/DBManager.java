package com.cs301w01.meatload.model;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * The DBManager interface abstracts the database logic away from the query
 * generator. This allows the query generators to work with any database
 * connection adapter that implements this interface.
 * 
 * @author Derek Dowling
 * 
 */
public interface DBManager {

	public Cursor performRawQuery(String query);

	public int update(String tableName, ContentValues cv, String whereClause, String whereArgs[]);

	public long insert(String tableName, String colId, ContentValues cv);

}
