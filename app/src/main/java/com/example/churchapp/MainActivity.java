package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button adminBtn;
    private Button studentBtn;
    private Button teacherBtn;
    private Intent intent;
    String loginName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adminBtn=(Button) findViewById(R.id.adminBtn);
        studentBtn=(Button) findViewById(R.id.studentBtn);
        teacherBtn=(Button) findViewById(R.id.teacherBtn);
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String type = prefs.getString("type", "No type defined");
        String name = prefs.getString("name", "No type defined");
//        int idName = prefs.getInt("idName", 0);
//        Toast.makeText(getApplicationContext(),"Shared Preference "+type+ "  value",Toast.LENGTH_SHORT).show();
        if(type.equals("student")){

            intent= new Intent(MainActivity.this,StudentMain.class);
//            intent.putExtra("login_Name", name);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(),"Welcome to "+type+ " Portal",Toast.LENGTH_SHORT).show();
        }
        if(type.equals("teacher")){

            intent= new Intent(MainActivity.this,TeacherMain.class);
//            intent.putExtra("login_Name", name);

            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(),"Welcome to "+type+ " Portal",Toast.LENGTH_SHORT).show();
        }
        if(type.equals("admin")){

            intent= new Intent(MainActivity.this,AdminMain.class);
//            intent.putExtra("login_Name", name);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(),"Welcome to "+type+ " Portal",Toast.LENGTH_SHORT).show();
        }
        teacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginName="Teacher";
                intent= new Intent(MainActivity.this,LoginActivity.class);
                intent.putExtra("Login_Type_Name", loginName);

                startActivity(intent);
                finish();

            }
        });

        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginName="Student";
                intent= new Intent(MainActivity.this,LoginActivity.class);
                intent.putExtra("Login_Type_Name", loginName);
                startActivity(intent);
                finish();
            }
        });
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginName="Admin";
                intent= new Intent(MainActivity.this,LoginActivity.class);
                intent.putExtra("Login_Type_Name", loginName);
                startActivity(intent);
                finish();
            }
        });

    }
}
