package com.cs301w01.meatload.controllers;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;

import com.cs301w01.meatload.model.DBManager;
import com.cs301w01.meatload.model.Photo;


/**
 * Created by IntelliJ IDEA.
 * User: Derek
 * Date: 3/3/12
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */


public class PhotoManager implements FController{

	DBManager dbMan;
	Photo photo;
	
    public PhotoManager(Context context, Photo photo){
    	dbMan = new DBManager(context);
    	this.photo = photo;
    }
    
    public PhotoManager(Context context){
    	dbMan = new DBManager(context);
    	this.photo = null;
    }
}
