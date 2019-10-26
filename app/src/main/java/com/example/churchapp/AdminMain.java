package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminMain extends AppCompatActivity {
    Button seeTeacher,seeStudents,seeSubjects,Logout,seeClasses;
    String Querry;
//    DbHandler mydb;
//    SQLiteDatabase db ;
//    StringBuffer buffer;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        seeStudents=(Button) findViewById(R.id.seeStudent);
        seeTeacher=(Button) findViewById(R.id.seeTecher);
        seeSubjects=(Button) findViewById(R.id.seeSubject);
        Logout=(Button) findViewById(R.id.logout);
        seeClasses=(Button) findViewById(R.id.seeClasses);

//        buffer= new StringBuffer();
//        mydb = new DbHandler(this);
//        db = mydb.getReadableDatabase();

        seeClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(AdminMain.this,AdminSeeClasses.class);
                startActivity(intent);

            }
        });

        seeStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(AdminMain.this,AdminSeeStudents.class);
                startActivity(intent);
            }
        });

        seeSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(AdminMain.this,AdminSeeSubjects.class);
                startActivity(intent);

            }
        });

        seeTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(AdminMain.this,AdminSeeTeacher.class);
                startActivity(intent);
            }
        });

    }
}
