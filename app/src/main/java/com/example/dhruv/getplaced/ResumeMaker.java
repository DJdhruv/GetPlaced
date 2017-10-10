package com.example.dhruv.getplaced;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ResumeMaker extends AppCompatActivity {
    private ListView headings;
    private Button saveresume;
    final List<String> detailslist=new ArrayList<String>();

    String header="\\documentclass[10pt]{article}\n" +
            "\\usepackage[a4paper,bottom = 0.6in,left = 0.75in,right = 0.75in,top = 0.5in]{geometry}\n" +
            "\\usepackage{graphicx}\n" +
            "\\usepackage{amsmath}\n" +
            "\\usepackage{array}\n" +
            "\\usepackage{enumitem}\n" +
            "\\usepackage{wrapfig}\n" +
            "\\usepackage{titlesec}\n" +
            "\\usepackage[colorlinks=false]{hyperref}\n" +
            "\\usepackage{verbatim}\n" +
            "\\newcommand{\\xfilll}[2][1ex]{\n" +
            "\\dimen0=#2\\advance\\dimen0 by #1\n" +
            "\\leaders\\hrule height \\dimen0 depth -#1\\hfill}\n" +
            "\\titleformat{\\section}{\\LARGE\\scshape\\raggedright}{}{0em}{}\n" +
            "\\renewcommand\\labelitemi{\\raisebox{0.4ex}{\\tiny$\\bullet$}}\n" +
            "\\renewcommand{\\labelitemii}{$\\cdot$}\n" +
            "\\pagenumbering{gobble}\\begin{document}\n" +
            "\t\\vspace*{4.5cm}";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_maker);

        headings=(ListView) findViewById(R.id.headings);
        final List<String> headinglist=new ArrayList<String>();
        final List<String> pointslist=new ArrayList<String>();
        headinglist.add("Add Heading");
        pointslist.add("");
        headinglist.add(" Scholastic Achievements");
        detailslist.add("\\section*{ Scholastic Achievements\\xfilll[0pt]{0.5pt}}\n" +
                "\\vspace{-7pt}\n" +
                "\\begin{itemize}\n");

        pointslist.add("");
        headinglist.add(" Projects");
        detailslist.add("\\section*{ Projects\\xfilll[0pt]{0.5pt}}\n"+
                "\\vspace{-5pt}\n\\begin{itemize}\n");
        pointslist.add("");
        final ResumeAdapter resumeAdapter=new ResumeAdapter(this,headinglist,pointslist);
        headings.setAdapter(resumeAdapter);
        saveresume =(Button) findViewById(R.id.saveresume);
        saveresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latexcode=header;
                for(int i=0;i<detailslist.size();i++){
                    latexcode=latexcode+detailslist.get(i)+"\\end{itemize}\n" +"\\vspace{-15pt}";

                }
                latexcode=latexcode+"\\end{document}";
                String convertedcode=Convert(latexcode);
                Uri uri=Uri.parse("https://latexonline.cc/compile?text="+convertedcode);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
                }
            }
        });



    }
    public String Convert(String code){
        code=code.replace("%","%25");
        code=code.replace("{","%7B");
        code=code.replace("}","%7D");
        code=code.replace("|","%7C");
        code=code.replace("#","%23");
        code=code.replace(" ","%20");
        code=code.replace("&","%26");
        code=code.replace("*","%2A");
        code=code.replace("-","%2D");
        code=code.replace(".","%2E");
        code=code.replace("=","%3D");
        code=code.replace(";","%3B");
        code=code.replace("\\","%5C");
        code=code.replace("^","%5E");
        code=code.replace("_","%5F");
        code=code.replace("[","%5B");
        code=code.replace("]","%5D");
        code=code.replace("<","%3C");
        code=code.replace(">","%3E");
        code=code.replace(":","%3A");
        code=code.replace("\"","%22");
        code=code.replace("+","%2B");
        code=code.replace("\t","%09");
        code=code.replace(",","%2C");
        code=code.replace("\n","%0A");
        return code;
    }
    public class ResumeAdapter extends BaseAdapter {
        public Context context;
        public List<String> headings;
        public List<String> points;
        public LayoutInflater inflter;

        public ResumeAdapter(Context applicationContext, List<String> names,List<String> p) {
            this.context = applicationContext;
            this.headings = names;
            this.points=p;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return headings.size();
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
        public View getView(final int i, View view, ViewGroup viewgroup) {
            view = inflter.inflate(R.layout.resume_item, null);
            TextView heading = (TextView) view.findViewById(R.id.heading);
            final TextView Points = (TextView) view.findViewById(R.id.points);
            final ImageButton Add=(ImageButton) view.findViewById(R.id.add1);
            final Button save=(Button) view.findViewById(R.id.save);
            final Button discard=(Button) view.findViewById(R.id.discard);
            final EditText details=(EditText) view.findViewById(R.id.details);
            Points.setText(points.get(i));
            heading.setText(headings.get(i));
            if(i==0){
                heading.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Add.setVisibility(View.GONE);
                        if (details.getVisibility() == View.GONE) {
                            details.setVisibility(View.VISIBLE);
                        } else {
                            details.setVisibility(View.GONE);
                        }
                        if (discard.getVisibility() == View.GONE) {
                            discard.setVisibility(View.VISIBLE);
                        } else {
                            discard.setVisibility(View.GONE);
                        }
                        if (save.getVisibility() == View.GONE) {
                            save.setVisibility(View.VISIBLE);
                        } else {
                            save.setVisibility(View.GONE);
                        }

                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        headings.add(details.getText().toString());
                        detailslist.add("\\section*{ details.getText().toString()\\xfilll[0pt]{0.5pt}}\n" +
                                "\\vspace{-5pt}\n\\begin{itemize}\n");
                        points.add("");
                        details.setText("");
                        details.setVisibility(View.GONE);
                        discard.setVisibility(View.GONE);
                        save.setVisibility(View.GONE);

                    }
                });
                discard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        details.setText("");
                        details.setVisibility(View.GONE);
                        discard.setVisibility(View.GONE);
                        save.setVisibility(View.GONE);

                    }
                });
            }
            else {
                Add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        details.setVisibility(View.VISIBLE);
                        discard.setVisibility(View.VISIBLE);
                        save.setVisibility(View.VISIBLE);

                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       detailslist.set(i-1,detailslist.get(i-1)+"\\item "+details.getText().toString()+"\n");
                        points.set(i, points.get(i) + details.getText());
                        Points.setText(points.get(i));
                        details.setText("");
                        details.setVisibility(View.GONE);
                        discard.setVisibility(View.GONE);
                        save.setVisibility(View.GONE);

                    }
                });
                discard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        details.setText("");
                        details.setVisibility(View.GONE);
                        discard.setVisibility(View.GONE);
                        save.setVisibility(View.GONE);

                    }
                });
                heading.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Points.getVisibility() == View.GONE) {
                            Points.setVisibility(View.VISIBLE);
                        } else {
                            Points.setVisibility(View.GONE);
                        }
                        if (Add.getVisibility() == View.GONE) {
                            Add.setVisibility(View.VISIBLE);
                        } else {
                            Add.setVisibility(View.GONE);
                        }

                        save.setVisibility(View.GONE);


                        discard.setVisibility(View.GONE);


                        details.setVisibility(View.GONE);

                    }
                });
            }
            return view;
        }
    }
}
