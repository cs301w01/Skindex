package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.activities.ViewAlbumsActivity;
import com.cs301w01.meatload.model.SQLiteDBManager;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

public class DatabaseTest extends ActivityInstrumentationTestCase2<ViewAlbumsActivity> {
    private Context mContext;
    private ViewAlbumsActivity mActivity;
    boolean firstRun = true;
    
	public DatabaseTest(){
		super("com.cs301w01.meatload", ViewAlbumsActivity.class);
	}

    @Override
    protected void setUp() throws Exception {
    	super.setUp();

        mActivity = getActivity();
        mContext = mActivity.getBaseContext();
        
        if(firstRun){
        	firstRun = false;
        	SQLiteDBManager db = new SQLiteDBManager(mContext);
            db.resetDB();
            db.close();
        }

    }
    
    @Override
    protected void tearDown() throws Exception {     
        super.tearDown();    
        
        if (mActivity != null) {
            mActivity.finish();
        }

    }
    
    public void testAddAlbum(){
    	assert(1==1);
    }
    
    public void testDeleteAlbum(){
    	assert(1==1);
    }
    
    public void testUpdateAlbum(){
    	assert(1==1);
    }
    
    public void testAddPicture(){
    	assert(1==1);
    }
    
    public void testDeletePicture(){
    	assert(1==1);
    }
    
    public void testUpdatePicture(){
    	assert(1==1);
    }
    
    public void testAddTags(){
    	assert(1==1);
    }
    
    public void testDeleteTags(){
    	assert(1==1);
    }
    
    public void testUpdateTags(){
    	assert(1==1);
    }
	
	
}
