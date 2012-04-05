package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.activities.SendEmailActivity;
import com.cs301w01.meatload.model.SQLiteDBManager;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

public class SendEmailActivityTest extends ActivityInstrumentationTestCase2<SendEmailActivity> {
    private Context mContext;
    private SendEmailActivity mActivity;
    
	public SendEmailActivityTest(){
		super("com.cs301w01.meatload", SendEmailActivity.class);
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
    
    public void testSaveWithEmptyTextFields(){
    	assertTrue(1 == 1);
    }
    
    public void testSaveVaildTextFields(){
    	
    }
    
    public void testInvalidEmail(){
    	
    }
	
}