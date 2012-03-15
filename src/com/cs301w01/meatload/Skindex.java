package com.cs301w01.meatload;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

import com.cs301w01.meatload.activities.Skindactivity;
import com.cs301w01.meatload.activities.ViewGroupsActivity;
import com.cs301w01.meatload.activities.ViewTagsActivity;
import com.cs301w01.meatload.controllers.GalleryManager;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.controllers.PhotoManager;
import com.cs301w01.meatload.model.Album;

import java.util.Collection;

public class Skindex extends TabActivity

//used to extend Skindactivity
{
	private MainManager mainManager;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //BEGIN TAB CODE////////////////////////
        //COPIED FROM http://developer.android.com/resources/tutorials/views/hello-tabwidget.html
        	Resources res = getResources(); // Resource object to get Drawables
        	TabHost tabHost = getTabHost();  // The activity TabHost
        	TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        	Intent intent;  // Reusable Intent for each tab

        	// Create an Intent to launch an Activity for the tab (to be reused)
        	intent = new Intent().setClass(this, ViewGroupsActivity.class);

        	// Initialize a TabSpec for each tab and add it to the TabHost
        	spec = tabHost.newTabSpec("albums").setIndicator(this.getString(R.string.tab_albums),
                           res.getDrawable(R.drawable.tab_main))
                         .setContent(intent);
        	tabHost.addTab(spec);

        	// Do the same for the other tabs
        	intent = new Intent().setClass(this, ViewTagsActivity.class);
        	spec = tabHost.newTabSpec("tags").setIndicator(this.getString(R.string.tab_tags),
                              res.getDrawable(R.drawable.tab_main))
                          .setContent(intent);
        	tabHost.addTab(spec);


        	tabHost.setCurrentTab(0);
        ///END TAB CODE/////////////////////////
        

        //Current tab set to Album.  ViewGroupsActivity runs in that tab
        	
        
        
        
        
        /**TODO
         * map objects created as variables to real objects in the XML R.layout.main
         */
        /*
        newAlbumButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				addAlbum();
			}
		});
        
        takePictureButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				takePicture();
			}
		});
		*/
        
        /**TODO
         * we should add all the other listeners here.
         * Use the openGallery functions to open the gallery activity
         */
        
        /**TODO
         * should we call update here... we probably want to update the screen right away
         */
    }
    
    /**TODO
     * use selectAllAlbums and selectAllTags to get the lists you need to
     * populate the screen
     */
    //@Override
    public void update(Object model) {
        //update whatever screen is up.
    }
    
}
