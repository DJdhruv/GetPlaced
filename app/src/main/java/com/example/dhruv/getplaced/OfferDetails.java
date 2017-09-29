package com.example.dhruv.getplaced;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class OfferDetails extends AppCompatActivity {
    private ListView students;
    private TextView offer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);
        students=(ListView) findViewById(R.id.appliedstudents);
        offer=(TextView) findViewById(R.id.offerdetails);
        final List<String> studentlist=new ArrayList<String>();
        studentlist.add("Akshay Patidar");
        studentlist.add("Aditya Jadhav");
        studentlist.add("Dhruv Jaglan");
        Bundle extras= getIntent().getExtras();
        ShortListAdapter shortListAdapter=new ShortListAdapter(this,studentlist);
        students.setAdapter(shortListAdapter);
        offer.setText(extras.getString("OfferName"));


        students.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Name=studentlist.get(position);
                Intent myIntent = new Intent(OfferDetails.this,StudentProfileForCompany.class);

                myIntent.putExtra("Name",Name);
                startActivity(myIntent);
            }
        });
    }

    public class ShortListAdapter extends BaseAdapter {
        public Context context;
        public List<String> student;

        public LayoutInflater inflter;

        public ShortListAdapter(Context applicationContext, List<String> names) {
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

            name.setText(student.get(i));

            return view;
        }
    }
}
