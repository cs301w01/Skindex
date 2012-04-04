package com.cs301w01.meatload.authentication.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.cs301w01.meatload.R;
import com.cs301w01.meatload.activities.Skindactivity;
import com.cs301w01.meatload.authentication.Controllers.UserManager;
import com.cs301w01.meatload.authentication.Model.Patient;
import com.cs301w01.meatload.authentication.Model.Specialist;
import com.cs301w01.meatload.authentication.Model.User;
import com.cs301w01.meatload.authentication.querygenerator.UserQueryGenerator;
import com.cs301w01.meatload.model.Album;

import java.util.ArrayList;

// TODO: (Derek) Write Javadoc for SignupActivity
/**
 * 
 */
public class SignupActivity extends Skindactivity {

    private EditText name;
    private EditText email;
    private EditText username;
    private Spinner role;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);

        name = (EditText) findViewById(R.id.fullNameEditText);
        email = (EditText) findViewById(R.id.emailEditText);
        role = (Spinner) findViewById(R.id.roleSpinner);
        username = (EditText) findViewById(R.id.userNameSignupEditText);

        createListeners();

    }

    private void createListeners() {

        final Button submit = (Button) findViewById(R.id.submitNewUserButton);
        submit.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                createNewUser();
            }
        });


    }

    private void createNewUser() {

        // Display select password alert, then confirm and compare password

        // Create user
        String fullName = name.getText().toString();
        String uEmail = email.getText().toString();
        String uRole = String.valueOf(role.getSelectedItem());
        String usrName = username.getText().toString();
        String password = getPassword();

        User newUser;
        UserManager uM = new UserManager(this);

        if(uRole.equals(UserQueryGenerator.SPECIALIST_ROLE)) {

            newUser = new Specialist(fullName, uEmail, new ArrayList<Patient>());
            uM.createNewUser(newUser, usrName, password, UserQueryGenerator.SPECIALIST_ROLE);

        }
        else {

            newUser = new Patient(fullName, uEmail, new ArrayList<Album>(), 0);
            uM.createNewUser(newUser, usrName, password, UserQueryGenerator.PATIENT_ROLE);

        }


        Intent resultIntent = new Intent();
        resultIntent.putExtra("user", newUser);
        this.setResult(Activity.RESULT_OK);

        finish();

    }

    public String getPassword() {

        String pwd = "";

        AlertDialog.Builder pwdBuilder = new AlertDialog.Builder(this);

        pwdBuilder.setTitle("Select A Password");
        pwdBuilder.setMessage("Please select and confirm your password.");

        pwdBuilder.show();

        return pwd;

    }

    public void update(Object model) {

    }
}
