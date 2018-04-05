package com.example.henri.lostandfound;

import android.app.Activity;
import android.app.Application;
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

import static android.content.Context.MODE_PRIVATE;


public class FetchData extends AsyncTask<Void, Void, String> {

    final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJpc3MiOiJhbHBzIiwic3ViIjoiMDlmZTEyZjUtODRmYS00YTI1LTg3NDAtODNjNTlmZjhiMTM3IiwiYXVkIjpbIlB1YmxpYyJdLCJuYmYiOjE1MjA1MDQ1ODYsImlhdCI6MTUyMDUwNDU4NiwianRpIjoiMSJ9.KhZOaDqod6QD0dVT_VSnMjqnpXJCfhyE6x9z8X0afAvE6wcS5pL3FhxCoN2yTWUorsmbXEHeX8gRSA_ivIgokQ";
    String dataJSON = "";
    private Context context;
    String deviceId;

    private AsyncInterface asyncInterface;

    //Constructor
    public FetchData(Context context, AsyncInterface asyncInterface) {
        this.context = context;
        this.asyncInterface = asyncInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        SharedPreferences sharedPreferences = context.getSharedPreferences("deviceId", MODE_PRIVATE);
        deviceId = sharedPreferences.getString("deviceId", deviceId);

    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.matchmore.io/v5/devices/" + deviceId + "/matches");
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

        //Test lenntgh of JSONObject requested (for debug purposes)
        try {
            JSONArray test = new JSONArray(dataJSON);
            Log.d("TESTLENGTH", String.valueOf(test.length()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Print in logcat the JSONObject
        Log.d("JSONTEST", dataJSON);
    }

}
