package com.example.dhruv.getplaced;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.dhruv.getplaced.studentlogin.USERID;

public class comitemclick extends AppCompatActivity {
    private TextView companyname;
    private TextView industry;
    private TextView offer_type;
    private TextView job_description;
    private TextView requirements;
    private TextView role;
    private TextView salary;
    private TextView recuritment_procedure;
    private TextView allowed_branches;
    private Button apply;
    public ImageView logo;
    String offerid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comitemclick);
        companyname=(TextView) findViewById(R.id.companyname);
        industry=(TextView) findViewById(R.id.Industry);
        offer_type=(TextView) findViewById(R.id.offertype);
        job_description=(TextView) findViewById(R.id.Job_description);
        requirements=(TextView) findViewById(R.id.requirements);
        role=(TextView) findViewById(R.id.Role);
        salary=(TextView) findViewById(R.id.Salary);
        recuritment_procedure=(TextView) findViewById(R.id.recruitment_procedure);
        allowed_branches=(TextView) findViewById(R.id.Branches_allowed);
        apply=(Button) findViewById(R.id.apply);
        logo=(ImageView) findViewById(R.id.company_logo);
        Bundle bundle=getIntent().getExtras();
        String temp=bundle.getString("companyname");
         offerid=bundle.getString("offerid");
        companyname.setText(temp);
        //-----------------------------------------------------
        Picasso.with(comitemclick.this).load("http://"+getResources().getString(R.string.ip_address)+"/media/media/company/" +
                temp+".png").fit().into(logo);
        //-----------------------------------------------------
        temp=bundle.getString("industry");
        SpannableString ss1=  new SpannableString("Industry ");
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        industry.setText(ss1);
        industry.append(temp);
        //------------------------------------------------
        temp=bundle.getString("offer_type");
        SpannableString offer=  new SpannableString("Offer Type :");
        offer.setSpan(new StyleSpan(Typeface.BOLD), 0, offer.length(), 0);
        offer_type.append(offer);
        offer_type.append(temp);
        //-----------------------------------------------------
        temp=bundle.getString("job_description");
        SpannableString description=  new SpannableString("Job Description :");
        description.setSpan(new StyleSpan(Typeface.BOLD), 0, description.length(), 0);
        job_description.append(description);
        job_description.append(temp);
        //-----------------------------------------------------
        temp=bundle.getString("requirements");
        SpannableString req=  new SpannableString("Requirements :");
        req.setSpan(new StyleSpan(Typeface.BOLD), 0, req.length(), 0);
        requirements.append(req);
        requirements.append(temp);
        //-----------------------------------------------------
        temp=bundle.getString("role");
        SpannableString rol=  new SpannableString("Role :");
        rol.setSpan(new StyleSpan(Typeface.BOLD), 0, rol.length(), 0);
        role.append(rol);
        role.append(temp);
        //-----------------------------------------------------
        temp=bundle.getString("salary");
        SpannableString sal=  new SpannableString("Salary :");
        sal.setSpan(new StyleSpan(Typeface.BOLD), 0, sal.length(), 0);
        salary.append(sal);
        salary.append(temp);
        //-----------------------------------------------------
        temp=bundle.getString("recuritment_procedure");
        SpannableString rec=  new SpannableString("Recuritment Procedure :");
        rec.setSpan(new StyleSpan(Typeface.BOLD), 0, rec.length(), 0);
        recuritment_procedure.append(rec);
        recuritment_procedure.append(temp);
        //-----------------------------------------------------
        temp=bundle.getString("allowed_branches");
        SpannableString allowed=  new SpannableString("Allowed Branches :");
        allowed.setSpan(new StyleSpan(Typeface.BOLD), 0, allowed.length(), 0);
        allowed_branches.append(allowed);
        allowed_branches.append(temp);
        //-----------------------------------------------------
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(comitemclick.this,StudentHome.class);
                startActivity(i);
                new sendGet().execute();
            }
        });


    }
    public class sendGet extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String...params){

            String url = "http://"+getResources().getString(R.string.ip_address)+"/offers/offer/"+offerid;
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

                JSONObject jsonObject = new JSONObject(result);
                String temp = jsonObject.getString("interested_students");
                String[] temp2 = temp.split(",");
                Boolean temp3 = false;
                for(int i=0; i<temp2.length; i++){
                    if(temp2[i].equals(USERID)){
                        temp3=true;
                    }
                }
                if(temp3){
                    Toast.makeText(comitemclick.this, "Already applied", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(comitemclick.this, "Succesfully Applied", Toast.LENGTH_SHORT).show();
                    jsonObject.put("interested_students",jsonObject.getString("interested_students").toString()+USERID+",");
                    System.out.println(jsonObject.toString());
                    new patch().execute(jsonObject.toString());
                }

            } catch (JSONException e) {
                Log.e("teesfs",result);
            }
            super.onPostExecute(result);
            return;
        }
    }

    public class patch extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            String url = "http://"+getResources().getString(R.string.ip_address)+"/offers/offer/"+offerid+"/";
            HttpURLConnection con = null;
            InputStream in = null;
            try {

                URL obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();


                con.setRequestMethod("PATCH");
                con.setRequestProperty("Content-Type", "application/json");
                System.out.println("1");
                String urlParameters = params[0];

                con.setDoOutput(true);
                con.setDoInput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                System.out.println(urlParameters);
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
                System.out.println("3");
                int responseCode = con.getResponseCode();

                System.out.println("4");
                in =new BufferedInputStream(con.getInputStream());
                int inputLine;
                StringBuffer response = new StringBuffer();
                System.out.println("5");
                in.read();
                in.close();
                System.out.println("6");
                return response.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
                try {
                    if (in != null) {
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

            return;
        }
    }
}
