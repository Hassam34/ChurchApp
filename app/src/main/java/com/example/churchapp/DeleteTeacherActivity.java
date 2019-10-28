package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteTeacherActivity extends AppCompatActivity {
    EditText user, pass;
    Button deleteButton;
    DbHandler mydb;
    SQLiteDatabase db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_teacher);
        user= (EditText) findViewById(R.id.dUsername);
        pass =(EditText) findViewById(R.id.dPassword);
        deleteButton=(Button) findViewById(R.id.dteacher);
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String username=user.getText().toString();
                    String password =pass.getText().toString();
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
