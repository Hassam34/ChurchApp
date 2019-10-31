package com.example.churchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteClassActivity extends AppCompatActivity {
    EditText className;
    Button deleteButton;
    DbHandler mydb;
    SQLiteDatabase db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_class);
        className= (EditText) findViewById(R.id.dUsernameClass);
        deleteButton=(Button) findViewById(R.id.dclass);
        mydb = new DbHandler(this);
        db = mydb.getReadableDatabase();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String username=className.getText().toString();
                    db.execSQL(" DELETE FROM CLASSES WHERE CNAME='"+username+"'");
                    finish();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Enter Valid Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
