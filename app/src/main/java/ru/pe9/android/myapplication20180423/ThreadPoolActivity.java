package ru.pe9.android.myapplication20180423;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

public class ThreadPoolActivity extends AppCompatActivity {


    private TextView resultsTextView;
    private TextView statusTextView;

    private ExecutorService executorService = null;
    private Handler uiHandler = null;

    private static int threadsNumber = 0;

    private List<Future> futuresList = null;


    public static void StartThisActivity(Context context) {
        Intent intent = new Intent(context, ThreadPoolActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thred);


        uiHandler = new Handler(Looper.getMainLooper());
        futuresList = new ArrayList<Future>();

        initializeViews();

    } // onCreate() //

    @Override
    protected void onDestroy() {
        super.onDestroy();

        clearFuturesList();

        if (executorService != null) {
            executorService.shutdownNow();
            executorService = null;
        }
    } // onDestroy() //


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
    } // initializeViews() //


    private void createBackgroundThread() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(2);
        } else {
            Toast.makeText(this, "Executor service has already been created", Toast.LENGTH_SHORT).show();
        }
    }

    private void startBackgroundThread() {
        if (executorService != null) {
            Runnable runnable = new Runnable() {
                String threadNum = Integer.toString(threadsNumber++);
                @Override
                public void run() {

                    try {
                        for (int i = 1; i <= 10; i++) {
                            final int fi = i;
                            uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    resultsTextView.setText(Integer.toString(fi));
                                    statusTextView.setText("Thread #" + threadNum);
                                }
                            });
                            sleep(1000);
                        }
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                resultsTextView.setText("#" + threadNum + " is Done");
                            }
                        });
                    } catch (InterruptedException ie) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                statusTextView.setText("interrupted");
                            }
                        });
                    }
                }
            };
            Future future = executorService.submit(runnable);
            futuresList.add(future);
        } else {
            Toast.makeText(this, "Create new executor service", Toast.LENGTH_SHORT).show();
        }

    }

    private void cancelBackgroundThread() {
        if (executorService != null) {
//            executorService.shutdownNow();
//            executorService = null;

            clearFuturesList();

            Toast.makeText(this, "Tasks cancelled", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Create new executor service", Toast.LENGTH_SHORT).show();
        }
    }


    private void clearFuturesList() {
        for(int i = 0; i < futuresList.size(); i++) {
            futuresList.get(i).cancel(true);
        }
        futuresList.clear();
    }

} // end of class //

