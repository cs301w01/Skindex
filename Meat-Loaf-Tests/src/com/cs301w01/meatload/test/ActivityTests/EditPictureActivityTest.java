package com.cs301w01.meatload.test.ActivityTests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.activities.EditPictureActivity;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.controllers.PictureManager;
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
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class EditPictureActivityTest extends
		ActivityInstrumentationTestCase2<EditPictureActivity> {
    
    private Context mContext;
    private EditPictureActivity mActivity;
    private MainManager mainMan;
    private final int SLEEP_TIME = 500;
    
	public EditPictureActivityTest(){
		super("com.cs301w01.meatload", EditPictureActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
    	
        Date tempDate = Calendar.getInstance().getTime();
        Picture tempPic = new Picture("temp", "temp", "temp",tempDate, new ArrayList<Tag>());
        
        // The EditPictureActivity will only use the ID of the given picture, as set below
        tempPic.setID(1);
    	
        Intent editPicIntent = new Intent();
        editPicIntent.setClassName("com.cs301w01.meatload",
				"com.cs301w01.meatload.activities.EditPicturesActivity");

		editPicIntent.putExtra("picture", tempPic);
		setActivityIntent(editPicIntent);

        mActivity = getActivity();	
        mContext = mActivity.getBaseContext();
        
        mainMan = new MainManager(mContext);
                
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
    
    public void testEditNameThenSave(){
    	final Button saveButton = (Button) mActivity.findViewById(com.cs301w01.meatload.R.id.savePictureButton);
		assertNotNull(saveButton);
		
    	final EditText pictureName = (EditText) mActivity.findViewById(R.id.pictureNameEditText);
    	assertNotNull(pictureName);
    	
		mActivity.runOnUiThread(new Runnable() {
			public void run(){
		    	pictureName.requestFocus();
		    	pictureName.setText("Moley Mole");
		    	saveButton.requestFocus();
		    	saveButton.performClick();
			}
		});
		
		try {
			Thread.sleep(SLEEP_TIME * 5);
		} catch (InterruptedException e) {
			e.printStackTrace();
			assertTrue("Sleep failed", false);
		}

    	PictureQueryGenerator picGen = new PictureQueryGenerator(mContext);
    	Picture newPic = picGen.selectPictureByID(1);
    	
    	
    	assertTrue(newPic.getName().equals("Moley Mole"));
    }
    
    public void testChangeAlbumThenSave(){
    	
    }
    
    public void testAddTagsThenSave(){
    	
    }
    
    public void testDeleteTagsThenSave(){
    	
    }
    
    public void testChangeInfoThenPressBackButton(){
    	
    }
    
    public void testDeletePicture(){
    	
    }
    

}
