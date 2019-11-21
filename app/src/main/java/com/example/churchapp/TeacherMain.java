package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherMain extends AppCompatActivity {
    Button logout, addAttendence, showAttendence;
    TextView showN;
    ImageButton seeNotiTeacher,addNotiTeacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        logout=(Button) findViewById(R.id.logoutTeacher);
        showAttendence=(Button) findViewById(R.id.ShowAtendenceT);

        seeNotiTeacher=(ImageButton) findViewById(R.id.seeNotiTeacher);
        addNotiTeacher=(ImageButton) findViewById(R.id.addNotiTeacher) ;


        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String name = prefs.getString("name", "No type defined");
        showN=(TextView) findViewById(R.id.showNT);

        showN.setText("Welcome "+name+" to Teacher Portal");

//        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        addAttendence=(Button) findViewById(R.id.AddAtendence);
        addAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherMain.this, TeacherSeeClasses.class);
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
                Intent intent = new Intent(TeacherMain.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        seeNotiTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherMain.this, TeacherSeeNoti.class);
                startActivity(intent);
            }
        });

        addNotiTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherMain.this, TeacheraddNoti.class);
                startActivity(intent);
            }
        });

        showAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherMain.this, ShowAttendenceStatus.class);
                startActivity(intent);
            }
        });
    }
}
