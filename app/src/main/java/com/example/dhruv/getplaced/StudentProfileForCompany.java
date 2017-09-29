package com.example.dhruv.getplaced;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StudentProfileForCompany extends AppCompatActivity {
    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_for_company);
        Bundle extras = getIntent().getExtras();
        name=(TextView) findViewById(R.id.name);

        name.setText(extras.getString("Name"));
    }

}
