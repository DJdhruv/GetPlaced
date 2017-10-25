package com.example.dhruv.getplaced;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class comitemclick extends AppCompatActivity {
    private TextView companyname;
    private TextView industry;
    private TextView offer_type;
    private TextView job_description;
    private TextView requirements;
    private TextView role;
    private TextView salary;
    private TextView recuritment_procedure;
    private TextView allowed_branches;
    private Button apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comitemclick);
        companyname=(TextView) findViewById(R.id.companyname);
        industry=(TextView) findViewById(R.id.Industry);
        offer_type=(TextView) findViewById(R.id.offertype);
        job_description=(TextView) findViewById(R.id.Job_description);
        requirements=(TextView) findViewById(R.id.requirements);
        role=(TextView) findViewById(R.id.Role);
        salary=(TextView) findViewById(R.id.Salary);
        recuritment_procedure=(TextView) findViewById(R.id.recruitment_procedure);
        allowed_branches=(TextView) findViewById(R.id.Branches_allowed);
        apply=(Button) findViewById(R.id.apply);
        Bundle bundle=getIntent().getExtras();
        String temp=bundle.getString("companyname");
        companyname.setText(temp);
        //-----------------------------------------------------
       temp=bundle.getString("industry");
        SpannableString ss1=  new SpannableString("Industry ");
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        industry.setText(ss1);
        industry.append(temp);
        //------------------------------------------------
        temp=bundle.getString("offer_type");
        SpannableString offer=  new SpannableString("Offer Type :");
        offer.setSpan(new StyleSpan(Typeface.BOLD), 0, offer.length(), 0);
        offer_type.append(offer);
        offer_type.append(temp);
        //-----------------------------------------------------
        temp=bundle.getString("job_description");
        SpannableString description=  new SpannableString("Job Description :");
        description.setSpan(new StyleSpan(Typeface.BOLD), 0, description.length(), 0);
        job_description.append(description);
        job_description.append(temp);
        //-----------------------------------------------------
        temp=bundle.getString("requirements");
        SpannableString req=  new SpannableString("Requirements :");
        req.setSpan(new StyleSpan(Typeface.BOLD), 0, req.length(), 0);
        requirements.append(req);
        requirements.append(temp);
        //-----------------------------------------------------
        temp=bundle.getString("role");
        SpannableString rol=  new SpannableString("Role :");
        rol.setSpan(new StyleSpan(Typeface.BOLD), 0, rol.length(), 0);
        role.append(rol);
        role.append(temp);
        //-----------------------------------------------------
        temp=bundle.getString("salary");
        SpannableString sal=  new SpannableString("Salary :");
        sal.setSpan(new StyleSpan(Typeface.BOLD), 0, sal.length(), 0);
        salary.append(sal);
        salary.append(temp);
        //-----------------------------------------------------
        temp=bundle.getString("recuritment_procedure");
        SpannableString rec=  new SpannableString("Recuritment Procedure :");
        rec.setSpan(new StyleSpan(Typeface.BOLD), 0, rec.length(), 0);
        recuritment_procedure.append(rec);
        recuritment_procedure.append(temp);
        //-----------------------------------------------------
        temp=bundle.getString("allowed_branches");
        SpannableString allowed=  new SpannableString("Allowed Branches :");
        allowed.setSpan(new StyleSpan(Typeface.BOLD), 0, allowed.length(), 0);
        allowed_branches.append(allowed);
        allowed_branches.append(temp);
        //-----------------------------------------------------
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(comitemclick.this,StudentHome.class);
                startActivity(i);
            }
        });

    }

}
