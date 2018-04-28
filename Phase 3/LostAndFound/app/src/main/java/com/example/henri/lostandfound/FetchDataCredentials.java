package com.example.henri.lostandfound;

import android.app.Activity;
import android.content.Context;
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


public class FetchDataCredentials extends AsyncTask<Void, Void, String> {

    String dataJSON = "";

    private AsyncInterfaceCredentials asyncInterface;

    public FetchDataCredentials(AsyncInterfaceCredentials asyncInterface) {
        this.asyncInterface = asyncInterface;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL("http://10.0.2.2:8080/LostAndFound_RESTfulAPI/webresources/testcontroller/getData");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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

        //Test length of JSONObject requested (for debug purposes)
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
