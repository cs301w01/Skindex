package com.cs301w01.meatload.test.ActivityTests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.activities.EditAlbumActivity;
import com.cs301w01.meatload.activities.ViewAlbumsActivity;
import com.cs301w01.meatload.controllers.AlbumManager;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.SQLiteDBManager;
import com.cs301w01.meatload.model.Tag;
import com.cs301w01.meatload.model.gallery.AlbumGallery;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    	Album alb = new Album(null, 0, 1);
    	AlbumGallery albGal = new AlbumGallery(alb);
        Intent editPicIntent = new Intent();
        editPicIntent.setClassName("com.cs301w01.meatload",
				"com.cs301w01.meatload.activities.EditPicturesActivity");

		editPicIntent.putExtra("gallery", albGal);
		setActivityIntent(editPicIntent);

        mActivity = getActivity();	
        mContext = mActivity.getBaseContext();
    	albumMan = new AlbumManager(mContext);
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();    
        
        if (mActivity != null) {
            mActivity.finish();
        }

    }
    
    public void testVerifyNamePopulates(){
    	EditText name = (EditText) mActivity.findViewById(R.id.edit_album_name);
		String newName = name.getText().toString();
		
    	assertTrue(albumMan.albumExists(newName));
    }
    
    public void testVerifyNumPicsPopulates(){
		TextView numView = (TextView) mActivity.findViewById(R.id.text_num_pics);
		int num = Integer.parseInt(numView.getText().toString());
		
    	EditText name = (EditText) mActivity.findViewById(R.id.edit_album_name);
		String newName = name.getText().toString();
		
		AlbumGallery albGallery = new AlbumGallery(albumMan.getAlbumByName(newName));
		ArrayList<Picture> pics = (ArrayList<Picture>) albGallery.getPictureGallery(mContext);
		
		assertTrue(num == pics.size());
    }
    
    public void testNoChangesPressSave(){
    	final Button button = (Button) mActivity.findViewById(R.id.save_album_button);
    	
    	EditText name = (EditText) mActivity.findViewById(R.id.edit_album_name);
		String newName = name.getText().toString();
		
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				button.requestFocus();
				button.performClick();
			}
			
		});
		sleep();
    	
		assertTrue(albumMan.albumExists(newName));
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
    
	private void sleep(){
		try {
			Thread.sleep(SLEEP_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
			assertTrue("Sleep failed", false);
		}
	}

}
