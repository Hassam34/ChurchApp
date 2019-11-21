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
import android.widget.Toast;

public class teacherSeeAttenceDates extends AppCompatActivity {
    LinearLayout linearLayout;
    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    Intent intent;
    Button Dbutton;
    String name, className;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_see_attence_dates);

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String type = prefs.getString("type", "No type defined");
        name = prefs.getString("name", "No type defined");
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        className = getIntent().getStringExtra("Class_Name");

        linearLayout=(LinearLayout) findViewById(R.id.showDatesClassT);
    }
    @Override
    protected void onStart() {
        super.onStart();
        fetchFromDataBAse();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(((LinearLayout) linearLayout).getChildCount() > 0)
            ((LinearLayout) linearLayout).removeAllViews();
    }
    private void addClass(String name){

        Dbutton = new Button(this);
        Dbutton.setText(name);
        Dbutton.setOnClickListener(getOnClickDoSomething(Dbutton));
        linearLayout.addView(Dbutton);

    }
    View.OnClickListener getOnClickDoSomething(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                if(!button.getText().toString().equals("No Class")){
                    intent= new Intent(teacherSeeAttenceDates.this,AttendenceStatus.class);
                    intent.putExtra("Class_Name",className);
                    intent.putExtra("date", button.getText().toString());
                    startActivity(intent);
                }

            }
        };
    }

    private void fetchFromDataBAse(){
        try{
            String date="";
            Cursor cursor = db.rawQuery("SELECT DISTINCT DATE FROM STUDENTATTENDENCE WHERE TNAME='"+name+"'AND CNAME='"+className+"'", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    date = cursor.getString(0);
                    addClass(date);

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
}
