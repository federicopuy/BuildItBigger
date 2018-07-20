package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jokeandroidlibrary.JokeDisplayActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.security.auth.callback.Callback;

import static com.example.jokeandroidlibrary.JokeDisplayActivity.JOKE_INTENT;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        EndpointsAsyncTask task = new EndpointsAsyncTask();
        task.setContext(MainActivity.this);
        task.execute();
    }

        public interface JsonGetTaskListener {
            void onComplete(String jsonString, Exception e);
        }

    // https://stackoverflow.com/questions/44309241/warning-this-asynctask-class-should-be-static-or-leaks-might-occur
    public static class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
        private MyApi myApiService = null;
        private WeakReference<MainActivity> activityWeakReference;
        private JsonGetTaskListener mListener = null;
        private Exception mError = null;


        void setContext(MainActivity context){
            activityWeakReference = new WeakReference<>(context); //used weak reference to prevent memory leaks
            activityWeakReference.get().progressBar.setVisibility(View.VISIBLE);
        }


        public EndpointsAsyncTask setListener(JsonGetTaskListener listener) {
            this.mListener = listener;
            return this;
        }

        @Override
        protected String doInBackground(Context... contexts) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });

                myApiService = builder.build();
            }

            try {
                return myApiService.getJoke().execute().getData();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onCancelled() {
            if (this.mListener != null) {
                mError = new InterruptedException("AsyncTask cancelled");
                this.mListener.onComplete(null, mError);
            }
        }

        @Override
        protected void onPostExecute(String result) {

            if (this.mListener != null)
                this.mListener.onComplete(result, mError);

            if (activityWeakReference!=null){
                Context context = activityWeakReference.get();
                activityWeakReference.get().progressBar.setVisibility(View.INVISIBLE);

                if (result!=null){
                    Class destination = JokeDisplayActivity.class;
                    Intent goToLibraryActivity = new Intent(context, destination);
                    goToLibraryActivity.putExtra(JOKE_INTENT, result);
                    context.startActivity(goToLibraryActivity);
                } else {
                    Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show();
                } }
            }

        public interface JsonGetTaskListener {
           public void onComplete(String jsonString, Exception e);
        }
    }
    }


