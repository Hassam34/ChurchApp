package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AdminMain extends AppCompatActivity {
    Button seeTeacher,seeStudents,seeSubjects,Logout,seeClasses;
    String Querry;
//    DbHandler mydb;
//    SQLiteDatabase db ;
//    StringBuffer buffer;
    Intent intent;
    TextView showN;
    ImageButton addNoti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
//        <Button
//        android:id="@+id/seeSubject"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:text="SEE ALL Subjects "
//        android:layout_marginRight="30dp"
//        android:layout_marginLeft="30dp"
//        android:layout_marginTop="30dp"
//                />

        seeStudents=(Button) findViewById(R.id.seeStudent);
        seeTeacher=(Button) findViewById(R.id.seeTecher);
//        seeSubjects=(Button) findViewById(R.id.seeSubject);
        Logout=(Button) findViewById(R.id.logout);
        seeClasses=(Button) findViewById(R.id.seeClasses);
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String name = prefs.getString("name", "No type defined");
        showN=(TextView) findViewById(R.id.showNA);

        showN.setText("Welcome "+name+" to Admin Portal");
        addNoti=(ImageButton) findViewById(R.id.addnitification);
        addNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminMain.this, AdminNotification.class);
                startActivity(intent);
            }
        });

//        buffer= new StringBuffer();
//        mydb = new DbHandler(this);
//        db = mydb.getReadableDatabase();
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit();
                editor.putString("name", "No");
                editor.putString("type", "No");
//                    editor.putInt("idName", 12);
                editor.apply();
                Intent intent = new Intent(AdminMain.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        seeClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(AdminMain.this,AdminSeeClasses.class);
                startActivity(intent);

            }
        });

        seeStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(AdminMain.this,AdminSeeStudents.class);
                startActivity(intent);
            }
        });

//        seeSubjects.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                intent= new Intent(AdminMain.this,AdminSeeSubjects.class);
//                startActivity(intent);
//
//            }
//        });

        seeTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(AdminMain.this,AdminSeeTeacher.class);
                startActivity(intent);
            }
        });

    }
}
