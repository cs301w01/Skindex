package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.activities.ComparePicturesActivity;
import com.cs301w01.meatload.model.SQLiteDBManager;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

public class ComparePicturesActivityTest extends
		ActivityInstrumentationTestCase2<ComparePicturesActivity> {
    private Instrumentation mInstrumentation;
    private Context mContext;
    private ComparePicturesActivity mActivity;

	
	public ComparePicturesActivityTest(){
		super("com.cs301w01.meatload", ComparePicturesActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
        
        mInstrumentation = getInstrumentation();
        mContext = mInstrumentation.getContext();
        mActivity = getActivity();
        
        SQLiteDBManager db = new SQLiteDBManager(mActivity.getBaseContext());
        db.resetDB();
        db.close();
    }
    
    @Override
    protected void tearDown() throws Exception {
    	
    	SQLiteDBManager db = new SQLiteDBManager(mActivity.getBaseContext());
        db.resetDB();
        db.close();
        
        super.tearDown();    
        
        if (mActivity != null) {
            mActivity.finish();
        }
    }
    
    public void testTemp(){
    	assertTrue(1 == 1);
    }
}
