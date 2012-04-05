package com.cs301w01.meatload.test.ActivityTests;

import java.util.ArrayList;

import com.cs301w01.meatload.activities.ViewAlbumsActivity;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.model.Album;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;

public class ViewAlbumActivityTest extends ActivityInstrumentationTestCase2<ViewAlbumsActivity> {
    
	private Instrumentation mInstrumentation;
    private Context mContext;
    private ViewAlbumsActivity mActivity;
	MainManager mainMan;
    private final int SLEEP_TIME = 150;
    
    //private Solo solo;
    
	public ViewAlbumActivityTest(){
		super("com.cs301w01.meatload", ViewAlbumsActivity.class);
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
        
    	//solo = new Solo(getInstrumentation(), getActivity());
    	
        mInstrumentation = getInstrumentation();
        mContext = mInstrumentation.getContext();
        mActivity = getActivity();
        
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
		
		origAlbs = mainMan.getAllAlbums();
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
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
					assertTrue("Sleep failed", false);
				}
			}
			finalAlbs = mainMan.getAllAlbums();
			
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
				assertTrue("Sleep failed", false);
			}
			
		assertTrue(finalAlbs.size() == origAlbs.size() + 3);
		
	}
	
	
	//THIS TEST HAS ERRORS AND IS NOT GETTING THE VIEWS CORRECTLY
	public void testAddAlbumsUsingDialog() {
		
		
//////////////TEMP ROBO CODE

//		solo.clickOnText("New Album");
//		//solo.enterText(index, text)
//		solo.clickOnText("Ok");
//		assert(solo.searchText("New Album"));

		//solo.clickOnButton("New Album");
		
///////////////TEMP ROBO CODE
		
		int origNumAlbs, finalNumAlbs;
		final String name = "abc";
		AlertDialog dialog;
				
		final Button button = (Button) mActivity.findViewById(com.cs301w01.meatload.R.id.newAlbum);
		assertNotNull(button);
				
		origNumAlbs = mainMan.getAllAlbums().size();

		mActivity.runOnUiThread(new Runnable() {
			
			public void run() {
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
		
		mActivity.runOnUiThread(new Runnable() {
			
			public void run() {
				mActivity.setDialogEditText("Album 5");
				mActivity.performDialogClick(true);
			}
			
		});

		try {
			Thread.sleep(SLEEP_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
			assertTrue("Sleep failed", false);
		}

		finalNumAlbs = mainMan.getAllAlbums().size();
		
		try {
			Thread.sleep(SLEEP_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
			assertTrue("Sleep failed", false);
		}
			
		assertTrue(finalNumAlbs == origNumAlbs + 1);
		//TODO: WHY IS THIS TEST FAILING?!?!
		//		THE ALBUM IS BEING ENTERED IN THE DATABASE, WHATS UP WITH THAT YO!
	}

}
