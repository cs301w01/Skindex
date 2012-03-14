package com.cs301w01.meatload;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.cs301w01.meatload.activities.Skindactivity;
import com.cs301w01.meatload.activities.ViewGroupsActivity;
import com.cs301w01.meatload.activities.ViewTagsActivity;
import com.cs301w01.meatload.controllers.GalleryManager;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.controllers.PhotoManager;

import java.util.Collection;

public class Skindex extends TabActivity

//used to extend Skindactivity
{
	private Button newAlbumButton;
	private Button takePictureButton;
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
        	//on click listener 
            final Button button = (Button) findViewById(R.id.takePic);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	takePicture();
                }
            });
        
        
        
        
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
    
    /**TODO
     * Prompt the user to enter an album name and pick a bunch of tags
     * When the user presses OK, gather the name and tags the user entered, 
     * and call photoManager.addNewAlbum();
     * @return the name of the new album (for use in takePicture)
     */
    private String addAlbum(){
    	return null;
    }
    
    /**TODO
     * Display a message asking if the user wants to create a new album
     * populate the boolean with true is the user wants a new album
     * otherwise assume the user wants to choose an album
     * if they hit cancel, exit out of this function
     */
    protected void takePicture(){
    	//Display prompt
    	boolean wantsNewAlbum = false;
    	String newAlbumName = "";
    	if(wantsNewAlbum){
    		newAlbumName = addAlbum();
    	}
    	else{
    		/**TODO
    		 * Prompt the user to pick an album from the list
    		 * Populate this string with the name
    		 */
    		newAlbumName = "";
    	}
    	
    	/**TODO
    	 * we need to start the gallery activity then have the gallery activity
    	 * immediately switch to takePicture, so that when the user goes back, they'll
    	 * end up at the gallery activity.
    	 * Use openGalleryFromAlbum for this maybe?
    	 */
        	//Code on push of stats button
        	Intent myIntent = new Intent();
        	myIntent.setClassName("com.cs301w01.meatload", "com.cs301w01.meatload.activities.TakePictureActivity");
        	startActivity(myIntent); 
    }
    
    /**TODO
     * start a new gallery activity and pass it pMan
     * @param pMan - the PhotoManager object to be used in this activity
     */
    private void openGallery(GalleryManager pMan){
    	
    }
    
    private void openGalleryFromAlbum(String albumName){
    	openGallery(new GalleryManager(albumName, this));
    }
    
    private void openGalleryFromTags(Collection<String> tags){
    	openGallery(new GalleryManager(tags, this));
    }
    
    private void openGalleryAllPhotos(){
    	openGallery(new GalleryManager(this));
    }
}
