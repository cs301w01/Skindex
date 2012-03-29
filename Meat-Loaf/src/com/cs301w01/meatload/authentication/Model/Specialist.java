package com.cs301w01.meatload.authentication.Model;

import com.cs301w01.meatload.model.Album;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Derek
 * Date: 3/28/12
 * Time: 4:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Specialist extends User {

    private Collection<Patient> patients;

    public Specialist(String name, String email, Collection<Patient> patients ) {
        super(name, email);

        this.patients = patients;
    }


}
