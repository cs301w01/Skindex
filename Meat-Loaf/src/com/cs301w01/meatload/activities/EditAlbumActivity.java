package com.cs301w01.meatload.activities;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.controllers.GalleryManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * View Activity which uses GalleryManager to modify Albums.
 * @author Blake Bouchard
 */
public class EditAlbumActivity extends Skindactivity {
	String aName;

    @Override
    public void update(Object model) {
    	
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.edit_album);
    	
    	
    	Bundle b = getIntent().getExtras();
        aName = (String) b.getSerializable("albumName");
        
        
    }
}
