package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StudentSeeHisClass extends AppCompatActivity {
    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    Intent intent;
    Button Dbutton;
    String name;
    LinearLayout linearLayout;
    TextView classStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_see_his_class);
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String type = prefs.getString("type", "No type defined");
        name = prefs.getString("name", "No type defined");
        buffer= new StringBuffer();
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        linearLayout = (LinearLayout) findViewById(R.id.showClasesStudent);
        classStatus=(TextView) findViewById(R.id.classesStatus);

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
                if(!button.getText().toString().equals("No Class")){
                    intent= new Intent(StudentSeeHisClass.this,StudentSeeDateWiseAttendence.class);
                    intent.putExtra("Class_Name", button.getText().toString());
                    startActivity(intent);
                }

            }
        };
    }
    private void fetchFromDataBAse(){
        try{
            Cursor cursor = db.rawQuery("SELECT CNAME FROM STUDENTCLASS WHERE SNAME='"+name+"'", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    String tname = cursor.getString(0);
                    addClass(tname);
//                    Toast.makeText(getApplicationContext(),tname,Toast.LENGTH_SHORT).show();
                    buffer.append("name = " + tname);
                } while (cursor.moveToNext());
//                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            classStatus.setText("You are enroll in no Class Yet");
//            Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
        }
    }
}
