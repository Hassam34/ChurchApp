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

public class AdminSeeClasses extends AppCompatActivity {
    String Querry;
    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    Intent intent;
    Button Dbutton, addClassButton, deleteClassButton;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_see_classes);
        buffer= new StringBuffer();
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutclass);
        addClassButton=(Button) findViewById(R.id.addClasses) ;
        deleteClassButton=(Button) findViewById(R.id.deleteClasses);

        deleteClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(AdminSeeClasses.this,DeleteClassActivity.class);
                startActivity(intent);
            }
        });

        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(AdminSeeClasses.this,AddClassActivity.class);
                startActivity(intent);
            }
        });    }
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
        linearLayout.addView(Dbutton);

    }
    private void fetchFromDataBAse(){

        try{
            Cursor cursor = db.rawQuery("SELECT CNAME FROM CLASSES", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    String tname = cursor.getString(0);
                    addClass(tname);
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
