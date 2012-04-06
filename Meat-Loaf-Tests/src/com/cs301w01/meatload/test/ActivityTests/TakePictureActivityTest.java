package com.cs301w01.meatload.test.ActivityTests;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.activities.TakePictureActivity;
import com.cs301w01.meatload.controllers.MainManager;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageView;

public class TakePictureActivityTest extends ActivityInstrumentationTestCase2<TakePictureActivity> {
	private Instrumentation mInstrumentation;
	private Context mContext;
	private TakePictureActivity mActivity;

	public TakePictureActivityTest() {
		super("com.cs301w01.meatload", TakePictureActivity.class);
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
		
		Button cancelButton = (Button) mActivity.findViewById(R.id.cancelDialogButton);
		cancelButton.requestFocus();
		cancelButton.performClick();
		
		int finalPictureCount = mainManager.getPictureCount();
		assertEquals(initialPictureCount, finalPictureCount);
	}

	public void testTakePicOK() {
		// capture Intent and ensure picture saved validly
		// Not implemented yet
		fail();
	}

}
