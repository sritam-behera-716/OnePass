package com.securevault.onepass.ui.main.password;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.securevault.onepass.R;
import com.securevault.onepass.databinding.ActivityGeneratePasswordBinding;
import com.securevault.onepass.utils.ClipboardHelper;
import com.securevault.onepass.utils.PasswordGeneratorHelper;

import java.util.Objects;

public class GeneratePasswordActivity extends AppCompatActivity {
    private ActivityGeneratePasswordBinding binding;
    private PasswordGeneratorHelper generator;
    private String[] length;
    private String[] types;
    private String[] yesNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityGeneratePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        length = getResources().getStringArray(R.array.length);
        types = getResources().getStringArray(R.array.letter_types);
        yesNo = getResources().getStringArray(R.array.yes_no);
        generator = new PasswordGeneratorHelper();

        setUpUserInterface();
        generateRandomPassword();

        binding.randomizeButton.setOnClickListener(v -> generateRandomPassword());
        binding.copyButton.setOnClickListener(v -> copyPassword());
    }

    private void generateRandomPassword() {
        int lengthIndex = binding.lengthSpinner.getSelectedItemPosition();
        int typesIndex = binding.letterTypesSpinner.getSelectedItemPosition();
        int symbolsIndex = binding.symbolsSpinner.getSelectedItemPosition();
        int digitsIndex = binding.digitsSpinner.getSelectedItemPosition();

        int passwordLength = Integer.parseInt(length[lengthIndex]);
        boolean includeUppercase;
        boolean includeLowercase;
        switch (typesIndex) {
            case 0:
                includeUppercase = true;
                includeLowercase = true;
                break;
            case 1:
                includeUppercase = true;
                includeLowercase = false;
                break;
            case 2:
                includeUppercase = false;
                includeLowercase = true;
                break;
            default:
                includeUppercase = false;
                includeLowercase = false;
                break;
        }

        boolean includeSymbols = symbolsIndex == 0;
        boolean includeDigits = digitsIndex == 0;

        String password = generator.generatePassword(passwordLength, includeUppercase, includeLowercase, includeSymbols, includeDigits);
        binding.passwordText.setText(password);
    }

    private void copyPassword() {
        final String password = Objects.requireNonNull(binding.passwordText.getText()).toString();
        ClipboardHelper.copyToClipboard(this, password);
        Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show();
    }

    private void setUpUserInterface() {
        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, length);
        lengthAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.lengthSpinner.setAdapter(lengthAdapter);

        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, types);
        typesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.letterTypesSpinner.setAdapter(typesAdapter);

        ArrayAdapter<String> yesNoAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, yesNo);
        yesNoAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.symbolsSpinner.setAdapter(yesNoAdapter);
        binding.digitsSpinner.setAdapter(yesNoAdapter);

        binding.lengthSpinner.setSelection(1);
        binding.letterTypesSpinner.setSelection(0);
        binding.symbolsSpinner.setSelection(0);
        binding.digitsSpinner.setSelection(0);
    }
}