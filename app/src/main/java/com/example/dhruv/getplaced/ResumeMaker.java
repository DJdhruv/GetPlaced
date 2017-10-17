package com.example.dhruv.getplaced;

import android.app.Activity;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.security.Key;
import java.util.ArrayList;
import java.util.List;



public class ResumeMaker extends AppCompatActivity {
    private ListView headings;
    private Button saveresume;
    private ImageButton settings;
    final List<String> codelist=new ArrayList<String>();
    private RelativeLayout layout;
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
        layout=(RelativeLayout)findViewById(R.id.resumemaker);
        final List<String> headinglist=new ArrayList<String>();
        final List<String> pointslist=new ArrayList<String>();
        headings=(ListView) findViewById(R.id.headings);

        headinglist.add("Add Heading");
        pointslist.add("");
        headinglist.add("Scholastic Achievements");
        codelist.add("\\section*{ Scholastic Achievements\\xfilll[0pt]{0.5pt}}\n" +
                "\\vspace{-7pt}\n" +
                "\\begin{itemize}[itemsep="+itemsep+"]\n");

        pointslist.add("");
        headinglist.add("Projects");
        codelist.add("\\section*{ Projects\\xfilll[0pt]{0.5pt}}\n"+
                "\\vspace{-7pt}\n");
        pointslist.add("");
        headinglist.add("Technical Skills");
        codelist.add("\\section*{ Technical Skills\\xfilll[0pt]{0.5pt}}\n" +
                "\\vspace{-7pt}\n\\renewcommand{\\arraystretch}{1.1}\n" +
                "\t\\begin{tabular}{ p{4.7cm}  p{12cm} }\n");
        headinglist.add("Key Courses Undertaken");
        codelist.add("\\section*{ Key Courses Undertaken\\xfilll[0pt]{0.5pt}}\n" +
                "\\vspace{-7pt}\n\\renewcommand{\\arraystretch}{1.1}\n" +
                "\t\\begin{tabular}{ p{4.7cm}  p{12cm} }\n");

        pointslist.add("");
        headinglist.add("Positions Of Responsibility");
        codelist.add("\\section*{ Positions Of Responsibility\\xfilll[0pt]{0.5pt}}\n"+
                "\\vspace{-7pt}\n");

        pointslist.add("");

        headinglist.add("Extracurricular Activities");
        codelist.add("\\section*{Extracurricular Activities\\xfilll[0pt]{0.5pt}}\n" +
                "\\vspace{-7pt}\n" +
                "\\begin{itemize}[itemsep="+itemsep+"]\n");

        pointslist.add("");

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
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Are you Sure you want to delete "+headinglist.get(pos));
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
                    // latexcode=latexcode+codelist.get(i)+"\\end{itemize}\n" +"\\vspace{-15pt}";
                    if(headinglist.get(i+1).equals("Projects")||
                            headinglist.get(i+1).equals("Key Projects")||
                            headinglist.get(i+1).equals("Technical Skills")||
                            headinglist.get(i+1).equals("Key Courses Undertaken")||
                            headinglist.get(i+1).equals("Positions Of Responsibility")){
                        latexcode=latexcode+codelist.get(i)+"\\vspace{-5pt}\n";
                    }
                    else{
                        latexcode=latexcode+codelist.get(i)+"\\end{itemize}\n" +"\\vspace{-18pt}";
                    }
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
        public Boolean KeyboardShown=false;

