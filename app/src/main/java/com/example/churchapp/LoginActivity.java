package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    private Button loginBtn;
    private Intent intent;
    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    EditText eNaame,ePass;
    String type="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final String loginName = getIntent().getStringExtra("Login_Type_Name");
//        Toast.makeText(getApplicationContext(),loginName,Toast.LENGTH_SHORT).show();
        buffer= new StringBuffer();
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        eNaame=(EditText) findViewById(R.id.name);
        ePass=(EditText) findViewById(R.id.pass);

        loginBtn=(Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                varifyUser();
                if(type.equals("student")){

                    intent= new Intent(LoginActivity.this,StudentMain.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Welcome to "+loginName+ " Portal",Toast.LENGTH_SHORT).show();
                }
                if(type.equals("teacher")){
                    intent= new Intent(LoginActivity.this,TeacherMain.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Welcome to "+loginName+ " Portal",Toast.LENGTH_SHORT).show();
                }
                if(type.equals("admin")){
                    intent= new Intent(LoginActivity.this,AdminMain.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Welcome to "+loginName+ " Portal",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void varifyUser(){
        String name= eNaame.getText().toString();
        String pass = ePass.getText().toString();
        try{
            Cursor cursor = db.rawQuery("SELECT NAME ,TYPE, PASSWORD FROM CHURCH WHERE NAME='"+name+"' AND PASSWORD='"+pass+"'", null);

            if(cursor!=null){
                cursor.moveToFirst();
                do{
                    String tname=cursor.getString(0);

                    type=cursor.getString(1);
                    String tpass=cursor.getString(2);
                    buffer.append("name = "+name+" pass = "+pass +"type = "+type);
                }while(cursor.moveToNext());
                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();

        }

    }
}
