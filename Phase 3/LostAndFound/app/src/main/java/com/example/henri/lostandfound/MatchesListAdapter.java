package com.example.henri.lostandfound;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MatchesListAdapter extends BaseAdapter {

    private Context context;
    private List<Matches> matchesList;

    //Constructor
    public MatchesListAdapter(Context context, List<Matches> matchesList) {
        this.context = context;
        this.matchesList = matchesList;
    }

    @Override
    public int getCount() {
        return matchesList.size();
    }

    @Override
    public Object getItem(int position) {
        return matchesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.matches_list, null);
        TextView tvCategory = (TextView) v.findViewById(R.id.tvCategory);
        TextView tvDescription = (TextView) v.findViewById(R.id.tvDescription);

        tvCategory.setText(matchesList.get(position).getCategory());
        tvDescription.setText(matchesList.get(position).getDescription());

        //Save match id to tag
        v.setTag(matchesList.get(position).getId());

        return v;
    }

}
