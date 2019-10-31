package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddTeacherActivity extends AppCompatActivity {

    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    Intent intent;
    Button addTeacherButton;
    EditText username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        addTeacherButton= (Button) findViewById(R.id.teacherAdd);
        username=(EditText) findViewById(R.id.username);

        password=(EditText) findViewById(R.id.password);




        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String user=username.getText().toString();
                    String pass=password.getText().toString();
                   mydb.insertUSERS(user,"teacher",pass,db);
                   finish();
                    Toast.makeText(getApplicationContext(),"Teacher Added",Toast.LENGTH_SHORT).show();

                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
