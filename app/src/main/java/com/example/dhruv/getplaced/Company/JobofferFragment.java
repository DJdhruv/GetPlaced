package com.example.dhruv.getplaced.Company;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dhruv.getplaced.R;

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
import java.util.List;

import static com.example.dhruv.getplaced.Company.CompanyLogin.USERID;

/**
 *
 * Fragment of CompanyHome containing all the offers by the current company
 */
public class JobofferFragment extends Fragment {
    private ImageButton add_job_offer;
    private ListView offers;
    private ProgressDialog dialog;
    private String Temp;
    private JSONObject JsonCompany;
    private JSONArray JsonOffers;
    List<String[]> listofoffers;

    /**
     *
     * Sets the layout of the activity
     *
     * Displays all the Offers which on clicked open a activity with details of that offer
     *
     * contains a add offer button which allows user to add new job offer
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView=inflater.inflate(R.layout.fragment_job_offer,container,false);

        add_job_offer=(ImageButton) rootView.findViewById(R.id.add_button);
        add_job_offer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i=new Intent(getActivity(), AddOffer.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });
        offers = (ListView) rootView.findViewById(R.id.offerlist);
        listofoffers = new ArrayList<String[]>();

        new sendGet().execute();
        Log.e("Updated", "Update ho gya");

        dialog = ProgressDialog.show(getContext(), "",
                "Loading. Please wait...", true);



        return rootView;

    }

    /**
     *
     * Sends a get request to server and receives json string of the details of the offer
     */
    public class sendGet extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String...params){

            String url = "http://"+getResources().getString(R.string.ip_address)+"/offers/offer/?format=json&q="+USERID;
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

        /**
         *
         * adds details of the offer to a list of strings
         */
            @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            try {

                JsonOffers = new JSONArray(result);
            } catch (JSONException e) {
                Log.e("JSON", "CRASHES");
            }

            dialog.dismiss();
            for(int i=0; i<JsonOffers.length(); i++){
                try {
                    listofoffers.add(new String[] {JsonOffers.getJSONObject(i).getString("role"),JsonOffers.getJSONObject(i).getString("requirements"),
                            JsonOffers.getJSONObject(i).getString("job_description"), JsonOffers.getJSONObject(i).getString("allowed_branches"),
                            JsonOffers.getJSONObject(i).getString("interested_students"),JsonOffers.getJSONObject(i).getString("shortlisted_students"),JsonOffers.getJSONObject(i).getString("id")});
                            System.out.println(JsonOffers.getJSONObject(i).getString("shortlisted_students"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            OfferAdaptor offerAdaptor=new OfferAdaptor(getContext(),listofoffers);

            offers.setAdapter(offerAdaptor);

            offers.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String[] OfferList=listofoffers.get(position);
                    Intent myIntent = new Intent(getActivity(),OfferDetails.class);
                    Bundle b = new Bundle();
                    b.putStringArray("OfferList",OfferList);
                    myIntent.putExtras(b);

                    startActivity(myIntent);
                }
            });

            return;
        }
    }

    /**
     *
     * Converts a list of string of offers to a list view
     */
    public class OfferAdaptor extends BaseAdapter {
        public Context context;
        public List<String[]> Offer;

        public LayoutInflater inflter;

        public OfferAdaptor(Context applicationContext, List<String[]> names) {
            this.context = applicationContext;
            this.Offer = names;

            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return Offer.size();
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
            view = inflter.inflate(R.layout.company_offer_item, null);
            TextView name = (TextView) view.findViewById(R.id.role);
            TextView requirement=(TextView) view.findViewById(R.id.requirement);
            requirement.setText(Offer.get(i)[0]);
            name.setText(Offer.get(i)[1]);

            return view;
        }
    }
}
