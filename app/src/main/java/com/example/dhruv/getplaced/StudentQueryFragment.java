package com.example.dhruv.getplaced;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dhruv on 28/9/17.
 */

public class StudentQueryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_studentquery, container, false);
        ListView query = (ListView) rootView.findViewById(R.id.querylist);
        ArrayList<queritem> list = new ArrayList<queritem>();
        list.add(new queritem("facebook", "dhruv", "newoffer", "a min ago"));
        list.add(new queritem("google", "dhruv", "newoffer", "a min ago"));
        querylistAdapter listAdapter = new querylistAdapter(getActivity(), list);
        query.setAdapter(listAdapter);
        return rootView;
    }

    class queritem {
        public String heading;
        public String bywhom;
        public String details;
        public String date;

        queritem(String Heading, String Bywhom, String Details, String Date) {

            heading = Heading;
            bywhom = Bywhom;
            details = Details;
            date = Date;
        }
    }


    class querylistAdapter extends BaseAdapter {
        ArrayList<queritem> list;
        Context context;

        public querylistAdapter(Context mcontext, ArrayList<queritem> mlist) {
            this.context = mcontext;
            this.list = mlist;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.query_item, parent, false);
            TextView heading = (TextView) row.findViewById(R.id.heading);
            TextView bywhom = (TextView) row.findViewById(R.id.bywhom);
            TextView details = (TextView) row.findViewById(R.id.content);
            TextView time = (TextView) row.findViewById(R.id.time);
            heading.setText(list.get(position).heading);
            bywhom.setText(list.get(position).bywhom);
            details.setText(list.get(position).details);
            time.setText(list.get(position).date);
            return row;
        }
    }
}