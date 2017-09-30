package com.example.dhruv.getplaced;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by dhruv on 26/9/17.
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)return new StudentProfileFragment();
        else if(position==1)return new StudentQueryFragment();
        else if (position==2)return new StudentNotificationFragment();
        else return new CompanyListFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) return "profile";
        else if(position==1) return "query";
        else if (position==2) return null;
        else return "list";
    }

}
