package com.cs301w01.meatload.activities;

import java.util.Collection;

import android.widget.EditText;
import android.widget.TextView;
import com.cs301w01.meatload.R;
import com.cs301w01.meatload.controllers.GalleryManager;
import com.cs301w01.meatload.controllers.MainManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.AlbumGallery;
import com.cs301w01.meatload.model.Picture;

/**
 * View Activity which uses GalleryManager to modify Albums.
 * @author Blake Bouchard
 */
public class EditAlbumActivity extends Skindactivity {

	private MainManager mainManager;
    private GalleryManager gMan;
    private Album album;
    
    @Override
    public void update(Object model) {
    	
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.edit_album);
    	   	
    	Bundle b = getIntent().getExtras();
    	AlbumGallery aGal = (AlbumGallery) b.getSerializable("gallery");
    	
		// Set up MainManager
		mainManager = new MainManager();
		mainManager.setContext(this);
    	
    	album = aGal.getAlbum(this);
        gMan = new GalleryManager(new AlbumGallery(album));
        gMan.setContext(this);

        createListeners();
        populate();
    }
    
    /**
     * Populates all dynamic fields on the screen
     */
    private void populate(){
		EditText name = (EditText) findViewById(R.id.edit_album_name);
		name.setText(album.getName());
		
		Collection<Picture> test = gMan.getPictureGallery();
		TextView numView = (TextView) findViewById(R.id.text_num_pics);
		numView.setText("" + gMan.getPictureGallery().size());
		
		TextView dateView = (TextView) findViewById(R.id.text_date);
		dateView.setText(album.getDate().toString());
    }

    private void createListeners() {
        final Button saveAlbumButton = (Button) findViewById(R.id.save_album_button);
        saveAlbumButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(updateAlbumName() > 0){
                	finish();
                }
            }
        });
    }

    /**
     * Checks to see if the user has made changes to the name of the album, if so updates the 
     * album name in the DB.
     * @return int, 1 if album update successful, -1 if albumUpdate failed
     */
    private int updateAlbumName() {
        final EditText albumName = (EditText) findViewById(R.id.edit_album_name);
        if (albumName.length() == 0) {
        	errorDialog("Invalid album name, no changes saved.");
        	return -1;
        }
        String newAlbumName = albumName.getText().toString().trim();       
        String oldAlbumName = album.getName();
        
        if (newAlbumName.length() == 0){
        	errorDialog("Album name cannot be empty, no changes saved.");
        	return -1;
        } else if (newAlbumName.length() > mainManager.getMaxAlbumName()) {
        	newAlbumName = newAlbumName.substring(0, mainManager.getMaxAlbumName());
        }
        
        if(oldAlbumName.equals(newAlbumName)){
        	return 1;
        }
        
        if(mainManager.albumExists(newAlbumName)){
        	errorDialog("Album name already exists, no changes saved.");
        	return -1;
        }
        
        gMan.changeAlbumName(newAlbumName, album);
        return 1;
    }
    
    /**
     * Calls the mainManager, which will print the error to an AlertDialog.
     * @param String, error message to be printed
     */
    private void errorDialog(String err){
    	mainManager.errorDialog(err, this);
    }
}
