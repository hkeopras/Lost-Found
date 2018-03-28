package com.example.henri.lostandfound;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import io.matchmore.sdk.MatchMore;
import io.matchmore.sdk.MatchMoreSdk;
import io.matchmore.sdk.api.models.Device;
import io.matchmore.sdk.api.models.Publication;


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_notice, container, false);

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
                } else if (radioLost.isChecked()) {
                    //TODO: Create Publication
                } else if (radioFound.isChecked()) {
                    //TODO: Create Subscription
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

    public Void matchMore() {

        //Configuration of API
        MatchMore.config(getContext(),
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJpc3MiOiJhbHBzIiwic3ViIjoiMDlmZTEyZjUtODRmYS00YTI1LTg3NDAtODNjNTlmZjhiMTM3IiwiYXVkIjpbIlB" +
                        "1YmxpYyJdLCJuYmYiOjE1MjA1MDQ1ODYsImlhdCI6MTUyMDUwNDU4NiwianRpIjoiMSJ9.KhZOaDqod6QD0dVT_VSnMjqnpXJCfhyE6x9z8X0afAvE6wcS5pL3FhxCo" +
                        "N2yTWUorsmbXEHeX8gRSA_ivIgokQ",
                Boolean.parseBoolean("true"));

        //Getting instance. It's static variable. It's possible to have only one instance of matchmore.
        MatchMoreSdk matchMore = MatchMore.getInstance();

        //Creating main device
        matchMore.startUsingMainDevice(new Device() {

        });
        Publication publication = new Publication("Test topic", 1.0, 0.0);
        matchMore.createPublication(publication);

        return null;

    }

}
