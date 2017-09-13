package com.gomez.jeanpi.spotifymaterial.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;


public class TokenManagement {

    private static TokenManagement instance = null;

    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "SpotifyMaterial";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Key token (make variable public to access from outside)
    public static final String KEY_TOKEN = "token";

    private Context context;

    public static TokenManagement getInstance(Context context) {
        if (instance == null) {
            instance = new TokenManagement(context);
        }
        return instance;
    }

    private TokenManagement(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        this.context = context;
    }

    public void createLoginSession(String token) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing key in pref
        editor.putString(KEY_TOKEN, token);

        // commit changes
        editor.commit();
    }

    public void updateToken(String token) {
        editor.putString(KEY_TOKEN, token);

        editor.commit();
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getToken() {
        HashMap<String, String> oauth = new HashMap<>();

        // user key
        oauth.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));

        // return user
        return oauth;
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}