package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AdminSeeTeacher extends AppCompatActivity {

    String Querry;
    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    Intent intent;
    Button Dbutton, deleteTeacherButton;
    ImageButton addTeacherButton;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_see_teacher);
        buffer= new StringBuffer();
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        addTeacherButton=(ImageButton) findViewById(R.id.addTeacher) ;
        deleteTeacherButton=(Button) findViewById(R.id.deleteTeacher);

        deleteTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(AdminSeeTeacher.this,DeleteTeacherActivity.class);
                startActivity(intent);
            }
        });

        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(AdminSeeTeacher.this,AddTeacherActivity.class);
                startActivity(intent);
            }
        });



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

    private void addTeachers(String name){

        Dbutton = new Button(this);
        Dbutton.setText(name);
        Dbutton.setOnClickListener(getOnClickDoSomething(Dbutton));
        linearLayout.addView(Dbutton);
    }
    View.OnClickListener getOnClickDoSomething(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                intent= new Intent(AdminSeeTeacher.this,AdminAssignTeacherClasss.class);
                intent.putExtra("Teacher_Name", button.getText().toString());
                startActivity(intent);
//                Toast.makeText(getApplicationContext(),button.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void fetchFromDataBAse(){

        try{
            Cursor cursor = db.rawQuery("SELECT NAME FROM CUSERS WHERE TYPE='teacher' ", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    String tname = cursor.getString(0);
                    addTeachers(tname);
                    buffer.append("name = " + tname);
                } while (cursor.moveToNext());
//                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
        }

    }

}
