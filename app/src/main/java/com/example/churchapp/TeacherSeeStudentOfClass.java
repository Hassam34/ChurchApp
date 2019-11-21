package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TeacherSeeStudentOfClass extends AppCompatActivity {
    String className;
    String Querry;
    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    Intent intent;
    Button Dbutton;
    String name;

    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_see_student_of_class);
        className = getIntent().getStringExtra("Class_Name");
//        Toast.makeText(this, className, Toast.LENGTH_SHORT).show();

        buffer= new StringBuffer();
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        linearLayout = (LinearLayout) findViewById(R.id.teacherclassStudents);
    }
    @Override
    protected void onStart() {
        super.onStart();
        fetchFromDataBAse();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(((LinearLayout) linearLayout).getChildCount() > 0)
            ((LinearLayout) linearLayout).removeAllViews();
    }
    private void addClass(String name){

        Dbutton = new Button(this);
        Dbutton.setText(name);
        Dbutton.setOnClickListener(getOnClickDoSomething(Dbutton));
        linearLayout.addView(Dbutton);

    }
    View.OnClickListener getOnClickDoSomething(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                intent= new Intent(TeacherSeeStudentOfClass.this,TeacherTakeAttendenceStudent.class);
                intent.putExtra("Student_Name", button.getText().toString());
                intent.putExtra("Class_Name", className);
                startActivity(intent);
//                Toast.makeText(getApplicationContext(),button.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        };
    }
    private void fetchFromDataBAse(){
        try{
            Cursor cursor = db.rawQuery("SELECT SNAME FROM STUDENTCLASS WHERE CNAME='"+className+"'", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    String tname = cursor.getString(0);
                    addClass(tname);
//                    Toast.makeText(getApplicationContext(),tname,Toast.LENGTH_SHORT).show();
//                    buffer.append("name = " + tname);
                } while (cursor.moveToNext());
//                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
        }
    }
}
