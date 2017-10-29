package com.example.dhruv.getplaced.Student;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 *
 * set the activities of different tabs
 *
 * headings of the tabbed layout
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)return new StudentProfileFragment();
        else if(position==1)return new StudentQueryFragment();

        else return new CompanyListFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) return "profile";
        else if(position==1) return "query";

        else return "Offers";
    }

}
