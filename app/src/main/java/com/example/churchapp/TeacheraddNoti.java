package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TeacheraddNoti extends AppCompatActivity {

    DbHandler mydb;
    SQLiteDatabase db ;
    Button broadcastNoti;
    EditText commentNotiT;
    String formattedDate ,selectClass,teacherName;
    Spinner spinner;

    List<String> arraySpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacheradd_noti);

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        teacherName = prefs.getString("name", "No type defined");

        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);

        spinner=(Spinner) findViewById(R.id.spinerSeeClass);

        arraySpinner = new ArrayList<String>();
        fetchTeacherClasses();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectClass = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        commentNotiT=(EditText) findViewById(R.id.commentNotiTeacher);
        broadcastNoti=(Button) findViewById(R.id.sendNotificationT);

        broadcastNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    mydb.insertTeacherNotification(teacherName,commentNotiT.getText().toString(),selectClass,formattedDate,db);
                    finish();
                    Toast.makeText(getApplicationContext(),"Notification is broadcast to "+selectClass,Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void fetchTeacherClasses(){
        try{
            Cursor cursor = db.rawQuery("SELECT CNAME FROM TEACHERCLASS WHERE TNAME='"+teacherName+"'", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    arraySpinner.add(cursor.getString(0));
//                    addClass(tname);
//                    buffer.append("name = " + tname);
                } while (cursor.moveToNext());
//                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
            }
            else{
//                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
//            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }
}
