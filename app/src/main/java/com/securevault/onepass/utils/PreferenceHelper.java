package com.securevault.onepass.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class PreferenceHelper {
    public static final String KEY_INTRO_OPEN = "isIntroOpened";
    public static final String KEY_BIOMETRIC = "isBiometricEnabled";
    public static final String KEY_THEME_MODE = "themeMode";

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

    public void isBiometricEnable(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBiometricMode(String key) {
        return preferences.getBoolean(key, false);
    }

    public void setThemeMode(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public int getThemeMode(String key) {
        return preferences.getInt(key, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}