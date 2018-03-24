package com.example.henri.lostandfound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    TextView tvRegisterLogin;
    EditText etRegisterEmail, etRegisterPassword, etRegisterConfirmPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvRegisterLogin = (TextView) findViewById(R.id.tvRegisterLogin);
        etRegisterEmail = (EditText) findViewById(R.id.etRegisterEmail);
        etRegisterPassword = (EditText) findViewById(R.id.etRegisterPassword);
        etRegisterConfirmPassword = (EditText) findViewById(R.id.etRegisterConfirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etRegisterPassword.getText().toString().equals(etRegisterConfirmPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Creating account...", Toast.LENGTH_SHORT).show();
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(), "Passwords are not the same", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
