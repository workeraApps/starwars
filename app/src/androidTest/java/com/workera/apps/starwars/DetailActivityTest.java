package com.workera.apps.starwars;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Performing instrumented test to check activity creation of the detail screen.
 */

public class DetailActivityTest {

    @Rule
    public ActivityTestRule<DetailActivity> detailActivityTestRule = new ActivityTestRule(DetailActivity.class);

    private DetailActivity detailActivity = null;

    // executed before the test is run
    @Before
    public void setUp() throws Exception {
        detailActivity = detailActivityTestRule.getActivity();
    }

    @Test
    public void testlaunch(){
        // finding a view in the activity
        View view = detailActivity.findViewById(R.id.namelabel);
        assertNotNull(view);

    }
    //executed after the test case is run
    @After
    public void tearDown() throws Exception {

        detailActivity = null;
    }



}