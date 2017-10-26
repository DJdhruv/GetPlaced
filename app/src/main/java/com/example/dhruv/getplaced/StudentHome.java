package com.example.dhruv.getplaced;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StudentHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getSupportFragmentManager());
        viewPager.setAdapter(categoryAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        /*
        setting logo for tab layout
         */
        //tabLayout.getTabAt(2).setIcon(R.mipmap.logo);
    }

}



