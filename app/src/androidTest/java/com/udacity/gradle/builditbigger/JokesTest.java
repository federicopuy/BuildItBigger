package com.udacity.gradle.builditbigger;

import android.support.test.InstrumentationRegistry;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//copied from https://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
@RunWith(JUnit4.class)
public class JokesTest {
    @Test
    public void testStringNotNull() throws Exception {
        EndpointsAsyncTaskTesting test =  new EndpointsAsyncTaskTesting();
        test.execute(InstrumentationRegistry.getContext());
        String joke = test.get();
        Assert.assertNotNull(joke);
    }

    @Test
    public void testStringISValid() throws Exception {
        int minimumStringLength = 4;
        EndpointsAsyncTaskTesting test =  new EndpointsAsyncTaskTesting();
        test.execute(InstrumentationRegistry.getContext());
        String joke = test.get();
        Assert.assertTrue(joke.length()>minimumStringLength);
    }
}