package com.securevault.onepass.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    public static final String KEY_INTRO_OPEN = "isIntroOpened";

    private static PreferenceHelper instance;
    private final SharedPreferences preferences;

    public PreferenceHelper(Context context) {
        preferences = context.getSharedPreferences("my_pref", Context.MODE_PRIVATE);
    }

    public static synchronized PreferenceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PreferenceHelper(context.getApplicationContext());
        }
        return instance;
    }

    public void setIntroOpenInformation(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public boolean getIntroOpenInformation(String key) {
        return preferences.getBoolean(key, false);
    }
}