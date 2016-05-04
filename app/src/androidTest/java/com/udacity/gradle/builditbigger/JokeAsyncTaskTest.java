package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.concurrent.CountDownLatch;

public class JokeAsyncTaskTest extends ApplicationTestCase<Application> {

    /**
     * async task test as found here:
     * http://marksunghunpark.blogspot.co.at/2015/05/how-to-test-asynctask-in-android.html
     */
    String mJoke = null;
    Exception mException = null;
    CountDownLatch mLatch = null;

    public JokeAsyncTaskTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        mLatch = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        mLatch.countDown();
    }

    public void testJokesAsyncTask() throws InterruptedException {
        JokesAsyncTask jokesAsyncTask = new JokesAsyncTask(null);
        jokesAsyncTask.setListener(new JokesAsyncTask.AsyncTaskListener() {

            @Override
            public void onComplete(String joke, Exception e) {
                mJoke = joke;
                mException = e;
                mLatch.countDown();
            }
        });
        jokesAsyncTask.execute();
        mLatch.await();
        assertNull("An exception occured.", mException);
        assertNotNull("Joke is null.", mJoke);
        assertNotSame("Joke string is empty.", "");
    }
}