package com.example.enviro.completebitsandpizzas;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.ActionBar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class OrderActivity extends Activity {

    private TextView tvDisplayDate;
    private TextView tvDisplayTime;
    private Button btnChangeDate;
    private static String WORKER_ID = "WORKER_ID";

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int iD;


    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        if (savedInstanceState != null) {
            iD = savedInstanceState.getInt(WORKER_ID);
        } else {
            iD = getIntent().getIntExtra(WORKER_ID, -2);
        }
        Log.i("Order", Integer.toString(iD));
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        setCurrentDateOnView();
        addListener();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(WORKER_ID, iD);
    }

    // display current date
    public void setCurrentDateOnView() {

        tvDisplayDate = (TextView) findViewById(R.id.tvDate);
        tvDisplayTime = (TextView) findViewById(R.id.tvTime);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // set current date into textview
        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
        tvDisplayTime.setText(new StringBuilder()
                .append(hour).append(":")
                .append(minute).append(" "));

    }

    public void addListener() {
        addListenerOnDateView();
        addListenerOnTimeView();
        addListenerOnButton();
    }

    public void addListenerOnDateView() {

        tvDisplayDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

    }

    public void addListenerOnTimeView() {

        tvDisplayTime.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(TIME_DIALOG_ID);

            }

        });

    }

    public void addListenerOnButton() {

        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);

        btnChangeDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                shiftTrader();

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, timePickerListener,
                        hour, minute, true);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet (TimePicker view, int selectedHour,
                               int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;

            Date date = new Date(year - 1900, month, day, hour, minute);
            DateFormat df = new SimpleDateFormat("HH:mm");
            tvDisplayTime.setText(df.format(date));
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            Date date = new Date(year - 1900, month, day);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            tvDisplayDate.setText(df.format(date));
        }
    };

    public void shiftTrader() {
        SQLiteDatabase db = (new HornerWorkerDatabaseHelper(this)).getReadableDatabase();
        ContentValues shift = new ContentValues();
        shift.put("CURRENT", -1);
        int row = db.update("SHIFTS", shift, "ORIGINAL = ? AND YEAR = ? AND MONTH = ? AND DATE = ? AND HOUR = ? AND MINUTE = ?",
                new String[] {Integer.toString(iD), Integer.toString(year-1900), Integer.toString(month), Integer.toString(day), Integer.toString(hour), Integer.toString(minute)});
        Date date = new Date(year - 1900, month, day, hour, minute);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        String dateS = df.format(date);
        if (row == 1) {
            Toast.makeText(getApplicationContext(), "Successfully Giving\n at " + dateS, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(WORKER_ID, iD);
            startActivity(intent);
            return;
        }
        Toast.makeText(getApplicationContext(), "No Shift Found", Toast.LENGTH_SHORT).show();
    }
}