        public ResumeAdapter(Context applicationContext,List<String> names,List<String> p) {
            this.context = applicationContext;
            this.headings = names;
            this.points=p;
            inflter = (LayoutInflater.from(applicationContext));
        }
        public void ShowKeyboard() {
            if (!KeyboardShown) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                        layout.getApplicationWindowToken(),
                        InputMethodManager.SHOW_FORCED, 0);
                KeyboardShown=true;
            }
        }
        public void HideKeyboard() {
            if (KeyboardShown) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                        layout.getApplicationWindowToken(),
                        InputMethodManager.RESULT_HIDDEN, 0);
                KeyboardShown=false;
            }
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
            final ImageButton next=(ImageButton) view.findViewById(R.id.next);
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
                            details.requestFocus();
                            ShowKeyboard();
                        } else {
                            details.setVisibility(View.GONE);
                            HideKeyboard();
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
                        saveedit.setVisibility(View.GONE);
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
                        HideKeyboard();

                    }
                });
                discard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        details.setText("");
                        details.setVisibility(View.GONE);
                        discard.setVisibility(View.GONE);
                        save.setVisibility(View.GONE);
                        HideKeyboard();

                    }
                });
            }
            else {
                heading.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Points.getVisibility() == View.GONE  && Add.getVisibility() == View.GONE
                        && edit.getVisibility() == View.GONE) {
                            Points.setVisibility(View.VISIBLE);
                            Add.setVisibility(View.VISIBLE);
                            edit.setVisibility(View.VISIBLE);
                        }
                        else{
                            Points.setVisibility(View.GONE);
                            Add.setVisibility(View.GONE);
                            edit.setVisibility(View.GONE);
                        }
                        save.setVisibility(View.GONE);
                        next.setVisibility(View.GONE);
                        saveedit.setVisibility(View.GONE);
                        discard.setVisibility(View.GONE);
                        details.setVisibility(View.GONE);
                        HideKeyboard();

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
                        edit.setVisibility(View.VISIBLE);
                        Add.setVisibility(View.VISIBLE);
                        Points.setVisibility(View.VISIBLE);
                        HideKeyboard();

                    }
                });

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edit.setVisibility(View.GONE);
                        Add.setVisibility(View.GONE);
                        details.setText(points.get(i));
                        ShowKeyboard();
                        details.setVisibility(View.VISIBLE);
                        details.requestFocus();
                        discard.setVisibility(View.VISIBLE);
                        saveedit.setVisibility(View.VISIBLE);
                    }
                });
                if (headings.get(i).equals("Projects") || headings.get(i).equals("Key Projects")||
                        headings.get(i).equals("Positions Of Responsibility")) {
                    final String hint;
                    if(headings.get(i).equals("Positions Of Responsibility")){
                        hint="POR Name";
                    }
                    else hint="Project Name";
                    Add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Add.setVisibility(View.GONE);
                            edit.setVisibility(View.GONE);
                            details.setVisibility(View.VISIBLE);
                            details.setHint(hint);
                            details.requestFocus();
                            next.setVisibility(View.VISIBLE);
                            ShowKeyboard();

                        }
                    });
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (details.getHint().toString().equals(hint)) {
                                points.set(i, points.get(i) + details.getText().toString() + "\n");
                                Points.setText(points.get(i));
                                codelist.set(i - 1, codelist.get(i - 1) + "\\textbf{" + details.getText() + "}");
                                details.setText("");
                                details.requestFocus();
                                details.setHint("Year");
                            } else if (details.getHint().toString().equals("Year")) {
                                points.set(i, points.get(i).substring(0, points.get(i).length() - 1) + "    " + details.getText().toString() + "\n");
                                Points.setText(points.get(i));
                                codelist.set(i - 1, codelist.get(i - 1) + "\\hfill{\\sl \\small " + details.getText() + "}\\\\" + "\n"
                                        + "\\vspace{-18pt}\n\\begin{itemize}[itemsep=" + itemsep + "]\n");
                                details.setText("");
                                details.requestFocus();
                                details.setHint("Details");
                            } else {
                                points.set(i, points.get(i) + details.getText() + "\n");
                                Points.setText(points.get(i));
                                codelist.set(i - 1, codelist.get(i - 1) + "\\item " + details.getText().toString() + "\n");
                                details.setText("");
                                details.requestFocus();
                                details.setHint("Enter Another Detail or Click Save");
                                save.setVisibility(View.VISIBLE);
                                discard.setVisibility(View.VISIBLE);

                            }
                        }
                    });


                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            codelist.set(i - 1, codelist.get(i - 1) + "\\item " + details.getText().toString() + "\n\\end{itemize}\\vspace{-18pt}");
                            points.set(i, points.get(i) + details.getText() + "\n");
                            Points.setText(points.get(i));
                            details.setText("");
                            details.setVisibility(View.GONE);
                            discard.setVisibility(View.GONE);
                            save.setVisibility(View.GONE);
                            next.setVisibility(View.GONE);
                            edit.setVisibility(View.VISIBLE);
                            Add.setVisibility(View.VISIBLE);
                            HideKeyboard();

                        }
                    });
                    saveedit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            points.set(i, details.getText().toString());
                            Points.setText(points.get(i));
                            details.setText("");
                            String temp[] = points.get(i).split("\n");
                            String newcode = codelist.get(i - 1).substring(0, codelist.get(i - 1).indexOf("\n") + 1);
                            newcode = newcode + "\\vspace{-7pt}\n";
                            for (int j = 0; j < temp.length; j++) {
                                if (temp[j].contains("    ")) {
                                    if(j!=0){
                                        newcode=newcode+"\\end{itemize}\n";
                                    }
                                    newcode = newcode + "\\textbf{" + temp[j].substring(0, temp[j].indexOf("    ")) + "} ";
                                    newcode = newcode + "\\hfill{\\sl \\small " + temp[j].substring(temp[j].indexOf("    ") + 4) + "}\\\\\n\\vspace{-18pt}" + "\n"
                                            + "\n\\begin{itemize}[itemsep=" + itemsep + "]\n";
                                } else {
                                    newcode = newcode + "\\item " + temp[j] + "\n";
                                }
                            }
                            newcode=newcode+"\\end{itemize}\n";
                            codelist.set(i - 1, newcode);
                            details.setVisibility(View.GONE);
                            discard.setVisibility(View.GONE);
                            saveedit.setVisibility(View.GONE);
                            Add.setVisibility(View.VISIBLE);
                            edit.setVisibility(View.VISIBLE);
                            HideKeyboard();

                        }
                    });


                }
                else if (headings.get(i).equals("Technical Skills")||headings.get(i).equals("Key Courses Undertaken")) {
                    final String Hint[];
                    if(headings.get(i).equals("Technical Skills")){
                        Hint=new String[]{"Programming Languages","Software Skills","Development"};
                    }
                    else Hint=new String[]{"Computer Science","Mathematics","Others"};
                    Add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edit.setVisibility(View.GONE);
                            Add.setVisibility(View.GONE);
                            details.setVisibility(View.VISIBLE);
                            details.requestFocus();
                            details.setHint(Hint[0]);
                            next.setVisibility(View.VISIBLE);
                            ShowKeyboard();

                        }
                    });
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (details.getHint().toString().equals(Hint[0])) {
                                points.set(i,points.get(i)+ Hint[0]+": " + details.getText().toString() + "\n");
                                Points.setText(points.get(i));
                                codelist.set(i - 1, codelist.get(i - 1) + "\\textbf{"+Hint[0]+" : } & " +
                                        details.getText()+"\\\\\n");
                                details.setText("");
                                details.requestFocus();
                                details.setHint(Hint[1]);
                            } else   {
                                points.set(i, points.get(i)+Hint[1]+": " + details.getText().toString() + "\n");
                                Points.setText(points.get(i));
                                codelist.set(i - 1, codelist.get(i - 1) + "\\textbf{"+Hint[1]+" : } & " +
                                        details.getText()+"\\\\\n");
                                details.setText("");
                                details.setHint(Hint[2]);
                                details.requestFocus();
                                next.setVisibility(View.GONE);
                                save.setVisibility(View.VISIBLE);
                                discard.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            codelist.set(i - 1, codelist.get(i - 1) + "\\textbf{"+Hint[2]+" : } & " +
                                    details.getText()+"\n\\end{tabular}");
                            points.set(i, points.get(i)+ Hint[2]+": " + details.getText().toString() + "\n");
                            Points.setText(points.get(i));
                            details.setText("");
                            Add.setVisibility(View.VISIBLE);
                            details.setVisibility(View.GONE);
                            discard.setVisibility(View.GONE);
                            save.setVisibility(View.GONE);
                            next.setVisibility(View.GONE);
                            edit.setVisibility(View.VISIBLE);
                            HideKeyboard();

                        }
                    });
                    saveedit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            points.set(i, details.getText().toString());
                            Points.setText(points.get(i));
                            details.setText("");
                            String temp[] = points.get(i).split("\n");
                            String newcode = "\\section*{ "+headings.get(i)+"\\xfilll[0pt]{0.5pt}}\n" +
                                    "\\vspace{-7pt}\n\\renewcommand{\\arraystretch}{1.1}\n" +
                                    "\t\\begin{tabular}{ p{4.7cm}  p{12cm} }\n";
                            newcode = newcode + "\\textbf{" + Hint[0] + " : } & " + temp[0].substring(Hint[0].length()+2) + "\\\\\n";
                            newcode = newcode + "\\textbf{" + Hint[1] + " : } & " + temp[1].substring(Hint[1].length()+2) + "\\\\\n";
                            newcode = newcode + "\\textbf{" + Hint[2] + " : } & " + temp[2].substring(Hint[2].length()+2) + "\n\\end{tabular}";


                            codelist.set(i-1,newcode);
                            details.setVisibility(View.GONE);
                            discard.setVisibility(View.GONE);
                            saveedit.setVisibility(View.GONE);
                            edit.setVisibility(View.VISIBLE);
                            Add.setVisibility(View.VISIBLE);
                            HideKeyboard();

                        }
                    });

                }
                else {
                    Add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edit.setVisibility(View.GONE);
                            Add.setVisibility(View.GONE);
                            details.setVisibility(View.VISIBLE);
                            details.requestFocus();
                            discard.setVisibility(View.VISIBLE);
                            save.setVisibility(View.VISIBLE);
                            ShowKeyboard();

                        }
                    });

                    saveedit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            points.set(i, details.getText().toString());
                            Points.setText(points.get(i));
                            details.setText("");
                            String temp[] = points.get(i).split("\n");
                            String newcode = codelist.get(i - 1).substring(0, codelist.get(i - 1).indexOf("\n") + 1);
                            newcode = newcode + "\\vspace{-7pt}\n" +
                                    "\\begin{itemize}[itemsep=" + itemsep + "]\n";
                            for (int j = 0; j < temp.length; j++) {
                                newcode = newcode + "\\item " + temp[j] + "\n";
                            }
                            codelist.set(i - 1, newcode);
                            details.setVisibility(View.GONE);
                            discard.setVisibility(View.GONE);
                            saveedit.setVisibility(View.GONE);
                            edit.setVisibility(View.VISIBLE);
                            Add.setVisibility(View.VISIBLE);
                            HideKeyboard();
                        }
                    });
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            codelist.set(i - 1, codelist.get(i - 1) + "\\item " + details.getText().toString() + "\n");
                            points.set(i, points.get(i) + details.getText() + "\n");
                            Points.setText(points.get(i));
                            details.setText("");
                            details.setVisibility(View.GONE);
                            discard.setVisibility(View.GONE);
                            save.setVisibility(View.GONE);
                            edit.setVisibility(View.VISIBLE);
                            Add.setVisibility(View.VISIBLE);
                            HideKeyboard();

                        }
                    });


                }
            }

            return view;
        }
    }
}
