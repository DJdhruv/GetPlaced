package com.example.dhruv.getplaced;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by dhruv on 26/9/17.
 */

public class company_list extends Fragment {
    public company_list(){
    }
    private ListView companys;
    //String companynames[]={"google","facebook"};
   // int logo[]={R.mipmap.logo,R.mipmap.logo};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.companylist, container, false);
        //companys = (ListView) rootView.findViewById(R.id.company_list);
     //   CustomAdapter customAdapter = new CustomAdapter(rootView.getContext(), companynames, logo);

        return rootView;
    }
}
