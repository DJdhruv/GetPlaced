package com.example.dhruv.getplaced;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class JobofferFragment extends Fragment {
    private ImageButton add_job_offer;
    private ListView offers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView=inflater.inflate(R.layout.fragment_job_offer,container,false);

        add_job_offer=(ImageButton) rootView.findViewById(R.id.add_button);
        add_job_offer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i=new Intent(getActivity(), AddOffer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });
        offers = (ListView) rootView.findViewById(R.id.offerlist);
        List<String> listofoffers = new ArrayList<String>();
        listofoffers.add("foo");
        listofoffers.add("bar");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,
                listofoffers );

        offers.setAdapter(arrayAdapter);

        return rootView;
    }
    class CompanyListAdapter extends BaseAdapter {
        public Context context;
        public String offerlist[];

        public LayoutInflater inflter;

        public CompanyListAdapter(Context applicationContext, String[] offerlist) {
            this.context = applicationContext;
            this.offerlist = offerlist;

            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return offerlist.length;
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
            view = inflter.inflate(R.layout.company_offerlist_item, null);
            TextView offer = (TextView) view.findViewById(R.id.offer);

            offer.setText(offerlist[i]);

            return view;
        }
    }






}
