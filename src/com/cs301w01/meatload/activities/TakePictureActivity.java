package com.cs301w01.meatload.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import com.cs301w01.meatload.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class TakePictureActivity extends Skindactivity {
	private Bitmap imgOnDisplay;
	
	public void onCreate(Bundle savedInstanceState) {
		
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.take_picture);
	    
	    imgOnDisplay = generatePicture();
    	ImageView image = (ImageView) findViewById(R.id.imgDisplay);
    	image.setImageBitmap(imgOnDisplay);
	    
	    //Take Picture button listener
        final Button takePicButton = (Button) findViewById(R.id.takePic);
        takePicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	takePicture();
            }
        });
        
	    //Generate Picture button listener
        final Button genPicButton = (Button) findViewById(R.id.genPic);
        genPicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	imgOnDisplay = generatePicture();
            	
            	//used http://stackoverflow.com/questions/6772024/how-to-update-or-change-images-of-imageview-dynamically-in-android
            	ImageView image = (ImageView) findViewById(R.id.imgDisplay);
            	image.setImageBitmap(imgOnDisplay);
            }
        });
	}

    //@Override
    public void update(Object model) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    
    //take imgOnDisplay, save it to file, do all DB mumbo jumbo
    //http://stackoverflow.com/questions/649154/android-bitmap-save-to-location
    private void takePicture(){
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy_HH-mm-sss");
    	String timestamp = sdf.format(cal.getTime());
    	String fname = "img-" + timestamp + ".PNG";
    	String fpath = getFilesDir().toString() + "/";

    	try{
    		OutputStream outStream = null;
    		File file = new File(getFilesDir(), fname);

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
    //Used http://developer.android.com/reference/android/graphics/Bitmap.html#createBitmap%28int[],%20int,%20int,%20int,%20int,%20android.graphics.Bitmap.Config%29
    //http://developer.android.com/reference/android/graphics/Color.html
    //http://developer.android.com/reference/android/widget/ImageView.html
    //http://docs.oracle.com/javase/1.4.2/docs/api/java/util/Random.html
    //http://developer.android.com/reference/android/graphics/BitmapFactory.html
    
    private Bitmap generatePicture(){
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
