package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddClassActivity extends AppCompatActivity {
    DbHandler mydb;
    SQLiteDatabase db ;
    StringBuffer buffer;
    Intent intent;
    Button addClassButton;
    EditText className;
    Spinner spinnerStart,spinnerEnd;
    List<String> listStart,listEnd;
    String spinStartVlaue, spinnerEndValue;
    String TimeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();
        listStart = new ArrayList<String>();
        listEnd = new ArrayList<String>();
        spinnerEnd=(Spinner) findViewById(R.id.spinnerEnd);
        spinnerStart=(Spinner)findViewById(R.id.spinStart);

        listStart.add("9");
        listStart.add("10");
        listStart.add("11");
        listStart.add("12");
        listStart.add("13");
        listStart.add("14");
        listStart.add("15");

        listEnd.add("10");
        listEnd.add("11");
        listEnd.add("12");
        listEnd.add("13");
        listEnd.add("14");
        listEnd.add("15");
        listEnd.add("16");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,listStart);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStart.setAdapter(adapter);
        spinnerStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinStartVlaue = parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,listEnd);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEnd.setAdapter(adapter1);
        spinnerEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerEndValue = parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        addClassButton= (Button) findViewById(R.id.ClassAdd);

        className=(EditText) findViewById(R.id.usernameClass);


        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String user=className.getText().toString();
                    TimeValue=spinStartVlaue+" to "+spinnerEndValue;
                    int start=Integer.parseInt(spinStartVlaue);
                    int end=Integer.parseInt(spinnerEndValue);
                    if(start<end){
                        mydb.insertCLASSES(user,TimeValue,db);
                        finish();
                        Toast.makeText(getApplicationContext(),"Class Added",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Invalid Entry",Toast.LENGTH_SHORT).show();

                    }

                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
