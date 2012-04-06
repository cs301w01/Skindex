package com.cs301w01.meatload.activities;

import android.os.Bundle;
import android.widget.Gallery;

import com.cs301w01.meatload.R;
import com.cs301w01.meatload.adapters.ComparePictureGalleryAdapter;
import com.cs301w01.meatload.controllers.GalleryManager;
import com.cs301w01.meatload.model.GalleryData;

/**
 * Implements the logic of the Compare Pictures interface.
 * @author Derek Dowling
 */
public class ComparePicturesActivity extends Skindactivity {

	//gallery related variables
    private Gallery topGallery;
    private Gallery bottomGallery;
    private ComparePictureGalleryAdapter gAdapter;
    private GalleryManager galleryManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_picture);

        // Create gallery manager
        Bundle b = getIntent().getExtras();
        GalleryData gallerydata = (GalleryData) b.getSerializable("gallery");
        galleryManager = new GalleryManager(this, gallerydata);

        // Create adapter used by galleries
        gAdapter = new ComparePictureGalleryAdapter(this, galleryManager.getPictureGallery(),
                R.styleable.ComparePicturesActivity, 
                R.styleable.ComparePicturesActivity_android_galleryItemBackground);

        topGallery = (Gallery) findViewById(R.id.topCompareGallery);
        bottomGallery = (Gallery) findViewById(R.id.bottomCompareGallery);

        // Set adapters for both galleries to gAdapter
        topGallery.setAdapter(gAdapter);
        bottomGallery.setAdapter(gAdapter);

        // Set the bottom gallery to the second picture
        if (galleryManager.getPictureGallery().size() >= 2) {
            bottomGallery.setSelection(1);
        }

    }

    public void update(Object model) {
    	
    }

    /**
     * Compare two pictures pixel by pixel.
     * 
     * @param path1 Path of first image to be compared
     * @param path2 Path of first image to be compared
     * @return float value which is a XX.X% out of 100% value.
     */
    @SuppressWarnings("unused")
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
    

}
