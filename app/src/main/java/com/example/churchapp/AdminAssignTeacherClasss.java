package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminAssignTeacherClasss extends AppCompatActivity {
    Spinner spinner,classAssignName;
    TextView showname, showClass, showTeacherC;
    String Querry;
    DbHandler mydb;
    SQLiteDatabase db ;
    Intent intent;
    Button assignButton, unassignButton;
    String teacherName;
    List<String> arraySpinner;
    List<String> arrayClasses;
    List<String> arrayTC;
    LinearLayout linearLayout;

//    List<String> arrayTeacherClasses;


    String selecClass, selecClass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_assign_teacher_classs);

        teacherName = getIntent().getStringExtra("Teacher_Name");
        showname=(TextView) findViewById(R.id.showTeacherName);
        showClass=(TextView) findViewById(R.id.showTeacherClass);
        String showNameString="Assign Class to "+teacherName;
        showname.setText(showNameString);
        assignButton=(Button) findViewById(R.id.assignclass);
        unassignButton=(Button) findViewById(R.id.unassignclassTeacher);
        linearLayout=(LinearLayout) findViewById(R.id.linearAddClassT);


        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();

        spinner=(Spinner) findViewById(R.id.spinnerid);
        classAssignName=(Spinner) findViewById(R.id.spinnerid2);

        arraySpinner = new ArrayList<String>();
        arrayClasses = new ArrayList<String>();
        arrayTC = new ArrayList<String>();

        showTeacherClass();
        fetchClasses();
        fetchAvailableClasses();
        fetchTeacherClasses();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selecClass = parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!selecClass.equals("")){
                        int check= checkClassStudent();
                        if(check!=1){
                            mydb.insertTeacherClass(teacherName, selecClass, db);
                            Toast.makeText(getApplicationContext(),selecClass+" is assign to "+ teacherName,Toast.LENGTH_SHORT).show();

                            finish();

//                       String showNameString="Student is currently assign to :"+selecClass;
//                       showClass.setText(showNameString);
                            showTeacherClass();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Already enroll in this class",Toast.LENGTH_SHORT).show();
                        }

                    }

                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayTC);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classAssignName.setAdapter(adapter2);
        classAssignName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selecClass2 = parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        unassignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!arrayTC.equals("null")) {
                        db.execSQL(" DELETE FROM TEACHERCLASS WHERE CNAME='" + selecClass2 + "'");
                        finish();
                    }

                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    private int checkClassStudent(){
        try {
            Cursor cursor = db.rawQuery("SELECT TNAME ,CNAME FROM TEACHERCLASS WHERE TNAME='"+teacherName+"' AND CNAME='"+selecClass+"'", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    String cname= cursor.getString(1);
                    return 1;
                } while (cursor.moveToNext());
            }
            else{

                Toast.makeText(getApplicationContext(),"No Student avialable in database",Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
        catch (Exception e){
//            Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
            return 0;
        }

//        return 0;
    }
    private void addStudentClass (String name){

        showTeacherC = new TextView(this);
        showTeacherC.setText(name);
//        Dbutton.setOnClickListener(getOnClickDoSomething(Dbutton));
        linearLayout.addView( showTeacherC);
    }
    private void showTeacherClass(){
        try{
            if(((LinearLayout) linearLayout).getChildCount() > 0)
                ((LinearLayout) linearLayout).removeAllViews();
            Cursor cursor = db.rawQuery("SELECT TNAME ,CNAME FROM TEACHERCLASS WHERE TNAME='"+teacherName+"'", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    String cname=cursor.getString(1);
                    String showNameString="Student is currently assign to :";
                    showClass.setText(showNameString);
                    addStudentClass(cname);
//                    Toast.makeText(getApplicationContext(),cname,Toast.LENGTH_SHORT).show();

                } while (cursor.moveToNext());
            }
            else{
                Toast.makeText(getApplicationContext(),"No Student avialable in database",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
//            Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        arraySpinner.add("");
        arrayTC.add("");
        for (int i=0;i<arrayClasses.size();i++)
        {
//            Toast.makeText(getApplicationContext(),arrayClasses.get(i),Toast.LENGTH_SHORT).show();
            arraySpinner.remove(arrayClasses.get(i));
        }
    }

    private void fetchClasses(){
        try{
            Cursor cursor = db.rawQuery("SELECT CNAME FROM CLASSES", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {

                    arraySpinner.add(cursor.getString(0));
//                    addClass(tname);
//                    buffer.append("name = " + tname);
                } while (cursor.moveToNext());
//                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
//            Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
        }
    }
    private void fetchAvailableClasses(){
        try{
            Cursor cursor = db.rawQuery("SELECT CNAME FROM TEACHERCLASS", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    arrayClasses.add(cursor.getString(0));
//                    addClass(tname);
//                    buffer.append("name = " + tname);
                } while (cursor.moveToNext());
//                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
            }
            else{
//                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
//            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }
    private void fetchTeacherClasses(){
        try{
            Cursor cursor = db.rawQuery("SELECT CNAME FROM TEACHERCLASS WHERE TNAME='"+teacherName+"'", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    arrayTC.add(cursor.getString(0));
//                    addClass(tname);
//                    buffer.append("name = " + tname);
                } while (cursor.moveToNext());
//                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
            }
            else{
//                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
//            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

}
