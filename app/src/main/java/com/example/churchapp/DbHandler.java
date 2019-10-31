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
        String SQL_CREATE_USERS="CREATE TABLE CUSERS (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,TYPE TEXT,PASSWORD TEXT)";
        String SQL_CREATE_CLASSES="CREATE TABLE CLASSES (ID INTEGER PRIMARY KEY AUTOINCREMENT,CNAME TEXT)";
        String SQL_CREATE_SUBJECTS="CREATE TABLE SUBJECTS (ID INTEGER PRIMARY KEY AUTOINCREMENT,SNAME TEXT, SCODE TEXT)";

        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_CLASSES);
        db.execSQL(SQL_CREATE_SUBJECTS);

        insertUSERS("Mukesh","student", "123",db);
        insertUSERS("Yadav","student", "123",db);
        insertUSERS("Virat","student", "123",db);


        insertUSERS("Jaskiran","teacher", "123",db);
        insertUSERS("Bhargavi","teacher", "123",db);
        insertUSERS("Anushka","teacher", "123",db);

        insertUSERS("Bhavdeep","admin", "123",db); //Try to Login with This

        insertCLASSES("Class 1",db);
        insertCLASSES("Class 2",db);
        insertCLASSES("Class 3",db);

        insertSUBJECTS("English","E-101",db);
        insertSUBJECTS("History","E-102",db);
        insertSUBJECTS("Economics","E-103",db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String SQL_DELETE="DROP TABLE IF EXISTS CUSERS";
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }

    public void insertCLASSES(String CName, SQLiteDatabase db){
        ContentValues values= new ContentValues();
        values.put("CNAME", CName);

        db.insert(" CLASSES",null,values);
    }

    public void insertSUBJECTS(String SName, String SCode, SQLiteDatabase db){
        ContentValues values= new ContentValues();
        values.put("SNAME", SName);
        values.put("SCODE",SCode);
        db.insert(" SUBJECTS",null,values);
    }

    public void insertUSERS(String Name, String Type, String Password, SQLiteDatabase db){
        ContentValues values= new ContentValues();
        values.put("NAME", Name);
        values.put("TYPE",Type);
        values.put("PASSWORD",Password);
        db.insert(" CUSERS",null,values);
    }
}
