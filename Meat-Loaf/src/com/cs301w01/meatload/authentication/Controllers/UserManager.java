package com.cs301w01.meatload.authentication.Controllers;

import android.content.Context;
import android.database.Cursor;
import com.cs301w01.meatload.authentication.Model.Password;
import com.cs301w01.meatload.authentication.Model.Patient;
import com.cs301w01.meatload.authentication.Model.Specialist;
import com.cs301w01.meatload.authentication.Model.User;
import com.cs301w01.meatload.authentication.querygenerator.UserQueryGenerator;
import com.cs301w01.meatload.controllers.FController;
import com.cs301w01.meatload.model.Album;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class handles all the intermediate steps between the db and the GUI/activity.
 */
public class UserManager implements FController{

    private Context context;
    
    public UserManager(Context c) {
        
        this.context = context;
        
    }

    public User authenticateUser(String userName, String password) {

        UserQueryGenerator uQG = new UserQueryGenerator(context);

        String salt = uQG.getUserSalt(userName);

        if(salt == null)
            return null;

        String encryptedPwd = PasswordManager.generatePasswordWithSalt(password, salt);

        Cursor c = uQG.logUserIn(userName, encryptedPwd);

        if(c.getCount() == 0) {

            return null;

        } else {

            //c has the following: COL_ID, COL_NAME, COL_USERNAME, COL_EMAIL, COL_ROLE
            int id = c.getInt(c.getColumnIndex(UserQueryGenerator.COL_ID));
            String name = c.getString(c.getColumnIndex(UserQueryGenerator.COL_NAME));
            String email = c.getString(c.getColumnIndex(UserQueryGenerator.COL_EMAIL));
            String role = c.getString(c.getColumnIndex(UserQueryGenerator.COL_ROLE));

            User u;

            if(role.equals(UserQueryGenerator.PATIENT_ROLE)) {

                return uQG.getPatientByID(id);

            } else {

                Collection<Patient> patients = uQG.getPatientsByID(id);

                u = uQG.getSpecialistByID(id, patients);

                return u;

            }
         }
    }

    public long createNewUser(User u, String userName, String password, String role) {

        Password hashedPW = PasswordManager.generateNewPassword(password);

        UserQueryGenerator uQ = new UserQueryGenerator(context);

        return uQ.insertNewUser(u.getName(), u.getEmail(), userName, hashedPW, role);

    }

}
