package com.example.yash.resourcify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Yash on 3/20/2016.
 */
public class SessionManagement {
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "ResourcifyPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_USERNAME = "username";

    // Email address (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";

    // Constructor
    public SessionManagement(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void createLoginSession(String username, String password){

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD ,password);
        editor.commit();
    }
    public String getUsername(){
        HashMap<String, String> userDetails = new HashMap<String, String>();
       // userDetails.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
       // userDetails.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
       String uname=pref.getString(KEY_USERNAME,null);
        return uname;
    }
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LogActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }

    }
    public void logoutUser(){
        editor.clear();
        editor.commit();

    }
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
