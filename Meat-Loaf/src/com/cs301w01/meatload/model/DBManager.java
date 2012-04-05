package com.cs301w01.meatload.model;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * The DBManager interface abstracts the database logic away from the query generator. This allows
 * the query generators to work with any database connection adapter that implements this interface.
 * 
 * @author Derek Dowling
 *
 */
public interface DBManager {
		
	public Cursor performRawQuery(String query);

	public Cursor query(boolean b, String tableName, String[] selectColumns,
                        String string, String selectionArgs[], String object2, String object3,
                        String object4, String object5);

	public int update(String tableName, ContentValues cv, String whereClause,
                      String whereArgs[]);

	public long insert(String tableName, String colId, ContentValues cv);

}
