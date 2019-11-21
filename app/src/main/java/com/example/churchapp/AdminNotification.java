package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminNotification extends AppCompatActivity {

    DbHandler mydb;
    SQLiteDatabase db ;
    Button broadcastNoti;
    EditText commentNoti;
    String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification);

        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);

        commentNoti=(EditText) findViewById(R.id.commentNoti);
        broadcastNoti=(Button) findViewById(R.id.sendNotification);

        broadcastNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                        mydb.insertAdminNotification("admin",commentNoti.getText().toString(),formattedDate,db);
                        finish();
                        Toast.makeText(getApplicationContext(),"Notification is broadcast",Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                }

            }
        });




    }
}
