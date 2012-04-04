package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.activities.ViewTagsActivity;
import com.cs301w01.meatload.model.SQLiteDBManager;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

public class ViewTagsActivityTest extends ActivityInstrumentationTestCase2<ViewTagsActivity> {
    private Instrumentation mInstrumentation;
    private Context mContext;
    private ViewTagsActivity mActivity;
    
	public ViewTagsActivityTest(){
		super("com.cs301w01.meatload", ViewTagsActivity.class);
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
