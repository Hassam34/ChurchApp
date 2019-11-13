package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StudentSeeAttendenceStatus extends AppCompatActivity {
    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    Intent intent;
    Button Dbutton;
    String name, className,date1;
    TextView status,instructor,Class,date,comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_see_attendence_status);

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String type = prefs.getString("type", "No type defined");
        name = prefs.getString("name", "No type defined");
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();

        status=(TextView) findViewById(R.id.status);
        instructor=(TextView) findViewById(R.id.instructor);
        Class=(TextView) findViewById(R.id.Class);
        date=(TextView) findViewById(R.id.date);
        comments=(TextView) findViewById(R.id.comments);


        className = getIntent().getStringExtra("Class_Name");
        date1 = getIntent().getStringExtra("date");

    }
    @Override
    protected void onStart() {
        super.onStart();
        fetchFromDataBAse();
    }

    private void fetchFromDataBAse(){
        try{
            Cursor cursor = db.rawQuery("SELECT STATUS,TNAME,CNAME,DATE,COMMENTS FROM STUDENTATTENDENCE WHERE SNAME='"+name+"'AND CNAME='"+className+"' AND DATE ='"+date1+"'", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    status.setText(cursor.getString(0));
                    instructor.setText(cursor.getString(1));
                    Class.setText(cursor.getString(2));
                    date.setText(cursor.getString(3));
                    comments.setText(cursor.getString(4));
                } while (cursor.moveToNext());
            }
            else{
                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }

        catch(Exception e){
            System.out.println(e);
//            Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
        }
    }

}
