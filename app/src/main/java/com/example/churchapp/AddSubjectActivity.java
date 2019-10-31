package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSubjectActivity extends AppCompatActivity {
    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    Intent intent;
    Button addSubjectButton;
    EditText subjectName,passwordSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();

        addSubjectButton= (Button) findViewById(R.id.SubjectAdd);

        subjectName=(EditText) findViewById(R.id.usernameSubject);

        passwordSubject=(EditText) findViewById(R.id.passwordSubject);

        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String user=subjectName.getText().toString();
                    String pass=passwordSubject.getText().toString();
                    mydb.insertSUBJECTS(user,pass,db);
                    finish();
                    Toast.makeText(getApplicationContext(),"Subject Added",Toast.LENGTH_SHORT).show();

                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
