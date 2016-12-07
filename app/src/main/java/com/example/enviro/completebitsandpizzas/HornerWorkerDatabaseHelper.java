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
    private static final int DB_VERSION = 2; //the version of the database

    HornerWorkerDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            //Query to create table shifts
            String shiftQuery = "CREATE TABLE SHIFTS (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "YEAR INTEGER, "
                    + "MONTH INTEGER, "
                    + "DATE INTEGER, "
                    + "HOUR INTEGER, "
                    + "MINUTE INTEGER, "
                    + "ORIGINAL INTEGER,"
                    + "CURRENT INTEGER) ;";

            //Query to create table workers
            String workerQuery = "CREATE TABLE WORKERS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT,"
                    + "USERNAME TEXT,"
                    + "PASSWORD TEXT, "
                    + "PERMISSION INTEGER) ;";

            //Execute query to create shifts
            db.execSQL(shiftQuery);
            //Execute query to create shifts
            db.execSQL(workerQuery);

            //Populate shifts table
            populateShifts(db);

            //Populate workers table
            populateWorkers(db);

        }
    }

    private void populateShifts(SQLiteDatabase db) {
        insertShift(db, 116, 11, 7, 0, 0, 1, 1);
        insertShift(db, 116, 11, 10, 7, 0, 1, 1);
        insertShift(db, 116, 11, 11, 12, 30, 1, 1);
        insertShift(db, 116, 11, 12, 17, 30, 1, -1);
        insertShift(db, 116, 11, 16, 20, 30, 1, -1);
        insertShift(db, 116, 11, 17, 22, 45, 1, -1);
        insertShift(db, 116, 11, 12, 17, 30, 2, 2);
        insertShift(db, 116, 11, 16, 20, 30, 2, 2);
        insertShift(db, 116, 11, 17, 22, 45, 2, 2);
        insertShift(db, 116, 11, 18, 12, 15, 3, 3);
        insertShift(db, 116, 11, 19, 14, 35, 3, 3);
        insertShift(db, 116, 11, 20, 15, 55, 4, 4);
        insertShift(db, 116, 11, 21, 17, 30, 4, -1);
        insertShift(db, 116, 11, 22, 5, 45, 3, -1);
        insertShift(db, 116, 11, 23, 7, 0, 2, -1);
        insertShift(db, 116, 11, 24, 12, 30, 5, -1);
        insertShift(db, 116, 11, 25, 17, 30, 4, 4);
        insertShift(db, 116, 11, 26, 20, 30, 4, 4);
        insertShift(db, 116, 11, 27, 22, 45, 1, 1);
        insertShift(db, 116, 11, 28, 12, 15, 2, 2);
        insertShift(db, 116, 11, 29, 14, 35, 3, 3);
        insertShift(db, 116, 11, 30, 15, 55, 4, 4);
        insertShift(db, 116, 11, 27, 22, 45, 4, 4);
        insertShift(db, 116, 11, 28, 12, 15, 3, 3);
        insertShift(db, 116, 11, 29, 14, 35, 2, 3);
        insertShift(db, 116, 11, 30, 15, 55, 1, -1);
        insertShift(db, 116, 11, 8, 17, 30, 5, 5);
        insertShift(db, 116, 11, 28, 22, 45, 1, -1);
        insertShift(db, 116, 11, 29, 12, 15, 2, -1);
        insertShift(db, 116, 11, 30, 14, 35, 3, -1);
        insertShift(db, 116, 11, 1, 15, 55, 4, -1);
        insertShift(db, 116, 11, 2, 17, 30, 5, 5);
    }

    private void populateWorkers(SQLiteDatabase db) {
        insertWorker(db, "Hoang Van", "vanh17", "vanh17pass", 0);
        insertWorker(db, "Carly Bentz", "bentzc17", "bentzc17pass", 0);
        insertWorker(db, "Courtney Bauchana", "bauchana18", "bauchana18pass", 0);
        insertWorker(db, "Mike Braun", "braunm17", "braunm17pass", 0);
        insertWorker(db, "Molie Morder", "morderm20", "morder20pass", 0);
        insertWorker(db, "Kylie Hawk", "hawkk17", "hawkk17pass", 1);
        insertWorker(db, "Molly Jones", "jonesm", "jonesmpass", 1);
    }

    private static void insertShift(SQLiteDatabase db, int year, int month, int date, int hour, int minute, int original, int current) {
        ContentValues shiftValue = new ContentValues();
        shiftValue.put("YEAR", year);
        shiftValue.put("MONTH", month);
        shiftValue.put("DATE", date);
        shiftValue.put("HOUR", hour);
        shiftValue.put("MINUTE", minute);
        shiftValue.put("ORIGINAL", original);
        shiftValue.put("CURRENT", current);
        db.insert("SHIFTS", null, shiftValue);
    }

    private static void insertWorker(SQLiteDatabase db, String name, String username, String password, int permission) {
        ContentValues workerValue = new ContentValues();
        workerValue.put("NAME", name);
        workerValue.put("USERNAME", username);
        workerValue.put("PASSWORD", password);
        workerValue.put("PERMISSION", permission);
        db.insert("WORKERS", null, workerValue);
    }
}
