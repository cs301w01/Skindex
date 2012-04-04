package com.cs301w01.meatload.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

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

	//gallery related variables
    private Gallery topGallery;
    private Gallery bottomGallery;
    private ComparePictureGalleryAdapter gAdapter;
    private GalleryManager galleryManager;
    
    //data strings
    private TextView topGalleryData;
    private TextView bottomGalleryData;


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
                R.styleable.ComparePicturesActivity, 
                R.styleable.ComparePicturesActivity_android_galleryItemBackground);

        topGallery = (Gallery) findViewById(R.id.topCompareGallery);
        bottomGallery = (Gallery) findViewById(R.id.bottomCompareGallery);

        //set adapter
        topGallery.setAdapter(gAdapter);
        bottomGallery.setAdapter(gAdapter);

        //set the bottom gallery to the second picture
        if(galleryManager.getPictureGallery().size() >= 2) {
            bottomGallery.setSelection(1);
        }

       // createListeners();

    }

    //@Override
    public void update(Object model) {
    	
    }

    public void createListeners() {

    	
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
    

}
