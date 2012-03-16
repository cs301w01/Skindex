package com.cs301w01.meatload.activities;

import com.cs301w01.meatload.R;

import android.os.Bundle;

/**
 * View Activity which uses GalleryManager to modify Albums.
 * @author Blake Bouchard
 */
public class EditAlbumActivity extends Skindactivity {

    @Override
    public void update(Object model) {
        
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.edit_album);
    }
}
