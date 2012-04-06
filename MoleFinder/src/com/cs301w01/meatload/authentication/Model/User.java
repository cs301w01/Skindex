package com.cs301w01.meatload.authentication.Model;

import com.cs301w01.meatload.model.Album;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Derek
 * Date: 3/28/12
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class User implements Serializable {
    
    public static final String NO_SPECIALIST = "No Specialist";
    
    protected String name;
    protected String email;
    
    public User(String name, String email) {
    
        this.name = name;
        this.email = email;
        
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
