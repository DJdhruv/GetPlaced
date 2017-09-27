package com.example.dhruv.getplaced;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StudentRegistration extends AppCompatActivity {
    private Button submit;
    private TextView comment;
    private EditText firstname,lastname,email,contact,userid,password,confirmpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        comment=(TextView) findViewById(R.id.comment);
        submit=(Button) findViewById(R.id.submit);
        firstname=(EditText) findViewById(R.id.firstname);
        lastname=(EditText) findViewById(R.id.lastname);
        email=(EditText) findViewById(R.id.email);
        contact=(EditText) findViewById(R.id.phone);
        userid=(EditText) findViewById(R.id.userid);
        password=(EditText) findViewById(R.id.password);
        confirmpassword=(EditText) findViewById(R.id.confirmpassword);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Firstname,Lastname,Email,Contact,Userid,Password,Confirmpassword;
                Firstname=firstname.getText().toString();
                Lastname=lastname.getText().toString();
                Email=email.getText().toString();
                Contact=contact.getText().toString();
                Userid=userid.getText().toString();
                Password=password.getText().toString();
                Confirmpassword=confirmpassword.getText().toString();
                if(Password!=Confirmpassword){
                    comment.setText("Re-enter Password");
                    password.setText("");
                    confirmpassword.setText("");
                }
            }
        });
    }
}
