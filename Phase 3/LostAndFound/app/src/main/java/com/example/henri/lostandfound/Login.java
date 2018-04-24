package com.example.henri.lostandfound;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity
        implements View.OnClickListener {

    TextView tvRegister;
    EditText etEmail, etPassword;
    Button btnLogin;
    CheckBox checkboxRememberMe;

    //Variables to save preferences
    private String email,password;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hide keyboard when activity starts
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        tvRegister = (TextView) findViewById(R.id.tvRegister);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        checkboxRememberMe = (CheckBox) findViewById(R.id.checkboxRememberMe);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        //Initializing preference
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            etEmail.setText(loginPreferences.getString("email", ""));
            etPassword.setText(loginPreferences.getString("password", ""));
            checkboxRememberMe.setChecked(true);
        }

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Register.class);
                startActivity(intent);
            }
        });
    }

    public void onClick(View view) {
        //Check correct credentials
        if(etEmail.getText().toString().equals("admin") && etPassword.getText().toString().equals("admin")) {
            Toast.makeText(getApplicationContext(), "Authenticating...", Toast.LENGTH_SHORT).show();

            if (view == btnLogin) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                //Store email and password WITHOUT ENCRYPTING if checkboxRememberMe is checked
                if (checkboxRememberMe.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("email", email);
                    loginPrefsEditor.putString("password", password);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }
                nextActivity();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
        }
    }

    //Start Menu
    public void nextActivity() {
        startActivity(new Intent(Login.this, Menu.class));
        Login.this.finish();
    }
}