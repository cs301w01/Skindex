package com.cs301w01.meatload.activities;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.controllers.PictureManager;
import com.cs301w01.meatload.model.Picture;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Takes a picture and displays it in exploded view along with important metadata including
 * tags, date, etc.
 * <p>
 * Gives the user an exploded view of the picture being edited. 
 * <p>
 * Allows user to change certain metadata such as tags and album.
 * @author Blake Bouchard
 */
public class EditPictureActivity extends Skindactivity {

	PictureManager pictureManager;
	
	@Override
	public void update(Object model) {
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_picture);
		
		// Get picture object from Intent's extras bundle
		pictureManager = (PictureManager) getIntent().getExtras().getSerializable("manager");
		pictureManager.setContext(this);
		Picture picture = pictureManager.getPicture();
		
		// Set pictureView to path provided by Picture object
		ImageView pictureView = (ImageView) findViewById(R.id.pictureView);
		pictureView.setImageDrawable(Drawable.createFromPath(picture.getPath()));
		
		// Set dateView to toString representation of Date in Picture object
		TextView dateView = (TextView) findViewById(R.id.dateView);
		dateView.setText(picture.getDate().toString());
		
		// Set albumView to string representation of Album in Picture object
		TextView albumView = (TextView) findViewById(R.id.albumView);
		albumView.setText(picture.getAlbumName());
		
		Button changeAlbumButton = (Button) findViewById(R.id.changeAlbumButton);
		// TODO: Add Change Album functionality to EditPicture
		
		// TODO: Add Edit Tags functionality to EditPicture
	}
	
	@Override
    protected void onResume() {
    	super.onResume();
    	pictureManager.setContext(this);
    }
    
}