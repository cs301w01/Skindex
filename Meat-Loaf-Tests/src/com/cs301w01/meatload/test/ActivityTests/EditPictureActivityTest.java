package com.cs301w01.meatload.test.ActivityTests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.cs301w01.meatload.activities.EditPictureActivity;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.AlbumGallery;
import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.Tag;
import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;
import com.jayway.android.robotium.solo.Solo;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

public class EditPictureActivityTest extends
		ActivityInstrumentationTestCase2<EditPictureActivity> {
    
	private Instrumentation mInstrumentation;
    private Context mContext;
    private EditPictureActivity mActivity;
    
    //private Solo solo;
    
	public EditPictureActivityTest(){
		super("com.cs301w01.meatload", EditPictureActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
        
    	//solo = new Solo(getInstrumentation(), getActivity());
    	
    	long aid[];
    	Picture[] pics;
    	
        Intent editPicIntent = new Intent();
        editPicIntent.setClassName("com.cs301w01.meatload",
				"com.cs301w01.meatload.activities.ComparePicturesActivity");
        Date tempDate = Calendar.getInstance().getTime();
        Picture tempPic = new Picture("taco", "root", "Album 1",tempDate, new ArrayList<Tag>());
        tempPic.setID(1);
		editPicIntent.putExtra("picture", tempPic);
		setActivityIntent(editPicIntent);
		
        mInstrumentation = getInstrumentation();
        mContext = mInstrumentation.getContext();
        mActivity = getActivity();	
        
        
        //RESET THE DB
        SQLiteDBManager db = new SQLiteDBManager(mActivity.getBaseContext());
        db.resetDB();
        db.close();
        
        //POPULATE DB WITH ALBUMS AND THEN PUT PHOTOS IN THOSE ALBUMS
        aid = DatabaseTestingTools.populateAlbums(mActivity.getBaseContext());
        pics = DatabaseTestingTools.populatePictures(mActivity.getBaseContext());
        
        
        //TAKE pics[0] AND PUT IT IN THE EXTRAS AS NEEDED BY EditPictureActivity
    }
    
    
    public void testDisplayBlackBox() {
    	
    
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
    	MainManager mManage = new MainManager();
    	mManage.setContext(mActivity.getBaseContext());
    	
    	mManage.getPictureCount();
    	
    	
    	assertTrue(7 == mManage.getPictureCount());
    }

}
