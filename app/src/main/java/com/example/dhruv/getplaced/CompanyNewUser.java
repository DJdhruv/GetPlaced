package com.example.dhruv.getplaced;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class CompanyNewUser extends AppCompatActivity {
    private TextView akshay,dhruv,aditya;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_new_user);
        akshay=(TextView)findViewById(R.id.akshay);
        akshay.setText(Html.fromHtml("<a href=\"mailto:"+akshay.getText()+"\">Akshay Patidar</a>"));
        akshay.setMovementMethod(LinkMovementMethod.getInstance());
        dhruv=(TextView)findViewById(R.id.dhruv);
        dhruv.setText(Html.fromHtml("<a href=\"mailto:"+dhruv.getText()+"\">Dhruv Jaglan</a>"));
        akshay.setMovementMethod(LinkMovementMethod.getInstance());
        aditya=(TextView) findViewById(R.id.aditya);
        aditya.setText(Html.fromHtml("<a href=\"mailto:"+aditya.getText()+"\">Aditya Jadhav</a>"));
        aditya.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
