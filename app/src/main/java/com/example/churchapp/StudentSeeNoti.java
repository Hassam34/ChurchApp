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

public class StudentSeeNoti extends AppCompatActivity {

    LinearLayout header,body, notifications;
    DbHandler mydb;
    SQLiteDatabase db ;
    TextView Name, Date, Comment;
    String Cname, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_see_noti);
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        name = prefs.getString("name", "No type defined");

        notifications=(LinearLayout) findViewById(R.id.notificationsS);

        fetchFromDataBAse();
        fetchNotification();

    }
    private void addTeachers(String name, String comment, String date){
        body=new LinearLayout(this);
        header=new LinearLayout(this);

        Name = new TextView(this);
        Comment = new TextView(this);
        Date = new TextView(this);

        Name.setText(name);
        Name.setTextColor(Color.BLACK);
        Name.setTextSize(17);
        Name.setWidth(450);
        Date.setText(date);
        Comment.setText(comment);

        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setPadding(0,20,0,0);
        body.setOrientation(LinearLayout.VERTICAL);
        body.setPadding(10,10,10,0);

        header.addView(Name);
        header.addView(Date);
        body.addView(Comment);

        notifications.addView(header);
        notifications.addView(body);


    }

    private void fetchNotification() {

        try{
            Cursor cursor = db.rawQuery("SELECT NAME, COMMENT,DATE FROM TEACHEROTIFICATION WHERE CNAME='"+Cname+"' ORDER BY ID DESC", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    String name = cursor.getString(0);
                    String comment = cursor.getString(1);
                    String date = cursor.getString(2);
                    addTeachers(name, comment,date);

                } while (cursor.moveToNext());
//                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
        }

    }
    private void fetchFromDataBAse(){
        try{
            Cursor cursor = db.rawQuery("SELECT CNAME FROM STUDENTCLASS WHERE SNAME='"+name+"'", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    Cname= cursor.getString(0);

//                    Toast.makeText(getApplicationContext(),tname,Toast.LENGTH_SHORT).show();

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
}



