package com.example.dhruv.getplaced;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.dhruv.getplaced.Company.CompanyLogin;
import com.example.dhruv.getplaced.Student.StudentLogin;

/**
 * The first activity of our application.
 */
public class MainActivity extends AppCompatActivity {
    private Button companyLogin;
    private Button studentLogin;
    //! Called automatically when activity launches
    /**
     * Sets the layout of the activity
     * Contains two buttons, one for Student Login and other for Company Login
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        companyLogin = (Button) findViewById(R.id.company_login);
        studentLogin = (Button) findViewById(R.id.student_login);

        companyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, CompanyLogin.class);

                /*i.putExtra("FontSize","10");
                i.putExtra("HeadingSize","Large");
                i.putExtra("itemsep","Normal");*/
                startActivity(i);
            }
        });
        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, StudentLogin.class);

                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });



    }
}