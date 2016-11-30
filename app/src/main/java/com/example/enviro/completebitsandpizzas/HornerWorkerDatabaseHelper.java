package com.example.enviro.completebitsandpizzas;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by Hoang on 11/30/2016.
 */

class HornerWorkerDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "hornerschedule"; //the name of the database
    private static final int DB_VERSION = 1; //the version of the database

    HornerWorkerDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Query to create table shifts
        String shiftQuery = "CREATE TABLE SHIFTS ("
                + "shift_id INTEGER PRIMARY KEY AUTOINCRMENT, "
                + "MONTH INTEGER, "
                + "DATE INTEGER, "
                + "HOUR INTEGER, "
                + "MINUTE INTEGER, "
                + "ORIGINAL INTEGER,"
                + "CURRENT INTEGER) ;";

        //Query to create table workers
        String workerQuery = "CREATE TABLE WORKERS ("
                + "worker_id INTEGER PRIMARY KEY AUTOINCRMENT, "
                + "NAME TEXT,"
                + "USERNAME TEXT,"
                + "PASSWORD TEXT) ;";

        //Execute query to create shifts
        db.execSQL(shiftQuery);
        //Execute query to create shifts
        db.execSQL(workerQuery);

        //Populate shifts table
        

        //Populate workers table

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertShift(SQLiteDatabase db, int month, int date, int hour, int minute, int original, int current) {
        ContentValues shiftValue = new ContentValues();
        shiftValue.put("MONTH", month);
        shiftValue.put("DATE", date);
        shiftValue.put("HOUR", hour);
        shiftValue.put("MINUTE", minute);
        shiftValue.put("ORIGINAL", original);
        shiftValue.put("CURRENT", current);
        db.insert("SHIFTS", null, shiftValue);
    }

    private static void insertWorker(SQLiteDatabase db, String name, String username, String password) {
        ContentValues workerValue = new ContentValues();
        workerValue.put("NAME", name);
        workerValue.put("USERNAME", username);
        workerValue.put("PASSWORD", password);
        db.insert("WORKERS", null, workerValue);
    }
}
