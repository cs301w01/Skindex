package com.cs301w01.meatload.authentication.querygenerator;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.database.Cursor;

import com.cs301w01.meatload.authentication.Model.Patient;
import com.cs301w01.meatload.model.querygenerators.QueryGenerator;


/**
 * This class handles queries to the database involving diagnosis functionality.
 * 
 * @author Derek Dowling
 *
 */
public class DiagnosisQueryGenerator extends QueryGenerator {

	public static String TABLE_NAME_PENDING_DIAGNOSES = "pendingDiagnoses";
	public static String TABE_NAME_COMPLETED_DIAGNOSES = "completedDiagnoses";
	
	public static String COL_ASSESSMENT = "assessment";
	public static String COL_TREATMENT = "treatment";
	public static String COL_COMMENT = "comment";
	
	public static String CREATE_PENDING_DIAGNOSES_TABLE =
        "CREATE TABLE " + TABLE_NAME_PENDING_DIAGNOSES + " (" +
        COL_ID + " INTEGER PRIMARY KEY, " +
        COL_ASSESSMENT + " TEXT, " +
        COL_TREATMENT + " TEXT, " +
        COL_COMMENT + " TEXT, " +
        UserQueryGenerator.COL_SPECIALIST_ID + " INTEGER " +
                "FOREIGN KEY(" + UserQueryGenerator.COL_SPECIALIST_ID + ") REFERENCES " +
                UserQueryGenerator.TABLE_NAME_USERS + "( " + COL_ID + "), " +
        UserQueryGenerator.COL_PATIENT_ID + " INTEGER " +
                "FOREIGN KEY(" + UserQueryGenerator.COL_PATIENT_ID + ") REFERENCES " +
                UserQueryGenerator.TABLE_NAME_USERS + "( " + COL_ID + "));";
	
	
	public DiagnosisQueryGenerator(Context context) {
		super(context);
		
	}
	
	/**
	 * Inserts a patient's diagnosis request into the database.
	 */
	public void insertDiagnosisRequest() {
		
		
	}
	
	/**
	 * Updates a specialist completed diagnosis by moving from pending
	 * to complete table and adds assessment and other fields to the diagnosis.
	 */
	public void completeDiagnosis() {
		
		
	}
	
	/**
     * Gets a collection of patients that a specialist is subscribed too by id.
     * @return
     */
    public Collection<Patient> getPendingRequeststsBySpecialistID(int specialistId) {

        String query = "SELECT " + UserQueryGenerator.COL_PATIENT_ID + " " +
                       "FROM " + TABLE_NAME_PENDING_DIAGNOSES + " " +
                       "WHERE " + UserQueryGenerator.COL_SPECIALIST_ID + " = '" + specialistId + "'";

        Cursor c = db.performRawQuery(query);
        Collection<Patient> patients = new ArrayList<Patient>();

        //return empty list if patient count is zero
        if(c.getCount() == 0)
            return patients;


        //retrieve ids from cursor
        Collection<Integer> ids = new ArrayList<Integer>();
        while(!c.isAfterLast()) {

            ids.add(c.getInt(c.getColumnIndex(UserQueryGenerator.COL_PATIENT_ID)));
            c.moveToNext();

        }

        UserQueryGenerator uQG = new UserQueryGenerator(context);
        
        //now create patients
        for(Integer id : ids) {

            patients.add(uQG.getPatientByID(id));

        }

        return patients;

    }

}
