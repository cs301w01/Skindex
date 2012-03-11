package com.cs301w01.meatload;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cs301w01.meatload.activities.Skindactivity;
import com.cs301w01.meatload.controllers.PhotoManager;

import java.util.Collection;

public class Skindex extends Skindactivity
{
	private Button newAlbumButton;
	private Button takePictureButton;
	private PhotoManager photoManager;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        photoManager = new PhotoManager();
        
        /**TODO
         * map objects created as variables to real objects in the XML R.layout.main
         */
        
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
    @Override
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
    private void takePicture(){
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
    }
    
    /**TODO
     * start a new gallery activity and pass it pMan
     * @param pMan - the PhotoManager object to be used in this activity
     */
    private void openGallery(PhotoManager pMan){
    	
    }
    
    private void openGalleryFromAlbum(String albumName){
    	openGallery(new PhotoManager(albumName));
    }
    
    private void openGalleryFromTags(Collection<String> tags){
    	openGallery(new PhotoManager(tags));
    }
    
    private void openGalleryAllPhotos(){
    	openGallery(new PhotoManager());
    }
}
