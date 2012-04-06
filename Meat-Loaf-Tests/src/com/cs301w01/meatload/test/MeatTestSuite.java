package com.cs301w01.meatload.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import com.cs301w01.meatload.test.ActivityTests.*;

public class MeatTestSuite extends TestCase {

	public MeatTestSuite(String name) {
		super(name);
	}
	
	static public Test suite() {
		TestSuite suite = new TestSuite();
		    
		suite.addTestSuite(DatabaseTest.class);
		suite.addTestSuite(ViewAlbumActivityTest.class);
		suite.addTestSuite(EditPictureActivityTest.class);
		suite.addTestSuite(TakePictureActivityTest.class);
		suite.addTestSuite(ViewTagsActivityTest.class);
		    
		return suite;
	}

}
