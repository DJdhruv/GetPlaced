package com.example.dhruv.getplaced.Resume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhruv.getplaced.R;

/**
 * Activity to set settings for resume maker
 */
public class ResumeSettings extends AppCompatActivity {
    private SeekBar fontsizebar;
    private TextView fontsize;
    private RadioGroup headingsizegroup,itemsepgroup;
    private RadioButton headingsize,itemsep;
    private Button apply;
    @Override
    /**
     * Sets the layout of activity
     *
     * Contains on click defination of apply button
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_settings);
        fontsizebar=(SeekBar) findViewById(R.id.fontsizebar);
        fontsize=(TextView) findViewById(R.id.fontsize);
        apply=(Button) findViewById(R.id.applysettings);
        headingsizegroup=(RadioGroup)findViewById(R.id.headinggroup);
        itemsepgroup=(RadioGroup)findViewById(R.id.itemsepgroup);

        fontsizebar.setMax(10);

        fontsizebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            /**
             * Changes the font size displayed when seek bar is changed
             */
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                int size=progress+10;
                fontsize.setText(Integer.toString(size)+" pt");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(ResumeSettings.this, "Seek bar progress is :" + progressChangedValue,
                      //  Toast.LENGTH_SHORT).show();
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apply();
            }
        });




    }

    /**
     * function what happens on back pressed
     */
    @Override
    public void onBackPressed() {
        apply();
    }

    /**
     * Apply the settings chosen by student to resume maker
     */
    void apply() {
        int headingsizeID = headingsizegroup.getCheckedRadioButtonId();
        int itemsepID=itemsepgroup.getCheckedRadioButtonId();
        if(headingsizeID!=-1 && itemsepID!=-1){
            headingsize = (RadioButton)headingsizegroup.findViewById(headingsizeID);
            String headingSize = headingsize.getText().toString();

            itemsep=(RadioButton) itemsepgroup.findViewById(itemsepID);
            String Itemsep= itemsep.getText().toString();
            Intent i  = new Intent(ResumeSettings.this,ResumeMaker.class);
            i.putExtra("FontSize",fontsize.getText().toString().substring(0,2));
            i.putExtra("HeadingSize",headingSize);
            i.putExtra("itemsep",Itemsep);
            startActivity(i);
            finish();
        }
        else if(headingsizeID==-1){
            Toast.makeText(ResumeSettings.this, "Choose Heading Size",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ResumeSettings.this, "Choose Line Separation Size",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
