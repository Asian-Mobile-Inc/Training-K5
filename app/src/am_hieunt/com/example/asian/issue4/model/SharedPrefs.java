package com.example.asian.issue4.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private static final String PREFS_NAME = "multi_language_active";
    private final SharedPreferences mSharedPreferences;
    private static SharedPrefs mInstance;

    private SharedPrefs(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefs getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefs(context.getApplicationContext());
        }
        return mInstance;
    }

    public String getSharedPrefs(String key) {
        return  mSharedPreferences.getString(key, "");
    }

    @SuppressLint("CommitPrefEdits")
    public void putSharedPrefs(String key, String code) {
        mSharedPreferences.edit().putString(key, code).apply();
    }
}
