package com.cs301w01.meatload.activities;

import java.util.ArrayList;
import java.util.HashMap;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.controllers.GalleryManager;
import com.cs301w01.meatload.controllers.PictureManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Shows all pictures in a gallery as denoted by the GalleryManager object passed in to the 
 * Activity through the Intent Extras with key "manager".
 * @author Isaac Matichuk
 * @see GalleryManager
 */
public class GalleryActivity extends Skindactivity {
		
	ListView pictureListView;
	SimpleAdapter adapter;
	
	GalleryManager galleryManager;
	
	private int[] adapterIDs = { R.id.itemName, R.id.itemValue };
	private String[] adapterCols = { "date", "id" };
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        
        Bundle b = getIntent().getExtras();
        galleryManager = (GalleryManager) b.getSerializable("manager");
        galleryManager.setContext(this);
        
        TextView albumTitle = (TextView) findViewById(R.id.albumTitle);
        albumTitle.setText(galleryManager.getTitle());
        
        refreshScreen();
        
        // TODO: Map objects created as variables to real objects in the XML R.layout.main
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
        
        pictureListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
             HashMap<String, String> temp = (HashMap<String, String>) adapter.getItem(position);
             String clickedPhoto = temp.get("id");
             openPhoto(new Integer(clickedPhoto));
            }
        });
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	refreshScreen();
    	galleryManager.setContext(this);
    }

    @Override
    public void update(Object model) {
        
    }
    
    /**
     * @see GalleryManager
     */
    public void refreshScreen() {
    	pictureListView = (ListView) findViewById(R.id.pictureListView);   
        ArrayList<HashMap<String, String>> albumList = galleryManager.getPictureGallery();
        adapter = new SimpleAdapter(this, albumList, R.layout.list_item, adapterCols, adapterIDs);
		pictureListView.setAdapter(adapter);
    }
    
    private void editAlbum() {
    	
    }
    
    /**
     * Starts a new TakePictureActivity using the Album referred to by the GalleryManager object
     * in the GalleryActivity state.
     * @see TakePictureActivity
     */
    private void takePicture() {
    	Intent myIntent = new Intent();
    	myIntent.setClassName("com.cs301w01.meatload", 
    			"com.cs301w01.meatload.activities.TakePictureActivity");
    	Log.d("derp", "ALBUM NAME:" + galleryManager.getAlbumName());
    	// TODO: Should not care about albumName if galleryManager is based off of tags or "all"
    	PictureManager pMan = new PictureManager(galleryManager.getAlbumName());
    	myIntent.putExtra("manager", pMan);
    	
    	startActivity(myIntent); 
    }
    
    /**
     * Starts a new EditPictureActivity using the Picture object referred to by the photoID
     * argument.
     * <p>
     * Passes a PictureManager as part of the Intent. 
     * @param photoID The tuple ID of the photo to be opened.
     */
    private void openPhoto(int photoID) {
    	Intent myIntent = new Intent();
    	myIntent.setClassName("com.cs301w01.meatload", 
    			"com.cs301w01.meatload.activities.EditPictureActivity");
    	PictureManager pMan = new PictureManager(photoID);
    	myIntent.putExtra("manager", pMan);
    	
    	startActivity(myIntent); 
    }
}
