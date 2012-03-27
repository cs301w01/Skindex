package com.cs301w01.meatload.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 */
public class Album implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
    private int numPhotos;
    private long id;
    private Collection<Picture> pictures;
    
    public Album(String name, int numPhotos, Collection<Picture> pictures, long id) {
    	
    	this.name = name;
    	this.numPhotos = numPhotos;
    	this.pictures = pictures;
        this.id = id;
    	
    }
    
    public String getName() {
    	return name;
    }
    
    public int getNumPhotos() {
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
    
    public long getID() {
        return id;
    }
}
