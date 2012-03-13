package com.cs301w01.meatload.activities;

import java.util.Collection;

import com.cs301w01.meatload.controllers.PhotoManager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class ViewGroupsActivity extends Skindactivity {
	private PhotoManager photoManager;

    //@Override
    public void update(Object model) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Albums tab");
        setContentView(textview);
        
        photoManager = new PhotoManager(this);
        Collection<String> aNames = photoManager.getAlbumNames();
    }

}
