package com.cs301w01.meatload.authentication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.cs301w01.meatload.R;
import com.cs301w01.meatload.activities.Skindactivity;
import com.cs301w01.meatload.authentication.Controllers.UserManager;
import com.cs301w01.meatload.authentication.Model.Patient;
import com.cs301w01.meatload.authentication.Model.Specialist;
import com.cs301w01.meatload.authentication.Model.User;

/**
 * Acts as the base activity users land on. They are prompted to provide credentials in order to log in or
 * to create a new account.
 */
public class LoginActivity extends Skindactivity {

    private EditText usernameField;
    private EditText passwordField;

    private static int GET_USER_REQUEST = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        
        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        createListeners();
    }

    public void createListeners() {
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                login();
            }
        });
        
        final Button signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
                signup();
            }
        });
        
    }

    /**
     * Handles logic of redirecting new users to the sign up page.
     */
    private void signup() {

        Intent myIntent = new Intent();
        myIntent.setClassName("com.cs301w01.meatload",
                "com.cs301w01.meatload.authentication.activities.SignupActivity");
        startActivity(myIntent);

    }

    /**
     * Gets the users username and passwords from the form fields, authenticates values, and finally
     * passes the results in the form of a User object to the method that handles the login process.
     */
    private void login() {

        String username = usernameField.getText().toString();
        String pw = passwordField.getText().toString();
        
        UserManager um = new UserManager(this);
        User u = um.authenticateUser(username, pw);

        handleLoginResult(u);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == GET_USER_REQUEST) {
                if (resultCode == Activity.RESULT_OK) {

                    Bundle extras = data.getExtras();
                    User u = (User) extras.getSerializable("user");

                    handleLoginResult(u);

                }
            }

    }

    /**
     * Checks to see if the user is of a certain type, if the object is null, this is handled and the user
     * is made aware of their invalid credentials, otherwise moves them on to the corresponding activity for their
     * role.
     * @param u User
     */
    private void handleLoginResult(User u) {

        if(u.getClass() == Patient.class) {

            Intent myIntent = new Intent();
            myIntent.setClassName("com.cs301w01.meatload",
                    "com.cs301w01.meatload.Skindex");
            myIntent.putExtra("user", u);

            startActivity(myIntent);

        } else if(u.getClass() == Specialist.class) {

            Intent myIntent = new Intent();
            myIntent.setClassName("com.cs301w01.meatload",
                    "com.cs301w01.meatload.authentication.activities.ViewPatientsActivity");
            myIntent.putExtra("user", u);

            startActivity(myIntent);

        } else {

            // Add invalid credentials alert

        }


    }

    public void update(Object model) {

    }
}
