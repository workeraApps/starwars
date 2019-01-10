package com.workera.apps.starwars;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Performing instrumented test to check activity creation of the first screen.
 */

public class MainActivityTest  {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule(MainActivity.class);

   private MainActivity mainActivity = null;

    // executed before the test is run
    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testlaunch(){
        // finding a view in the activity
        View view = mainActivity.findViewById(R.id.empty_view);
        assertNotNull(view);

    }
    //executed after the test case is run
    @After
    public void tearDown() throws Exception {

        mainActivity = null;
    }


    @Test
    public void onCreate() {
    }



    @Test
    public void checkInternetConnection() {


        ConnectivityManager connMgr = (ConnectivityManager)
                mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected()) {

            assertNotNull(networkInfo);
        }

    }

    @Test
    public void onCreateLoader() {


    }

    @Test
    public void onLoadFinished() {


    }

    @Test
    public void onLoaderReset() {
    }
}