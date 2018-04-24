package ru.pe9.android.myapplication20180423;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;



public class HandlerMessagingCustomThread extends Thread {

    private TextView fieldForResults;
    private MyCallback<Boolean> callback;

    private Handler handler;

    private boolean stopped = false;

    public HandlerMessagingCustomThread(TextView fieldForResults/*, TextView statusStringView*/,
                                        MyCallback<Boolean> callback) {
        this.fieldForResults = fieldForResults;
        this.callback = callback;
        handler = new Handler(Looper.getMainLooper());
    }

    private class StringPublisher implements Runnable {

        private String text;
        private TextView view;

        public StringPublisher(TextView view, String str) {
            this.view = view;
            this.text = str;
        }

        @Override
        public void run() {
            view.setText(text);
        }
    }


    int counter = 0;

    @Override
    public void run() {

        counter = 0;
        boolean wasInterrupted = false;

        try {
            while((counter < 10 && !isInterrupted()) && !stopped) {
                counter++;
                // "#counter" //
                handler.post( new StringPublisher(fieldForResults, Integer.toString(counter)));

                sleep(500);
            }
        }
        catch(InterruptedException ie) {
            wasInterrupted = true;
        }

        if ((!isInterrupted() && !wasInterrupted) && ! stopped) {
            handler.post(new StringPublisher(fieldForResults, "Done"));

            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.callback(true);
                }
            });
        }
        else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.callback(false);
                }
            });
        }

    }

    public void stopThread() {
        stopped = true;
    }
}
