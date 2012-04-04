package com.cs301w01.meatload.test.ModelTests;

import java.util.ArrayList;

import android.app.Instrumentation;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.cs301w01.meatload.Skindex;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.model.Album;


//JOEL USED CODE FROM HERE
//http://www.java2s.com/Open-Source/Android/android-core/platform-cts/android/app/cts/DialogTest.java.htm
public class SkindexTest extends ActivityInstrumentationTestCase2<Skindex> {
    private Instrumentation mInstrumentation;
    private Context mContext;
    private Skindex mActivity;
    
	public SkindexTest(){
		super("com.cs301w01.meatload", Skindex.class);
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



}
