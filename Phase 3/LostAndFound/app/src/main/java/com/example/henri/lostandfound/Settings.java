package com.example.henri.lostandfound;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by Henri on 22.03.2018.
 */

public class Settings extends Fragment {

    View myView;

    EditText etSettingsFirstName, etSettingsLastName, etSettingsEmail, etSettingsPassword, etSettingsNewPassword, etSettingsConfirmNewPassword;
    Button btnApply;
    String dataJSON = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_settings, container, false);

        etSettingsFirstName = (EditText) myView.findViewById(R.id.etSettingsFirstName);
        etSettingsLastName = (EditText) myView.findViewById(R.id.etSettingsLastName);
        etSettingsEmail = (EditText) myView.findViewById(R.id.etSettingsEmail);
        etSettingsPassword = (EditText) myView.findViewById(R.id.etSettingsPassword);
        etSettingsNewPassword = (EditText) myView.findViewById(R.id.etSettingsNewPassword);
        etSettingsConfirmNewPassword = (EditText) myView.findViewById(R.id.etSettingsConfirmNewPassword);
        btnApply = (Button) myView.findViewById(R.id.btnApply);

        //Get data
        dataJSON = ((Menu) getActivity()).extraDataJSON;

        try {
            JSONObject jsonObject = new JSONObject(dataJSON);
            etSettingsFirstName.setText(jsonObject.getString("firstName"));
            etSettingsLastName.setText(jsonObject.getString("lastName"));
            etSettingsEmail.setText(jsonObject.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Set title bar
        ((Menu) getActivity()).setActionBarTitle("Settings");

        //Hide FloatingActionButton
        ((Menu) getActivity()).hideFAB();

        //Hide keyboard when activity starts
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if both new passwords are empty OR new passwords don't match
                if (etSettingsNewPassword.getText().toString().isEmpty() && etSettingsConfirmNewPassword.getText().toString().isEmpty() ||
                        (etSettingsNewPassword.getText().toString().equals(etSettingsConfirmNewPassword.getText().toString()) && !etSettingsNewPassword.getText().toString().isEmpty())) {
                    try {
                        JSONObject jsonObject = new JSONObject(dataJSON);
                        if (digest("SHA-256", etSettingsPassword.getText().toString()).equals(jsonObject.getString("password"))) {
                            UpdateCredentials process = new UpdateCredentials();
                            process.execute();
                            startActivity(new Intent(getActivity(), Login.class));
                            Toast.makeText(getContext(), "Please login again.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Please enter your password to apply changes.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "The new passwords don't match.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return myView;
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

    public class UpdateCredentials extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {

                JSONObject jsonObject = new JSONObject(dataJSON);

                String email = etSettingsEmail.getText().toString();
                int indexAt = email.indexOf("@");
                String newEmail = email.substring(0, indexAt) + "%40" + email.substring(indexAt + 1);

                if (etSettingsNewPassword.getText().toString().equals(etSettingsConfirmNewPassword.getText().toString()) && !etSettingsNewPassword.getText().toString().isEmpty()) {
                    String strUrl = "http://10.0.2.2:8080/LostFound-war/EditServlet?id=" + jsonObject.get("id") +
                            "&firstName=" + etSettingsFirstName.getText().toString() +
                            "&lastName=" + etSettingsLastName.getText().toString() +
                            "&email=" + newEmail +
                            "&password=" + digest("SHA-256", etSettingsNewPassword.getText().toString()) +
                            "&submit=submit";

                    URL url = new URL(strUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    //This line is necessary for some reason
                    InputStream inputStream = httpURLConnection.getInputStream();

                } else {
                    String strUrl = "http://10.0.2.2:8080/LostFound-war/EditServlet?id=" + jsonObject.get("id") +
                            "&firstName=" + etSettingsFirstName.getText().toString() +
                            "&lastName=" + etSettingsLastName.getText().toString() +
                            "&email=" + newEmail +
                            "&password=" + digest("SHA-256", etSettingsPassword.getText().toString()) +
                            "&submit=submit";

                    URL url = new URL(strUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    //This line is necessary for some reason
                    InputStream inputStream = httpURLConnection.getInputStream();

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

    }

}
