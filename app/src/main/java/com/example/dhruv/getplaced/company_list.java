package com.example.dhruv.getplaced;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by dhruv on 26/9/17.
 */

public class company_list extends Fragment {
    private ListView companies;

    String companyNames[]={"google","facebook"};
    int logo[]={R.mipmap.logo,R.mipmap.logo};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.companylist, container, false);
        companies = (ListView) rootView.findViewById(R.id.companylist);
        CompanyListAdapter listAdapter = new CompanyListAdapter(getActivity().getApplicationContext(), companyNames, logo);
        companies.setAdapter(listAdapter);
        return rootView;
    }


    //-------------------------------------


    class CompanyListAdapter extends BaseAdapter {
        public Context context;
        public String companylist[];
        public int logo[];
        public LayoutInflater inflter;

        public CompanyListAdapter(Context applicationContext, String[] companylist, int[] logo) {
            this.context = applicationContext;
            this.companylist = companylist;
            this.logo = logo;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return companylist.length;
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
        public View getView(int i, View view, ViewGroup viewgroup) {
            view = inflter.inflate(R.layout.company_listitem, null);
            TextView country = (TextView)           view.findViewById(R.id.company_name);
            ImageView icon = (ImageView) view.findViewById(R.id.company_icon);
            country.setText(companylist[i]);
            icon.setImageResource(logo[i]);
            return view;
        }
    }

}

