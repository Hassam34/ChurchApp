package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteTeacherActivity extends AppCompatActivity {
    EditText user, pass;
    Button deleteButton;
    DbHandler mydb;
    SQLiteDatabase db ;
    CheckBox checkBox;
    LinearLayout linearLayout;
    List<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_teacher);
        list = new ArrayList<String>();
        checkBox = new CheckBox(this);

        deleteButton=(Button) findViewById(R.id.dteacher);
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        linearLayout=(LinearLayout) findViewById(R.id.lineardeleteTeacher);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    for (int i=0;i<list.size();i++)
                    {
                        db.execSQL(" DELETE FROM CUSERS WHERE NAME='"+list.get(i)+"'");
                    }

                    finish();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }
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


    private void addTeachers(String name, int i){

        checkBox = new CheckBox(this);
        checkBox.setText(name);
        checkBox.setId(i);
        checkBox.setOnClickListener(getOnClickDoSomething(checkBox));
        linearLayout.addView(checkBox);

    }
    View.OnClickListener getOnClickDoSomething(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                if(list.size() > 0) {
                    System.out.println("*************id******" + button.getId());
                    boolean getArrayIndex = list.contains(button.getText().toString());

                    if (getArrayIndex) {
                        list.remove(button.getText());
                    } else {
                        list.add(button.getText().toString());
                    }
                } else {
                    list.add(button.getText().toString());
                }

//                Toast.makeText(getApplicationContext(),list.toString(),Toast.LENGTH_SHORT).show();
                System.out.println("*************id******" + button.getId());
                System.out.println("and text***" + button.getText().toString());
            }
        };
    }

    private void fetchFromDataBAse(){

        try{
            int i=0;
            Cursor cursor = db.rawQuery("SELECT NAME FROM CUSERS WHERE TYPE='teacher' ", null);
            if(cursor!=null) {
                cursor.moveToFirst();
                do {
                    String tname = cursor.getString(0);
                    addTeachers(tname,i);
                    i++;
//                    buffer.append("name = " + tname);
                } while (cursor.moveToNext());
//                Toast.makeText(getApplicationContext(),buffer,Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"No Teacher avialable in database",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),"No records found",Toast.LENGTH_SHORT).show();
        }

    }


}
