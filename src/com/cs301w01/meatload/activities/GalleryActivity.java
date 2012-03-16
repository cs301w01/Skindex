package com.cs301w01.meatload.activities;

import java.util.ArrayList;
import java.util.HashMap;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.controllers.GalleryManager;
import com.cs301w01.meatload.controllers.PhotoManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class GalleryActivity extends Skindactivity {
	
	
	ListView photoListView;
	SimpleAdapter adapter;
	
	GalleryManager galleryManager;
	
	private int[] adapterIDs = { R.id.itemName, R.id.itemValue };
	private String[] adapterCols = { "date", "id" };
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);  //That xml doesn't exist yet.
        
        Bundle b = getIntent().getExtras();
        galleryManager = (GalleryManager) b.getSerializable("manager");
        
        TextView albumTitle = (TextView) findViewById(R.id.albumTitle);
        albumTitle.setText(galleryManager.getTitle());
        
        refreshScreen();
        
        /**TODO
         * map objects created as variables to real objects in the XML R.layout.main
         */
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
        
        photoListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
             HashMap<String, String> temp = (HashMap<String, String>) adapter.getItem(position);
             String clickedPhoto = temp.get("id");
             openPhoto(new Integer(clickedPhoto));
            }
        }
        );
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	refreshScreen();
    }

    @Override
    public void update(Object model) {
        
    }
    
    public void refreshScreen(){
    	photoListView = (ListView) findViewById(R.id.photoListView);
        
        ArrayList<HashMap<String, String>> albumList = galleryManager.getPhotoGallery();
        
        adapter = new SimpleAdapter(this, albumList, R.layout.list_item, adapterCols, adapterIDs);
		photoListView.setAdapter(adapter);
    }
    
    private void editAlbum(){
    	
    }
    
    private void takePicture(){
    	Intent myIntent = new Intent();
    	myIntent.setClassName("com.cs301w01.meatload", "com.cs301w01.meatload.activities.TakePictureActivity");
    	PhotoManager pMan = new PhotoManager(galleryManager.getAlbumName());
    	myIntent.putExtra("manager", pMan);
    	
    	startActivity(myIntent); 
    }
    
    private void switchToTakePicture(String album){
    	Intent myIntent = new Intent();
    	myIntent.setClassName("com.cs301w01.meatload", "com.cs301w01.meatload.activities.TakePictureActivity");
    	PhotoManager pMan = new PhotoManager(this, album);
    	myIntent.putExtra("manager", pMan);
    	
    	startActivity(myIntent); 
    }
    
    private void openPhoto(int photoID){
    	Intent myIntent = new Intent();
    	myIntent.setClassName("com.cs301w01.meatload", "com.cs301w01.meatload.activities.EditPictureActivity");
    	PhotoManager pMan = new PhotoManager(photoID);
    	myIntent.putExtra("manager", pMan);
    	
    	startActivity(myIntent); 
    }
}
