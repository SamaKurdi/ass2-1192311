package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private CheckBox checkBoxRememberMe;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.usernameEditText1);
        editTextPassword = findViewById(R.id.passwordEditText1);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe1);
        Button buttonLogin = findViewById(R.id.loginButton1);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Check if Remember Me is enabled
        boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);
        if (rememberMe) {
            // If enabled, load saved credentials
            String savedUsername = sharedPreferences.getString("username", "");
            String savedPassword = sharedPreferences.getString("password", "");

            editTextUsername.setText(savedUsername);
            editTextPassword.setText(savedPassword);
            checkBoxRememberMe.setChecked(true);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve entered credentials
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Save credentials if Remember Me is checked
                if (checkBoxRememberMe.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putBoolean("rememberMe", true);
                    editor.apply();
                } else {
                    // If Remember Me is not checked, clear saved credentials
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("username");
                    editor.remove("password");
                    editor.remove("rememberMe");
                    editor.apply();
                }
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save the current state (e.g., credentials and Remember Me status) when the activity is paused
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("rememberMe", checkBoxRememberMe.isChecked());
        editor.apply();
    }
}
