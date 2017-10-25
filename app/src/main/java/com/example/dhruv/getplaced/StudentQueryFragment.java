package com.example.dhruv.getplaced;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import static com.example.dhruv.getplaced.studentlogin.USERID;


public class StudentQueryFragment extends Fragment {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    private ImageButton addquery;
    public  ArrayList<queritem> list ;
    ListView query;
    public String Query,Datetime;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_studentquery, container, false);
        query = (ListView) rootView.findViewById(R.id.querylist);
        addquery=(ImageButton) rootView.findViewById(R.id.add_query_button);
        list = new ArrayList<queritem>();


        new sendGet().execute();
        addquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.query, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());


                alertDialogBuilder.setView(promptsView);

                final EditText userquery = (EditText) promptsView.findViewById(R.id.query);



                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                        Query=userquery.getText().toString();
                                        Datetime=timestamp.toString();
                                        new sendGet1().execute();

                                        list.add(new queritem(USERID,Query,Datetime));
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();

            }

        });
        return rootView;

    }
    public class sendGet extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String...params){

            String url = "http://192.168.0.105:8000/queries/query/?format=json";
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

                for(int i=0;i<jsonArray.length();i++){
                    Date currentTime = Calendar.getInstance().getTime();
                    list.add(new queritem(jsonArray.getJSONObject(i).getString("userid"),
                            jsonArray.getJSONObject(i).getString("description"),
                            jsonArray.getJSONObject(i).getString("datetime")));

                }
                querylistAdapter listAdapter = new querylistAdapter(getActivity(), list);
                query.setAdapter(listAdapter);

            } catch (JSONException e) {
                Log.e("teesfs","sdfsfds");
            }
            super.onPostExecute(result);
            return;
        }
    }

    class queritem {

        public String bywhom;
        public String details;
        public String date;

        queritem( String Bywhom, String Details,String Date) {


            bywhom = Bywhom;
            details = Details;
            date = Date;
        }
    }


    class querylistAdapter extends BaseAdapter {
        ArrayList<queritem> list;
        Context context;

        public querylistAdapter(Context mcontext, ArrayList<queritem> mlist) {
            this.context = mcontext;
            this.list = mlist;
        }

        @Override
        public int getCount() {
            return list.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.query_item, parent, false);

            TextView bywhom = (TextView) row.findViewById(R.id.bywhom);
            TextView details = (TextView) row.findViewById(R.id.content);
            TextView time = (TextView) row.findViewById(R.id.time);

            bywhom.setText(list.get(position).bywhom);
            details.setText(list.get(position).details);
            time.setText(list.get(position).date);
            return row;
        }
    }
    public class sendGet1 extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            String url = "http://"+getResources().getString(R.string.ip_address)+"/queries/query/";
            HttpURLConnection con = null;
            InputStream in = null;
            try {

                URL obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();


                con.setRequestMethod("PUT");
                con.setRequestProperty("Content-Type", "application/json");
                System.out.println("1");
                String urlParameters = "{\"userid\":\""+USERID+"\",\"description\":\"" + Query +
                        "\", \"datetime\":\""+Datetime+ "\"}";

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
            Toast.makeText(getActivity(), "Query Added Succesfully", Toast.LENGTH_SHORT).show();

            return;
        }
    }
}