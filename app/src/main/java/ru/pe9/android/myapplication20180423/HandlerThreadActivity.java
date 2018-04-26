package ru.pe9.android.myapplication20180423;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HandlerThreadActivity extends AppCompatActivity {

    TextView resultsTextView;
    TextView statusTextView;

    HandlerThread handlerThread = null;
    Handler handler = null;

    List<HandlerMessagingCustomThread> threadsList = new ArrayList<HandlerMessagingCustomThread>();

    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, HandlerThreadActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thred);

        initializeViews();

        handlerThread = new HandlerThread("My Handler Thread");

        handlerThread.start();


    } // onCreate() //

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handlerThread.quit();
        handler = null;
        handlerThread = null;
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
        if (handlerThread != null && handler == null) {
            Looper looper = handlerThread.getLooper();
            handler = new Handler(looper);

            statusTextView.setText("New handler created");
        }
        else {
            Toast.makeText(this, "Handler already exists", Toast.LENGTH_SHORT).show();
        }
    }

    private void startBackgroundThread() {


        if (handler != null) {

            HandlerMessagingCustomThread myNewThread = new HandlerMessagingCustomThread(resultsTextView, new MyCallback<Boolean>() {
                @Override
                public void callback(Boolean val) {

                }
            });

            threadsList.add(myNewThread);

            handler.post(myNewThread);

            statusTextView.setText("New thread is started");
        }
        else {
            Toast.makeText(this, "There is no Handler to past thread to", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelBackgroundThread() {

//        handler.removeMessages();

        if (handler != null && threadsList.size() > 0) {
            for(HandlerMessagingCustomThread r : threadsList) {
                r.stopThread();
                r.interrupt();
                handler.removeCallbacks(r);
            }

            threadsList.clear();

            //handler.removeCallbacksAndMessages(null);

            statusTextView.setText("Tasks queue is cleared");
        }
        else
        {
                Toast.makeText(this, "Can't stop threads. There is no running threads or handler", Toast.LENGTH_SHORT).show();
        }
    }


} // end of class //
