package com.securevault.onepass.utils;

import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import com.securevault.onepass.R;

public class ThemeHelper {
    private final Context context;
    private final PreferenceHelper preferenceHelper;

    public ThemeHelper(Context context) {
        this.context = context;
        preferenceHelper = PreferenceHelper.getInstance(context);
    }

    public void setThemeToApp(int checkedId) {
        int mode;

        if (checkedId == R.id.deviceSettingMode) {
            mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        } else if (checkedId == R.id.lightMode) {
            mode = AppCompatDelegate.MODE_NIGHT_NO;
        } else {
            mode = AppCompatDelegate.MODE_NIGHT_YES;
        }

        AppCompatDelegate.setDefaultNightMode(mode);

        preferenceHelper.setThemeMode(PreferenceHelper.KEY_THEME_MODE, mode);
    }


    public int getCurrentDeviceTheme() {
        int currentMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentMode == Configuration.UI_MODE_NIGHT_YES) {
            return AppCompatDelegate.MODE_NIGHT_YES;
        } else if (currentMode == Configuration.UI_MODE_NIGHT_NO) {
            return AppCompatDelegate.MODE_NIGHT_NO;
        } else {
            return AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        }
    }
}