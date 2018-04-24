package ru.pe9.android.myapplication20180423;

import android.widget.TextView;

public class CustomThread extends Thread {

    private TextView fieldForResults;
    private MyCallback<Boolean> callback;

    public CustomThread(TextView fieldForResults/*, TextView statusStringView*/,
                        MyCallback<Boolean> callback) {
        this.fieldForResults = fieldForResults;
        this.callback = callback;
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
            while(counter < 10 && !isInterrupted()) {
                counter++;
                // "#counter" //
                fieldForResults.post( new StringPublisher(fieldForResults, Integer.toString(counter))
//                        new Runnable() {
//                    @Override
//                    public void run() {
//                        fieldForResults.setText(Integer.toString(counter));
//                    }
//                }
                );

                sleep(500);
            }
        }
        catch(InterruptedException ie) {
            wasInterrupted = true;
        }

        if (!isInterrupted() && !wasInterrupted) {
            fieldForResults.post(new StringPublisher(fieldForResults, "Done"));

            fieldForResults.post(new Runnable() {
                @Override
                public void run() {
                    callback.callback(true);
                }
            });
        }
        else {
            fieldForResults.post(new Runnable() {
                @Override
                public void run() {
                    callback.callback(false);
                }
            });
        }

    }
}
