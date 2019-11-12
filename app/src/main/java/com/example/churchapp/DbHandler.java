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
        String SQL_CREATE_CLASSES="CREATE TABLE CLASSES (ID INTEGER PRIMARY KEY AUTOINCREMENT,CNAME TEXT, Time Text)";
        String SQL_CREATE_SUBJECTS="CREATE TABLE SUBJECTS (ID INTEGER PRIMARY KEY AUTOINCREMENT,SNAME TEXT, SCODE TEXT)";
        String SQL_TEACHER_CLASSES="CREATE TABLE TEACHERCLASS (ID INTEGER PRIMARY KEY AUTOINCREMENT,TNAME TEXT, CNAME TEXT)";
        String SQL_STUDENT_CLASSES="CREATE TABLE STUDENTCLASS (ID INTEGER PRIMARY KEY AUTOINCREMENT,SNAME TEXT, CNAME TEXT)";
        String ATTENDENCE="CREATE TABLE STUDENTATTENDENCE (ID INTEGER PRIMARY KEY AUTOINCREMENT, SNAME TEXT, TNAME TEXT, CNAME TEXT,DATE TEXT,STATUS TEXT,COMMENTS TEXT)";


        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_CLASSES);
        db.execSQL(SQL_CREATE_SUBJECTS);
        db.execSQL(SQL_TEACHER_CLASSES);
        db.execSQL(SQL_STUDENT_CLASSES);
        db.execSQL(ATTENDENCE);


        insertUSERS("Mukesh","student", "123",db);
        insertUSERS("Yadav","student", "123",db);
        insertUSERS("Virat","student", "123",db);


        insertUSERS("Jaskiran","teacher", "123",db);
        insertUSERS("Bhargavi","teacher", "123",db);
        insertUSERS("Anushka","teacher", "123",db);

        insertTeacherClass("Jaskiran","No Class",db);
        insertTeacherClass("Bhargavi","No Class",db);
        insertTeacherClass("Anushka","No Class",db);

        insertUSERS("Bhavdeep","admin", "123",db);

        insertCLASSES("Class 1","9 to 10",db);
        insertCLASSES("Class 2","10 to 11",db);
        insertCLASSES("Class 3","11 to 12",db);

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
    public void isertAttendence(String Sname,String Tname, String Cname, String Date,String Status, String Comments, SQLiteDatabase db){
        ContentValues values= new ContentValues();
        values.put("SNAME", Sname);
        values.put("Tname", Tname);
        values.put("CNAME", Cname);
        values.put("DATE", Date);
        values.put("STATUS", Status);
        values.put("COMMENTS", Comments);
        db.insert("STUDENTATTENDENCE ",null,values);
    }
    public void insertStudentClass(String Sname,String CName, SQLiteDatabase db){
        ContentValues values= new ContentValues();
        values.put("SNAME", Sname);
        values.put("CNAME", CName);
        db.insert(" STUDENTCLASS",null,values);
    }
    public void insertTeacherClass(String Tname,String CName, SQLiteDatabase db){
        ContentValues values= new ContentValues();
        values.put("TNAME", Tname);
        values.put("CNAME", CName);
        db.insert(" TEACHERCLASS",null,values);
    }

    public void updateTeacherClass(String Tname,String CName, SQLiteDatabase db){
        ContentValues values= new ContentValues();
        values.put("CNAME", CName);
        db.update(" TEACHERCLASS",values, "TNAME='"+Tname+"'",null);
    }

    public void insertCLASSES(String CName,String Time, SQLiteDatabase db){
        ContentValues values= new ContentValues();
        values.put("CNAME", CName);
        values.put("TIME", Time);

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
