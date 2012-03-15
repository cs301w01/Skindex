package com.cs301w01.meatload.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.cs301w01.meatload.model.DBManager;
import com.cs301w01.meatload.model.Photo;


/**
 * Created by IntelliJ IDEA.
 * User: Derek
 * Date: 3/3/12
 * Time: 1:14 PM
 * To change this template use File | Settings | File Templates.
 */


public class PhotoManager implements FController, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//DBManager dbMan;
	Photo photo;
	String albumName;
	private Bitmap imgOnDisplay;
	
	public PhotoManager(Context context, String albumName){
		//dbMan = new DBManager(context);
		this.albumName = albumName;
	}
	
    public PhotoManager(Context context, Photo photo){
    	//dbMan = new DBManager(context);
    	this.photo = photo;
    }
    
    public PhotoManager(Context context){
    	//dbMan = new DBManager(context);
    	this.photo = null;
    }
    
    //take imgOnDisplay, save it to file, do all DB mumbo jumbo
    //http://stackoverflow.com/questions/649154/android-bitmap-save-to-location
    public void takePicture(File path){
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy_HH-mm-sss");
    	String timestamp = sdf.format(cal.getTime());
    	String fname = "img-" + timestamp + ".PNG";
    	String fpath = path.toString() + "/";

    	try{
    		OutputStream outStream = null;
    		File file = new File(path, fname);

    		outStream = new FileOutputStream(file);
    		imgOnDisplay.compress(Bitmap.CompressFormat.PNG, 100, outStream);
    		outStream.flush();
    		outStream.close();
    		Log.d("SAVE", "Saving " + fpath + fname);
        }
        catch(IOException e){
        	Log.d("ERROR", "Unable to write " + fpath + fname);
        }
        
        //Writing file "fname" to path "fpath".  Seems to be succesful.  Now
        //we have to write this information to the database and move to
        //EditPicture activity
    }
    
    
    //Generates a random bitmap
    //Used http://developer.android.com/reference/android/graphics/Bitmap.html#createBitmap
    //%28int[],%20int,%20int,%20int,%20int,%20android.graphics.Bitmap.Config%29
    //http://developer.android.com/reference/android/graphics/Color.html
    //http://developer.android.com/reference/android/widget/ImageView.html
    //http://docs.oracle.com/javase/1.4.2/docs/api/java/util/Random.html
    //http://developer.android.com/reference/android/graphics/BitmapFactory.html
    public Bitmap generatePicture(){
    	int height = 200;
    	int width = 200;
    	int[] colors = new int[height*width];
    	int r,g,b;
    	int test = 0;
        
    	Random gen = new Random(System.currentTimeMillis());
		r = gen.nextInt(256);
		g = gen.nextInt(256);
		b = gen.nextInt(256);
		
        for (int i = 0; i < height; i++){
        	if (test > 15){
        		r = gen.nextInt(256);
        		g = gen.nextInt(256);
        		b = gen.nextInt(256);
        		test = 0;
        	} else {
        		test++;
        	}
        	
        	for (int j = 0; j < width; j++){
        		colors[i * height + j] = Color.rgb(r,g,b);
        	}
        }
        
        imgOnDisplay = Bitmap.createBitmap(colors, width, height, Bitmap.Config.RGB_565);

        return imgOnDisplay;
    }
    
    
}
