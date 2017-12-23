package com.example.sumitasharma.bakingapp;


import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;


@RunWith(AndroidJUnit4.class)
public class BakingAppDetailActivityBasicTest {
    @Rule
    public ActivityTestRule<BakingAppDetailActivity> mBakingAppDetailActivityTestRule
            = new ActivityTestRule<>(BakingAppDetailActivity.class);

    @Test
    public void clickRecipeCard_OpensBakingAppDetailActivity() {

        onView(allOf(withId(R.id.recipe_step_text_view_holder), withText("Cheesecake"))).perform(click());
        onView(ViewMatchers.withId(R.id.step_instruction_text_view)).check(matches(hasDescendant(withText("Cheesecake"))));
    }

}
