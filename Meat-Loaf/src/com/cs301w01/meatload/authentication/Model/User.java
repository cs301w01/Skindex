package com.cs301w01.meatload.authentication.Model;

import com.cs301w01.meatload.model.Album;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Derek
 * Date: 3/28/12
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class User {
    
    public static final String NO_SPECIALIST = "No Specialist";
    
    private String name;
    private String email;
    private Collection<Album> albums;
    private String specialistsName;
    
    public User(String name, String email, Collection<Album> albums, String specialist) {
    
        this.name = name;
        this.email = email;
        this.albums = albums;
        this.specialistsName = specialist;
        
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Collection<Album> getAlbums() {
        return albums;
    }

    public String getSpecialistsName() {
        if(specialistsName.equals(""))
            return NO_SPECIALIST;
        else
            return specialistsName;
    }
}
