package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.activities.EditAlbumActivity;
import com.cs301w01.meatload.activities.ViewAlbumsActivity;
import com.cs301w01.meatload.controllers.AlbumManager;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.model.SQLiteDBManager;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

public class EditAlbumActivityTest extends ActivityInstrumentationTestCase2<EditAlbumActivity> {
    private Context mContext;
    private EditAlbumActivity mActivity;
	private AlbumManager albumMan;
	private MainManager mainMan;
	private final int SLEEP_TIME = 1000;
    
	public EditAlbumActivityTest(){
		super("com.cs301w01.meatload", EditAlbumActivity.class);
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
    
    public void testVerifyNamePopulates(){
    	assertTrue(1 == 1);
    }
    
    public void testVerifyNumPicsPopulates(){
    	
    }
    
    public void testNoChangesPressSave(){
    	//verify album state doesn't change
    }
    
    public void testChangeNamePressDelete(){
    	//verify old album deleted
    }
    
    public void testChangeNamePressSave(){
    	//verify changes made
    }
    
    public void testInvalidName(){
    	
    }
    
    public void testChangeNameToOneThatAlreadyExists(){
    	
    }

}
