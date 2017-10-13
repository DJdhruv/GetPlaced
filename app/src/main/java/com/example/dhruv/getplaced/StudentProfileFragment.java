package com.example.dhruv.getplaced;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;



public class StudentProfileFragment extends Fragment {
    private Button makeresume;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_studentprofile, container, false);
        makeresume=(Button) rootView.findViewById(R.id.createresume);
        makeresume.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ResumeMaker.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("FontSize","10");
                i.putExtra("HeadingSize","Large");
                i.putExtra("itemsep","Normal");
                startActivity(i);
            }
        });


        return rootView;

    }
}
