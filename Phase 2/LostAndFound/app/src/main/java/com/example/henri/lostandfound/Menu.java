package com.example.henri.lostandfound;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Change ActionBar name
                getSupportActionBar().setTitle("Notice");

                //Hide FloatingActionButton
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.hide();

                //Switch Interface
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new Notice())
                        .commit();

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.nav_notice);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Highlight "Status" in navigation drawer onCreate
        navigationView.setCheckedItem(R.id.nav_status);

        //Set "Status" as default view onCreate
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_status));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Change ActionBar name
            getSupportActionBar().setTitle("Settings");

            //Hide FloatingActionButton
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();

            //Switch Interface
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new Settings())
                    .commit();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setCheckedItem(R.id.nav_settings);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_status) {
            //Change ActionBar name
            getSupportActionBar().setTitle("Status");

            //Show FloatingActionButton
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.show();

            //Switch Interface
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new Status())
                    .commit();
        } else if (id == R.id.nav_notice) {
            //Change ActionBar name
            getSupportActionBar().setTitle("New notice");

            //Hide FloatingActionButton
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();

            //Switch Interface
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new Notice())
                    .commit();
        } else if (id == R.id.nav_map) {
            //Change ActionBar name
            getSupportActionBar().setTitle("Map");

            //Hide FloatingActionButton
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();

            //Switch Interface
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new Map())
                    .commit();
        } else if (id == R.id.nav_settings) {
            //Change ActionBar name
            getSupportActionBar().setTitle("Settings");

            //Hide FloatingActionButton
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();

            //Switch Interface
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new Settings())
                    .commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
