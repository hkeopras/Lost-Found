package com.example.henri.lostandfound;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;


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

        return myView;
    }
    @Override
    public void onClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioLost:
                if (checked)
                    break;
            case R.id.radioFound:
                if (checked)
                    break;
        }
    }

}
