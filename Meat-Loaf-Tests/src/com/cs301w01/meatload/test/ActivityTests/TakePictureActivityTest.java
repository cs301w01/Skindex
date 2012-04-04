package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.activities.TakePictureActivity;
import com.cs301w01.meatload.model.SQLiteDBManager;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

public class TakePictureActivityTest extends
		ActivityInstrumentationTestCase2<TakePictureActivity> {
    private Instrumentation mInstrumentation;
    private Context mContext;
    private TakePictureActivity mActivity;
    
	public TakePictureActivityTest(){
		super("com.cs301w01.meatload", TakePictureActivity.class);
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
