package com.example.termproject;
/// RegistrationActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nameEditText, usernameEditText, passwordEditText, emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameEditText = findViewById(R.id.signup_name);
        usernameEditText = findViewById(R.id.signup_username);
        passwordEditText = findViewById(R.id.signup_password);
        emailEditText = findViewById(R.id.signup_email);

        Button registerButton = findViewById(R.id.signup_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void register() {
        // Get user input
        String name = nameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        // TODO: Perform registration and database operations

        // After successful registration, redirect to the login page
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        finish();
    }
}
