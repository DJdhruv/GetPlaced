package com.example.dhruv.getplaced.Company;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dhruv.getplaced.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * this Activity shows student details to the company
 */

public class StudentProfileForCompany extends AppCompatActivity {
    public TextView name;
    public TextView userid;
    public TextView email;
    public TextView resume;
    public TextView contact,program,department;
    String Userid;
    public ImageView profilepic;

    /**
     *
     *Displays all the details of the Student
     *
     *send a get request to backend to get details of the Student
     *
     *link for the resume of the student
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_for_company);
        Bundle extras = getIntent().getExtras();

        Userid=extras.getString("userid");


        name = (TextView)findViewById(R.id.Studentname);
        userid = (TextView)findViewById(R.id.Userid);
        contact = (TextView)findViewById(R.id.Contactnumber);
        email = (TextView)findViewById(R.id.Emailaddress);
        resume=(TextView) findViewById(R.id.Resume);
        program=(TextView)findViewById(R.id.Program);
        department=(TextView)findViewById(R.id.Department);
        profilepic=(ImageView)findViewById(R.id.Profilepicture);
        Picasso.with(getApplicationContext()).load("http://"+getResources().getString(R.string.ip_address)+"/media/media/students/images/" +
                Userid+".jpg").fit().into(profilepic);

        new sendGet().execute();


        new sendGet().execute();
        Userid=extras.getString("userid");
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://"+getResources().getString(R.string.ip_address)+"/media/media/students/resume/"+Userid+".pdf");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setPackage("com.android.chrome");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
                }
            }
        });

    }

    /**
     *
     * Sends a get request to receive details of the particular student in json string format
     */

    public class sendGet extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String...params){






            String url = "http://"+getResources().getString(R.string.ip_address)+"/students/student/?format=json&q="+Userid;
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
         * displays Student's Details
         */
        @Override
        protected void onPostExecute(String result){
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                name.setText(jsonObject.getString("name").toString());
                userid.setText(jsonObject.getString("userid").toString());
                contact.setText(jsonObject.getString("contact_number").toString());
                email.setText(jsonObject.getString("email").toString());

                program.setText(jsonObject.getString("program").toString());
                department.setText(jsonObject.getString("department").toString());

            } catch (JSONException e) {
                Log.e("teesfs","sdfsfds");
            }
            super.onPostExecute(result);
            return;
        }
    }
}


