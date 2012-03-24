package com.cs301w01.meatload.model;

import android.content.ContentValues;
import android.database.Cursor;

public interface DBManager {
		
	public Cursor performRawQuery(String query);

	public Cursor query(boolean b, String tableName, String[] selectColumns,
			String string, Object object, Object object2, Object object3,
			Object object4, Object object5);

	public int update(String tableName, ContentValues cv, String string,
			Object object);

	public long insert(String tableName, String colId, ContentValues cv);

}
