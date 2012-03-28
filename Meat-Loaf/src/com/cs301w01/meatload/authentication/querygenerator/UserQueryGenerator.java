package com.cs301w01.meatload.authentication.querygenerator;

import android.content.Context;
import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.QueryGenerator;

public class UserQueryGenerator extends QueryGenerator {

    //static strings used by this class
    public static final String USERS_TABLE_NAME = "users";
    public static final String SUBSCRIPTIONS_TABLE_NAME = "subscriptions";
    public static final String USER_ALBUMS_TABLE_NAME = "useralbums";
    public static final String SPECIALIST_ROLE = "specialist";
    public static final String PATIENT_ROLE = "patient";
    
    //col's specific to this class
    public static final String COL_ROLE = "role";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_EMAIL = "email";
    public static final String COL_SPECIALIST_ID = "s_id";
    public static final String COL_PATIENT_ID = "p_id";
    public static final String COL_USER_ID = "u_id";
    public static final String COL_ALBUM_ID = "a_id";
    
    //sql for creating the user info table
    public static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + USERS_TABLE_NAME +" (" +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_NAME + " TEXT, " +
            COL_USERNAME + " TEXT, " +
            COL_EMAIL + " TEXT, " +
            COL_PASSWORD + " TEXT, " +
            COL_ROLE + " TEXT );";

    //sql for creating the specialist/patient subscription table
    public static final String CREATE_SUBSCRIPTIONS_TABLE = 
            "CREATE TABLE " + SUBSCRIPTIONS_TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_SPECIALIST_ID + " INTEGER " +
                    "FOREIGN KEY(" + COL_SPECIALIST_ID + ") REFERENCES " +
                    USERS_TABLE_NAME + "( " + COL_ID + "), " +
            COL_PATIENT_ID + " INTEGER " +
                    "FOREIGN KEY(" + COL_PATIENT_ID + ") REFERENCES " +
                    USERS_TABLE_NAME + "( " + COL_ID + "));";
    
    //sql for creating the album ownership table
    public static final String CREATE_USER_ALBUMS_TABLE =
            "CREATE TABLE " + USER_ALBUMS_TABLE_NAME + " " +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_USER_ID + " INTEGER " +
                    "FOREIGN KEY(" + COL_USER_ID + ") REFERENCES " +
                    USERS_TABLE_NAME + "( " + COL_ID + "), " +
            COL_ALBUM_ID + " INTEGER " +
                    "FOREIGN KEY(" + COL_ALBUM_ID + ") REFERENCES " +
                    AlbumQueryGenerator.TABLE_NAME + "( " + COL_ID + "));";
            
    
    
    public UserQueryGenerator(Context context) {
        super(context);
    }
    
    public void createNewUser() {

        //TODO

    }

    public void addNewUserAlbum() {

        //TODO

    }

    public void deleteUserAlbum() {

        //TODO

    }

    public void addSubscription() {

        //TODO

    }

    public void removeSubscription() {

        //TODO

    }

    public void updateUserInfo() {

        //TODO

    }
    
}
