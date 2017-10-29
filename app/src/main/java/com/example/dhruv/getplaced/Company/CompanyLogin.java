package com.example.dhruv.getplaced.Company;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dhruv.getplaced.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 *
 * Activity for company login
 */
public class CompanyLogin extends AppCompatActivity {
    public static String USERID, PASSWORD;
    private Button login,newuser;
    private EditText userid, password;

    private String JSONString;
    /**
     * Sets the layout of the activity
     *
     * contains a login button which vaildates login credentials form our backend
     *
     * contains a button for new user which opens a page containing a list of admins
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);
        newuser=(Button) findViewById(R.id.newuser_button);
        login = (Button)findViewById(R.id.login_button);
        userid = (EditText)findViewById(R.id.company_userid);
        password = (EditText)findViewById(R.id.company_password);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                USERID = userid.getText().toString();
                PASSWORD = password.getText().toString();
                new sendGet().execute();

            }
        });
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CompanyLogin.this,CompanyNewUser.class);
                startActivity(i);
            }
        });

    }

    /**
     *
     * @param ID Entered User Id
     *
     * @param PASS Entered password
     *
     *checks if entered credentials are valid
     */
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


    /**
     *
     * send get request to get list of all users from our backend
     */
    public class sendGet extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String...params){
            String url = "http://"+getResources().getString(R.string.ip_address)+"/login/company/?format=json";
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

        /**
         *
         * @param result the JSON string obtained from backend containing user data
         *
         * check login credentials, if valid opens CompanyHome page
         */
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            JSONString = result;
            try {
                if(check_account(USERID, PASSWORD)){
                    Toast.makeText(CompanyLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CompanyLogin.this, CompanyHome.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(CompanyLogin.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }
            return;
        }
    }
}
