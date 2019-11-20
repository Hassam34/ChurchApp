package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TeacherTakeAttendenceStudent extends AppCompatActivity {
    String studentName;
    TextView showAttendenceText,showComments,date;
    String className;
    String Querry;
    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    Intent intent;
    Button present,absent, confirm;
    String Teachername;
    EditText behaviour;
    String formattedDate;
    String Status="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_take_attendence_student);
        studentName = getIntent().getStringExtra("Student_Name");
        className = getIntent().getStringExtra("Class_Name");
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        Teachername = prefs.getString("name", "No type defined");

        absent=(Button) findViewById(R.id.absent);
        present=(Button) findViewById(R.id.present);
        confirm=(Button)findViewById(R.id.confirm);
        behaviour=(EditText) findViewById(R.id.behaviours);
        showAttendenceText=(TextView) findViewById(R.id.showAttendenceText);
        date=(TextView) findViewById(R.id.date);
        showComments=(TextView) findViewById(R.id.showComments);
        buffer= new StringBuffer();
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);

        date.setText(formattedDate);

        checkAttendence();
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present.setVisibility(View.GONE);
                Status="Not Taken";
            }
        });
        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                absent.setVisibility(View.GONE);
                Status="Taken";
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Status==""){
                    Toast.makeText(TeacherTakeAttendenceStudent.this, "Mark Attendence First", Toast.LENGTH_SHORT).show();
                }
                else{
                    MarkAttendence();
                    finish();
                }
            }
        });
    }
    private void checkAttendence(){
        try{
            String Sname="";
            Cursor cursor = db.rawQuery("SELECT SNAME FROM STUDENTATTENDENCE WHERE SNAME='"+studentName+"' AND DATE='"+formattedDate+"' AND TNAME='"+Teachername+"'", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    Sname = cursor.getString(0);
                    if (Sname!=""){
                        absent.setVisibility(View.GONE);
                        present.setVisibility(View.GONE);
                        confirm.setVisibility(View.GONE);
                        behaviour.setVisibility(View.GONE);
                        showComments.setVisibility(View.GONE);
                        showAttendenceText.setText("Already marked for today class");
                    }

                } while (cursor.moveToNext());
            }
            else{
                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){

        }
    }

    public void MarkAttendence(){
        try{

            mydb.isertAttendence(studentName,Teachername,className,formattedDate,Status,behaviour.getText().toString(),db);
            Toast.makeText(this, "Attendence has been added", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            Toast.makeText(this, "no", Toast.LENGTH_SHORT).show();
        }
    }
}
