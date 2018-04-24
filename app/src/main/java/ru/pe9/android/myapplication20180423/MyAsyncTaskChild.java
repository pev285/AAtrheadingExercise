package ru.pe9.android.myapplication20180423;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class MyAsyncTaskChild extends AsyncTask<Integer,Integer,Boolean> {

    private Context context;
    private TextView text;
    private TextView statusText;
    private int startValue;


    public MyAsyncTaskChild(Context context, TextView mainTextView, TextView statusText) {
        this.context = context;
        this.text = mainTextView;
        this.statusText = statusText;
        startValue = 0;
    }

    public MyAsyncTaskChild(Context context, TextView mainTextView, TextView statusText, int startingValue) {
        this.context = context;
        this.text = mainTextView;
        this.statusText = statusText;
        this.startValue = startingValue;
    }

    @Override
    protected Boolean doInBackground(Integer... ints) {

        try {
            if (startValue > 0) {
                startValue--;
            }
            for (int i = startValue; i < 10 && !isCancelled(); i++) {
                publishProgress(i+1);
                sleep(500);
            }


        } catch (InterruptedException ie) {
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... vals) {
        text.setText(Integer.toString(vals[0]));
    }

    @Override
    protected void onPreExecute() {
        statusText.setText("onPreExecute");
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        text.setText("Done");
        statusText.setText("onPostExecute");
    }

    @Override
    protected void onCancelled() {
        statusText.setText("onCanceled");
    }

}
