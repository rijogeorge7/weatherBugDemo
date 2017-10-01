package com.rijo.weatherbugdemo;

import android.app.Fragment;
import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.rijo.weatherbugdemo.activities.ImageDetailsActivity;
import com.rijo.weatherbugdemo.model.Image;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;

/**
 * Created by rijogeorge on 9/30/17.
 */

public class ImageDetailsActivityTest {
    //// third parameter is set to false which means the activity is not started automatically
    @Rule
    public IntentsTestRule<ImageDetailsActivity> mActivityRule = new IntentsTestRule<>(ImageDetailsActivity.class,true,false);

    @Before
    public void setUpIntent() throws Exception {
        String title="amazing beautiful beauty blue jpg";
        String description="amazing beautiful beauty blue.jpg";
        String filename="amazing-beautiful-beauty-blue.jpg";
        Image image=new Image(title,description,filename);
        Intent intent=new Intent();
        intent.putExtra(Image.KEY,image);
        mActivityRule.launchActivity(intent);
    }
    @Test
    public void activityStartedSuccessfully() throws Exception {
        onView(withText("Title: "))
                .check(matches(isDisplayed()));
    }
}
