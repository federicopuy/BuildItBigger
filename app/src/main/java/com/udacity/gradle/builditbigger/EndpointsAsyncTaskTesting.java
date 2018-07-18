package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

//This class is only intended for testing
public class EndpointsAsyncTaskTesting extends AsyncTask<Context, Void, String> {
        private  MyApi myApiService = null;
        private final CountDownLatch signal = new CountDownLatch(1);

        EndpointsAsyncTaskTesting() {
        }

        @Override
        protected String doInBackground(Context... contexts) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });

                myApiService = builder.build();
            }

            try {
                String joke =   myApiService.getJoke().execute().getData();
                signal.countDown();
                return joke;
            } catch (IOException e) {
                e.printStackTrace();
                signal.countDown();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }

