package com.example.henri.lostandfound;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class FetchData extends AsyncTask<Void, Void, String> {

    final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJpc3MiOiJhbHBzIiwic3ViIjoiMDlmZTEyZjUtODRmYS00YTI1LTg3NDAtODNjNTlmZjhiMTM3IiwiYXVkIjpbIlB1YmxpYyJdLCJuYmYiOjE1MjA1MDQ1ODYsImlhdCI6MTUyMDUwNDU4NiwianRpIjoiMSJ9.KhZOaDqod6QD0dVT_VSnMjqnpXJCfhyE6x9z8X0afAvE6wcS5pL3FhxCoN2yTWUorsmbXEHeX8gRSA_ivIgokQ";
    String dataJSON = "";
    private Context context;

    private AsyncInterface asyncInterface;

    public FetchData(AsyncInterface asyncInterface) {
        this.context = context;
        this.asyncInterface = asyncInterface;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            URL url = new URL("https://api.matchmore.io/v5/devices/" + "f9a4b459-74bf-477e-85db-06518cb015a6" + "/matches");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("api-key", API_KEY);
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                dataJSON = dataJSON + line;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataJSON;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        asyncInterface.response(aVoid);

        try {
            JSONArray test = new JSONArray(dataJSON);
            Log.d("TESTLENGTH", String.valueOf(test.length()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("JSONTEST", dataJSON);
    }

}
