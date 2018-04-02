package com.example.henri.lostandfound;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Henri on 22.03.2018.
 */

public class Status extends Fragment {

    View myView;
    ListView lvMatches;
    private MatchesListAdapter adapter;
    private List<Matches> matchesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_status, container, false);

        lvMatches = (ListView) myView.findViewById(R.id.listview_matches);
        matchesList = new ArrayList<>();

        //Add data
        matchesList.add(new Matches(1, "TestCat1", "Nice description1 here."));
        matchesList.add(new Matches(2, "TestCat2", "Nice description2 here."));
        matchesList.add(new Matches(3, "TestCat3", "Nice description3 here."));
        matchesList.add(new Matches(4, "TestCat4", "Nice description4 here."));
        matchesList.add(new Matches(5, "TestCat5", "Nice description5 here."));
        matchesList.add(new Matches(6, "TestCat6", "Nice description6 here."));
        matchesList.add(new Matches(7, "TestCat7", "Nice description7 here."));
        matchesList.add(new Matches(8, "TestCat8", "Nice description8 here."));
        matchesList.add(new Matches(9, "TestCat9", "Nice description9 here."));
        matchesList.add(new Matches(10, "TestCat10", "Nice description10 here."));
        matchesList.add(new Matches(11, "TestCat11", "Nice description11 here."));
        matchesList.add(new Matches(12, "TestCat12", "Nice description12 here."));
        matchesList.add(new Matches(13, "TestCat13", "Nice description13 here."));

        //Init adapter
        adapter = new MatchesListAdapter(getContext(), matchesList);
        lvMatches.setAdapter(adapter);

        lvMatches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Do something
                Toast.makeText(getContext(), "Clicked match id: " + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

        return myView;
    }

}
