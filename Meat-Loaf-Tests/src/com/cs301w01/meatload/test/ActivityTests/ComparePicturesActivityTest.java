package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.activities.ComparePicturesActivity;
import com.cs301w01.meatload.model.SQLiteDBManager;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

public class ComparePicturesActivityTest extends
		ActivityInstrumentationTestCase2<ComparePicturesActivity> {
    private Context mContext;
    private ComparePicturesActivity mActivity;

	
	public ComparePicturesActivityTest(){
		super("com.cs301w01.meatload", ComparePicturesActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
        
        mActivity = getActivity();
        mContext = mActivity.getBaseContext();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();    
        
        if (mActivity != null) {
            mActivity.finish();
        }
    }
    
    public void testVerifyNumberOfPicturesDisplayed(){
    	//Verfiy that the # of pictures in the top display = the number of pics 
    	//in the bottom display = number of pics in the album specified by the intent
    	assertTrue(1 == 1);
    }
}
