package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteStudentActivity extends AppCompatActivity {
    EditText stuName, stuId;
    Button deleteButton;
    DbHandler mydb;
    SQLiteDatabase db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);
        stuName= (EditText) findViewById(R.id.dUsernameStudent);
        stuId =(EditText) findViewById(R.id.dPasswordStudent);
        deleteButton=(Button) findViewById(R.id.dstudent);
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String username=stuName.getText().toString();
                    String password =stuId.getText().toString();
                    db.execSQL(" DELETE FROM CUSERS WHERE NAME='"+username+"' AND PASSWORD='"+password+"'");
                    finish();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
