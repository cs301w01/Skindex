package com.cs301w01.meatload.activities;

import com.cs301w01.meatload.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class TakePictureActivity extends Skindactivity {
	
	public void onCreate(Bundle savedInstanceState) {
		Bitmap imgOnDisplay;
		
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.take_picture);
	    
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
            	generatePicture();
            }
        });
	}

    //@Override
    public void update(Object model) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    
    private void takePicture(){

    }
    
    private void generatePicture(){
    	//used http://stackoverflow.com/questions/6772024/how-to-update-or-change-images-of-imageview-dynamically-in-android
    	ImageView image = (ImageView) findViewById(R.id.imgDisplay);
        image.setImageResource(R.drawable.tab_icon_grey);
    }
}
