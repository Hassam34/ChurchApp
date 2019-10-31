package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AdminSeeSubjects extends AppCompatActivity {
    String Querry;
    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    Intent intent;
    Button Dbutton, addSubjectButton, deleteSubjectButton;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_see_subjects);
        buffer= new StringBuffer();
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutsubject);
        addSubjectButton=(Button) findViewById(R.id.addSubject) ;
        deleteSubjectButton=(Button) findViewById(R.id.deleteSubject);

        deleteSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(AdminSeeSubjects.this,DeleteSubjectActivity.class);
                startActivity(intent);
            }
        });

        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(AdminSeeSubjects.this,AddSubjectActivity.class);
                startActivity(intent);
            }
        });    }
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
    private void addSubject(String name){

        Dbutton = new Button(this);
        Dbutton.setText(name);
        linearLayout.addView(Dbutton);

    }
    private void fetchFromDataBAse(){

        try{
            Cursor cursor = db.rawQuery("SELECT SNAME FROM SUBJECTS", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    String tname = cursor.getString(0);
                    addSubject(tname);
                    buffer.append("name = " + tname);
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
}
