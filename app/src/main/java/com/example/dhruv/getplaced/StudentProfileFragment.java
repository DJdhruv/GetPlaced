package com.example.dhruv.getplaced;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dhruv on 26/9/17.
 */

public class StudentProfileFragment extends Fragment {
    public StudentProfileFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_studentprofile, container, false);
        return rootView;

    }
}
