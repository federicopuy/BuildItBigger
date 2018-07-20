package com.udacity.gradle.builditbigger;

import android.support.test.InstrumentationRegistry;
import android.test.InstrumentationTestCase;
import android.text.TextUtils;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.CountDownLatch;

//copied from https://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
// copied from http://marksunghunpark.blogspot.com/2015/05/how-to-test-asynctask-in-android.html
@RunWith(JUnit4.class)
public class JokesTest extends InstrumentationTestCase {

    private String jokeString = null;
    private Exception mError = null;
    private CountDownLatch signal = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        signal.countDown();
    }

    @Test
    public void test() throws InterruptedException {
        signal = new CountDownLatch(1);
        MainActivity.EndpointsAsyncTask task = new MainActivity.EndpointsAsyncTask();
        task.setListener(new MainActivity.EndpointsAsyncTask.JsonGetTaskListener() {
            @Override
            public void onComplete(String jsonString, Exception e) {
                jokeString = jsonString;
                mError = e;
                signal.countDown();
            }
        }).execute();
        signal.await();

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(jokeString));
        int minimumStringLength = 4;
        Assert.assertTrue(jokeString.length()> minimumStringLength);
    }
}
