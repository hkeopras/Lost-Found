package com.example.henri.lostandfound;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Switch to "Menu"
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }
}
