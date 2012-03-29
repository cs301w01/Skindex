package com.cs301w01.meatload.activities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.adapters.AlbumAdapter;
import com.cs301w01.meatload.controllers.GalleryManager;
import com.cs301w01.meatload.controllers.MainManager;
import com.cs301w01.meatload.controllers.PictureManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.AlbumGallery;
import com.cs301w01.meatload.model.AllPicturesGallery;
import com.cs301w01.meatload.model.GalleryData;
import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.TagQueryGenerator;

/**
 * Implements the logic in the Albums view of the Tab layout in Skindex.
 * @author Joel Burford
 */
public class ViewAlbumsActivity extends Skindactivity {
	
	//TODO: Can these be moved inside a method?
	private MainManager mainManager;
	private ListView albumListView;
	private AlbumAdapter adapter;
	private AlertDialog currentDialog;
	
//	private int[] adapterIDs = { R.id.itemName, R.id.itemValue };
//	private String[] adapterCols = { "name", "numPictures" };
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewgroups);
        mainManager = new MainManager();
        mainManager.setContext(this);

        refreshScreen();
        createListeners();
        
    }
    
    public void update(Object model) {
    	refreshScreen();
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	refreshScreen();
    	mainManager.setContext(this);
    }
    
    protected void createListeners() {
        //on click listener 
    	final Button takePicButton = (Button) findViewById(R.id.takePic);
    	takePicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	takePicture();
            }
        });
        
        final Button addAlbumButton = (Button) findViewById(R.id.newAlbum);
        addAlbumButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	addAlbum(false); //false means not taking a picture, just adding album
            }
        });
        
        // Below is the listener for a list button click. It takes the id
        // of the clicked item and passes it to the FlogEdit activity so
        // you can edit the selected log.
        
        albumListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                openGallery(adapter.getItem(position));
            }
        });
    }
    
    public void refreshScreen() {
    	albumListView = (ListView) findViewById(R.id.albumListView);
    	
    	ArrayList<Album> albumList = new ArrayList<Album>();
    	albumList.add(new Album("All Pictures", mainManager.getPictureCount(), -1));
    	albumList.addAll(mainManager.getAllAlbums());
		
		adapter = new AlbumAdapter(this, R.layout.list_item, albumList);
		albumListView.setAdapter(adapter);
    }
    
    /* 
     * TODO: Display a message asking if the user wants to create a new album
     * populate the boolean with true is the user wants a new album
     * otherwise assume the user wants to choose an album
     * if they hit cancel, exit out of this function
     */
    protected void takePicture() {
    	//Display prompt
    	boolean wantsNewAlbum = false;
    	String albumName;

        showCreateAlbumPrompt();

    }
    
    private void switchToTakePicture(Album album) {

        Intent goToGallery = new Intent();
        goToGallery.setClassName("com.cs301w01.meatload", 
        		"com.cs301w01.meatload.activities.GalleryActivity");
        goToGallery.putExtra("gallery", new AlbumGallery(album));

        startActivity(goToGallery);

        Intent myIntent = new Intent();
    	myIntent.setClassName("com.cs301w01.meatload", 
    			"com.cs301w01.meatload.activities.TakePictureActivity");
    	myIntent.putExtra("album", album);
    	
    	startActivity(myIntent);
    }

    private void showCreateAlbumPrompt() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("New Album?");
        alert.setMessage("Would you like to create a new album or choose an existing one?");

        alert.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                addAlbum(Boolean.TRUE);
            }
        });

        alert.setNegativeButton("Choose", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                chooseAlbumPrompt();
            }
        });

        alert.show();

    }

    private void chooseAlbumPrompt() {
        //TODO: present list of albums, make user select one, redirect to takePicture
    	final CharSequence[] albumNames = mainManager.albumsToStrings(mainManager.getAllAlbums());
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Choose an Album");
    	builder.setItems(albumNames, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	    	switchToTakePicture(mainManager.getAlbumByName((String)albumNames[item]));
    	    }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();
    }

    /** 
     * Prompts the user to enter an album name and pick a set of tags. When the user presses OK, 
     * gather the name and tags the user entered, and call photoManager.addNewAlbum();
     * @return Name of the new album (for use in takePicture)
     * @see <a href="http://www.androidsnippets.com/prompt-user-input-with-an-alertdialog">
     http://www.androidsnippets.com/prompt-user-input-with-an-alertdialog</a>
     */
    private void addAlbum(final Boolean takePicture) {
    	
    	// Alert code snippet taken from: 
    	// http://www.androidsnippets.com/prompt-user-input-with-an-alertdialog
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		

		alert.setTitle("New Album");
		alert.setMessage("Enter the name of the new album");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		input.setOnKeyListener(new View.OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_ENTER)
				{
					Button positiveButton = currentDialog.getButton(AlertDialog.BUTTON_POSITIVE);
					positiveButton.requestFocus();
					positiveButton.performClick();
				}
				// TODO Auto-generated method stub
				return false;
			}
		});
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String newAlbumName = input.getText().toString();
				newAlbumName = mainManager.addAlbum(newAlbumName, new ArrayList<String>());
				
				if(newAlbumName.length() == 0){
					errorDialog("Adding album failed.  Album names must be " + mainManager.getMaxAlbumName() + 
							" chars, not empty, and unique.");
				} else if (takePicture) {
                	AlbumQueryGenerator albumQueryGenerator = new AlbumQueryGenerator(ViewAlbumsActivity.this); 
                    switchToTakePicture(albumQueryGenerator.getAlbumByName(newAlbumName));
                }

                refreshScreen();
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		currentDialog = alert.show();

    }
   
    
    /**
     * Starts a new gallery activity using the GalleryManager object passed via argument.
     * @param album, the album we are opening the gallery manager on
     */
    private void openGallery(Album album) {
    	GalleryData gDat;
    	if(album.getID() < 0)
    		gDat = new AllPicturesGallery();
    	else
    		gDat = new AlbumGallery(album);
    	Intent myIntent = new Intent();
    	myIntent.setClassName("com.cs301w01.meatload", 
    			"com.cs301w01.meatload.activities.GalleryActivity");
    	myIntent.putExtra("gallery", gDat);
    	
    	startActivity(myIntent); 
    }
    
    
    //Pops up error dialog with given string in message
    private void errorDialog(String err){
    	mainManager.errorDialog(err, this);
    	//AlertDialog.Builder alert = new AlertDialog.Builder(this);
		//alert.setTitle("Error");
		//alert.setMessage(err);
		//alert.show();
    }
    
    private void openGalleryFromAlbum(String albumName) {
    	openGallery(new AlbumQueryGenerator(this).getAlbumByName(albumName));
    }
    
    private void openGalleryFromTags(Collection<String> tags) {
        //openGallery(new GalleryManager(tags, this).getAlbum());
    }
    
    private void openGalleryAllPhotos() {
    	//openGallery(new Album(GalleryManager.ALL_PICTURES_ALBUM_NAME, 0, new ArrayList<Picture>(), 
    			//-1));
    }

}
