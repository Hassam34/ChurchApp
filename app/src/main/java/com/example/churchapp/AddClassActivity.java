package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddClassActivity extends AppCompatActivity {
    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    Intent intent;
    Button addClassButton;
    EditText className;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();

        addClassButton= (Button) findViewById(R.id.ClassAdd);

        className=(EditText) findViewById(R.id.usernameClass);


        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String user=className.getText().toString();
                    mydb.insertCLASSES(user,db);
                    finish();
                    Toast.makeText(getApplicationContext(),"Class Added",Toast.LENGTH_SHORT).show();

                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
