package com.cs301w01.meatload.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 */
public class Album implements Serializable {
    
    private String name;
    private int numPhotos;
    private int id;
    private Collection<Picture> pictures;
    
    public Album(String name, int numPhotos, Collection<Picture> pictures){
    	
    	this.name = name;
    	this.numPhotos = numPhotos;
    	this.pictures = pictures;
    	
    }
    
    public String getName(){
    	return name;
    }
    
    public int getNumPhotos(){
    	return numPhotos;
    }
    
    public Collection<Picture> getPictures() {
    	
    	return pictures;
    	
    }
    
    public Collection<String> getAlbumTags() {
    	
    	Collection<String> tags = new ArrayList<String>();
    	

    	
    	return tags;
    	
    }
    
    public Collection<String> getAllAlbumTags() {
    	
    	Collection<String> tags = new ArrayList<String>();
    	
    	//TODO: get all tags that are associated with the album and pictures
    	
    	return tags;
    	
    }
    
    

}
