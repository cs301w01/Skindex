package com.cs301w01.meatload.activities;

import java.util.Random;

import com.cs301w01.meatload.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
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
	    
	    //button stuff
        final Button takePicButton = (Button) findViewById(R.id.takePic);
        takePicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	takePicture();
            }
        });
        
	    //button stuff
        final Button genPicButton = (Button) findViewById(R.id.genPic);
        genPicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	imgOnDisplay = generatePicture();
            	
            	//used http://stackoverflow.com/questions/6772024/how-to-update-or-change-images-of-imageview-dynamically-in-android
            	ImageView image = (ImageView) findViewById(R.id.imgDisplay);
            	image.setImageBitmap(imgOnDisplay);
                //image.setImageResource(R.drawable.tab_icon_grey);
            }
        });
	}

    //@Override
    public void update(Object model) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    
    //take imgOnDisplay, save it to file, do all DB mumbo jumbo
    private void takePicture(){
    	
    }
    
    
    //Generates a random bitmap
    private Bitmap generatePicture(){
    	int height = 200;
    	int width = 200;
    	int[] colors = new int[height*width];
    	int a,r,g,b;
    	a = 255;
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
        	
        	colors[i * height + j] = Color.argb(a,r,g,b);
        	}
        }
        
        imgOnDisplay = Bitmap.createBitmap(colors, width, height, Bitmap.Config.RGB_565);

        return imgOnDisplay;
    }
}
