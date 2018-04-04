package com.example.henri.lostandfound;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by Henri on 22.03.2018.
 */

public class Status extends Fragment implements AsyncInterface {

    View myView;
    ListView lvMatches;
    private MatchesListAdapter adapter;
    private List<Matches> matchesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_status, container, false);

        // Set title bar
        ((Menu) getActivity()).setActionBarTitle("Status");

        //Hide FloatingActionButton
        ((Menu) getActivity()).showFAB();

        //Get JSON data
        FetchData process = new FetchData(Status.this);
        process.execute();

        return myView;
    }

    @Override
    public void response(String response) {

        lvMatches = (ListView) myView.findViewById(R.id.listview_matches);
        matchesList = new ArrayList<>();

        //Add JSON data
        try {
            JSONArray jsonArray = new JSONArray(response);

            //Get hashmap of JSON data with <key, value> being <position, Unix time in string>
            HashMap hashMap = new HashMap<String, String>();
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject sortJSON = new JSONObject(jsonArray.getString(i));
                String sortJSON1 = sortJSON.getString("createdAt");
                long sortJSON1BI = Long.parseLong(sortJSON1);
                //Only use data no older than 7 days
                if (sortJSON1BI > (System.currentTimeMillis())-3600000) {
                    hashMap.put(i, sortJSON1);
                }
            }

            Log.d("TESTSIZE", String.valueOf(hashMap.size()));

            //Add data by DESCENDING order
            for (int i=hashMap.size()-1; i>=0; i--) {
                int j = (Integer) sortHashMapByValues(hashMap).keySet().toArray()[i];

                JSONObject jsonObject = new JSONObject(jsonArray.getString(j));
                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("publication"));
                JSONObject jsonObject2 = new JSONObject((jsonObject1.getString("properties")));

                if (jsonObject2.length() != 0) {
                    matchesList.add(new Matches(j, jsonObject1.getString("topic"), jsonObject2.getString("Description")));
                } else {
                    matchesList.add(new Matches(j, jsonObject1.getString("topic"), "Description test")); //Only need this part because description was not mandatory at the beginning
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Init adapter
        adapter = new MatchesListAdapter(getContext(), matchesList);
        lvMatches.setAdapter(adapter);

        lvMatches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = new JSONObject(jsonArray.getString((Integer) view.getTag()));
                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("publication"));
                    JSONObject jsonObject2 = new JSONObject((jsonObject1.getString("location")));
                    JSONObject jsonObject3 = new JSONObject((jsonObject1.getString("properties")));

                    FragmentManager fragmentManager = getFragmentManager();
                    Bundle args = new Bundle();
                    args.putString("latitude", jsonObject2.getString("latitude"));
                    args.putString("longitude", jsonObject2.getString("longitude"));
                    args.putString("description", jsonObject3.getString("Description"));

                    Fragment fragment = new Map();
                    fragment.setArguments(args);

                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();

                    ((Menu) getActivity()).highlight(R.id.nav_map);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    //Sort HashMap by ASCENDING order
    public LinkedHashMap<Integer, String> sortHashMapByValues(
            HashMap<Integer, String> passedMap) {
        List<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
        List<String> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<Integer, String> sortedMap =
                new LinkedHashMap<>();

        Iterator<String> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            String val = valueIt.next();
            Iterator<Integer> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Integer key = keyIt.next();
                String comp1 = passedMap.get(key);
                String comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

}
