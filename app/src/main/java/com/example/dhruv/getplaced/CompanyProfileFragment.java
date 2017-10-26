package com.example.dhruv.getplaced;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static com.example.dhruv.getplaced.companylogin.USERID;

/**
 * Created by dell on 9/27/17.
 */

public class CompanyProfileFragment extends Fragment {
    TextView name,website,location,contact,userid;
    public ImageView logo;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView=inflater.inflate(R.layout.fragment_company_profile,container,false);
        name = (TextView) rootView.findViewById(R.id.companyname);
        website = (TextView) rootView.findViewById(R.id.companywebsite);
        location = (TextView) rootView.findViewById(R.id.location);
        contact = (TextView) rootView.findViewById(R.id.contact);
        userid = (TextView) rootView.findViewById(R.id.userid);
        logo=(ImageView) rootView.findViewById(R.id.companylogo);
        new sendGet().execute();
        Picasso.with(getContext()).load("http://"+getResources().getString(R.string.ip_address)+"/media/media/company/" +
                USERID+".png").into(logo);
        return rootView;

    }

    public class sendGet extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String...params){
            String url = "http://"+getResources().getString(R.string.ip_address)+"/companies/company/?format=json&q="+USERID;
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
                SpannableString ss2=  new SpannableString("Website : ");
                ss2.setSpan(new StyleSpan(Typeface.BOLD), 0, ss2.length(), 0);
                website.setText(ss2);
                website.append(jsonObject.getString("website").toString());
                SpannableString ss3=  new SpannableString("Location : ");
                ss3.setSpan(new StyleSpan(Typeface.BOLD), 0, ss3.length(), 0);
                location.setText(ss3);
                location.append(jsonObject.getString("location").toString());
                SpannableString ss4=  new SpannableString("Contact : ");
                ss4.setSpan(new StyleSpan(Typeface.BOLD), 0, ss4.length(), 0);
                contact.setText(ss4);
                contact.append(jsonObject.getString("contact").toString());
                SpannableString ss5=  new SpannableString("User ID : ");
                ss5.setSpan(new StyleSpan(Typeface.BOLD), 0, ss5.length(), 0);
                userid.setText(ss5);
                userid.append(jsonObject.getString("userid").toString());


            } catch (JSONException e) {
                Log.e("teesfs","sdfsfds");
            }
            super.onPostExecute(result);
            return;
        }
    }
}
