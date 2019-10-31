package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteSubjectActivity extends AppCompatActivity {
    EditText subName, subId;
    Button deleteButton;
    DbHandler mydb;
    SQLiteDatabase db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_subject);
        subName= (EditText) findViewById(R.id.dUsernameSubject);
        subId =(EditText) findViewById(R.id.dPasswordSubject);
        deleteButton=(Button) findViewById(R.id.dsubject);
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String username=subName.getText().toString();
                    String password =subId.getText().toString();
                    db.execSQL(" DELETE FROM SUBJECTS WHERE SNAME='"+username+"' AND SCODE='"+password+"'");
                    finish();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}