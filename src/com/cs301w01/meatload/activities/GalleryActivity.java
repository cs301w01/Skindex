package com.cs301w01.meatload.activities;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.controllers.PhotoManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GalleryActivity extends Skindactivity {
	
	Button editAlbumButton;
	Button takePictureButton;
	
	PhotoManager photoManager;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.gallery);  //That xml doesn't exist yet.
        
        /**TODO
         * this activity should some how be passed a PhotoManager by it's caller
         * I'm not entirely sure how to do this, but it's possible.
         */
        photoManager = new PhotoManager(this);
        
        /**TODO
         * map objects created as variables to real objects in the XML R.layout.main
         */
        
        editAlbumButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				editAlbum();
			}
		});
        
        takePictureButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				takePicture();
			}
		});
        
        /**TODO
         * we should add all the other listeners here.
         */
        
        /**TODO
         * should we call update here... we probably want to update the screen right away
         */
    }

    @Override
    public void update(Object model) {
        
    }
    
    private void editAlbum(){
    	
    }
    
    private void takePicture(){
    	
    }
}
