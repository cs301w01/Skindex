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
    
    protected String name;
    protected String email;
    protected String role;
    
    public User(String name, String email) {
    
        this.name = name;
        this.email = email;
        this.role = role;
        
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        if(role.equals(""))
            return NO_SPECIALIST;
        else
            return role;
    }
}
