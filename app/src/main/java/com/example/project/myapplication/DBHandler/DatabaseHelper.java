package com.example.project.myapplication.DBHandler;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;


import com.example.project.myapplication.Model.Users;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Places.db";

    //Users table
    public static final String TABLE_USERS = "users_table";
    public static final String COL_ID = "ID";
    public static final String COL_FNAME = "FIRSTNAME";
    public static final String COL_LNAME = "LASTNAME";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_PASSWORD = "PASSWORD";
    public static final String COL_ROLE = "ROLE";




    public static final String DB_TABLE="Task";
    public static final String DB_COLUMN = "TaskName";

   /* public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VER);
    }

*/

    //City Table
    /*public static final String TABLE_CITY = "city_table";
    public static final String CITY_ID = "ID";
    public static final String CITY_NAME = "NAME";
    */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 12);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_USERS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME TEXT," +
                "LASTNAME TEXT,EMAIL TEXT UNIQUE,PASSWORD TEXT,ROLE TEXT)");

        db.execSQL("create table " + DB_TABLE + " (DB_COLUMN Text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);


        onCreate(db);
    }


    public Cursor login(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE EMAIL = '" + email +
                "' AND PASSWORD = '" + password + "';";
        Log.i("Mera", query);
        Cursor res = db.rawQuery(query, null);
        return res;
    }
    public boolean insertUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FNAME, user.getFIRSTNAME());
        contentValues.put(COL_LNAME, user.getLASTNAME());
        contentValues.put(COL_EMAIL, user.getEMAIL());
        contentValues.put(COL_PASSWORD, user.getPASSWORD());
        contentValues.put(COL_ROLE, user.getROLE());
        long result = db.insert(TABLE_USERS, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertNewTask(String task){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN,task);
        long result=db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        if (result == -1)
            return false;
        else
            return true;

    }

    public void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN + " = ?",new String[]{task});
        db.close();
    }

    public ArrayList<String> getTaskList(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COLUMN},null,null,null,null,null);
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COLUMN);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return taskList;
    }

}