package com.example.sumitasharma.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(AndroidJUnit4.class)
public class BakingAppMainActivityBasicTest {
    @Rule
    public ActivityTestRule<BakingAppMainActivity> mBakingAppMainActivityTestRule
            = new ActivityTestRule<>(BakingAppMainActivity.class);

    /**
     * Clicks on a GridView item and checks it opens up the OrderActivity with the correct details.
     */
    @Test
    public void clickRecipeStep_ShowsStepVideoInstructionFragmentWithVideo() {


        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.baking_app_main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        // First scroll to the position that needs to be matched and click on it.
        //onView(withId(R.id.recipe_steps_recycler_view)).perform(scrollToPosition(2));
        onView(ViewMatchers.withId(R.id.recipe_steps_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Starting prep."))));
        onView(ViewMatchers.withId(R.id.recipe_steps_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks that the correct step instruction is displayed
        onView(ViewMatchers.withId(R.id.step_instruction_text_view)).check(matches(withText(containsString("Recipe Introduction"))));

        // Checks that the correct message is displayed for video available
        onView(allOf(withId(R.id.step_video_player_view), withClassName(CoreMatchers.is(SimpleExoPlayerView.class.getName()))));
    }
}
