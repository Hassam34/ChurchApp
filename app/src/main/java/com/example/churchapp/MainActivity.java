package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        teacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginName="Teacher";
                intent= new Intent(MainActivity.this,LoginActivity.class);
                intent.putExtra("Login_Type_Name", loginName);

                startActivity(intent);
            }
        });

        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginName="Student";
                intent= new Intent(MainActivity.this,LoginActivity.class);
                intent.putExtra("Login_Type_Name", loginName);
                startActivity(intent);
            }
        });
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginName="Admin";
                intent= new Intent(MainActivity.this,LoginActivity.class);
                intent.putExtra("Login_Type_Name", loginName);
                startActivity(intent);
            }
        });

    }
}
