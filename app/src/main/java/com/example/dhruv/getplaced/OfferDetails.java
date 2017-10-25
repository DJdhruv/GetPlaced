package com.example.dhruv.getplaced;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class OfferDetails extends AppCompatActivity {
    private ListView appliedstudents,shortlistedstudents;
    private TextView offer;
    private TextView shortlistheading;
    public JSONArray applicants,shortlisted;
    private Button shortlistbutton;
    public Boolean buttonvisible=true;

    Boolean[] isSelected;

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
        String[] OfferList = extras.getStringArray("OfferList");
        final ShortlistAdapter shortlistAdapter=new ShortlistAdapter(this,shortlistedstudentlist);
        shortlistedstudents.setAdapter(shortlistAdapter);
        final ApplicantsAdapter applicantsAdapter=new ApplicantsAdapter(this,appliedstudentlist);
        appliedstudents.setAdapter(applicantsAdapter);
        offer.setText("");
        String[] field=new String[]{"Role","Requirements","Description","Allowed Branches"};
        for(int i=0; i<OfferList.length -2; i++){
            offer.setText(offer.getText()+field[i]+" : "+OfferList[i]+"\n");

        }

        try {
            applicants=new JSONArray(OfferList[OfferList.length-2]);
            isSelected=new Boolean[applicants.length()];
            for(int i=0;i<applicants.length();i++){
                appliedstudentlist.add(applicants.getJSONObject(i).getString("name").toString());
                isSelected[i]=false;

            }
        } catch (JSONException e) {
            e.printStackTrace();
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
                try {
                    userid = applicants.getJSONObject(position).getString("userid").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // myIntent.putExtra("Name",Name);
                myIntent.putExtra("userid",userid);
                startActivity(myIntent);
            }
        });
        appliedstudents.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        shortlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < applicants.length(); i++) {
                    if(isSelected[i]) {
                        shortlistedstudentlist.add(appliedstudentlist.get(i));
                        shortlistAdapter.notifyDataSetChanged();
                    }
                }

                shortlistheading.setVisibility(View.VISIBLE);
                shortlistbutton.setVisibility(View.GONE);
                buttonvisible=false;
                applicantsAdapter.notifyDataSetChanged();
            }
        });
    }

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
}
