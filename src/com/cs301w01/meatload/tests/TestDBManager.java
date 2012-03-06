package com.cs301w01.meatload.tests;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.mock.MockContext;
import com.cs301w01.meatload.model.DBManager;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

/**
 * Created by IntelliJ IDEA.
 * User: Derek
 * Date: 3/4/12
 * Time: 7:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestDBManager extends TestCase{

    private DBManager dbPlug;

    @Before
    public void setUp() throws Exception {

        super.setUp();
        final SQLiteDatabase db = SQLiteDatabase.create(null);
        Context context = new MockContext() {
            @Override
            public SQLiteDatabase openOrCreateDatabase(String file, int mode, SQLiteDatabase.CursorFactory factory) {
                return db;
            };
        };
        dbPlug = new DBManager(context);
        mDb = mHelper.getWritableDatabase();
        wipeData(mDb);

    }

    @After
    public void tearDown() {
        db.close();
    }

}
