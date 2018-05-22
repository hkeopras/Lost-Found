package com.example.henri.lostandfound;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity
        implements View.OnClickListener, AsyncInterfaceCredentials {

    TextView tvRegister;
    EditText etEmail, etPassword;
    Button btnLogin;
    CheckBox checkboxRememberMe;

    String dataJSON, currentDataJSON = "";
    Boolean isCorrectCredentials = Boolean.FALSE;

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
                Login.this.finish();
            }
        });

        FetchDataCredentials process = new FetchDataCredentials(this);
        process.execute();

    }

    public void onClick(View view) {

        //Check correct credentials
        try {
            //If the local server is not available
            if (dataJSON == null || dataJSON.isEmpty()) {
                if (etEmail.getText().toString().equals("admin") && etPassword.getText().toString().equals("admin")) {

                    currentDataJSON = "{\"id\":1,\"firstName\":\"Henri\",\"lastName\":\"Keopraseuth\",\"email\":\"admin\",\"password\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}";

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
                        Toast.makeText(getApplicationContext(), "Authenticating...", Toast.LENGTH_SHORT).show();
                        nextActivity();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong credentials.", Toast.LENGTH_SHORT).show();
                }

                //if the local server is available
            } else {
                JSONArray jsonArray = new JSONArray(dataJSON);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                    if ((jsonObject.getString("email").equals(etEmail.getText().toString()) && jsonObject.getString("password").equals(digest("SHA-256", etPassword.getText().toString())))) {
                        currentDataJSON = jsonObject.toString();
                        isCorrectCredentials = Boolean.TRUE;
                        break;
                    }
                }

                if (isCorrectCredentials) {
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
                        Toast.makeText(getApplicationContext(), "Authenticating...", Toast.LENGTH_SHORT).show();
                        nextActivity();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong credentials.", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (JSONException e) {
                e.printStackTrace();
            }

    }

    //Start Menu
    public void nextActivity() {
        Intent intent = new Intent(Login.this, Menu.class);
        intent.putExtra("jsonData", currentDataJSON);
        startActivity(intent);

        Login.this.finish();
    }

    @Override
    public void response(String response) {
        dataJSON = response;
    }

    public static String digest(String alg, String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(alg);
            byte[] buffer = input.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();
            return encodeHex(digest);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private static String encodeHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}