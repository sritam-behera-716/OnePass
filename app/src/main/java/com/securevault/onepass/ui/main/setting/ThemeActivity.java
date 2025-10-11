package com.securevault.onepass.ui.main.setting;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.securevault.onepass.R;
import com.securevault.onepass.databinding.ActivityThemeBinding;
import com.securevault.onepass.utils.PreferenceHelper;
import com.securevault.onepass.utils.ThemeHelper;

public class ThemeActivity extends AppCompatActivity {
    private ActivityThemeBinding binding;
    private PreferenceHelper preferenceHelper;
    private ThemeHelper themeHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityThemeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        preferenceHelper = PreferenceHelper.getInstance(this);

        setUpUserInterface();

        themeHelper = new ThemeHelper(this);
        binding.themeGroup.setOnCheckedChangeListener((group, checkedId) -> themeHelper.setThemeToApp(checkedId));
    }

    private void setUpUserInterface() {
        int mode = preferenceHelper.getThemeMode(PreferenceHelper.KEY_THEME_MODE);
        switch (mode) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                binding.lightMode.setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                binding.darkMode.setChecked(true);
                break;
            default:
                binding.deviceSettingMode.setChecked(true);
        }
    }
}