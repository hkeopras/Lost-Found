package com.example.henri.lostandfound;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Register extends AppCompatActivity implements AsyncInterfaceCredentials{

    TextView tvRegisterLogin;
    EditText etRegisterFirstName, etRegisterLastName, etRegisterEmail, etRegisterPassword, etRegisterConfirmPassword;
    Button btnRegister;

    String dataJSON = "";
    Boolean isEmailUsed = Boolean.FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Hide keyboard when activity starts
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        tvRegisterLogin = (TextView) findViewById(R.id.tvRegisterLogin);
        etRegisterFirstName = (EditText) findViewById(R.id.etRegisterFirstName);
        etRegisterLastName = (EditText) findViewById(R.id.etRegisterLastName);
        etRegisterEmail = (EditText) findViewById(R.id.etRegisterEmail);
        etRegisterPassword = (EditText) findViewById(R.id.etRegisterPassword);
        etRegisterConfirmPassword = (EditText) findViewById(R.id.etRegisterConfirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        //Get existing email
        FetchDataCredentials process = new FetchDataCredentials(this);
        process.execute();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etRegisterFirstName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your first name.", Toast.LENGTH_SHORT).show();
                } else if (etRegisterLastName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your last name.", Toast.LENGTH_SHORT).show();
                } else if (android.util.Patterns.EMAIL_ADDRESS.matcher(etRegisterEmail.getText().toString()).matches()) {
                    isEmailUsed = Boolean.FALSE;

                    if(etRegisterPassword.getText().toString().equals(etRegisterConfirmPassword.getText().toString())) {

                        //Check if email exists in DB
                        try {
                            JSONArray jsonArray = new JSONArray(dataJSON);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                                if (jsonObject.getString("email").equals(etRegisterEmail.getText().toString())) {
                                    isEmailUsed = Boolean.TRUE;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //Insert data only if not already used
                        if (isEmailUsed) {
                            Toast.makeText(getApplicationContext(), "This email is already used!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Creating account...", Toast.LENGTH_SHORT).show();
                            AddCredentials process = new AddCredentials();
                            process.execute();
                            finish();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords are not the same", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Toast.makeText(getApplicationContext(), "Email is not valid.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });
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

    @Override
    public void response(String response) {
        dataJSON = response;
    }

    public class AddCredentials extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                String url = "jdbc:derby://10.0.2.2:1527/LostAndFound";
                Connection conn = DriverManager.getConnection(url,"LFadmin","LFadmin");
                Statement st = conn.createStatement();
                st.executeUpdate("INSERT INTO LFADMIN.USERS (firstName, lastName, email, password) " +
                        "VALUES (\'" + etRegisterFirstName.getText().toString() + "\', \'"
                        + etRegisterLastName.getText().toString() + "\', \'"
                        + etRegisterEmail.getText().toString() + "\', \'"
                        + digest("SHA-256", etRegisterPassword.getText().toString()) + "\')");
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

    }

}
