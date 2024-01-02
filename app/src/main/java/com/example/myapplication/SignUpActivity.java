package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextFullName;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextFullName = findViewById(R.id.editTextFullName1);
        editTextEmail = findViewById(R.id.editTextEmail1);
        editTextPassword = findViewById(R.id.editTextPassword1);
        Button buttonSignUp = findViewById(R.id.buttonSignUp1);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve entered information
                String fullName = editTextFullName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                // Perform signup logic (you can replace this with your own logic)
                // For now, just display a toast message
                String message = "Sign up successful!\nFull Name: " + fullName + "\nEmail: " + email;
                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(LoginActivity.this, MainActivity.class));            }
        });
    }
}

