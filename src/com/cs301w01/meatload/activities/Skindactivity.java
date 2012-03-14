package com.cs301w01.meatload.activities;

import com.cs301w01.meatload.controllers.PhotoManager;

import android.app.Activity;
import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: Derek
 * Date: 3/3/12
 * Time: 1:19 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Skindactivity extends Activity implements FView{
	
	public Skindactivity(){
		super();
	}

    //for logger, adds class name labels to log msgs
    protected String tag;
    
    /**public Skindactivity(String className){
        super();

        tag = className;

    }*/
    
    public abstract void update(Object model);

    /**
     * Allows for easy debugging using androids logging system.
     * @param logMsg
     */
    protected void log(String logMsg){
        
        Log.d(tag, logMsg);
        
    }
}