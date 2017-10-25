package com.example.dhruv.getplaced;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

import static com.example.dhruv.getplaced.studentlogin.USERID;


public class StudentProfileFragment extends Fragment {
    private Button makeresume;
    public TextView name;
    public TextView userid;
    public TextView email;
    public TextView contact,program,department;
    public ImageView profilepic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_studentprofile, container, false);
        makeresume=(Button) rootView.findViewById(R.id.createresume);
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
                name.setText(jsonObject.getString("name").toString());
                userid.setText(jsonObject.getString("userid").toString());
                contact.setText(jsonObject.getString("contact_number").toString());
                email.setText(jsonObject.getString("email").toString());
                program.setText(jsonObject.getString("program").toString());
                department.setText(jsonObject.getString("department").toString());
            } catch (JSONException e) {
                Log.e("teesfs",result);
            }
            super.onPostExecute(result);
            return;
        }
    }
}
