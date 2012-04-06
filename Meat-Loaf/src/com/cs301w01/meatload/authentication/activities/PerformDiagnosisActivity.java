package com.cs301w01.meatload.authentication.activities;

import android.os.Bundle;
import android.widget.EditText;

import com.cs301w01.meatload.activities.Skindactivity;
import com.cs301w01.meatload.authentication.Controllers.DiagnosisManager;

/**
* This activity allows specialists to diagnose a skin condition based on the
* patients request.
*/
public class PerformDiagnosisActivity extends Skindactivity {

	private EditText assessmentField;
	private EditText treatmentField;
	private EditText commentsField;
	
	private DiagnosisManager diagnosisManager;
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	diagnosisManager = new DiagnosisManager(this);
    	
    	createListeners();
    	
	}
	
	private void createListeners() {
		
		
	}

	@Override
	public void update(Object model) {
		
		
	}
	
	private void sendDiagnosis() {
		
		diagnosisManager.createDiagnosis();
		
	}
}