package com.securevault.onepass.ui.main.password;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.securevault.onepass.data.DatabaseHelper;
import com.securevault.onepass.data.PasswordItem;
import com.securevault.onepass.databinding.FragmentPasswordBinding;
import com.securevault.onepass.utils.EditTextHelper;

import java.time.LocalDate;
import java.util.Objects;

public class PasswordFragment extends Fragment {
    private FragmentPasswordBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditTextHelper editTextHelper = new EditTextHelper();
        editTextHelper.setStrokeColorByTyping(binding.nameEditText);
        editTextHelper.setStrokeColorByTyping(binding.urlEditText);
        editTextHelper.setStrokeColorByTyping(binding.usernameEditText);
        editTextHelper.setStrokeColorByTyping(binding.passwordEditText);

        binding.addPasswordButton.setOnClickListener(v -> {
            String name = Objects.requireNonNull(binding.nameEditText.getText()).toString();
            String url = Objects.requireNonNull(binding.urlEditText.getText()).toString();
            String username = Objects.requireNonNull(binding.usernameEditText.getText()).toString();
            String password = Objects.requireNonNull(binding.passwordEditText.getText()).toString();

            if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter the details", Toast.LENGTH_SHORT).show();
                return;
            }

            LocalDate date = LocalDate.now();

            DatabaseHelper databaseHelper = DatabaseHelper.getInstance(requireContext());
            databaseHelper.passwordDao().insertRecord(new PasswordItem(name, url, username, password, date));
            Toast.makeText(requireContext(), "Password added successfully!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.nameEditText.setText(null);
        binding.urlEditText.setText(null);
        binding.usernameEditText.setText(null);
        binding.passwordEditText.setText(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}