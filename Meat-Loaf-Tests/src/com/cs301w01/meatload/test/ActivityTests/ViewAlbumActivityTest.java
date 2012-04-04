package com.cs301w01.meatload.test.ActivityTests;

import java.util.ArrayList;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.Skindex;
import com.cs301w01.meatload.activities.ViewAlbumsActivity;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.SQLiteDBManager;
import com.jayway.android.robotium.solo.Solo;

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
    
    private Solo solo;
    
	public ViewAlbumActivityTest(){
		super("com.cs301w01.meatload", ViewAlbumsActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
        
    	solo = new Solo(getInstrumentation(), getActivity());
    	
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

	public void testAddAlbumsUsingEnter() {
		
		solo.clickOnText("New Album");
		//solo.enterText(index, text)
		solo.clickOnText("Ok");
		assert(solo.searchText("New Album"));
		
		//solo.clickOnButton("New Album");

		
//		ArrayList<Album> origAlbs;
//		ArrayList<Album> finalAlbs;
//		int key_num = KeyEvent.KEYCODE_A;
//				
//		final Button button = (Button) mActivity.findViewById(com.cs301w01.meatload.R.id.newAlbum);
//		assertNotNull(button);
//		
//		MainManager mainMan = new MainManager();
//		mainMan.setContext(mActivity.getApplicationContext());
//		
//		origAlbs = mainMan.getAllAlbums();
//			for(int i = 0; i < 3; i++){
//				mActivity.runOnUiThread(new Runnable() {
//					public void run(){
//						button.requestFocus();
//						button.performClick();
//					}
//				});
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//					assertTrue("Sleep failed", false);
//				}
//				sendKeys(key_num + i);
//				sendKeys(key_num + i + 1);
//				sendKeys(key_num + i + 2);
//				sendKeys(KeyEvent.KEYCODE_ENTER);
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//					assertTrue("Sleep failed", false);
//				}
//			}
//			finalAlbs = mainMan.getAllAlbums();
//			
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//				assertTrue("Sleep failed", false);
//			}
//			
//		assertTrue(finalAlbs.size() == origAlbs.size() + 3);
		
		
	}
	
	
	//THIS TEST HAS ERRORS AND IS NOT GETTING THE VIEWS CORRECTLY
	public void testAddAlbumsUsingDialog() {
		ArrayList<Album> origAlbs;
		ArrayList<Album> finalAlbs;
		String name = "abc";
		AlertDialog dialog;
				
		final Button button = (Button) mActivity.findViewById(com.cs301w01.meatload.R.id.newAlbum);
		assertNotNull(button);
		
		MainManager mainMan = new MainManager();
		mainMan.setContext(mActivity.getApplicationContext());
		
		origAlbs = mainMan.getAllAlbums();
			for(int i = 0; i < 3; i++){
				name.replace(name.charAt(0), (char) (name.charAt(0) + i));
				name.replace(name.charAt(1), (char) (name.charAt(1) + i));
				name.replace(name.charAt(2), (char) (name.charAt(2) + i));
				
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
				
				//The two below calls currently error, saying that you cannot edit a View from a seperate thread
				//mActivity.setDialogEditText(name);
				//mActivity.performDialogClick(true);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					assertTrue("Sleep failed", false);
				}
							}
			finalAlbs = mainMan.getAllAlbums();
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
				assertTrue("Sleep failed", false);
			}
			
		assertTrue(finalAlbs.size() == origAlbs.size() + 3);
	}

}
