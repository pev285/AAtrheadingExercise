package ru.pe9.android.myapplication20180423;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NewThreadActivity extends AppCompatActivity {

    Thread thread = null;

    TextView resultsTextView;
    TextView statusTextView;

//    private boolean threadRunningNow = false;

    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, NewThreadActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thred);

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

    } // onCreate() //


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (thread != null) {
            thread.interrupt();
        }
    }

    private void createBackgroundThread() {
        if(thread == null) {
            thread = new CustomThread(resultsTextView, new MyCallback<Boolean>() {
                @Override
                public void callback(Boolean val) {
//                    threadRunningNow = false;
                    thread = null;
                    if (val) {
                        statusTextView.setText("thread finished");
                    }
                    else {
                        statusTextView.setText("thread was interrupted");
                    }
                }
            });

            statusTextView.setText("Thread is created");
        }
        else {
            Toast.makeText(this, "One thread at a time, please", Toast.LENGTH_SHORT).show();
        }
    }

    private void startBackgroundThread() {
        if (thread != null && !thread.isAlive()) {
            thread.start();

            statusTextView.setText("Thread is started");
        }
        else {
            if (thread != null) {
                Toast.makeText(this, "Can't start one thread twice", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "There is no thread to start", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void cancelBackgroundThread() {

        if (thread != null && thread.isAlive()) {
            thread.interrupt();

            thread = null;
            statusTextView.setText("Thread is interrupted");
        }
        else {
            Toast.makeText(this, "There is no thread to interrupt", Toast.LENGTH_SHORT).show();
        }
    }

} // End of class //

