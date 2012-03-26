package com.cs301w01.meatload.activities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.*;
import com.cs301w01.meatload.R;
import com.cs301w01.meatload.controllers.GalleryManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import com.cs301w01.meatload.model.Album;
import com.cs301w01.meatload.model.Picture;
import com.cs301w01.meatload.model.querygenerators.PictureQueryGenerator;

/**
 * Shows all pictures in a gallery as denoted by the GalleryManager object passed in to the 
 * Activity through the Intent Extras with key "manager".
 * @author Isaac Matichuk
 * @see GalleryManager
 */
public class GalleryActivity extends Skindactivity {
		
	//private ListView pictureListView;
	//private SimpleAdapter adapter;

    private Gallery gallery;
    private ImageAdapter adapter;
	
	private GalleryManager galleryManager;
	
//	private int[] adapterIDs = { R.id.itemName, R.id.itemValue };
//	private String[] adapterCols = { "date", "id" };
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gallery);

        Bundle b = getIntent().getExtras();
        Album album = (Album) b.getSerializable("album");
        galleryManager = new GalleryManager(album, this);


        adapter = new ImageAdapter(this, galleryManager.getAlbum().getPictures());

        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(adapter);
                
        populateTextFields(galleryManager.getTitle());

        refreshScreen();
        
    }
    
    protected void populateTextFields(String title){

        TextView albumTitle = (TextView) findViewById(R.id.albumTitle);
        albumTitle.setText(title);

    }
    
    protected void createListeners(){
        // TODO: Map objects created as variables to real objects in the XML R.layout.main

        final Button editAlbumButton = (Button) findViewById(R.id.editAlbum);
        editAlbumButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				editAlbum(galleryManager);
			}
		});

        final Button takePictureButton = (Button) findViewById(R.id.takePic);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				takePicture();
			}
		});

        gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Picture selectedPic = adapter.getItem(position);
                Log.d("Info For PictureListener", selectedPic.toString());
                int pictureID = selectedPic.getPictureID();


                openPicture(pictureID);
            }
        });

//        pictureListView.setOnItemClickListener(new OnItemClickListener() {
//
//            public void onItemClick(AdapterView<?> parent, View view,
//
//                 int position, long id) {
//                 HashMap<String, String> temp = (HashMap<String, String>) adapter.getItem(position);
//                 String clickedPicture = temp.get("id");
//                 openPicture(new Integer(clickedPicture));
//
//            }
//
//        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();

    	galleryManager.setContext(this);
        galleryManager.updateAlbum();

        refreshScreen();
    }

    @Override
    public void update(Object model) {

    }
    
    /**
     * @see GalleryManager
     */
    public void refreshScreen() {
    	
    	//TODO: MERGE WITH UPDATE

        adapter.notifyDataSetInvalidated();

        createListeners();

//    	pictureListView = (ListView) findViewById(R.id.pictureListView);
        Collection<Picture> albumPictures = galleryManager.getAlbum().getPictures();

        //adapter = new SimpleAdapter(this, albumPictures, R.layout.list_item, adapterCols, adapterIDs);
        adapter = new ImageAdapter(this, albumPictures);

        gallery.setAdapter(adapter);

//		pictureListView.setAdapter(adapter);

    }
    
    private void editAlbum(GalleryManager gm) {

    	//Launch the EditAlbumActivity with a given GalleryManager
    	Intent myIntent = new Intent();
    	myIntent.setClassName("com.cs301w01.meatload", 
    			"com.cs301w01.meatload.activities.EditAlbumActivity");
    	Log.d("GalleryActivity", "EDITING ALBUM, NAME:" + galleryManager.getAlbumName());
    	
    	//NEED TO SET TAGS AS WELL!
    	
    	myIntent.putExtra("album", galleryManager.getAlbum());
    	
    	startActivity(myIntent);

    }
    
    /**
     * Starts a new TakePictureActivity using the Album referred to by the GalleryManager object
     * in the GalleryActivity state. Can only be used if a true album is selected.
     * @see TakePictureActivity
     */
    private void takePicture() {

        Intent myIntent = new Intent();
    	
    	myIntent.setClassName("com.cs301w01.meatload", 
    			"com.cs301w01.meatload.activities.TakePictureActivity");
    	Log.d("Taking Picture", "ALBUM NAME:" + galleryManager.getAlbumName());

        myIntent.putExtra("album", galleryManager.getAlbum());

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

    private class ImageAdapter extends BaseAdapter {

        int mGalleryItemBackground;
        private Context mContext;
        private ArrayList<Picture> pictures;

        public ImageAdapter(Context c, Collection<Picture> pictures) {

            mContext = c;
            TypedArray attr = mContext.obtainStyledAttributes(R.styleable.GalleryActivity);
            mGalleryItemBackground = attr.getResourceId(
                    R.styleable.GalleryActivity_android_galleryItemBackground, 0);
            this.pictures = new ArrayList<Picture>(pictures);
            attr.recycle();
        }

        public int getCount() {
            return pictures.size();
        }

        public Picture getItem(int position) {
            return pictures.get(position);
        }

        public long getItemId(int position) {
            return pictures.get(position).getPictureID();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);

            imageView.setImageDrawable(Drawable.createFromPath(pictures.get(position).getPath()));
            imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(mGalleryItemBackground);

            return imageView;
        }
    }

}



