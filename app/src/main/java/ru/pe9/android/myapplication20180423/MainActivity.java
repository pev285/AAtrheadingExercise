package ru.pe9.android.myapplication20180423;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

    } // onCreate //

    private void initializeViews() {
        Button button1 = findViewById(R.id.buttonSimpleThread);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewThreadActivity.startThisActivity(v.getContext());
            }
        });

        Button buttonHandlerThread = findViewById(R.id.buttonHandlerThread);
        buttonHandlerThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerThreadActivity.startThisActivity(v.getContext());
            }
        });

        Button asyncTaskButton = findViewById(R.id.asyncTastButton);
        asyncTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskActivity.startThisActivity(v.getContext());
            }
        });

        Button asyncTaskThreadImitationButton = findViewById(R.id.asyncTaskImitationButton);
        asyncTaskThreadImitationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsynctaskImitationUsingThreadActivity.startThisActivity(v.getContext());
            }
        });

//        Button button4 = findViewById(R.id.button4);
//        button4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ///////////////////////////////////
//            }
//        });
    }

} // end of class //

