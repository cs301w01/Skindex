package com.cs301w01.meatload.authentication.activities;

import android.os.Bundle;

import com.cs301w01.meatload.activities.Skindactivity;
import com.cs301w01.meatload.authentication.controllers.DiagnosisManager;

/**
 * This activity allows specialists to view diagnosis requests from users. It will
 * show them who a list of people who have sent requests, and how many albums are related
 * to each request. The specialist can then choose to respond(diagnose) to each
 * request if they so choose.
 * 
 * @author Derek Dowling
 *
 */
public class ViewDiagnosisRequestsActivity extends Skindactivity {

	private DiagnosisManager diagnosisManager;
		
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	diagnosisManager = new DiagnosisManager(this);
    	
    	createListeners();	
	}
	
	public void update(Object model) {
		
	}
	
	private void createListeners() {
		
	}

	/**
	 * Creates a new PerformDiagnosisActivity with the appropriate information.
	 */
	public void viewPatientRequest() {	
		diagnosisManager.getPatientRequestInfo();
	}
}
