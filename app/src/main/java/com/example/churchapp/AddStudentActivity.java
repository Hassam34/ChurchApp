package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStudentActivity extends AppCompatActivity {
    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    Intent intent;
    Button addStudentButton;
    EditText studentName,passwordStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        addStudentButton= (Button) findViewById(R.id.StudentAdd);

        studentName=(EditText) findViewById(R.id.usernameStudent);

        passwordStudent=(EditText) findViewById(R.id.passwordStudent);

        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String user=studentName.getText().toString();
                    String pass=passwordStudent.getText().toString();
//                    mydb.insertUSERS(user,"student",pass,db);
//                    mydb.insertStudentClass(user,"No Class",db);
                    Intent intent= new Intent(AddStudentActivity.this,AdminAsignStudentClass.class);
                    intent.putExtra("Student_Name1", user);
                    intent.putExtra("password", pass);
                    intent.putExtra("isfromCreateLogin", true);
                    startActivity(intent);
                    finish();

                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
