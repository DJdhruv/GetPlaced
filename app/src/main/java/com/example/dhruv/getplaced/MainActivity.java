package com.example.dhruv.getplaced;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.net.Uri;
import android.webkit.WebView;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private Button companyLogin,studentLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        companyLogin = (Button) findViewById(R.id.company_login);
        companyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, companylogin.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        studentLogin=(Button) findViewById(R.id.student_login);
        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String URL = " http://gymkhana.iitb.ac.in/sso/oauth/authorize/?client_id=U5AjsEUlbXdDrcTilrXOmYAkAp6iTEulx9pBrP6x&response_type=code&scope=basic&redirect_uri=http://www.google.co.in&state=basic";
                Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("URL"));
                startActivity(openBrowser);*/



            }
        });

    }
}