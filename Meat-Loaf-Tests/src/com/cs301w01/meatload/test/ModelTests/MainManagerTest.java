package com.cs301w01.meatload.test.ModelTests;

import android.test.AndroidTestCase;
import com.cs301w01.meatload.*;
import com.cs301w01.meatload.controllers.MainManager;

public class MainManagerTest extends AndroidTestCase {
	MainManager mClassToTest;
	int mArg1;
	int mArg2;
 
	protected void setUp() throws Exception {
		//mClassToTest=new MainManager();
		mArg1=6;
		mArg2=3;
		super.setUp();
	}
 
	protected void tearDown() throws Exception {
		super.tearDown();
	}
 
	public void testAdd(){
		assertEquals(9,9);
		assertEquals(5,4);
		//assertEquals(9,mClassToTest.add(mArg1,mArg2));
	}
}
