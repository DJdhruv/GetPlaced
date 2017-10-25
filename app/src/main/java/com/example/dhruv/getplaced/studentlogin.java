package com.example.dhruv.getplaced;

import android.app.Dialog;
import android.content.Intent;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class studentlogin extends AppCompatActivity {
    private String URL;
    private String AUTHORIZATION_CODE;
    private  String ACCESS_TOKEN_JSON;
    private String ACCESS_TOKEN;
    private JSONObject USER_DATA_JSON;
    private EditText edit;
    private EditText userid, password;
    private Button login,newuser;
    public static String USERID, PASSWORD;
    private String JSONString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        newuser=(Button) findViewById(R.id.newuser);
        login=(Button) findViewById(R.id.login_button);
        userid = (EditText)findViewById(R.id.userid);
        password = (EditText)findViewById(R.id.password);
        URL = "https://gymkhana.iitb.ac.in/sso/oauth/authorize/?client_id=U5AjsEUlbXdDrcTilrXOmYAkAp6iTEulx9pBrP6x&response_type=code&scope=basic profile program&redirect_uri=https://www.google.co.in/&state=some_state";
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                USERID = userid.getText().toString();
                PASSWORD = password.getText().toString();
                new sendGetLogin().execute();
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
                                dialog.dismiss();
                            }
                            else{
                                AUTHORIZATION_CODE = getQueryMap(url);
                                if(AUTHORIZATION_CODE==null){
                                    url = URL;
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Authorization Successful",Toast.LENGTH_SHORT).show();
                                    Log.e("AuTHORIZATION CODE", AUTHORIZATION_CODE);
                                    new sendPost().execute(AUTHORIZATION_CODE);
                                    dialog.dismiss();
                                }

                            }

                        }
                        return true;
                    }
                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        handler.proceed(); // Ignore SSL certificate errors
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

    public class sendPost extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String...params){
            HttpURLConnection con = null;
            BufferedReader in = null;
            String url = "https://gymkhana.iitb.ac.in/sso/oauth/token/";
            try {
                // To ignore the certificate error on non-IITB network
                // ------------------START------------------
                TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {return null;}
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
                };
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {return true;}
                };
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
                // ------------------END------------------


                URL obj = new URL(url);
                con = (HttpsURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Host", "gymkhana.iitb.ac.in");
                con.setRequestProperty("Authorization", "Basic VTVBanNFVWxiWGREcmNUaWxyWE9tWUFrQXA2aVRFdWx4OXBCclA2eDp3NTNpb25IdEVNa2gyNFZBaHFmTUh0eVU1c3BFc0lMeGFQRElDUGE2Umk3SlQyblhWZzM4Z1VVR0RmWklUVlViWjVmamVkNXBqdjd1ejVJb1M3alFyQlNlbVRTMU1HTlpzZlpMSVdaaTNmVGRHSUlvUmp6ODR0bnFCQ0E0eGhJVg==");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                String urlParameters = "code=" + params[0] + "&redirect_uri=https://www.google.co.in/&grant_type=authorization_code";


                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();

                in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } finally {
                if(con != null) {
                    con.disconnect();
                }
                try {
                    if(in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ACCESS_TOKEN_JSON = result;
            JSONObject json;
            ACCESS_TOKEN = "";
            try {
                json = new JSONObject(ACCESS_TOKEN_JSON);
                ACCESS_TOKEN = json.getString("access_token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new sendGet().execute(ACCESS_TOKEN);
            return;

        }

    }
    public class sendGet extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String...params){
            String url = "https://gymkhana.iitb.ac.in/sso/user/api/user/?fields=first_name,last_name,roll_number";
            String Bearer_ACCESS_TOKEN = "Bearer "+params[0];
            HttpURLConnection con = null;
            BufferedReader in = null;
            try {
                // To ignore the certificate error on non-IITB network
                // ------------------START------------------
                TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {return null;}
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
                };
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {return true;}
                };
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
                // ------------------END------------------
                URL obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("GET");
                con.setRequestProperty("Host","gymkhana.iitb.ac.in");
                con.setRequestProperty("Authorization", Bearer_ACCESS_TOKEN);

                int responseCode = con.getResponseCode();
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } finally {
                if(con != null){
                    con.disconnect();
                }
                try{
                    if(in != null){
                        in.close();
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                USER_DATA_JSON = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent i = new Intent(studentlogin.this, StudentRegistration.class);
            try {
                i.putExtra("FirstName",USER_DATA_JSON.getString("first_name").toString());
                i.putExtra("LastName",USER_DATA_JSON.getString("last_name").toString());
                i.putExtra("LDAP", USER_DATA_JSON.getString("roll_number"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new LogoutPostRequest().execute(ACCESS_TOKEN);
            startActivity(i);
            return;
        }
    }
    public class LogoutPostRequest extends AsyncTask<String,Void,String> {


        String url;
        URL URL;

        @Override
        protected void onPreExecute() {
            url = "https://gymkhana.iitb.ac.in/sso/oauth/revoke_token/";
            try {
                URL = new URL(url);
            } catch (Exception e) { }
        }

        @Override
        protected String doInBackground(String... key) {
            HttpsURLConnection conn;
            StringBuffer response = new StringBuffer();

            try {

                // To ignore the certificate error on non-IITB network
                // ------------------START------------------
                TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {return null;}
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
                };
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {return true;}
                };
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
                // ------------------END------------------

                conn = (HttpsURLConnection) URL.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Host", "gymkhana.iitb.ac.in");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");


                String urlParameters = "token="+key[0]+"&client_id=XWdEl57bq3NkT1XJac4uDKXOlURJl0yIreldL8U3&client_secret=ydDAeGkntYpK2Nb46K2KuQ1xMnr6c0XEuTf5gS8TD2AuAFhx7JQSamVJV9mT2Pm4JspgRjOjELKEXOwZX54kL5IpX02Wgskyqe2FZi7KeOR5kHareA12ZeU6N3NfZjJm&token_type_hint=access_token";

                // Send post request
                conn.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            CookieManager cookieManager = CookieManager.getInstance();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.removeAllCookies(null);
            } else {
                cookieManager.removeAllCookie();
            }
        }

    }


    public boolean check_account(String ID, String PASS) throws Exception {

        JSONArray jsonArray = new JSONArray(JSONString);
        JSONObject jsonObject;
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            if ((jsonObject.getString("userid").toString().equals(ID)) &&
                    (jsonObject.getString("password").toString().equals(PASS))){
                return true;
            }
        }
        return false;
    }



    public class sendGetLogin extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String...params){
            String url = "http://"+getResources().getString(R.string.ip_address)+"/students/student/?format=json";
            HttpURLConnection con = null;
            BufferedReader in = null;
            try {
                URL obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("GET");

                int responseCode = con.getResponseCode();
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                if(con != null){
                    con.disconnect();
                }
                try{
                    if(in != null){
                        in.close();
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            JSONString = result;
            try {
                if(check_account(USERID, PASSWORD)){
                    Toast.makeText(studentlogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(studentlogin.this, StudentHome.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                else{
                    Toast.makeText(studentlogin.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }
            return;
        }
    }

}
