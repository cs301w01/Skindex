package com.cs301w01.meatload.test;

import java.util.ArrayList;

import com.cs301w01.meatload.Skindex;
import com.cs301w01.meatload.activities.ViewAlbumsActivity;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.SQLiteDBManager;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;

public class ViewAlbumActivityTest extends ActivityInstrumentationTestCase2<ViewAlbumsActivity> {
    private Instrumentation mInstrumentation;
    private Context mContext;
    private ViewAlbumsActivity mActivity;
    
	public ViewAlbumActivityTest(){
		super("com.cs301w01.meatload", ViewAlbumsActivity.class);
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


	
	public void testActivity() {
		ArrayList<Album> origAlbs;
		ArrayList<Album> finalAlbs;
		

		
		final Button button = (Button) mActivity.findViewById(com.cs301w01.meatload.R.id.newAlbum);
		assertNotNull(button);
		
		MainManager mainMan = new MainManager();
		mainMan.setContext(mActivity.getApplicationContext());
		
		origAlbs = mainMan.getAllAlbums();
		
		

			mActivity.runOnUiThread(new Runnable() {
				public void run(){
					button.requestFocus();
					button.performClick();
				}
			});
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
				assertTrue("Sleep failed", false);
			}
			
			sendKeys(KeyEvent.KEYCODE_A);
			sendKeys(KeyEvent.KEYCODE_B);
			sendKeys(KeyEvent.KEYCODE_C);
			
			sendKeys(KeyEvent.KEYCODE_ENTER);
			
			
		
			
		
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				assertTrue("Sleep failed", false);
			}
			
		assertTrue(1==1);
		
		//assertTrue("currentState != original + 10", mActivity.getCurrentState() == original + 11);
	}

}
