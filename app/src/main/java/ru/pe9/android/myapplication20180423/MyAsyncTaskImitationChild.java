package ru.pe9.android.myapplication20180423;

import android.content.Context;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class MyAsyncTaskImitationChild extends AsyncTaskAbstractImitation<Integer> {

    private Context context;
    private TextView text;
    private TextView statusText;
    private int startValue;


    public MyAsyncTaskImitationChild(Context context, TextView mainTextView, TextView statusText) {
        this.context = context;
        this.text = mainTextView;
        this.statusText = statusText;
        startValue = 0;
    }

    public MyAsyncTaskImitationChild(Context context, TextView mainTextView, TextView statusText, int startingValue) {
        this.context = context;
        this.text = mainTextView;
        this.statusText = statusText;
        this.startValue = startingValue;
    }

    @Override
    protected void doInBackground() {

        try {
            if (startValue > 0) {
                startValue--;
            }
            for (int i = startValue; i < 10 && !isCancelled(); i++) {
                publishProgress(i+1);
                sleep(500);
            }


        } catch (InterruptedException ie) {
        }
    }

    @Override
    protected void onProgressUpdate(Integer val) {
        text.setText(Integer.toString(val));
    }

    @Override
    protected void onPreExecute() {
        statusText.setText("onPreExecute");
    }

    @Override
    protected void onPostExecute() {
        text.setText("Done");
        statusText.setText("onPostExecute");
    }

    @Override
    protected void onCancelled() {
        statusText.setText("onCanceled");
    }

}
