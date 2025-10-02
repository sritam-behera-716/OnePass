package com.securevault.onepass.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.securevault.onepass.R;
import com.securevault.onepass.databinding.ActivitySplashBinding;
import com.securevault.onepass.ui.main.MainActivity;
import com.securevault.onepass.ui.onboarding.OnboardingActivity;
import com.securevault.onepass.utils.PreferenceHelper;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String text = getResources().getString(R.string.app_name).toUpperCase();
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.default_color)), 0, 3, 0);
        spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.span_color)), 3, text.length(), 0);
        binding.appName.setText(spannableString);

        PreferenceHelper preferenceHelper = PreferenceHelper.getInstance(this);
        boolean isIntroOpened = preferenceHelper.getIntroOpenInformation(PreferenceHelper.KEY_INTRO_OPEN);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (isIntroOpened) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(this, OnboardingActivity.class));
            }
            finish();
        }, 3000);
    }
}