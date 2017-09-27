package com.example.dhruv.getplaced;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;



public class studentlogin extends AppCompatActivity {
    private String URL;
    private String AUTHORIZATION_CODE;
    private EditText edit;
    private Button login,newuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        newuser=(Button) findViewById(R.id.newuser);
        login=(Button) findViewById(R.id.login_button);
        URL = " https://gymkhana.iitb.ac.in/sso/oauth/authorize/?client_id=U5AjsEUlbXdDrcTilrXOmYAkAp6iTEulx9pBrP6x&response_type=code&scope=basic&redirect_uri=https://www.google.co.in/&state=some_state";
       login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(studentlogin.this, student_home.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                AlertDialog.Builder SSO_login = new AlertDialog.Builder(studentlogin.this);
                View SSO_view = getLayoutInflater().inflate(R.layout.sso_page, null);
                WebView SSO_page = (WebView) SSO_view.findViewById(R.id.sso_login);
                edit = (EditText) SSO_view.findViewById(R.id.editText);
                edit.setFocusable(true);
                edit.requestFocus();
                SSO_page.loadUrl(URL);
                SSO_login.setView(SSO_view);
                final Dialog dialog = SSO_login.create();
                dialog.show();
                SSO_page.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);

                        if(url.contains("https://www.google.co.in/")){
                            if(url.contains("error=access_denied")){
                                Toast.makeText(getApplicationContext(),"Authorization Unsuccessful",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Authorization Sucessful",Toast.LENGTH_SHORT).show();
                                AUTHORIZATION_CODE = getQueryMap(url);
                                Intent i = new Intent(studentlogin.this, StudentRegistration.class);
                                startActivity(i);
                                //sendPost(AUTHORIZATION_CODE);
                            }
                            dialog.dismiss();
                        }
                        return true;
                    }

                });

            }
        });



    }

    public static String getQueryMap(String query)
    {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map.get("code");
    }

    private String sendPost(String Key) throws Exception {

        String url = "https://gymkhana.iitb.ac.in/sso/oauth/token/";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST /sso/oauth/token/ HTTP/1.1");
        con.setRequestProperty("Host", "gymkhana.iitb.ac.in");
        con.setRequestProperty("Authorization", "Basic AUTHENTICATION_TOKEN");

        String urlParameters = "code="+Key+"&redirect_uri=&grant_type=authorization_code";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();
    }


}
