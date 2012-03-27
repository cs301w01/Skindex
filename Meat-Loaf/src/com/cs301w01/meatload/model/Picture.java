package com.cs301w01.meatload.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Stores temporary values to be permanently stored in a database or used by one of the View 
 * classes.
 * @author Derek Dowling
 */
public class Picture implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
    private String albumName;
    private String path;
    private int id;
    private Date date;
    private Collection<String> tags;
    
    public Picture(String name, String path, String album, Date date, Collection<String> tags) {
        this.name = name;
        this.albumName = album;
        this.path = path;
        this.date = date;
        this.tags = tags;
    }

    public int getPictureID() {

        return this.id;

    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Date getDate() {
        return date;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public String getAlbumName(){
        return this.albumName;
    }
    
    public String toString() {
        
        String output = "Picture, id = " + getPictureID() + " " +
                        " name = " + getName() + " date =  " + getDate().toString() +
                        " tags = ( ";
        
                        for(String tag : getTags()) {

                            output.concat(tag + ", ");

                        }

                        output.concat(").");
        
        return output;
        
    }
    
}
