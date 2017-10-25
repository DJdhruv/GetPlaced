package com.example.dhruv.getplaced;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dhruv.getplaced.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class OfferDetails extends AppCompatActivity {
    private ListView appliedstudents,shortlistedstudents;
    private TextView offer;
    public JSONArray applicants,shortlisted;
    private Button shortlistbutton;
    Boolean[] isselected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);
        appliedstudents=(ListView) findViewById(R.id.appliedstudents);
        shortlistedstudents=(ListView) findViewById(R.id.shortlistedstudents);
        offer=(TextView) findViewById(R.id.offerdetails);
        shortlistbutton=(Button) findViewById(R.id.shortlistbutton);
        final List<String> appliedstudentlist=new ArrayList<String>();
        final List<String> shortlistedstudentlist=new ArrayList<String>();


        Bundle extras= getIntent().getExtras();
        String[] OfferList = extras.getStringArray("OfferList");
        ArrayAdapter<String> shortlist =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shortlistedstudentlist);
        shortlistedstudents.setAdapter(shortlist);
        ApplicantsAdapter applicantsAdapter=new ApplicantsAdapter(this,appliedstudentlist);
        appliedstudents.setAdapter(applicantsAdapter);
        offer.setText("");
        String[] field=new String[]{"Role","Requirements","Description","Allowed Branches"};
        for(int i=0; i<OfferList.length -2; i++){
            offer.setText(offer.getText()+field[i]+" : "+OfferList[i]+"\n");

        }

        try {
            applicants=new JSONArray(OfferList[OfferList.length-2]);
            for(int i=0;i<applicants.length();i++){
                appliedstudentlist.add(applicants.getJSONObject(i).getString("name").toString());
                isselected[i]=false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        isselected=new Boolean[applicants.length()];
        try {
            shortlisted=new JSONArray(OfferList[OfferList.length-1]);
            for(int i=0;i<shortlisted.length();i++){
                shortlistedstudentlist.add(shortlisted.getJSONObject(i).getString("name").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(appliedstudentlist.isEmpty()){
            shortlistbutton.setVisibility(View.GONE);
        }
        shortlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
        public View getView(int i, View view, ViewGroup viewgroup) {
            view = inflter.inflate(R.layout.shortlist_students, null);
            TextView name = (TextView) view.findViewById(R.id.name);
            CheckBox box=(CheckBox) view.findViewById(R.id.checkBox);
            if(box.isChecked()){
                isselected[i]=true;
            }
            else{
                isselected[i]=false;
            }
            name.setText(student.get(i));

            return view;
        }
    }
}
