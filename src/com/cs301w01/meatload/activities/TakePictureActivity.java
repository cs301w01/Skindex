package com.cs301w01.meatload.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.controllers.GalleryManager;
import com.cs301w01.meatload.controllers.PhotoManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class TakePictureActivity extends Skindactivity {
	private Bitmap imgOnDisplay;
	
	private PhotoManager photoManager;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.take_picture);
	    
	    Bundle b = getIntent().getExtras();
        photoManager = (PhotoManager) b.getSerializable("manager");
        photoManager.setContext(this);
	    
	    imgOnDisplay = photoManager.generatePicture();
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
            	imgOnDisplay = photoManager.generatePicture();
            	
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
    
    private void takePicture(){
    	
      //Alert code snippet taken from http://www.androidsnippets.com/prompt-user-input-with-an-alertdialog
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Confirm");
		alert.setMessage("Are you sure you want this picture?");

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
	        photoManager.takePicture(getFilesDir());
			  finish();
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
    }
    
}
