package com.example.dhruv.getplaced;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import static android.content.Context.MODE_PRIVATE;
import static com.example.dhruv.getplaced.studentlogin.USERID;


public class StudentProfileFragment extends Fragment {
    private Button makeresume, logout;
    public TextView name;
    public TextView userid;
    public TextView email;
    public TextView contact,program,department;
    public ImageView profilepic;
    SharedPreferences sharedpreferences;
    SessionManager sesssion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_studentprofile, container, false);
        makeresume=(Button) rootView.findViewById(R.id.createresume);
        logout=(Button) rootView.findViewById(R.id.logout);
        name = (TextView)rootView.findViewById(R.id.studentname);
        userid = (TextView)rootView.findViewById(R.id.userid);
        contact = (TextView)rootView.findViewById(R.id.contactnumber);
        email = (TextView)rootView.findViewById(R.id.emailaddress);
        program=(TextView)rootView.findViewById(R.id.Program);
        department=(TextView)rootView.findViewById(R.id.Department);
        profilepic=(ImageView)rootView.findViewById(R.id.profilepicture);


        Intent i = new Intent();

        Picasso.with(getContext()).load("http://"+getResources().getString(R.string.ip_address)+"/media/media/students/images/" +
                USERID+".jpg").fit().into(profilepic);


        new sendGet().execute();
        makeresume.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ResumeMaker.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("FontSize","10");
                i.putExtra("HeadingSize","Large");
                i.putExtra("itemsep","Normal");
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                getActivity().onBackPressed();
                Toast.makeText(getActivity().getApplicationContext(), "Successfully Logged Out", Toast.LENGTH_SHORT).show();
            }
        });



        return rootView;
    }

    public class sendGet extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String...params){

            String url = "http://"+getResources().getString(R.string.ip_address)+"/students/student/?format=json&q="+USERID;
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
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                SpannableString ss1=  new SpannableString("Name : ");
                ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
                name.setText(ss1);
                name.append(jsonObject.getString("name").toString());
                SpannableString ss2=  new SpannableString("User ID : ");
                ss2.setSpan(new StyleSpan(Typeface.BOLD), 0, ss2.length(), 0);
                userid.setText(ss2);
                userid.append(jsonObject.getString("userid").toString());
                SpannableString ss3=  new SpannableString("Contact Number : ");
                ss3.setSpan(new StyleSpan(Typeface.BOLD), 0, ss3.length(), 0);
                contact.setText(ss3);
                contact.append(jsonObject.getString("contact_number").toString());
                SpannableString ss4=  new SpannableString("Email : ");
                ss4.setSpan(new StyleSpan(Typeface.BOLD), 0, ss4.length(), 0);
                email.setText(ss4);
                email.append(jsonObject.getString("email").toString());
                SpannableString ss5=  new SpannableString("Program : ");
                ss5.setSpan(new StyleSpan(Typeface.BOLD), 0, ss5.length(), 0);
                program.setText(ss5);
                program.append(jsonObject.getString("program").toString());
                SpannableString ss6=  new SpannableString("Department : ");
                ss6.setSpan(new StyleSpan(Typeface.BOLD), 0, ss6.length(), 0);
                department.setText(ss6);
                department.append(jsonObject.getString("department").toString());
            } catch (JSONException e) {
                Log.e("teesfs",result);
            }
            super.onPostExecute(result);
            return;
        }
    }


}
