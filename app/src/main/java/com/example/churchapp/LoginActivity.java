package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    String tname;
public static final String MY_PREFS_NAME = "MyPrefsFile";



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
                    SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit();
                    editor.putString("type", type);
                    editor.putString("name", tname);
//                    editor.putInt("idName", 12);
                    editor.apply();

                    intent= new Intent(LoginActivity.this,StudentMain.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(),"Welcome to "+type+ " Portal",Toast.LENGTH_SHORT).show();
                }
                if(type.equals("teacher")){
                    SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit();
                    editor.putString("type", type);
                    editor.putString("name", tname);
//                    editor.putInt("idName", 12);
                    editor.apply();
                    intent= new Intent(LoginActivity.this,TeacherMain.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(),"Welcome to "+type+ " Portal",Toast.LENGTH_SHORT).show();
                }
                if(type.equals("admin")){
                    SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit();
                    editor.putString("type", type);
                    editor.putString("name", tname);
//                    editor.putInt("idName", 12);
                    editor.apply();
                    intent= new Intent(LoginActivity.this,AdminMain.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(),"Welcome to "+type+ " Portal",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void varifyUser(){
        String name= eNaame.getText().toString();
        String pass = ePass.getText().toString();
        try{
            Cursor cursor = db.rawQuery("SELECT NAME ,TYPE, PASSWORD FROM CUSERS WHERE NAME='"+name+"' AND PASSWORD='"+pass+"'", null);

            if(cursor!=null){
                cursor.moveToFirst();
                do{
                    tname=cursor.getString(0);

                    type=cursor.getString(1);
                    String tpass=cursor.getString(2);
                    buffer.append("name = "+name+" pass = "+pass +"type = "+type);
                }while(cursor.moveToNext());
               // Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();

        }

    }
}
