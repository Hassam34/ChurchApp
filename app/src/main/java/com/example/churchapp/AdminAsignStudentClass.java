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

public class AdminAsignStudentClass extends AppCompatActivity {
    Spinner spinner;
    TextView showname, showClass,showClassesStudent;
    String Querry;
    DbHandler mydb;
    SQLiteDatabase db ;
    Intent intent;
    Button assignButton , unassignButton;
    String StudentName,user,  password;
    List<String> arraySpinner;
    List<String> arrayClasses;
    String selecClass;
    LinearLayout linearLayout;
    Boolean checkStudent=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_asign_student_class);
        StudentName = getIntent().getStringExtra("Student_Name");
        user = getIntent().getStringExtra("Student_Name1");
    checkStudent=getIntent().getBooleanExtra("isfromCreateLogin",false);
//        checkStudent = getIntent().getBooleanExtra("isfromCreateLogin");
        password = getIntent().getStringExtra("password");
        Toast.makeText(getApplicationContext(),user+" : "+ password,Toast.LENGTH_SHORT).show();




        showname=(TextView) findViewById(R.id.showStudentName);
        showClass=(TextView) findViewById(R.id.showStudentClass);


        String showNameString="Assign Class to "+StudentName;
        showname.setText(showNameString);
        linearLayout= (LinearLayout) findViewById(R.id.linearAddClass);

        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();

        spinner=(Spinner) findViewById(R.id.spinneridStudent);
        arraySpinner = new ArrayList<String>();
        arrayClasses = new ArrayList<String>();

        showTeacherClass();
        fetchClasses();
//        fetchAvailableClasses();

        arraySpinner.add("No Class");
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

        assignButton=(Button) findViewById(R.id.assignclassStudent);
        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                   int check= checkClassStudent();
                   if(check!=1){
                       if(checkStudent==true){
                           mydb.insertUSERS(user,"student",password,db);
                       mydb.insertStudentClass(user,"No Class",db);
                           checkStudent=false;
                           StudentName=user;
                           mydb.updateStudentClass(user, selecClass, db);
                           showTeacherClass2();
                       Toast.makeText(getApplicationContext(),"Student Added",Toast.LENGTH_SHORT).show();
                       }
                       else{
                           mydb.updateStudentClass(StudentName, selecClass, db);
//                       String showNameString="Student is currently assign to :"+selecClass;
//                       showClass.setText(showNameString);
                           showTeacherClass();
                       }
                   }
                   else{
                       Toast.makeText(getApplicationContext(),"Already enroll in this class",Toast.LENGTH_SHORT).show();
                   }

                }
                catch (Exception e){
                    System.out.println("Error is: "+e);
//                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials Bro",Toast.LENGTH_SHORT).show();
                }
            }
        });

//        unassignButton =(Button) findViewById(R.id.unassignclassStudent);
//        unassignButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    int check= checkClassStudent();
//                    if(check==1){
//                        db.execSQL(" DELETE FROM STUDENTCLASS WHERE SNAME='"+StudentName+"' AND CNAME ='"+selecClass+"'");
//                        Toast.makeText(getApplicationContext(),selecClass +" Un assigned Successfuly",Toast.LENGTH_SHORT).show();
//                        showTeacherClass();
//                    }
//                    else{
//                        Toast.makeText(getApplicationContext(),"Class is already not assign",Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//                catch (Exception e){
//                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
    private int checkClassStudent(){
        try {
            Cursor cursor = db.rawQuery("SELECT SNAME ,CNAME FROM StudentCLASS WHERE SNAME='"+StudentName+"' AND CNAME='"+selecClass+"'", null);
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

        showClassesStudent = new TextView(this);
        showClassesStudent.setText(name);
//        Dbutton.setOnClickListener(getOnClickDoSomething(Dbutton));
        linearLayout.addView(showClassesStudent);
    }
    private void showTeacherClass(){
        try{
            if(((LinearLayout) linearLayout).getChildCount() > 0)
                ((LinearLayout) linearLayout).removeAllViews();
            Cursor cursor = db.rawQuery("SELECT SNAME ,CNAME FROM StudentCLASS WHERE SNAME='"+StudentName+"'", null);
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
    private void showTeacherClass2(){
        try{
            if(((LinearLayout) linearLayout).getChildCount() > 0)
                ((LinearLayout) linearLayout).removeAllViews();
            Cursor cursor = db.rawQuery("SELECT SNAME ,CNAME FROM StudentCLASS WHERE SNAME='"+user+"'", null);
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

//    @Override
//    protected void onStart() {
//        super.onStart();
//        for (int i=0;i<arrayClasses.size();i++)
//        {
////            Toast.makeText(getApplicationContext(),arrayClasses.get(i),Toast.LENGTH_SHORT).show();
//            arraySpinner.remove(arrayClasses.get(i));
//        }
//    }

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
            Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
        }
    }
//    private void fetchAvailableClasses(){
//        try{
//            Cursor cursor = db.rawQuery("SELECT CNAME FROM TEACHERCLASS", null);
//            if(cursor!=null) {
//                cursor.moveToFirst();
//                do {
//                    arrayClasses.add(cursor.getString(0));
////                    addClass(tname);
////                    buffer.append("name = " + tname);
//                } while (cursor.moveToNext());
////                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
//            }
//            else{
//                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
//            }
//        }
//        catch(Exception e){
//            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
//        }
//    }
}
