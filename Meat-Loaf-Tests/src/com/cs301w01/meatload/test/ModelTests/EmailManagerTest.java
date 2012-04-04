package com.cs301w01.meatload.test.ModelTests;

import android.test.AndroidTestCase;
import com.cs301w01.meatload.controllers.EmailManager;

/**
 * Created by IntelliJ IDEA.
 * User: Derek
 * Date: 3/21/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailManagerTest extends AndroidTestCase{

    EmailManager mClassToTest;
    
    protected void setUp() throws Exception {
        mClassToTest = new EmailManager();
        
        super.setUp();
    }

    public void testValidateEmail(String address) {

        //assertTrue(mClassToTest.validateEmail(address));

    }

    public void testEmailSubscription() {


    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
