package com.example.dhruv.getplaced;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private Button companyLogin;
    private Button student_login;
    private String URL;
    private String AUTHORIZATION_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        companyLogin = (Button) findViewById(R.id.company_login);
        student_login = (Button) findViewById(R.id.student_login);
        URL = " https://gymkhana.iitb.ac.in/sso/oauth/authorize/?client_id=U5AjsEUlbXdDrcTilrXOmYAkAp6iTEulx9pBrP6x&response_type=code&scope=basic&redirect_uri=https://www.google.co.in/&state=some_state";
        companyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, companylogin.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        student_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                AlertDialog.Builder SSO_login = new AlertDialog.Builder(MainActivity.this);
                View SSO_view = getLayoutInflater().inflate(R.layout.sso_page, null);
                WebView SSO_page = (WebView) SSO_view.findViewById(R.id.sso_login);
                SSO_page.loadUrl(URL);
                SSO_login.setView(SSO_view);
                final AlertDialog dialog = SSO_login.create();
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

    private void sendPost(String Key) throws Exception {

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
        System.out.println(response.toString());

    }
}