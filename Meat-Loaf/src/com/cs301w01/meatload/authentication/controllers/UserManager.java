package com.cs301w01.meatload.authentication.controllers;

import android.content.Context;
import android.database.Cursor;

import com.cs301w01.meatload.authentication.model.Password;
import com.cs301w01.meatload.authentication.model.Patient;
import com.cs301w01.meatload.authentication.model.User;
import com.cs301w01.meatload.authentication.querygenerator.DiagnosisQueryGenerator;
import com.cs301w01.meatload.authentication.querygenerator.UserQueryGenerator;
import com.cs301w01.meatload.controllers.FController;

import java.util.Collection;

/**
 * This class handles all the communication between the user system activities and the UserQueryGenerator.
 * 
 * @author Derek Dowling
 */
public class UserManager implements FController{

    private Context context;
    
    public UserManager(Context c) {
        
        this.context = c;
        
    }

    /**
     * This method is called by the login system. It checks to see if a user name exists, and if
     * so, the password matches it. If not, the system returns null, and if it does, returns a
     * user of appropriate type based on role.
     * 
     * @param userName
     * @param password
     * @return
     */
    public User authenticateUser(String userName, String password) {

        UserQueryGenerator uQG = new UserQueryGenerator(context);

        //using the user's username, return the corresponding salt value.
        String salt = uQG.getUserSalt(userName);
        if(salt == null)
            return null;

        String encryptedPwd = PasswordManager.generatePasswordWithSalt(password, salt);

        Cursor c = uQG.logUserIn(userName, encryptedPwd);

        //return null if the username does not exist
        if(c.getCount() == 0) {

        	c.close();
        	
            return null;

        } else {

            //c has the following: COL_ID, COL_NAME, COL_USERNAME, COL_EMAIL, COL_ROLE
            int id = c.getInt(c.getColumnIndex(UserQueryGenerator.COL_ID));
            String role = c.getString(c.getColumnIndex(UserQueryGenerator.COL_ROLE));

            c.close();
            
            User u;

            if(role.equals(UserQueryGenerator.PATIENT_ROLE)) {

                return uQG.getPatientByID(id);

            } else {

            	DiagnosisQueryGenerator dQG = new DiagnosisQueryGenerator(context);
            	
                Collection<Patient> patients = dQG.getPendingRequeststsBySpecialistID(id);

                u = uQG.getSpecialistByID(id, patients);

                return u;

            }
         }
    }

    /**
     * Creates a new encrypted password through the password generator, then inserts the
     * user into the database through the UserQueryManager.
     * 
     * @param u
     * @param userName
     * @param password
     * @param role
     * @return
     */
    public long createNewUser(User u, String userName, String password, String role) {

        Password hashedPW = PasswordManager.generateNewPassword(password);

        UserQueryGenerator uQ = new UserQueryGenerator(context);

        return uQ.insertNewUser(u.getName(), u.getEmail(), userName, hashedPW, role);

    }

    /**
     * This method makes an update to a user's personal identification data.
     */
	public void updateUserDetails() {
		
		
	}

}
