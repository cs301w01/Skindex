package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.activities.SendEmailActivity;
import com.cs301w01.meatload.model.SQLiteDBManager;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

public class SendEmailActivityTest extends ActivityInstrumentationTestCase2<SendEmailActivity> {
    private Instrumentation mInstrumentation;
    private Context mContext;
    private SendEmailActivity mActivity;
    
	public SendEmailActivityTest(){
		super("com.cs301w01.meatload", SendEmailActivity.class);
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
    
    public void testCorrectData(){
    	//Fill the form in with correct data, hit send, verify sent
    }
    
    public void testIncorrectEmail(){
    	//test with an invalid email (doesn't contain @ for instance) and verify that this errors
    }
    
    public void testBlankData(){
    	//leave everything blank, hit send, and verify that it errors
    }
	
}