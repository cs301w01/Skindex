package com.cs301w01.meatload.authentication.model;

import java.util.Collection;

/**
 * This class represents users of type specialist and contains specific information
 * pertaining to diagnosing patient skin conditions.
 */
public class Specialist extends User {

	private static final long serialVersionUID = 1L;
	private Collection<Patient> patients;

    public Specialist(String name, String email, Collection<Patient> patients ) {
        super(name, email);

        this.patients = patients;
    }

    public void addPatient(Patient patient) {
        
        patients.add(patient);
        
    }
    
    public void removePatient(Patient patient) {

        patients.remove(patient);

    }

}
