package com.cs301w01.meatload.activities;


import java.util.Collection;

import android.widget.*;
import com.cs301w01.meatload.R;
import com.cs301w01.meatload.adapters.GridViewGalleryAdapter;
import com.cs301w01.meatload.controllers.GalleryManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.gallery.AlbumGallery;
import com.cs301w01.meatload.model.gallery.GalleryData;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;

/**
* Shows all pictures in a gridview as denoted by the GalleryManager object passed in to the
* Activity through the Intent Extras with key "manager".
* @author Isaac Matichuk
* @see GalleryManager
*/
public class GalleryActivity extends Skindactivity {

    //private ListView pictureListView;
    //private SimpleAdapter adapter;

    private GridView gridview;
    private GridViewGalleryAdapter adapter;

    private GalleryManager galleryManager;

    // private int[] adapterIDs = { R.id.itemName, R.id.itemValue };
    // private String[] adapterCols = { "date", "id" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gallery);

        Bundle b = getIntent().getExtras();
        GalleryData gallerydata = (GalleryData) b.getSerializable("gallery");
        galleryManager = new GalleryManager(this, gallerydata);

        adapter = new GridViewGalleryAdapter(this, galleryManager.getPictureGallery());

        gridview = (GridView) findViewById(R.id.viewAlbumGridView);
        gridview.setAdapter(adapter);
                
        populateTextFields(galleryManager.getTitle());
        if (!galleryManager.isAlbum()) {
        	hideButtons();
        }

        refreshScreen();
        
    }
    
    protected void populateTextFields(String title) {

        TextView albumTitle = (TextView) findViewById(R.id.albumTitle);
        albumTitle.setText(title);

    }
    
    /**
     * This method is used to hide buttons that may not be appropriate depending on how
     * a gallery is viewed.
     */
    protected void hideButtons() {
    	 final Button editAlbumButton = (Button) findViewById(R.id.editAlbum);
    	 editAlbumButton.setEnabled(false);
    	 editAlbumButton.setVisibility(Button.INVISIBLE);
    	 
    	 final Button takePictureButton = (Button) findViewById(R.id.takePic);
    	 takePictureButton.setEnabled(false);
    	 takePictureButton.setVisibility(Button.INVISIBLE);
    }
    
    protected void createListeners() {

        final Button editAlbumButton = (Button) findViewById(R.id.editAlbum);
        editAlbumButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                editAlbum();
            }
        });

        final Button takePictureButton = (Button) findViewById(R.id.takePic);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                takePicture();
            }
        });

        final Button comparePictureButton = (Button) findViewById(R.id.comparePic);
        comparePictureButton.setOnClickListener(new View.OnClickListener() {			
				public void onClick(View v) {
					startComparePicture();
				}
		});
        
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Picture selectedPic = adapter.getItem(position);
                Log.d("Info For PictureListener", selectedPic.toString());
                int pictureID = selectedPic.getPictureID();
                openPicture(pictureID);
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == Activity.RESULT_CANCELED) {
    		finish();
    	}
    }
    
    @Override
    protected void onResume() {
        super.onResume();

     galleryManager.setContext(this);
        //galleryManager.updateAlbum();

        refreshScreen();
    }

    @Override
    public void update(Object model) {
    	refreshScreen();
    }
    
    /**
     * Used to start a new ComparePictureActivity.
     */
    private void startComparePicture() {
    	
    	Intent myIntent = new Intent();
		myIntent.setClassName("com.cs301w01.meatload",
				"com.cs301w01.meatload.activities.ComparePicturesActivity");
		myIntent.putExtra("gallery", galleryManager.getGallery());
		
		startActivity(myIntent);
    	
    }
    
    /**
     * @see GalleryManager
     */
    public void refreshScreen() {

        adapter.notifyDataSetInvalidated();

        createListeners();

        Collection<Picture> albumPictures = galleryManager.getPictureGallery();

        //adapter = new SimpleAdapter(this, albumPictures, R.layout.list_item, adapterCols, adapterIDs);
        adapter = new GridViewGalleryAdapter(this, albumPictures);

        gridview.setAdapter(adapter);

        populateTextFields(galleryManager.getTitle());
    }
    
    private void editAlbum() {
    	
    	AlbumGallery aGal = (AlbumGallery) galleryManager.getGallery();

		// Launch the EditAlbumActivity with a given GalleryManager
		Intent myIntent = new Intent();
		myIntent.setClassName("com.cs301w01.meatload",
				"com.cs301w01.meatload.activities.EditAlbumActivity");
		Log.d("GalleryActivity", "EDITING ALBUM, NAME:" + aGal.getAlbum(this).getName());

		myIntent.putExtra("gallery", aGal);
		startActivityForResult(myIntent, 0);

    }
    
    /**
	 * Starts a new TakePictureActivity using the Album referred to by the GalleryManager object
	 * in the GalleryActivity state. Can only be used if a true album is selected.
	 * @see TakePictureActivity
	 */
    private void takePicture() {

        Intent myIntent = new Intent();
        
        AlbumGallery aGal = (AlbumGallery) galleryManager.getGallery();
    
        myIntent.setClassName("com.cs301w01.meatload",
             "com.cs301w01.meatload.activities.TakePictureActivity");

        Log.d("Taking Picture", "ALBUM NAME:" + aGal.getAlbum(this).getName());

        myIntent.putExtra("album", aGal.getAlbum(this));

        startActivity(myIntent);

    }
    
    /**
	 * Starts a new EditPictureActivity using the Picture object referred to by the pictureID
	 * argument.
	 * <p>
	 * Passes a PictureManager as part of the Intent.
	 * @param pictureID The tuple ID of the picture to be opened.
	 */
    private void openPicture(int pictureID) {

        Intent myIntent = new Intent();
        myIntent.setClassName("com.cs301w01.meatload",
        "com.cs301w01.meatload.activities.EditPictureActivity");
        myIntent.putExtra("picture", new PictureQueryGenerator(this).selectPictureByID(pictureID));

        startActivity(myIntent);
    }
    
}