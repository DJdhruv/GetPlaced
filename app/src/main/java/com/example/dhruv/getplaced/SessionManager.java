package com.example.dhruv.getplaced;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dell on 10/26/17.
 */

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "islogin";
    private static final String KEY_NAME = "userid";
    private static final String KEY_PASSWORD = "password";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("StudentLogin", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String name, String rollno){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PASSWORD, rollno);
        editor.commit();
    }


    public String getUserid() {
        return pref.getString(KEY_NAME, null);
    }

    public String getPassword() {
        return pref.getString(KEY_PASSWORD,null);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

}
