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
		    
		return suite;
	}

	protected void setUp() throws Exception {
		//TODO: RESET THE DB
		super.setUp();
	}

	protected void tearDown() throws Exception {
		//TODO: RESET THE DB
		super.tearDown();
	}
	
}
