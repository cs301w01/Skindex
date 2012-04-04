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
//import com.jayway.android.robotium.solo.Solo;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

public class EditPictureActivityTest extends
		ActivityInstrumentationTestCase2<EditPictureActivity> {
    
    private Context mContext;
    private EditPictureActivity mActivity;
    private MainManager mainMan;
    
    //private Solo solo;
    
	public EditPictureActivityTest(){
		super("com.cs301w01.meatload", EditPictureActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
        
    	//solo = new Solo(getInstrumentation(), getActivity());
    	
        Date tempDate = Calendar.getInstance().getTime();
        Picture tempPic = new Picture("temp", "temp", "temp",tempDate, new ArrayList<Tag>());
        tempPic.setID(1);
    	
        Intent editPicIntent = new Intent();
        editPicIntent.setClassName("com.cs301w01.meatload",
				"com.cs301w01.meatload.activities.ComparePicturesActivity");

		editPicIntent.putExtra("picture", tempPic);
		setActivityIntent(editPicIntent);

        mActivity = getActivity();	
        mContext = mActivity.getBaseContext();
        
        mainMan = new MainManager();
        mainMan.setContext(mContext);
    }
    
    
    public void testDisplayBlackBox() {
    	
    
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();    
        
        if (mActivity != null) {
            mActivity.finish();
        }

    }
    
    public void testTemp(){
    	assertTrue(1==1);
    }

}
