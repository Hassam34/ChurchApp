package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StudentMain extends AppCompatActivity {
    Button logout, seeAttendence;
    String name;
    TextView showN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        seeAttendence=(Button) findViewById(R.id.seeAttendence);
        logout=(Button) findViewById(R.id.logoutStudent);
        showN=(TextView) findViewById(R.id.showN);

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        name = prefs.getString("name", "No type defined");
        showN.setText("Welcome "+name+" to Student Portal");
//        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();


        seeAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(StudentMain.this,StudentSeeHisClass.class);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit();
                editor.putString("name", "No");
                editor.putString("type", "No");
                 editor.apply();
                Intent intent = new Intent(StudentMain.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
