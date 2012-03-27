package com.cs301w01.meatload.activities;

import java.util.Collection;

import android.widget.EditText;
import android.widget.TextView;
import com.cs301w01.meatload.R;
import com.cs301w01.meatload.controllers.GalleryManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.Picture;

/**
 * View Activity which uses GalleryManager to modify Albums.
 * @author Blake Bouchard
 */
public class EditAlbumActivity extends Skindactivity {

    private GalleryManager gMan;

    @Override
    public void update(Object model) {
    	
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.edit_album);
    	
    	Bundle b = getIntent().getExtras();
        Album a = (Album) b.getSerializable("album");
        gMan = new GalleryManager(a, this);

        createListeners();
        populate();
    }
    
    private void populate(){
		EditText name = (EditText) findViewById(R.id.edit_album_name);
		name.setText(gMan.getAlbumName());
		
		Collection<Picture> test = gMan.getPictureGallery();
		TextView numView = (TextView) findViewById(R.id.text_num_pics);
		numView.setText("" + gMan.getPictureGallery().size());
		
		TextView dateView = (TextView) findViewById(R.id.text_date);
		dateView.setText(gMan.getDate().toString());
    }

    private void createListeners() {
        final Button saveAlbumButton = (Button) findViewById(R.id.save_album_button);
        saveAlbumButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateAlbumName();
                finish();
            }
        });
    }

    /**
     * Checks to see if the user has made changes to the name of the album, if so updates the 
     * album name in the DB.
     */
    private void updateAlbumName() {
        final EditText albumName = (EditText) findViewById(R.id.edit_album_name);
        if (albumName.length() == 0) {
        	return;
        }
        String newAlbumName = albumName.getText().toString();
        
        String oldAlbumName = gMan.getAlbum().getName();
        if (!oldAlbumName.equals(newAlbumName) && newAlbumName.length() > 0) {
            gMan.changeAlbumName(newAlbumName, gMan.getAlbum());
        }
    }
}
