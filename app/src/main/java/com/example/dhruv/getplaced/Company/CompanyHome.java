package com.example.dhruv.getplaced.Company;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.dhruv.getplaced.R;

/**
 * Tabbed Activity containing profile and offers fragment
 */
public class CompanyHome extends AppCompatActivity {
    /**
     *
     * Sets the layout of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);

        ViewPager viewPager=(ViewPager) findViewById(R.id.view_pager);
        CompanyCategoryAdapter companycategoryAdapter = new CompanyCategoryAdapter(getSupportFragmentManager());

        viewPager.setAdapter(companycategoryAdapter);


        TabLayout tablayout = (TabLayout) findViewById(R.id.tab_layout);
        tablayout.setupWithViewPager(viewPager);

    }

    /**
     *
     * Refresh the activity
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
