package com.example.henri.lostandfound;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by Henri on 22.03.2018.
 */

public class Settings extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_settings, container, false);

        // Set title bar
        ((Menu) getActivity()).setActionBarTitle("Settings");

        //Hide FloatingActionButton
        ((Menu) getActivity()).hideFAB();

        //Hide keyboard when activity starts
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        return myView;
    }

}
