package com.cs301w01.meatload.model;

/**
 * <b>Tag</b>
 * <p>
 * Currently not implemented
 */
public class Tag {
    
    private String name;
    private int numPhotos;
    
    public Tag(String name, int numPhotos){
    	this.name = name;
    	this.numPhotos = numPhotos;
    }
    
    public String getName(){
    	return name;
    }
    
    public int getNumPhotos(){
    	return numPhotos;
    }

}
