package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AttendenceStatus extends AppCompatActivity {
    LinearLayout taken,notTaken,takenBody, nottakenBody;
    DbHandler mydb;
    SQLiteDatabase db ;
    String name, className,dates;
    TextView stuname , statusStu;
    List<String> listTaken, listNotTaken, listAllStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_status);
        listTaken = new ArrayList<String>();
        listNotTaken = new ArrayList<String>();
        listAllStudent = new ArrayList<String>();
        taken=(LinearLayout) findViewById(R.id.taken);
        notTaken=(LinearLayout) findViewById(R.id.notTaken);

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String type = prefs.getString("type", "No type defined");
        name = prefs.getString("name", "No type defined");
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        className = getIntent().getStringExtra("Class_Name");
        dates = getIntent().getStringExtra("date");

        fetchStudentTakenClass();
        fetchStudentNottakenClass();
        for (int i=0;i<listTaken.size();i++)
        {
//            Toast.makeText(getApplicationContext(),arrayClasses.get(i),Toast.LENGTH_SHORT).show();
            listAllStudent.remove(listTaken.get(i));
        }

        notTakenStudent();


    }

    private void fetchStudentTakenClass() {
        try{

            Cursor cursor = db.rawQuery("SELECT SNAME ,STATUS FROM STUDENTATTENDENCE WHERE TNAME='"+name+"'AND CNAME='"+className+"' AND DATE='"+dates+"'", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    String sname = cursor.getString(0);
                    String status = cursor.getString(1);
                    listTaken.add(sname);
//                    Toast.makeText(getApplicationContext(),sname + status,Toast.LENGTH_SHORT).show();
                    takenStudent(sname,status);

                } while (cursor.moveToNext());
            }
            else{
                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }

        catch(Exception e){
//            Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
        }
    }

    private void takenStudent(String Sname, String Status) {


        takenBody= new LinearLayout(this);
        stuname= new TextView(this);
        statusStu = new TextView(this);
        stuname.setText(Sname);
        statusStu.setText(Status);

        if(Status.equals("Absent")){
            statusStu.setTextColor(Color.RED);
        }
        else{
            statusStu.setTextColor(Color.rgb(98, 209, 133));
        }

        stuname.setTextColor(Color.BLACK);
        stuname.setTextSize(17);
        stuname.setWidth(450);


        takenBody.setOrientation(LinearLayout.HORIZONTAL);
        takenBody.setPadding(0,20,0,0);
        takenBody.addView(stuname);
        takenBody.addView(statusStu);

        taken.addView(takenBody);




    }

    private  void fetchStudentNottakenClass(){
        try{
            String date="";
            Cursor cursor = db.rawQuery("SELECT SNAME FROM STUDENTCLASS WHERE CNAME='"+className+"'", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    listAllStudent.add(cursor.getString(0));

                } while (cursor.moveToNext());
            }
            else{
                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }

        catch(Exception e){
//            Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
        }
    }

    private void notTakenStudent() {

        for (int i=0;i<listAllStudent.size();i++)
        {
            nottakenBody= new LinearLayout(this);
            stuname= new TextView(this);
            stuname.setText(listAllStudent.get(i));


            stuname.setTextColor(Color.BLACK);
            stuname.setTextSize(17);
            stuname.setWidth(450);


            nottakenBody.setOrientation(LinearLayout.HORIZONTAL);
            nottakenBody.setPadding(0,20,0,0);
            nottakenBody.addView(stuname);


            notTaken.addView(nottakenBody);
        }
    }
}
