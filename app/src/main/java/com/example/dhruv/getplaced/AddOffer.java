package com.example.dhruv.getplaced;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class AddOffer extends AppCompatActivity {
    private Button canc,create;
    private EditText industry,description,requirements,role,salary,procedure,branches;
    private RadioButton intern,job;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        canc= (Button) findViewById(R.id.cancel);
        create=(Button) findViewById(R.id.create);
        industry=(EditText) findViewById(R.id.Industry);
        description=(EditText) findViewById(R.id.Job_description);
        requirements=(EditText) findViewById(R.id.Requirements);
        role=(EditText) findViewById(R.id.Role);
        salary=(EditText) findViewById(R.id.Salary);
        procedure=(EditText) findViewById(R.id.Procedure);
        branches=(EditText) findViewById(R.id.Branches);
        intern =(RadioButton) findViewById(R.id.Internship);
        job=(RadioButton) findViewById(R.id.Job);
        intern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(job.isChecked()){
                    job.setChecked(false);
                }

            }
        });
        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intern.isChecked()){
                    intern.setChecked(false);
                }

            }
        });

        canc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(AddOffer.this,companyhome.class));
            }
        });
        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String Industry,Description,Requirements,Role,Salary,Procedure,Branches;
                Industry=industry.getText().toString();
                Description=description.getText().toString();
                Requirements=requirements.getText().toString();
                Role=role.getText().toString();
                Salary=salary.getText().toString();
                Procedure=procedure.getText().toString();
                Branches=branches.getText().toString();




            }
        });
    }
}
