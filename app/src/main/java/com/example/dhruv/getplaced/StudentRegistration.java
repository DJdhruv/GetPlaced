package com.example.dhruv.getplaced;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StudentRegistration extends AppCompatActivity {
    private Button submit;
    private TextView comment;
    private Spinner department,program;
    private EditText firstname,lastname,email,contact,userid,password,confirmpassword;
    String Firstname,Lastname,Email,Contact,Userid,Password,Confirmpassword,Department,Program;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String FirstName = i.getStringExtra("FirstName");
        String LastName = i.getStringExtra("LastName");
        String LDAP = i.getStringExtra("LDAP");
        setContentView(R.layout.activity_student_registration);
        comment=(TextView) findViewById(R.id.comment);
        submit=(Button) findViewById(R.id.submit);

        firstname=(EditText) findViewById(R.id.firstname);
        firstname.setText(FirstName);

        lastname=(EditText) findViewById(R.id.lastname);
        lastname.setText(LastName);

        email=(EditText) findViewById(R.id.email);
        contact=(EditText) findViewById(R.id.phone);
        userid=(EditText) findViewById(R.id.userid);
        userid.setText(LDAP);
        contact=(EditText) findViewById(R.id.phone);
        password=(EditText)findViewById(R.id.password);
        confirmpassword=(EditText) findViewById(R.id.confirmpassword);
        department=(Spinner)findViewById(R.id.department);
        program=(Spinner) findViewById(R.id.program);
        addItemsOnDepartmentSpinner();
        addItemsOnProgramSpinner();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Firstname=firstname.getText().toString();
                Lastname=lastname.getText().toString();
                Email=email.getText().toString();
                Contact=contact.getText().toString();
                Userid=userid.getText().toString();
                Password=password.getText().toString();
                Department=department.getSelectedItem().toString();
                Program=program.getSelectedItem().toString();
                Confirmpassword=confirmpassword.getText().toString();
                if(!Password.equals(Confirmpassword)){
                    comment.setText("Re-enter Password");
                    password.setText("");
                    confirmpassword.setText("");
                }
                else{
                    new sendGet().execute();
                }
            }
        });
    }
    public void addItemsOnDepartmentSpinner() {


        List<String> depatments = new ArrayList<String>();
        depatments.add("None");
        depatments.add("Computer Science And Engineering");
        depatments.add("Electrical Engineering");
        depatments.add("Aerospace Engineering");
        depatments.add("Chemical Engineering");
        depatments.add("Biosciences and Bioengineering");
        depatments.add("Chemistry");
        depatments.add("Civil Engineering");
        depatments.add("Earth Sciences");
        depatments.add("Energy Science and Engineering");
        depatments.add("Humanities & Social Science");
        depatments.add("Industrial Design Centre");
        depatments.add("Mathematics");
        depatments.add("Mechanical Engineering");
        depatments.add("Metallurgical Engineering & Materials Science");
        depatments.add("Physics");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, depatments);
        department.setAdapter(dataAdapter);
    }
    public void addItemsOnProgramSpinner() {


        List<String> programs = new ArrayList<String>();
        programs.add("none");
        programs.add("UG First Year ");
        programs.add("UG Second Year ");
        programs.add("UG Third Year ");
        programs.add("UG Fourth Year ");
        programs.add("UG Dual Degree ");
        programs.add("PG First Year");
        programs.add("PG Second Year");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, programs);
        program.setAdapter(dataAdapter);
    }

    public class sendGet extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            String url = "http://"+getResources().getString(R.string.ip_address)+"/students/student/";
            HttpURLConnection con = null;
            InputStream in = null;
            try {

                URL obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();


                con.setRequestMethod("PUT");
                con.setRequestProperty("Content-Type", "application/json");
                System.out.println("1");
                String urlParameters = "{\"contact_number\":\""+Contact+"\",\"department\":\"" + Department + "\", \"email\":\""+Email+"\", \"name\":\""+Firstname+" "+Lastname+"\",\"userid\":\""+Userid+"\",\"program\":\""+Program+"\",\"password\":\"" + Password + "\"}";

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
            Toast.makeText(StudentRegistration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(StudentRegistration.this, studentlogin.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            return;
        }
    }
}
