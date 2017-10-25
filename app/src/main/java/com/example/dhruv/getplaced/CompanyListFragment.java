package com.example.dhruv.getplaced;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dhruv on 26/9/17.
 */

public class CompanyListFragment extends Fragment {
    private ListView companies;
    private PopupWindow itemclick;
    public ArrayList<String[]> companyNames;
    int logo[]={R.mipmap.logo,R.mipmap.logo};
    private Button closeitem;
    public JSONArray offer;
    public TextView check;
    public JSONObject jobj;
    public String temp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      final View rootView = inflater.inflate(R.layout.fragment_companylist, container, false);
        companies = (ListView) rootView.findViewById(R.id.companylist);
        companyNames=new ArrayList<String[]>();
        new sendGet().execute();
        //companyNames.add(new String[]{"dfdb","dndjf"});
       // check=(TextView) rootView.findViewById(R.id.check);


        return rootView;
    }
    //---------------------------------------------

    //-------------------------------------


    class CompanyListAdapter extends BaseAdapter {
        public Context context;
        public ArrayList<String[]> companylist;
        public int logo[];
        public LayoutInflater inflter;

        public CompanyListAdapter(Context applicationContext, ArrayList<String[]> companylist, int[] logo) {
            this.context = applicationContext;
            this.companylist = companylist;
            this.logo = logo;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return companylist.size();
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
            view = inflter.inflate(R.layout.company_listitem, null);
            TextView country = (TextView)   view.findViewById(R.id.company_name);
            TextView industry=(TextView) view.findViewById(R.id.industry);
            ImageView icon = (ImageView) view.findViewById(R.id.company_icon);
            industry.setText(companylist.get(i)[0]);
            country.setText(companylist.get(i)[1]);
            icon.setImageResource(logo[0]);
            return view;
        }
    }

    public class sendGet extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String...params){

            String url = "http://192.168.0.105:8000/offers/offer/?format=json";
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

            super.onPostExecute(result);
            try {
                 offer= new JSONArray(result);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i=0; i<offer.length(); i++) {
                try {
                    jobj=offer.getJSONObject(i);
                    //temp =jobj.getString("company_id");
                    companyNames.add(new String[]{jobj.getString("company_id"),jobj.getString("industry"),jobj.getString("offer_type"),jobj.getString("job_description")
                    ,jobj.getString("requirements"),jobj.getString("role"),jobj.getString("salary"),jobj.getString("recruitment_procedure"),
                    jobj.getString("allowed_branches")});
                    CompanyListAdapter listAdapter = new CompanyListAdapter(getActivity(), companyNames, logo);
                    companies.setAdapter(listAdapter);
                    companies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        //LayoutInflater inflater =(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        //View layout= inflater.inflate(R.layout.fragment_comitemclick, (ViewGroup) view.findViewById(R.id.itemlayout));
                        //itemclick = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                        //itemclick.showAtLocation(layout, Gravity.CENTER, 0, 0);
                        //temclick.setFocusable(true);
                        //companyname=(TextView) layout.findViewById(R.id.companyname);
                        //companydetail=(TextView) layout.findViewById(R.id.companydetails);
                        //companyname.setText(companyNames[position]);
                        //closeitem=(Button) layout.findViewById(R.id.cancel);
                        //closeitem.setOnClickListener(new View.OnClickListener() {
                        //  @Override
                        //public void onClick(View v) {
                        //  itemclick.dismiss();
                        // }
                        //});
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i=new Intent(getActivity(),comitemclick.class);
                            i.putExtra("companyname",companyNames.get(position)[0]);
                            i.putExtra("industry",companyNames.get(position)[1]);
                            i.putExtra("offer_type",companyNames.get(position)[2]);
                            i.putExtra("job_description",companyNames.get(position)[3]);
                            i.putExtra("requirements",companyNames.get(position)[4]);
                            i.putExtra("role",companyNames.get(position)[5]);
                            i.putExtra("salary",companyNames.get(position)[6]);
                            i.putExtra("recuritment_procedure",companyNames.get(position)[7]);
                            i.putExtra("allowed_branches",companyNames.get(position)[8]);
                            startActivity(i);

                        }
                    });
                } catch (JSONException e) {
                    //check.setText("err");
                    e.printStackTrace();
                }

            }
                return;
        }
    }

}

