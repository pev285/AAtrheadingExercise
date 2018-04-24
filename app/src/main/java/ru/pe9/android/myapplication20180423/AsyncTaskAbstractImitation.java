package ru.pe9.android.myapplication20180423;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public abstract class AsyncTaskAbstractImitation<T> {

    private boolean cancelled = false;
    private AsyncTask.Status status = AsyncTask.Status.PENDING;
    private Handler uiHandler;
    private Thread myThread = null;


    protected void onPreExecute() {};

    protected abstract void doInBackground();

    protected void onPostExecute() {};

    protected void onProgressUpdate(T val) {};

    protected void onCancelled() {};

    public void execute() {
        uiHandler = new Handler(Looper.getMainLooper());

        onPreExecute();

        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                doInBackground();

                if (!cancelled) {
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onPostExecute();
                        }
                    });
                }

                status = AsyncTask.Status.FINISHED;
            }
        });
        myThread.start();


        status = AsyncTask.Status.RUNNING;
    }


    protected void publishProgress(T val) {
        final T value = val;
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                onProgressUpdate(value);
            }
        });
    }


    public void cancel() {
        cancelled = true;
        status = AsyncTask.Status.FINISHED;
        onCancelled();
    }

    protected boolean isCancelled() {
        return cancelled;
    }


    public AsyncTask.Status getStatus() {
        return status;
    }

}
