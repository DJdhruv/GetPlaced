package com.example.dhruv.getplaced;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class companylogin extends AppCompatActivity {

    private Button login;
    private EditText userid, password;
    private String USERID, PASSWORD;
    private TextView Test;
    private String JSONString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companylogin);

        login = (Button)findViewById(R.id.login_button);
        userid = (EditText)findViewById(R.id.company_userid);
        password = (EditText)findViewById(R.id.company_password);
        Test = (TextView)findViewById(R.id.textView2);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                USERID = userid.getText().toString();
                PASSWORD = password.getText().toString();


            }
        });

    }

    public boolean check_account(String ID, String PASS) throws Exception {

        String response = JSONString;
        JSONArray jsonArray = new JSONArray(response);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if ((jsonObject.getJSONObject("userid").toString() == ID) &&
                    (jsonObject.getJSONObject("password").toString()) == PASS){
                return true;
            }
        }
        return false;
    }


    public class JSONTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String...params) {
            HttpURLConnection con = null;
            BufferedReader in = null;
            try {
                URL obj = new URL(params[0]);
                con = (HttpURLConnection) obj.openConnection();
                con.connect();
                InputStream stream = con.getInputStream();
                in = new BufferedReader(new InputStreamReader(stream));
                String inputLine;

                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
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

            return "nothing";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Test.setText(result);
            JSONString = result;
        }
    }
}
