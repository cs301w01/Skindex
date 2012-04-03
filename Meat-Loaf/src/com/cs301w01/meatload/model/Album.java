package com.cs301w01.meatload.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * 
 */
public class Album implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
    private int pictureCount;
    private long id;
    private Date date;
    
    public Album(String name, int pictureCount, long id, Date date) {
    	this.name = name;
    	this.pictureCount = pictureCount;
        this.id = id;
        this.date = date;
    	
    }
    
    public Album(String name, int pictureCount, long id) {
    	this.name = name;
    	this.pictureCount = pictureCount;
        this.id = id;
    	
    }
    
    public Album(String name){
    	this.name = name;
    	pictureCount = 0;
    	id = -1;
    }
    
    public String getName() {
    	return name;
    }
    
    public int getPictureCount() {
    	return pictureCount;
    }
    
    public long getID() {
        return id;
    }
    
    public Date getDate() {
    	return Calendar.getInstance().getTime();
    	
    	//return date;
    }
    
    @Override
    public String toString() {
		return getName();
    }
}
