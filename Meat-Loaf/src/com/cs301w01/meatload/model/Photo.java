package com.cs301w01.meatload.model;

import java.util.Collection;
import java.util.Date;

/**
 * Stores temporary values to be permanently stored in a database or used by one of the View 
 * classes.
 * @author Derek Dowling
 */
public class Photo {
    
    private String name;
    private String albumName;
    private String path;
    private Date date;
    private Collection<String> tags;
    
    public Photo(String name, String path, String album, Date date, Collection<String> tags) {
        this.name = name;
        this.albumName = album;
        this.path = path;
        this.date = date;
        this.tags = tags;
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
}
