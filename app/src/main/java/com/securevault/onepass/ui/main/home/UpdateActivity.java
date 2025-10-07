package com.securevault.onepass.ui.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.securevault.onepass.R;
import com.securevault.onepass.data.DatabaseHelper;
import com.securevault.onepass.data.PasswordItem;
import com.securevault.onepass.databinding.ActivityUpdateBinding;
import com.securevault.onepass.utils.EditTextHelper;

import java.time.LocalDate;
import java.util.Objects;

public class UpdateActivity extends AppCompatActivity {
    private ActivityUpdateBinding binding;
    private int id;
    private String title;
    private String date;
    private String link;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpToolBar();
        setUpUserInterface();

        EditTextHelper editTextHelper = new EditTextHelper();
        editTextHelper.setDrawableEndIcon(this, binding.passwordInputLayout.passwordEditText);

        binding.passwordInputLayout.screenTitle.setText(R.string.update);
        binding.passwordInputLayout.addPasswordButton.setText(R.string.save_changes);

        binding.passwordInputLayout.addPasswordButton.setOnClickListener(v -> updatePassword());
    }

    private void setUpUserInterface() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        title = intent.getStringExtra("password_name");
        date = intent.getStringExtra("created_date");
        link = intent.getStringExtra("url");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("encrypted_password");

        binding.passwordInputLayout.nameEditText.setText(title);
        binding.passwordInputLayout.urlEditText.setText(link);
        binding.passwordInputLayout.usernameEditText.setText(username);
        binding.passwordInputLayout.passwordEditText.setText(password);
    }

    private void updatePassword() {
        title = Objects.requireNonNull(binding.passwordInputLayout.nameEditText.getText()).toString();
        link = Objects.requireNonNull(binding.passwordInputLayout.urlEditText.getText()).toString();
        username = Objects.requireNonNull(binding.passwordInputLayout.usernameEditText.getText()).toString();
        password = Objects.requireNonNull(binding.passwordInputLayout.passwordEditText.getText()).toString();

        if (title.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter the details", Toast.LENGTH_SHORT).show();
            return;
        }

        title = title.substring(0, 1).toUpperCase() + title.substring(1);

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        databaseHelper.passwordDao().updateRecord(new PasswordItem(id, title, link, username, password, LocalDate.parse(date)));
        Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
    }

    private void setUpToolBar() {
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }
}