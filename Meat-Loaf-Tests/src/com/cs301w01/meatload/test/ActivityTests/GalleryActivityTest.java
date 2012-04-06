package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.activities.GalleryActivity;
import com.cs301w01.meatload.model.Album;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

public class GalleryActivityTest extends ActivityInstrumentationTestCase2<GalleryActivity> {
	private Context mContext;
	private GalleryActivity mActivity;

	public GalleryActivityTest() {
		super("com.cs301w01.meatload", GalleryActivity.class);
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

	public void testNumberOfPictures() {

	}

}
