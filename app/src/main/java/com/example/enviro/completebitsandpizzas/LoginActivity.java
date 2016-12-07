package com.example.enviro.completebitsandpizzas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity  {
    Button login, signup;
    EditText user,pass;
    private SQLiteDatabase db;
    private Cursor cursor;
    private static String WORKER_ID = "WORKER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.login);
        user = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);

//        signup = (Button)findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(authentication(user.getText().toString(), pass.getText().toString())) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                    int worker_id = cursor.getInt(0);
                    cursor.close();
                    Log.i("WORKER_ID", "*******"+ Integer.toString(worker_id));
                    Intent intent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    intent.putExtra(WORKER_ID, worker_id);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean authentication(String username, String password) {
        SQLiteOpenHelper hornerDatabaseHelper = new HornerWorkerDatabaseHelper(this);
        db = hornerDatabaseHelper.getReadableDatabase();

        cursor = db.query("WORKERS",
                new String[] {"_id"},
                "USERNAME = ? AND PASSWORD = ?",
                new String[] {username, password},
                null, null, null);
        boolean result = cursor.moveToFirst();
        Log.i("CURSOR","GET CALLLLLLLLL**********");
        return result;
    }
}