package com.example.henri.lostandfound;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Henri on 22.03.2018.
 */

public class Map extends Fragment
        implements OnMapReadyCallback {

    private static final int MY_REQUEST_INT = 177;
    View myView;
    GoogleMap map;
    Double lat = 46.522625;
    Double lng = 6.584393;
    String description = "UNIL-Internef";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_map, container, false);

        //Set title bar
        ((Menu) getActivity()).setActionBarTitle("Map");

        //Hide FloatingActionButton
        ((Menu) getActivity()).hideFAB();

        if (getArguments() != null) {
            lat = Double.parseDouble(getArguments().getString("latitude"));
            lng = Double.parseDouble(getArguments().getString("longitude"));
            description = getArguments().getString("description");
        }

        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        //Default location: UNIL - Internef
        LatLng latlng = new LatLng(lat, lng);
        MarkerOptions option = new MarkerOptions();
        option.position(latlng).title(description);
        map.addMarker(option);

        //Zoom in to location pp
        float zoomLevel = 16.0f; //This goes up to 21
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoomLevel));

        //Enable current location
        //Check permission of localization
        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_INT);
            }
        } else {
            map.setMyLocationEnabled(true);
        }
    }

}
