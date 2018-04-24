package ru.pe9.android.myapplication20180423;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AsynctaskImitationUsingThreadActivity extends AppCompatActivity {

    TextView resultsTextView;
    TextView statusTextView;

    MyAsyncTaskImitationChild asyncTask = null;

    private final String LAST_VALUE_KEY = "LAST_VALUE_KEY";


    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, AsynctaskImitationUsingThreadActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (asyncTask != null && asyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            try {
                String val = resultsTextView.getText().toString();
                int intVal = Integer.parseInt(val);
                outState.putInt(LAST_VALUE_KEY, intVal);
            }
            catch (NumberFormatException e) {}
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int startingNum = savedInstanceState.getInt(LAST_VALUE_KEY, -1);

        if (startingNum >= 0) {
            asyncTask = new MyAsyncTaskImitationChild(this, resultsTextView, statusTextView, startingNum);
            asyncTask.execute();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thred);

        initializeViews();

    } // onCreate() //

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (asyncTask != null) {
            asyncTask.cancel();
            asyncTask = null;
        }
    }

    private void initializeViews() {
        resultsTextView = findViewById(R.id.resultsTextView);
        statusTextView = findViewById(R.id.statusTextView);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBackgroundThread();
            }
        });

        Button createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBackgroundThread();
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelBackgroundThread();;
            }
        });
    }


    private void createBackgroundThread() {
        if (asyncTask == null || asyncTask.getStatus() == AsyncTask.Status.FINISHED) {
            asyncTask = new MyAsyncTaskImitationChild(this, resultsTextView, statusTextView);
        } else {
            Toast.makeText(this, "Wa already have an asyncTask", Toast.LENGTH_SHORT).show();
        }
    }

    private void startBackgroundThread() {
        if (asyncTask != null && asyncTask.getStatus() == AsyncTask.Status.PENDING) {
            asyncTask.execute();
        } else if (asyncTask != null && asyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            Toast.makeText(this, "AsyncTask is already running", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "There is no asyncTask to start", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelBackgroundThread() {
        if (asyncTask != null) {
            if (asyncTask.getStatus() == AsyncTask.Status.RUNNING) {
                asyncTask.cancel();
                asyncTask = null;
            } else {
                Toast.makeText(this, "Task is not running", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "There is no asyncTask to cancel", Toast.LENGTH_SHORT).show();
        }
    }


}
