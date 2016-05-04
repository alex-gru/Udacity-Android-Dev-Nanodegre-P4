package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.backend.myApi.MyApi;
import com.udacity.gradle.jokedisplaylibrary.JokeActivity;

import java.io.IOException;

/**
 * Created by alexgru on 04-May-16.
 * Android Developer Nanodegree
 * UDACITY
 */
class JokesAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private MainActivity context;
    private AsyncTaskListener mListener = null;
    private Exception mException = null;

    public JokesAsyncTask(MainActivity context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://udacity-android-nanodegree-p4.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }


        try {
            return myApiService.sayHi().execute().getData();
        } catch (IOException e) {
            mException = e;
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // inform listener about task completion
        if (this.mListener != null)
            this.mListener.onComplete(result, mException);

        if (context != null) {
            Intent intent = new Intent(context, JokeActivity.class);
            intent.putExtra(JokeActivity.JOKE_KEY, result);
            context.startActivity(intent);
        }
   }

    @Override
    protected void onCancelled() {
        if (this.mListener != null) {
            mException = new InterruptedException("AsyncTask cancelled");
            this.mListener.onComplete(null, mException);
        }
    }

    public JokesAsyncTask setListener(AsyncTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    public interface AsyncTaskListener {
        void onComplete(String joke, Exception e);
    }
}