package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.activities.TakePictureActivity;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.model.Album;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

public class TakePictureActivityTest extends ActivityInstrumentationTestCase2<TakePictureActivity> {

	private Context mContext;
	private TakePictureActivity mActivity;
	private final int SLEEP_TIME = 1000;

	public TakePictureActivityTest() {
		super("com.cs301w01.meatload", TakePictureActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		Album album = new Album("album", 0, 1);
		Intent intent = new Intent();
		intent.putExtra("album", album);
		setActivityIntent(intent);
		mActivity = getActivity();
		mContext = mActivity.getBaseContext();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		if (mActivity != null) {
			mActivity.finish();
		}

	}

	public void testNumberOfComparePicturesLoaded() {
		// Not implemented yet
		fail();
	}

	public void testGeneratePic() {
		// ensure new photo is populated
		ImageView imageView = (ImageView) mActivity.findViewById(R.id.imgDisplay);
		assertNotNull(imageView.getDrawable());
	}

	public void testTakePicCancel() {
		// press cancel in the dialog and ensure database does not change
		MainManager mainManager = new MainManager(mContext);
		int initialPictureCount = mainManager.getPictureCount();

		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				mActivity.performDialogClick(false);
			}
		});
		sleep();
		
		int finalPictureCount = mainManager.getPictureCount();
		assertEquals(initialPictureCount, finalPictureCount);
	}

	public void testTakePicOK() {
		// capture Intent and ensure picture saved validly
		// Not implemented yet
		fail();
	}
	
	private void sleep() {
		try {
			Thread.sleep(SLEEP_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
			assertTrue("Sleep failed", false);
		}
	}

}
