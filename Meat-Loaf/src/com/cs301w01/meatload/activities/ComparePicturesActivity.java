package com.cs301w01.meatload.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import com.cs301w01.meatload.R;
import com.cs301w01.meatload.adapters.ComparePictureGalleryAdapter;
import com.cs301w01.meatload.adapters.HorizontalGalleryAdapter;
import com.cs301w01.meatload.controllers.GalleryManager;
import com.cs301w01.meatload.model.GalleryData;
import com.cs301w01.meatload.model.Picture;

import java.util.ArrayList;

/**
 * Implements the logic of the Compare Pictures interface.
 * @author Derek Dowling
 */
public class ComparePicturesActivity extends Skindactivity {

    private Gallery topGallery;
    private Gallery bottomGallery;
    private ComparePictureGalleryAdapter gAdapter;

    private ImageView topPicture;
    private ImageView bottomPicture;
    
    private GalleryManager galleryManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_picture);

        //create gallery manager
        Bundle b = getIntent().getExtras();
        GalleryData gallerydata = (GalleryData) b.getSerializable("gallery");
        galleryManager = new GalleryManager(gallerydata);
        galleryManager.setContext(this);

        //create adapter used by galleries
        gAdapter = new ComparePictureGalleryAdapter(this, galleryManager.getPictureGallery(),
                R.styleable.ComparePicturesActivity, R.styleable.ComparePicturesActivity_android_galleryItemBackground);

        topGallery = (Gallery) findViewById(R.id.topCompareGallery);
        bottomGallery = (Gallery) findViewById(R.id.bottomCompareGallery);
        
        //set adapter
        topGallery.setAdapter(gAdapter);
        bottomGallery.setAdapter(gAdapter);

//        ArrayList<Picture> pictures = new ArrayList<Picture>(galleryManager.getPictureGallery());
        
//        //set pictures
//        topPicture = (ImageView) findViewById(R.id.topCompareImage);
//        topPicture.setImageDrawable(Drawable.createFromPath(pictures.get(0).getPath()));
//        bottomPicture = (ImageView) findViewById(R.id.bottomCompareImage);
//        bottomPicture.setImageDrawable(Drawable.createFromPath(pictures.get(1).getPath()));
    }

    //@Override
    public void update(Object model) {
    	
    }

    public void createListeners() {

//        topGallery.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//            
//                String pPath = galleryManager.getPhoto(view.getId()).getPath();
//                updateTopPicture(pPath);
//                
//            }
//        });
//
//        bottomGallery.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//
//                String pPath = galleryManager.getPhoto(view.getId()).getPath();
//                updateBottomPicture(pPath);
//
//            }
//        });

    	topGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	
    }

    /**
     * Could be cool to implement a compare two pictures pixel by pixel. Returns
     * a float value which is a XX.X% out of 100% value.
     * @param path1
     * @param path2
     * @return
     */
    private float compareByPixel(String path1, String path2) {
    	
    	float percentage = 0.0f;
    	
//    	File file1 = new File("path/image1.jpg");
//    	File file2 = new File("path/image2.jpg");
//
//    	BufferedImage image1 = ImageIO.read(file1);
//    	BufferedImage image2 = ImageIO.read(file2);
//
//    	int columns = image1.getWidth();
//    	int rows = image1.getHeight();
//
//    	for (int row=0; row<rows; row++){
//    	    for (int col=0; col<columns; col++){
//    	       int rgb = image1.getRGB(col, row);
//    	       int rgb2= image2.getRGB(col, row);
//    	       //compare here
//    	    }
//    	 }

    	return percentage;
    	
    }
    
    public void updateTopPicture(String path) {

        topPicture.setImageDrawable(Drawable.createFromPath(path));
        topPicture.refreshDrawableState();

    }

    public void updateBottomPicture(String path) {

        bottomPicture.setImageDrawable(Drawable.createFromPath(path));
        bottomPicture.refreshDrawableState();
    }
}
