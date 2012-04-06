package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.activities.ViewTagsActivity;
import com.cs301w01.meatload.model.SQLiteDBManager;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

public class ViewTagsActivityTest extends ActivityInstrumentationTestCase2<ViewTagsActivity> {
	private Instrumentation mInstrumentation;
	private Context mContext;
	private ViewTagsActivity mActivity;

	public ViewTagsActivityTest() {
		super("com.cs301w01.meatload", ViewTagsActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

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

	public void testBlankTag() {
		// display all possible tags
		assertTrue(1 == 1);
	}

	public void testSpecificTag() {
		// enter AAA and ensure tag AAA is in the list of tags
	}

	public void testGenericTag() {
		// enter AA and ensure all tags with AA in them appear
	}

	public void testAddTag() {
		// Click on tag, ensure it is removed from top group and added to bottom
	}

	public void testTwoTags() {
		// select two tags, ensure both added below and that number of photos is
		// correct
	}

}
