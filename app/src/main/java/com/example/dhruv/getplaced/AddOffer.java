package com.example.dhruv.getplaced;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.dhruv.getplaced.companylogin.USERID;

public class AddOffer extends AppCompatActivity {
    private Button canc,add;
    private EditText industry,description,requirements,role,salary,procedure,branches;
    private RadioButton intern,job;
    private TextView comment;
    String Industry,Description,Requirements,Role,Salary,Procedure,Branches,Offertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        comment=(TextView) findViewById(R.id.comment);
        canc= (Button) findViewById(R.id.cancel);
        add=(Button) findViewById(R.id.add);
        industry=(EditText) findViewById(R.id.Industry);
        description=(EditText) findViewById(R.id.Job_description);
        requirements=(EditText) findViewById(R.id.Requirements);
        role=(EditText) findViewById(R.id.Role);
        salary=(EditText) findViewById(R.id.Salary);
        procedure=(EditText) findViewById(R.id.Procedure);
        branches=(EditText) findViewById(R.id.Branches);
        intern =(RadioButton) findViewById(R.id.Internship);
        job=(RadioButton) findViewById(R.id.Job);
        intern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(job.isChecked()){
                    job.setChecked(false);

                }
                comment.setText("");


            }
        });
        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intern.isChecked()){
                    intern.setChecked(false);
                }
                comment.setText("");
            }
        });

        canc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(AddOffer.this,companyhome.class));
            }
        });
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Industry=industry.getText().toString();
                Description=description.getText().toString();
                Requirements=requirements.getText().toString();
                Role=role.getText().toString();
                Salary=salary.getText().toString();
                Procedure=procedure.getText().toString();
                Branches=branches.getText().toString();
                if(intern.isChecked()){
                    Offertype="Internship";
                }

                else{
                    Offertype="Job";
                }
                if(!intern.isChecked() && ! job.isChecked()){
                    comment.setText("Please select offer type ");
                }
                else{
                    new sendGet().execute();
                }





            }
        });

    }
    public class sendGet extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            String url = "http://"+getResources().getString(R.string.ip_address)+"/offers/offer/";
            HttpURLConnection con = null;
            InputStream in = null;
            try {

                URL obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();


                con.setRequestMethod("PUT");
                con.setRequestProperty("Content-Type", "application/json");
                System.out.println("1");
                String urlParameters = "{\"offer_type\":\""+Offertype+"\",\"industry\":\"" +
                        Industry + "\", \"job_description\":\""+Description+"\", \"requirements\":\""+Requirements+
                        "\",\"role\":\""+Role+"\",\"salary\":\""+Salary+"\",\"recruitment_procedure\":\"" + Procedure
                        + "\",\"allowed_branches\":\""+Branches+"\",\"company_id\":\""+USERID
                        +"\"}";

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
            Toast.makeText(AddOffer.this, "Offer Added Sucessfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(AddOffer.this, companyhome.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            return;
        }
    }
}
