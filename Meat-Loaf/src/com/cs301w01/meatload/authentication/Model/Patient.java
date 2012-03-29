package com.cs301w01.meatload.authentication.Model;

import com.cs301w01.meatload.model.Album;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Derek
 * Date: 3/28/12
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Patient extends User {

    private Collection<Album> albums;
    private String specialistID;

    public Patient(String name, String email, Collection<Album> albums) {
        super(name, email);

        this.albums = albums;
    }


    public Collection<Album> getAlbums() {
        return albums;
    }
}
