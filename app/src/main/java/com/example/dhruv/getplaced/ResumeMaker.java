package com.example.dhruv.getplaced;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.dhruv.getplaced.R.id.saveedit;

public class ResumeMaker extends AppCompatActivity {
    private ListView headings;
    private Button saveresume;
    private ImageButton settings;
    final List<String> codelist=new ArrayList<String>();

    String fontsize,itemsep,headingsize;


    public void ApplySettings(){
        Bundle extras=getIntent().getExtras();
        fontsize=extras.getString("FontSize");
        String Itemsep=extras.getString("itemsep");
        if(Itemsep.equals("Extra Small"))itemsep="-0.75mm";
        else if(Itemsep.equals("Small"))itemsep="-0.5mm";
        else if(Itemsep.equals("Normal"))itemsep="0mm";
        else itemsep="0.25mm";
        String Headingsize=extras.getString("HeadingSize");
        if(Headingsize.equals("Small")) headingsize="large";
        else if(Headingsize.equals("Normal")) headingsize="Large";
        else if (Headingsize.equals("Large")) headingsize="LARGE";
        else headingsize="huge";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_maker);
        ApplySettings();
        final String header="\\documentclass["+fontsize+"pt]{article}\n" +
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
                "\\titleformat{\\section}{\\"+headingsize+"\\scshape\\raggedright}{}{0em}{}\n" +
                "\\renewcommand\\labelitemi{\\raisebox{0.4ex}{\\tiny$\\bullet$}}\n" +
                "\\renewcommand{\\labelitemii}{$\\cdot$}\n" +
                "\\pagenumbering{gobble}\\begin{document}\n" +
                "\t\\vspace*{4.5cm}";
        final List<String> headinglist=new ArrayList<String>();
        final List<String> pointslist=new ArrayList<String>();
        headings=(ListView) findViewById(R.id.headings);

        headinglist.add("Add Heading");
        pointslist.add("");
        headinglist.add(" Scholastic Achievements");
        codelist.add("\\section*{ Scholastic Achievements\\xfilll[0pt]{0.5pt}}\n" +
                "\\vspace{-7pt}\n" +
                "\\begin{itemize}[itemsep="+itemsep+"]\n");

        pointslist.add("");
        headinglist.add(" Projects");
        codelist.add("\\section*{ Projects\\xfilll[0pt]{0.5pt}}\n"+
                "\\vspace{-5pt}\n\\begin{itemize}[itemsep="+itemsep+"]\n");
        pointslist.add("");
        final ResumeAdapter resumeAdapter=new ResumeAdapter(this,headinglist,pointslist);
        headings.setAdapter(resumeAdapter);
        saveresume =(Button) findViewById(R.id.saveresume);
        headings.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                // TODO Auto-generated method stub

                Log.v("long clicked","pos: " + pos);
                AlertDialog alertDialog = new AlertDialog.Builder(ResumeMaker.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Are you Sure you want to delete"+headinglist.get(pos));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(pos==0){
                                    Toast.makeText(ResumeMaker.this, "You Can't Delete Add Heading",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(ResumeMaker.this, "Succesfully Deleted "+headinglist.get(pos),
                                            Toast.LENGTH_SHORT).show();
                                    headinglist.remove(pos);
                                    pointslist.remove(pos);
                                    codelist.remove(pos - 1);
                                    dialog.dismiss();
                                    resumeAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return true;
            }
        });
        saveresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latexcode=header;
                for(int i=0;i<codelist.size();i++){
                    latexcode=latexcode+codelist.get(i)+"\\end{itemize}\n" +"\\vspace{-15pt}";

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
        settings=(ImageButton) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResumeMaker.this,ResumeSettings.class);
                startActivity(i);
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

        public ResumeAdapter(Context applicationContext,List<String> names,List<String> p) {
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
            final ImageButton edit=(ImageButton) view.findViewById(R.id.edit);
            final Button save=(Button) view.findViewById(R.id.save);
            final Button saveedit=(Button) view.findViewById(R.id.saveedit);
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
                        codelist.add("\\section*{"+ details.getText().toString()+"\\xfilll[0pt]{0.5pt}}\n" +
                                "\\vspace{-5pt}\n\\begin{itemize}[itemsep="+itemsep+"]\n");
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
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        details.setText(points.get(i));
                        details.setVisibility(View.VISIBLE);
                        discard.setVisibility(View.VISIBLE);
                        saveedit.setVisibility(View.VISIBLE);
                    }
                });
                saveedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        points.set(i,details.getText().toString());
                        Points.setText(points.get(i));
                        details.setText("");
                        String temp[]=points.get(i).split("\n");
                        String newcode=codelist.get(i-1).substring(0,codelist.get(i-1).indexOf("\n")+1);
                        newcode=newcode+"\\vspace{-7pt}\n" +
                                "\\begin{itemize}[itemsep="+itemsep+"]\n";
                        for(int j=0;j<temp.length;j++){
                            newcode=newcode+"\\item "+temp[j]+"\n";
                        }
                        codelist.set(i-1,newcode);
                        details.setVisibility(View.GONE);
                        discard.setVisibility(View.GONE);
                        saveedit.setVisibility(View.GONE);
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       codelist.set(i-1,codelist.get(i-1)+"\\item "+details.getText().toString()+"\n");
                        points.set(i, points.get(i) + details.getText()+"\n");
                        Points.setText(points.get(i));
                        details.setText("");
                        details.setVisibility(View.GONE);
                        discard.setVisibility(View.GONE);
                        save.setVisibility(View.GONE);
                        edit.setVisibility(View.VISIBLE);

                    }
                });
                discard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        details.setText("");
                        details.setVisibility(View.GONE);
                        discard.setVisibility(View.GONE);
                        save.setVisibility(View.GONE);
                        saveedit.setVisibility(View.GONE);

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
                        if (edit.getVisibility() == View.GONE) {
                            if(!Points.getText().toString().isEmpty())edit.setVisibility(View.VISIBLE);
                        } else {
                            edit.setVisibility(View.GONE);
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
