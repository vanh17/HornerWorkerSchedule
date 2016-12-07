package com.example.enviro.completebitsandpizzas;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShiftFragment extends ListFragment {

    private int iD;
    private SQLiteDatabase db;
    private Cursor cursor;
    private static String WORKER_ID = "WORKER_ID";
//    private ArrayList<Shift> currentShifts = new ArrayList<Shift>();
//    private  Shift item;

    public ShiftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Shift[] shifts = Shift.shifts;
//        int size = Shift.shifts.length;
//        for (int i = 0; i < size; i++) {
//            Shift item = shifts[i];
//            if (item.getCurrentWorker() == iD || (item.getCurrentWorker() == -1 && item.getOriginalWorker() == iD)) {
//                currentShifts.add(item);
//            }
//        }
//        ArrayAdapter<Shift> adapter = new ArrayAdapter<Shift>(
//                inflater.getContext(),
//                android.R.layout.simple_list_item_1,
//                currentShifts);
        iD = ((MainActivity) getActivity()).getWorker_id();
        Log.i("CURRENT", Integer.toString(iD));
        try {
            SQLiteOpenHelper hornerDatabaseHelper = new HornerWorkerDatabaseHelper(getActivity());
            db = hornerDatabaseHelper.getReadableDatabase();

            cursor = db.query("SHIFTS",
                             new String[] {"_id", "YEAR", "MONTH", "DATE", "HOUR", "MINUTE", "ORIGINAL", "CURRENT"},
                             "CURRENT = ? OR (ORIGINAL = ? AND CURRENT = ?)",
                             new String[] {Integer.toString(iD), Integer.toString(iD), Integer.toString(-1)},
                             null, null, null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(getActivity(),
                                             android.R.layout.simple_list_item_2,
                                             cursor,
                                             new String[] {"YEAR", "MONTH", "DATE", "HOUR", "MINUTE", "ORIGINAL","CURRENT"},
                                             new int[] {android.R.id.text1, android.R.id.text2},
                                             0);
            listAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor1, int columnIndex) {
                    if (view.getId() == android.R.id.text1)  {
                        Log.i("YEAR", Integer.toString(cursor1.getColumnIndex("YEAR")));
                        Log.i("MONTH", Integer.toString(cursor1.getColumnIndex("MONTH")));
                        int year = cursor1.getInt(cursor1.getColumnIndex("YEAR"));
                        int month = cursor1.getInt(cursor1.getColumnIndex("MONTH"));
                        int date = cursor1.getInt(cursor1.getColumnIndex("DATE"));
                        int hour = cursor1.getInt(cursor1.getColumnIndex("HOUR"));
                        int minute = cursor1.getInt(cursor1.getColumnIndex("MINUTE"));
                        Date time = new Date(year, month, date, hour, minute);
                        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                        String dateS = df.format(time);
                        ((TextView) view).setText(dateS);
                        // grab the values from firstName and lastName columns
                        // Build your string
                        // set your view as appropriate
                        return true;
                    } else if (view.getId() == android.R.id.text2) {
                        Log.i("ORIGINAL", Integer.toString(cursor1.getColumnIndex("ORIGINAL")));
                        Log.i("CURRENT", Integer.toString(cursor1.getColumnIndex("CURRENT")));
                        Log.i("TOTAL", Integer.toString(cursor1.getColumnCount()));
                        String workers = getWorker(cursor1.getInt(cursor1.getColumnIndex("ORIGINAL")), cursor1.getInt(cursor1.getColumnIndex("CURRENT")));
                        ((TextView) view).setText(workers);
                        return true;
                    }
                    return false;
                }
            });
            setListAdapter(listAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private String getWorker(int origin, int current) {
        String workers ="";
        if (origin == -1 && current == -1) {
            workers = "Orgin: None, Current: None";
        } else if (origin != -1) {
            SQLiteOpenHelper hornerDatabaseHelper = new HornerWorkerDatabaseHelper(getActivity());
            SQLiteDatabase db1 = hornerDatabaseHelper.getReadableDatabase();

            Cursor cursor1 = db1.query("WORKERS",
                    new String[] {"_id", "NAME"},
                    "_id = ?",
                    new String[] {Integer.toString(origin)},
                    null, null, null);
            Log.i("NAME", Integer.toString(cursor1.getColumnIndex("NAME")));
            Log.i("_id", Integer.toString(cursor1.getColumnIndex("_id")));
            Log.i("TOTAL", Integer.toString(cursor1.getColumnCount()));
            String org ="";
            if (cursor1.moveToFirst()) {
                org = cursor1.getString(cursor1.getColumnIndex("NAME"));
            }
            if (current == -1) {
                workers = "Orgin: " + org + ", Current: You are giving up this shift";
            } else {
                workers = "Orgin: " + org + ", Current: " + org;
            }
            cursor1.close();
        }
        return workers;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        item = currentShifts.get(position);
        final int pos = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.give)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        giveUpShift(pos);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void giveUpShift(int position) {
//        item.setCurrentWorker(-1);
        cursor.moveToPosition(position);
        ContentValues giveShift = new ContentValues();
        giveShift.put("CURRENT", -1);
        int row = db.update("SHIFTS", giveShift, "_id = ?", new String[]{Integer.toString(cursor.getInt(0))});
        if (row == 1) {
            Toast.makeText(getActivity(), "Successfully Giving Shift", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(),MainActivity.class);
            intent.putExtra(WORKER_ID, iD);
            startActivity(intent);
        }
    }
}
