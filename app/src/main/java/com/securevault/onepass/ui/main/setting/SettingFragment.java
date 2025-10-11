package com.securevault.onepass.ui.main.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.securevault.onepass.R;
import com.securevault.onepass.databinding.FragmentSettingBinding;
import com.securevault.onepass.utils.BiometricHelper;
import com.securevault.onepass.utils.PreferenceHelper;

public class SettingFragment extends Fragment {
    private FragmentSettingBinding binding;
    private PreferenceHelper preferenceHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferenceHelper = PreferenceHelper.getInstance(requireContext());
        setUpUserInterface();
        switchThemeMode();

        binding.biometricSwitch.setOnCheckedChangeListener(((buttonView, isChecked) -> checkBiometric(isChecked)));
        binding.themeLayout.setOnClickListener(v -> gotoThemeActivity());
    }

    private void checkBiometric(boolean isChecked) {
        BiometricHelper biometricHelper = new BiometricHelper(requireContext());
        if (biometricHelper.isBiometricAvailable()) {
            preferenceHelper.isBiometricEnable(PreferenceHelper.KEY_BIOMETRIC, isChecked);
        } else {
            Toast.makeText(requireContext(), "Biometric authentication is not available!", Toast.LENGTH_SHORT).show();
            binding.biometricSwitch.setChecked(false);
        }
    }

    private void gotoThemeActivity() {
        Intent intent = new Intent(requireContext(), ThemeActivity.class);
        startActivity(intent);
    }

    private void switchThemeMode() {
        int mode = preferenceHelper.getThemeMode(PreferenceHelper.KEY_THEME_MODE);
        switch (mode) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                binding.themeIcon.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_device_settings));
                binding.themeText.setText(R.string.device_settings);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                binding.themeIcon.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_dark_mode));
                binding.themeText.setText(R.string.dark_mode);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                binding.themeIcon.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_light_mode));
                binding.themeText.setText(R.string.light_mode);
                break;
        }
    }

    private void setUpUserInterface() {
        boolean check = preferenceHelper.getBiometricMode(PreferenceHelper.KEY_BIOMETRIC);
        if (check) {
            binding.biometricSwitch.setChecked(true);
        }
    }
}