package com.example.dhruv.getplaced;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by dhruv on 26/9/17.
 */

public class CompanyListFragment extends Fragment {
    private ListView companies;
    private PopupWindow itemclick;
    String companyNames[]={"google","facebook"};
    int logo[]={R.mipmap.logo,R.mipmap.logo};
    private Button closeitem;
    private TextView companyname;
    private TextView companydetail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      final View rootView = inflater.inflate(R.layout.fragment_companylist, container, false);
        companies = (ListView) rootView.findViewById(R.id.companylist);
        CompanyListAdapter listAdapter = new CompanyListAdapter(getActivity(), companyNames, logo);
        companies.setAdapter(listAdapter);
        companies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //LayoutInflater inflater =(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                //View layout= inflater.inflate(R.layout.fragment_comitemclick, (ViewGroup) view.findViewById(R.id.itemlayout));
                //itemclick = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                //itemclick.showAtLocation(layout, Gravity.CENTER, 0, 0);
                //temclick.setFocusable(true);
                //companyname=(TextView) layout.findViewById(R.id.companyname);
                //companydetail=(TextView) layout.findViewById(R.id.companydetails);
                //companyname.setText(companyNames[position]);
                //closeitem=(Button) layout.findViewById(R.id.cancel);
                //closeitem.setOnClickListener(new View.OnClickListener() {
                  //  @Override
                    //public void onClick(View v) {
                      //  itemclick.dismiss();
                   // }
                //});
                Intent i=new Intent(getActivity(),comitemclick.class);
                i.putExtra("companyname",companyNames[position]);
                startActivity(i);

            }
        });

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

