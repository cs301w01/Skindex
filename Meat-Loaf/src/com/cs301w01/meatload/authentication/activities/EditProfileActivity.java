package com.cs301w01.meatload.authentication.activities;

import android.os.Bundle;

import com.cs301w01.meatload.activities.Skindactivity;
import com.cs301w01.meatload.authentication.Controllers.UserManager;

/**
 * 
 * This activity allows users to edit their basic contact information.
 * 
 * @author Derek Dowling
 *
 */
public class EditProfileActivity extends Skindactivity {

	private UserManager userManager;
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	   
    	
    	
    	createListeners();
    	
	}
	
	private void createListeners() {
		
		
	}

	@Override
	public void update(Object model) {
		
		
	}
	
	private void saveChanges() {
		
		userManager.updateUserDetails();
		
	}

}
