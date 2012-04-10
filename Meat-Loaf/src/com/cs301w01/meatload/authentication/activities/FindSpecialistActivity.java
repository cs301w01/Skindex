package com.cs301w01.meatload.authentication.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cs301w01.meatload.activities.Skindactivity;
import com.cs301w01.meatload.authentication.controllers.DiagnosisManager;
import com.cs301w01.meatload.authentication.model.Specialist;

/**
 * This Activity is used by "Patients" who are looking for a specialist
 * to view their skin conditions. The user will have the option to browse through and select
 * potential specialists to receive services from, select one or multiple albums for them to
 * view and then send a Diagnosis request to them.
 * 
 * @author Derek Dowling
 *
 */
public class FindSpecialistActivity extends Skindactivity{

	private ListView specialistLV;
	private ArrayAdapter<Specialist> adapter;
	private DiagnosisManager diagnosisManager;
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	   
    	diagnosisManager = new DiagnosisManager(this);
    	
    	createListeners();
    	
	}
	
	@Override
	public void update(Object model) {
		
		
	}
	
	public void createListeners() {
		
		
	}
	
	/**
	 * Retrieves selected albums, selected specialists, and a comment from the
	 * patient, bundles it up and handles db inserts.
	 */
	public void sendDiagnosisRequest() {
	
		diagnosisManager.sendDiagnosisRequest();
		
		
	}

}
