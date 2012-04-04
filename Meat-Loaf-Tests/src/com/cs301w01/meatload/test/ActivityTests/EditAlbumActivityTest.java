package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.activities.EditAlbumActivity;
import com.cs301w01.meatload.model.SQLiteDBManager;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

public class EditAlbumActivityTest extends ActivityInstrumentationTestCase2<EditAlbumActivity> {
    private Instrumentation mInstrumentation;
    private Context mContext;
    private EditAlbumActivity mActivity;
    
	public EditAlbumActivityTest(){
		super("com.cs301w01.meatload", EditAlbumActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
        
        mInstrumentation = getInstrumentation();
        mContext = mInstrumentation.getContext();
        mActivity = getActivity();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();    
        
        if (mActivity != null) {
            mActivity.finish();
        }

    }
    
    public void testTEMPDELETETHIS(){
    	assertTrue(1 == 1);
    }

}
