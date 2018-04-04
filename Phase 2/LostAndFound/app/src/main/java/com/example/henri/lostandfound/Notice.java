package com.example.henri.lostandfound;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;

import io.matchmore.sdk.MatchMore;
import io.matchmore.sdk.MatchMoreSdk;
import io.matchmore.sdk.api.models.MobileDevice;
import io.matchmore.sdk.api.models.Publication;
import io.matchmore.sdk.api.models.Subscription;
import kotlin.Unit;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Henri on 22.03.2018.
 */

public class Notice extends Fragment
        implements View.OnClickListener {

    View myView;
    Spinner spinner;
    RadioButton radioLost, radioFound;
    EditText etDescription;
    Button btnCreateNotice;

    final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJpc3MiOiJhbHBzIiwic3ViIjoiMDlmZTEyZjUtODRmYS00YTI1LTg3NDAtODNjNTlmZjhiMTM3IiwiYXVkIjpbIlB1YmxpYyJdLCJuYmYiOjE1MjA1MDQ1ODYsImlhdCI6MTUyMDUwNDU4NiwianRpIjoiMSJ9.KhZOaDqod6QD0dVT_VSnMjqnpXJCfhyE6x9z8X0afAvE6wcS5pL3FhxCoN2yTWUorsmbXEHeX8gRSA_ivIgokQ";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_notice, container, false);

        //Set title bar
        ((Menu) getActivity()).setActionBarTitle("New notice");

        //Hide FAB
        ((Menu) getActivity()).hideFAB();

        //Hide keyboard when activity starts
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Hide keyboard when activity starts
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        spinner = (Spinner) myView.findViewById(R.id.spinner);
        radioLost = (RadioButton) myView.findViewById(R.id.radioLost);
        radioFound = (RadioButton) myView.findViewById(R.id.radioFound);
        etDescription = (EditText) myView.findViewById(R.id.etDescription);
        btnCreateNotice = (Button) myView.findViewById(R.id.btnCreateNotice);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.categoryArray, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Need this to check radio buttons
        myView.findViewById(R.id.radioLost).setOnClickListener(this);
        myView.findViewById(R.id.radioFound).setOnClickListener(this);

        btnCreateNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioLost.isChecked() == radioFound.isChecked()) {
                    Toast.makeText(getContext(), "Please indicate if the object is lost or found.", Toast.LENGTH_SHORT).show();
                } else if (spinner.getSelectedItem().toString().equals("Please select a category")) {
                    Toast.makeText(getContext(), "Please indicate a category.", Toast.LENGTH_SHORT).show();
                } else if (radioLost.isChecked() && TextUtils.isEmpty(etDescription.getText().toString())) {
                    Toast.makeText(getContext(), "Please provide a short description.", Toast.LENGTH_SHORT).show();
                } else if (radioLost.isChecked()) {
                    matchMorePub();
                    startActivity(new Intent(getContext(), Menu.class));
                } else if (radioFound.isChecked()) {
                    matchMoreSub();
                    startActivity(new Intent(getContext(), Menu.class));
                    match();
                }
            }
        });

        return myView;
    }

    @Override
    public void onClick(View view) {
        //Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        //Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioLost:
                if (checked)
                    break;
            case R.id.radioFound:
                if (checked)
                    break;
        }
    }

    public Void matchMorePub() {

        // Request Location permission
        ActivityCompat.requestPermissions(getActivity(), new String[]
                {android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        //Configuration of API
        if (!MatchMore.isConfigured()) {
            MatchMore.config(getContext(),
                    API_KEY,
                    true);
        }

        //Getting instance. It's a static variable. It's possible to have only one instance of matchmore.
        MatchMoreSdk matchMore = MatchMore.getInstance();

        //Creating main device
        matchMore.startUsingMainDevice((MobileDevice device) -> {

            Publication publication = new Publication(spinner.getSelectedItem().toString(), 0d, 3600d);
            HashMap hashMap = new HashMap<String, String>();
            hashMap.put("Description", etDescription.getText().toString());
            publication.setProperties(hashMap);
            matchMore.createPublication(publication, createdPublication -> {
                Log.d("Publication created", createdPublication.getId());
                Toast.makeText(getContext(), "Publication created", Toast.LENGTH_SHORT).show();
                return Unit.INSTANCE; // This is important (b/c kotlin vs java lambdas differ in implementation)
            }, e -> {
                e.printStackTrace();
                return Unit.INSTANCE;
            });
            return Unit.INSTANCE;
        }, e -> {
            e.printStackTrace();
            return Unit.INSTANCE;
        });

        return null;

    }

    public Void matchMoreSub() {

        // Request Location permission
        ActivityCompat.requestPermissions(getActivity(), new String[]
                {android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        //Configuration of API
        if (!MatchMore.isConfigured()) {
            MatchMore.config(getContext(),
                    API_KEY,
                    true);
        }

        //Getting instance. It's a static variable. It's possible to have only one instance of matchmore.
        MatchMoreSdk matchMore = MatchMore.getInstance();

        //Creating main device
        matchMore.startUsingMainDevice((MobileDevice device) -> {

            Subscription subscription = new Subscription(spinner.getSelectedItem().toString(), 1000d, 3600d, "");
            matchMore.createSubscription(subscription, createdSubscription -> {
                Log.d("Subscription created", createdSubscription.getId());
                Toast.makeText(getContext(), "Subscription created", Toast.LENGTH_SHORT).show();
                return Unit.INSTANCE;
            }, e -> {
                e.printStackTrace();
                return Unit.INSTANCE;
            });
            return Unit.INSTANCE;
        }, e -> {
            e.printStackTrace();
            return Unit.INSTANCE;
        });

        return null;

    }

    public Void match() {

        //Getting instance. It's a static variable. It's possible to have only one instance of matchmore.
        MatchMoreSdk matchMore = MatchMore.getInstance();

        // Start getting matches
        matchMore.getMatchMonitor().addOnMatchListener((matches, device) -> {
            Log.d("Matches found", device.getId());
            Log.d("Matches array", Arrays.deepToString(matchMore.getMatches().toArray()));

            SharedPreferences settings = getActivity().getSharedPreferences("deviceId", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("deviceId", device.getId());
            editor.apply();


            return Unit.INSTANCE;
        });

        matchMore.getMatchMonitor().startPollingMatches();

        // Start updating location
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            matchMore.startUpdatingLocation();
        }

        return null;

    }

}
