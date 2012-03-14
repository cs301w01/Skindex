package com.cs301w01.meatload.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Derek
 * Date: 3/3/12
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
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
