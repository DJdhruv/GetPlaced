package com.example.dhruv.getplaced;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

/**
 * Created by dell on 9/27/17.
 */

public class CompanyCategoryAdapter extends FragmentPagerAdapter {
    public CompanyCategoryAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position){
        if(position==0)return new JobofferFragment();
        else return new CompanyProfileFragment();

    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) return "Current Offers";

        else return "Profile";
    }
}

