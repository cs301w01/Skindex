package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.activities.GalleryActivity;
import com.cs301w01.meatload.model.SQLiteDBManager;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

public class GalleryActivityTest extends ActivityInstrumentationTestCase2<GalleryActivity> {
    private Instrumentation mInstrumentation;
    private Context mContext;
    private GalleryActivity mActivity;
    
	public GalleryActivityTest(){
		super("com.cs301w01.meatload", GalleryActivity.class);
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
    
    public void testTEMPDELETETHIS(){
    	assertTrue(1 == 1);
    }
    
    //What exactly is there to test?  We could check that each button, when clicked, generates a valid Intent,
    //but is that necessary?

}
