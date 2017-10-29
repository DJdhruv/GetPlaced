package com.example.dhruv.getplaced.Company;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhruv.getplaced.R;

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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Activity for the details of a particular offer
 */
public class OfferDetails extends AppCompatActivity {
    private ListView appliedstudents,shortlistedstudents;
    private TextView offer;
    private TextView shortlistheading;
    JSONObject jsonObject;
    private Button shortlistbutton;
    public Boolean buttonvisible=true;
    public String interestedstudents,shortlisted_students="";
    public String[] interested,shortlist;
    public String offerid;
    Boolean[] isSelected;

    /**
     *
     * Sets the layout of the activity
     *
     * Displays all the details of the offer including interested student, shortlisted student
     *
     * Company can shortlist Student from the list of interested student by looking at profile and resume
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);
        appliedstudents=(ListView) findViewById(R.id.appliedstudents);
        shortlistedstudents=(ListView) findViewById(R.id.shortlistedstudents);
        offer=(TextView) findViewById(R.id.offerdetails);
        shortlistbutton=(Button) findViewById(R.id.shortlistbutton);
        shortlistheading=(TextView) findViewById(R.id.shortlist);
        final List<String> appliedstudentlist=new ArrayList<String>();
        final List<String> shortlistedstudentlist=new ArrayList<String>();

        Bundle extras= getIntent().getExtras();
        final String[] OfferList = extras.getStringArray("OfferList");
        offerid=OfferList[OfferList.length-1];
        final ShortlistAdapter shortlistAdapter=new ShortlistAdapter(this,shortlistedstudentlist);
        shortlistedstudents.setAdapter(shortlistAdapter);
        final ApplicantsAdapter applicantsAdapter=new ApplicantsAdapter(this,appliedstudentlist);
        appliedstudents.setAdapter(applicantsAdapter);
        offer.setText("");
        String[] field=new String[]{"Role","Requirements","Description","Allowed Branches"};
        for(int i=0; i<OfferList.length -3; i++){
            SpannableString ss1=  new SpannableString(field[i] +" : ");
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
            offer.append(ss1);
            offer.append(OfferList[i]+"\n"+"\n");
        }


            interestedstudents=OfferList[OfferList.length-3];
            interested=interestedstudents.split(",");

            isSelected=new Boolean[interested.length];
            if(!interested[0].equals("")) {
                for (int i = 0; i < interested.length; i++) {
                    appliedstudentlist.add(interested[i]);
                    isSelected[i] = false;
                }
            }




        /*try {
            shortlisted=new JSONArray(OfferList[OfferList.length-1]);
            for(int i=0;i<shortlisted.length();i++){
                shortlistedstudentlist.add(shortlisted.getJSONObject(i).getString("name").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        if(appliedstudentlist.isEmpty() || !shortlistedstudentlist.isEmpty()){
            shortlistbutton.setVisibility(View.GONE);
        }





        appliedstudents.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // String Name=studentlist.get(position);
                Intent myIntent = new Intent(OfferDetails.this,StudentProfileForCompany.class);
                String userid="";

                    userid = interested[position];

                // myIntent.putExtra("Name",Name);
                myIntent.putExtra("userid",userid);
                startActivity(myIntent);
            }
        });
        if (!OfferList[OfferList.length - 2].equals("")) {
            shortlistbutton.setVisibility(View.GONE);
            buttonvisible = false;
            shortlist=OfferList[OfferList.length-2].split(",");
            for(int i=0;i<shortlist.length;i++){
                shortlistedstudentlist.add(shortlist[i]);
            }
        }
        appliedstudents.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        shortlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                    for (int i = 0; i < isSelected.length; i++) {
                        if (isSelected[i]) {
                            shortlisted_students = shortlisted_students + interested[i] + ",";
                            new sendGet().execute();
                            shortlistedstudentlist.add(appliedstudentlist.get(i));
                            shortlistAdapter.notifyDataSetChanged();


                        }
                    }
                    Log.e("if ke andar", "haan");

                    shortlistheading.setVisibility(View.VISIBLE);
                    shortlistbutton.setVisibility(View.GONE);
                    buttonvisible = false;
                    applicantsAdapter.notifyDataSetChanged();
                    onBackPressed();
                    Toast.makeText(getApplicationContext(), "Successfully Shortlisted", Toast.LENGTH_SHORT).show();
            }

        });
    }

    /**
     *
     * converts list of students to listview with checkbox for each student
     */

    public class ApplicantsAdapter extends BaseAdapter {
        public Context context;
        public List<String> student;

        public LayoutInflater inflter;

        public ApplicantsAdapter(Context applicationContext, List<String> names) {
            this.context = applicationContext;
            this.student = names;

            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return student.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewgroup) {
            view = inflter.inflate(R.layout.applicant_item, null);
            TextView name = (TextView) view.findViewById(R.id.name);
            final CheckBox box=(CheckBox) view.findViewById(R.id.checkBox);
            if(!buttonvisible)box.setVisibility(View.GONE);
            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(box.isChecked()){
                        isSelected[i]=true;
                    }
                    else{
                        isSelected[i]=false;
                    }
                }
            });



            name.setText(student.get(i));

            return view;
        }
    }

    /**
     *converts list of interested students to listview
     */
    public class ShortlistAdapter extends BaseAdapter {
        public Context context;
        public List<String> student;

        public LayoutInflater inflter;

        public ShortlistAdapter(Context applicationContext, List<String> names) {
            this.context = applicationContext;
            this.student = names;

            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return student.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewgroup) {
            view = inflter.inflate(R.layout.shortlist_item, null);
            TextView name = (TextView) view.findViewById(R.id.shortlistedname);




            name.setText(student.get(i));

            return view;
        }
    }

    /**
     *
     * Sends a get request to server and receives json string of the details of the offer
     */
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
                jsonObject.put("shortlisted_students",shortlisted_students);
                System.out.println(jsonObject.toString());
                new patch().execute(jsonObject.toString());
            } catch (JSONException e) {
                Log.e("teesfs",result);
            }
            super.onPostExecute(result);
            return;
        }
    }

    /**
     *
     * updates the details of the offer with shortlisted students
     */
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
