package com.cs301w01.meatload.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * <b>Album</b>
 * <p>
 * Currently not implemented
 */
public class Album {
    
    private String name;
    private int numPhotos;
    
    public Album(String name, int numPhotos){
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
