package com.example.churchapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {
    private static String DB_name="church";

    public DbHandler(Context context) {
        super(context, DB_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE="CREATE TABLE CHURCH (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,TYPE TEXT,PASSWORD TEXT)";
        db.execSQL(SQL_CREATE);
        insertEntry("Jhon","student", "123",db);
        insertEntry("Adam","teacher", "123",db);
        insertEntry("Sarah","admin", "123",db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String SQL_DELETE="DROP TABLE IF EXISTS CHURCH";
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }

    public void insertEntry(String Name, String Type, String Password, SQLiteDatabase db){
        ContentValues values= new ContentValues();
        values.put("NAME", Name);
        values.put("TYPE",Type);
        values.put("PASSWORD",Password);
        db.insert(" CHURCH",null,values);
    }
}
