package com.cs301w01.meatload.authentication.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.cs301w01.meatload.activities.Skindactivity;
import com.cs301w01.meatload.authentication.controllers.DiagnosisManager;

/**
 * This activity allows users to view a diagnosis given from a specialist.
 * There will be an assessment of the condition, how to treat the condition, and
 * an additional comment field.
 * 
 * @author Derek Dowling
 *
 */
public class ViewDiagnosisActivity extends Skindactivity {

	private TextView specialistNameLabel;
	private TextView assessmentLabel;
	private TextView treatmentLabel;
	private TextView commentLabel;
	
	private DiagnosisManager diagnosisManager;
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	diagnosisManager = new DiagnosisManager(this);
    	
    	populateTextFields();
    	createListeners();
    	
	}
	
	private void populateTextFields() {
		
		
	}

	private void createListeners() {
		
		
	}

	@Override
	public void update(Object model) {
		
		
	}

}
