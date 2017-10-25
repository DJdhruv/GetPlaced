package com.example.dhruv.getplaced;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button companyLogin;
    private Button studentLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        companyLogin = (Button) findViewById(R.id.company_login);
        studentLogin = (Button) findViewById(R.id.student_login);

        companyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, companylogin.class);

                /*i.putExtra("FontSize","10");
                i.putExtra("HeadingSize","Large");
                i.putExtra("itemsep","Normal");*/
                startActivity(i);
            }
        });
        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, studentlogin.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });



    }
}