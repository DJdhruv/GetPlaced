package com.example.dhruv.getplaced;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentRegistration extends AppCompatActivity {
    private Button submit;
    private TextView comment;
    private Spinner department,program;
    private EditText firstname,lastname,email,contact,userid,password,confirmpassword;
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
                String Firstname,Lastname,Email,Contact,Userid,Password,Confirmpassword,Department,Program;
                Firstname=firstname.getText().toString();
                Lastname=lastname.getText().toString();
                Email=email.getText().toString();
                Contact=contact.getText().toString();
                Userid=userid.getText().toString();
                Password=password.getText().toString();
                Department=department.getSelectedItem().toString();
                Program=program.getSelectedItem().toString();
                Confirmpassword=confirmpassword.getText().toString();
                if(Password!=Confirmpassword){
                    comment.setText("Re-enter Password");
                    password.setText("");
                    confirmpassword.setText("");
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
}
