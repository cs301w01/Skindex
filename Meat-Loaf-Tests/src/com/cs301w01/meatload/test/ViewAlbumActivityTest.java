package com.cs301w01.meatload.test;

import java.util.ArrayList;

import com.cs301w01.meatload.R;
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
import android.test.UiThreadTest;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

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

	public void testActivity() {
		ArrayList<Album> origAlbs;
		ArrayList<Album> finalAlbs;
		int key_num = KeyEvent.KEYCODE_A;
		

		
		final Button button = (Button) mActivity.findViewById(com.cs301w01.meatload.R.id.newAlbum);
		assertNotNull(button);
		
		MainManager mainMan = new MainManager();
		mainMan.setContext(mActivity.getApplicationContext());
		
		origAlbs = mainMan.getAllAlbums();
			for(int i = 0; i < 3; i++){
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
				sendKeys(key_num + i);
				sendKeys(key_num + i + 1);
				sendKeys(key_num + i + 2);
				sendKeys(KeyEvent.KEYCODE_ENTER);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					assertTrue("Sleep failed", false);
				}
			}
			finalAlbs = mainMan.getAllAlbums();
			
			final ListView albumListView = (ListView) mActivity.findViewById(com.cs301w01.meatload.R.id.albumListView);
			assertNotNull(albumListView);
			
		    //ListView taco = new ListView(this.getBaseContext());
		    //taco.getChildAt(1).
			
			mActivity.runOnUiThread(new Runnable() {
				public void run(){
					albumListView.requestFocus(1);
					//albumListView.getChildAt(1).requestFocus();
					//albumListView.getChildAt(1).performClick();
					//getChildCount()
					/*
					albumListView.requestFocus();
					albumListView.getRootView().focusSearch(ListView.FOCUS_DOWN).requestFocus();
					albumListView.focusSearch(ListView.FOCUS_DOWN).requestFocus();
					albumListView.performClick();
					
					ArrayList<View> items= albumListView.getFocusables(1);
					items.get(0).requestFocus();
					items.get(0).performClick();
					*/
				}
			});		
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				assertTrue("Sleep failed", false);
			}
			
		assertTrue(finalAlbs.size() == origAlbs.size() + 3);
		
	}

}
