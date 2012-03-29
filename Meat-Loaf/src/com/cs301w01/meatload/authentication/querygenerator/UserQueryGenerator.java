package com.cs301w01.meatload.authentication.querygenerator;

import android.content.Context;
import android.database.Cursor;
import com.cs301w01.meatload.authentication.Model.Patient;
import com.cs301w01.meatload.authentication.Model.Specialist;
import com.cs301w01.meatload.authentication.Model.User;
import com.cs301w01.meatload.model.querygenerators.AlbumQueryGenerator;
import com.cs301w01.meatload.model.querygenerators.QueryGenerator;

public class UserQueryGenerator extends QueryGenerator {

    //static strings used by this class
    public static final String TABLE_NAME_USERS = "users";
    public static final String TABLE_NAME_SUBSCRIPTIONS = "subscriptions";
    public static final String TABLE_NAME_USER_ALBUMS = "useralbums";
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
            "CREATE TABLE " + TABLE_NAME_USERS +" (" +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_NAME + " TEXT, " +
            COL_USERNAME + " TEXT, " +
            COL_EMAIL + " TEXT, " +
            COL_PASSWORD + " TEXT, " +
            COL_ROLE + " TEXT );";

    //sql for creating the specialist/patient subscription table
    public static final String CREATE_SUBSCRIPTIONS_TABLE = 
            "CREATE TABLE " + TABLE_NAME_SUBSCRIPTIONS + " (" +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_SPECIALIST_ID + " INTEGER " +
                    "FOREIGN KEY(" + COL_SPECIALIST_ID + ") REFERENCES " +
                    TABLE_NAME_USERS + "( " + COL_ID + "), " +
            COL_PATIENT_ID + " INTEGER " +
                    "FOREIGN KEY(" + COL_PATIENT_ID + ") REFERENCES " +
                    TABLE_NAME_USERS + "( " + COL_ID + "));";
    
    //sql for creating the album ownership table
    public static final String CREATE_USER_ALBUMS_TABLE =
            "CREATE TABLE " + TABLE_NAME_USER_ALBUMS + " " +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_USER_ID + " INTEGER " +
                    "FOREIGN KEY(" + COL_USER_ID + ") REFERENCES " +
                    TABLE_NAME_USERS + "( " + COL_ID + "), " +
            COL_ALBUM_ID + " INTEGER " +
                    "FOREIGN KEY(" + COL_ALBUM_ID + ") REFERENCES " +
                    AlbumQueryGenerator.TABLE_NAME + "( " + COL_ID + "));";
            
    
    
    public UserQueryGenerator(Context context) {
        super(context);
    }
    
    public void insertNewUser(User u) {

        String query = "INSERT INTO " + TABLE_NAME_USERS + "( " +
                       "";
        
        if(u.getClass() == Patient.class){
            
        } else if(u.getClass() == Specialist.class){

        } else {

        }
        
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

    /**
     * Attempts to log a user in, if the credentials are correct, will return one result with the users basic
     * information which can be used to retrieve other data. Otherwise returns a cursor with count == 0.
     * @param userName
     * @param password
     * @return Cursor
     */
    public Cursor logUserIn(String userName, String password) {

        
        String query = "SELECT " +
                        COL_ID + ", " + COL_NAME + ", " + COL_USERNAME + ", " + COL_EMAIL + ", " + COL_ROLE + " " +
                       "FROM " + TABLE_NAME_USERS + " " +
                       "WHERE " + COL_USERNAME + " = '" + userName + "' " +
                       "AND " + COL_PASSWORD + " = '" + password + "'";
        
        return db.performRawQuery(query);
        
    }
}
