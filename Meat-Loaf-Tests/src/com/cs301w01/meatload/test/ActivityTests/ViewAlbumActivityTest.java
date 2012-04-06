package com.cs301w01.meatload.test.ActivityTests;

import java.util.ArrayList;

import com.cs301w01.meatload.activities.ViewAlbumsActivity;
import com.cs301w01.meatload.controllers.AlbumManager;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.model.Album;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;

public class ViewAlbumActivityTest extends ActivityInstrumentationTestCase2<ViewAlbumsActivity> {
    
    private Context mContext;
    private ViewAlbumsActivity mActivity;
	private AlbumManager albumMan;
	private MainManager mainMan;
    private final int SLEEP_TIME = 500;
    
	public ViewAlbumActivityTest(){
		super("com.cs301w01.meatload", ViewAlbumsActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();

        mActivity = getActivity();
        mContext = mActivity.getBaseContext();
        
        albumMan = new AlbumManager(mContext);
        mainMan = new MainManager(mContext);
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();    
        
        if (mActivity != null) {
            mActivity.finish();
        }

    }

	public void testAddAlbumsUsingEnter() {
		
		ArrayList<Album> origAlbs;
		ArrayList<Album> finalAlbs;
		int key_num = KeyEvent.KEYCODE_A;
				
		final Button button = (Button) mActivity.findViewById(com.cs301w01.meatload.R.id.newAlbum);
		assertNotNull(button);
		
		origAlbs = albumMan.getAllAlbums();
			for(int i = 0; i < 3; i++){
				mActivity.runOnUiThread(new Runnable() {
					public void run(){
						button.requestFocus();
						button.performClick();
					}
				});
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
					assertTrue("Sleep failed", false);
				}
				sendKeys(key_num + i);
				sendKeys(key_num + i + 1);
				sendKeys(key_num + i + 2);
				sendKeys(KeyEvent.KEYCODE_ENTER);
				sleep();
			}
			finalAlbs = albumMan.getAllAlbums();
			
			sleep();
			
		assertTrue(finalAlbs.size() == origAlbs.size() + 3);
		
	}
	
	
	public void testAddAlbumsUsingDialog() {
		int origNumAlbs, finalNumAlbs;
		final String name = "Album 5";
		AlertDialog dialog;
				
		final Button button = (Button) mActivity.findViewById(com.cs301w01.meatload.R.id.newAlbum);
		assertNotNull(button);
				
		origNumAlbs = albumMan.getAllAlbums().size();

		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				button.requestFocus();
				button.performClick();
			}
			
		});
		sleep();
		
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				mActivity.setDialogEditText(name);
				mActivity.performDialogClick(true);
			}
		});
		sleep();
		finalNumAlbs = albumMan.getAllAlbums().size();
		sleep();
		assertTrue(finalNumAlbs == origNumAlbs + 1);
	}
	
	public void testAddAlbumWithTooLongName(){
		final String name = "123456789123456789123456789";
		String shortenedName = "12345678912345678912";
		//change name above to reflect current max album name in MainManager, by default it is 20 chars
		AlertDialog dialog;
				
		final Button button = (Button) mActivity.findViewById(com.cs301w01.meatload.R.id.newAlbum);
		assertNotNull(button);

		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				button.requestFocus();
				button.performClick();
			}
		});
		sleep();
		
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				mActivity.setDialogEditText(name);
				mActivity.performDialogClick(true);
			}
		});
		sleep();
		assertTrue(albumMan.albumExists(shortenedName));
	}
	
	public void testAddBlankAlbum(){
		int origNumAlbs, finalNumAlbs;
		final String name = "";
		AlertDialog dialog;
				
		final Button button = (Button) mActivity.findViewById(com.cs301w01.meatload.R.id.newAlbum);
		assertNotNull(button);
				
		origNumAlbs = albumMan.getAllAlbums().size();

		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				button.requestFocus();
				button.performClick();
			}
			
		});
		sleep();
		
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				mActivity.setDialogEditText(name);
				mActivity.performDialogClick(true);
			}
		});
		sleep();
		finalNumAlbs = albumMan.getAllAlbums().size();
		sleep();
		assertTrue(finalNumAlbs == origNumAlbs);
	}
	
	public void testPressTakePictureCreateNewAlbum(){
		int origNumAlbs, finalNumAlbs;
		final String name = "Album 6";
		AlertDialog dialog;
				
		final Button button = (Button) mActivity.findViewById(com.cs301w01.meatload.R.id.takePic);
		assertNotNull(button);
		//Positive button is "Create"
		//Negative button is "Choose"

		origNumAlbs = albumMan.getAllAlbums().size();

		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				button.requestFocus();
				button.performClick();
			}
			
		});
		sleep();
		
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				mActivity.performDialogClick(true);
			}
		});
		sleep();
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				mActivity.setDialogEditText(name);
				mActivity.performDialogClick(true);
			}
		});
		sleep();
		finalNumAlbs = albumMan.getAllAlbums().size();
		sleep();
		assertTrue(finalNumAlbs == origNumAlbs + 1);
	}
		
	public void testPressTakePictureChooseAlbum(){
		int origNumPics, finalNumPics;
		final String name = "12345678912345678912";
		AlertDialog dialog;
				
		final Button button = (Button) mActivity.findViewById(com.cs301w01.meatload.R.id.takePic);
		assertNotNull(button);
		//Positive button is "Create"
		//Negative button is "Choose"
		origNumPics = mainMan.getPictureCount();

		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				button.requestFocus();
				button.performClick();
			}
			
		});
		sleep();
		
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				mActivity.performDialogClick(false);
			}
		});
		sleep();
		
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				mActivity.performListViewClick(1);
			}
		});
		
		sleep();
		finalNumPics = mainMan.getPictureCount();
		sleep();
		Log.d("OLD PIC",origNumPics + "");
		Log.d("NEW PIC", finalNumPics + "");
		assertTrue(finalNumPics == origNumPics);
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
