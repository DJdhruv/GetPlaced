package com.example.dhruv.getplaced;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class comitemclick extends AppCompatActivity {
    private TextView companyname;
    private TextView companydetails;
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comitemclick);
        companyname=(TextView) findViewById(R.id.companyname);
        companydetails=(TextView) findViewById(R.id.companydetails);
        cancel=(Button) findViewById(R.id.cancel);
        Bundle bundle=getIntent().getExtras();
        String from_prev_activity=bundle.getString("companyname");
        companyname.setText(from_prev_activity);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(comitemclick.this,company_list.class);
                startActivity(i);
            }
        });

    }

}
