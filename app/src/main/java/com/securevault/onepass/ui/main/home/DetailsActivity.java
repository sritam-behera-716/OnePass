package com.securevault.onepass.ui.main.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.securevault.onepass.R;
import com.securevault.onepass.data.DatabaseHelper;
import com.securevault.onepass.data.PasswordItem;
import com.securevault.onepass.databinding.ActivityDetailsBinding;
import com.securevault.onepass.utils.ClipboardHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private boolean isPasswordHide = true;
    private int id;
    private String title;
    private String date;
    private String link;
    private String username;
    private String password;

    private final ActivityResultLauncher<Intent> detailsLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    setResult(Activity.RESULT_OK);
                    refreshDetails();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setUpUserInterface();

        binding.passwordToggle.setOnClickListener(v -> showOrHidePassword());
        binding.copyIcon.setOnClickListener(v -> copyPassword());
        binding.deleteButton.setOnClickListener(v -> deletePassword());
        binding.updateButton.setOnClickListener(v -> updatePassword());
    }

    private void showOrHidePassword() {
        if (isPasswordHide) {
            binding.passwordToggle.setImageResource(R.drawable.ic_eye_open);
            binding.passwordText.setText(password);
            binding.passwordText.setTransformationMethod(null);
            isPasswordHide = false;
        } else {
            binding.passwordToggle.setImageResource(R.drawable.ic_eye_close);
            binding.passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isPasswordHide = true;
        }
    }

    private void copyPassword() {
        ClipboardHelper.copyToClipboard(this, binding.passwordText.getText().toString());
        Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
    }

    private void updatePassword() {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("password_name", title);
        intent.putExtra("created_date", getOriginalDate(date));
        intent.putExtra("url", link);
        intent.putExtra("username", username);
        intent.putExtra("encrypted_password", password);
        detailsLauncher.launch(intent);
    }

    private void deletePassword() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if (id == -1) {
            Toast.makeText(this, "Password can't be delete!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        databaseHelper.passwordDao().deleteRecord(id);
        Toast.makeText(this, "Password successfully deleted!", Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void refreshDetails() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        PasswordItem passwordItem = databaseHelper.passwordDao().retrieveRecordById(id);

        title = passwordItem.getPasswordName();
        link = passwordItem.getUrl();
        username = passwordItem.getUsername();
        password = passwordItem.getEncryptedPassword();
        date = getFormattedDate(passwordItem.getCreatedDate().toString());

        binding.screenTitle.setText(title);
        binding.linkText.setText(link.isEmpty() ? "null" : link);
        binding.userText.setText(username);
        binding.passwordText.setText(password);
        binding.dateText.setText(date);

        binding.passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        isPasswordHide = true;
    }


    private void setUpUserInterface() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        title = intent.getStringExtra("password_name");
        date = getFormattedDate(intent.getStringExtra("created_date"));
        link = intent.getStringExtra("url");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("encrypted_password");

        binding.screenTitle.setText(title);
        binding.dateText.setText(date);
        binding.linkText.setText(link.isEmpty() ? "null" : link);
        binding.userText.setText(username);
        binding.passwordText.setText(password);
        binding.passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private String getFormattedDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
        return localDate.format(formatter);
    }

    private String getOriginalDate(String formattedDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
        LocalDate localDate = LocalDate.parse(formattedDate, formatter);
        return localDate.toString();
    }
}