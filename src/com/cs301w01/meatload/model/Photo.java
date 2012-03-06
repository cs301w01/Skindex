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
public class Photo {
    
    private String name;
    private String albumName;
    private String path;
    private Date date;
    private Collection<String> tags;
    
    public Photo(String name, String path, String album, Date date, Collection<String> tags){

        tags = tags;

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
