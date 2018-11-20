package com.example.supjain.bakingapp;

import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.any;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BakingAppUiTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private static ViewAction recyclerClick() {
        return new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return any(View.class);
            }

            @Override
            public String getDescription() {
                return "performing click() on recycler view item";
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.performClick();
            }
        };
    }

    @Test
    public void scrollToRecipeCard_checkRecipeNameDisplayed() {

        // First scroll to the position that needs to be matched.
        scrollToRecyclerViewItem(R.id.recipe_names_recycle_view, 2);

        // Match the text and check that it's displayed.
        checkDisplayedWithText("Yellow Cake");
    }

    @Test
    public void clickOnRecipeCard_checkRecipeStepDataDisplayed() {

        // First scroll to the position and then click on it.
        scrollToRecyclerViewItem(R.id.recipe_names_recycle_view, 2);
        clickRecyclerViewItem(R.id.recipe_names_recycle_view, 2);

        // Scroll to the Recipe Step data.
        scrollToRecyclerViewItem(R.id.recipe_steps_recycle_view, 2);
        // Match the text and check that it's displayed.
        checkDisplayedWithText("1.) Starting prep");
    }

    @Test
    public void clickOnRecipeStep_checkRecipeStepDetailsDisplayed() {

        // First scroll to the position and then click on it.
        scrollToRecyclerViewItem(R.id.recipe_names_recycle_view, 2);
        clickRecyclerViewItem(R.id.recipe_names_recycle_view, 2);

        // Scroll to the Recipe Step data.
        scrollToRecyclerViewItem(R.id.recipe_steps_recycle_view, 3);
        // Match the text and check that it's displayed.
        checkDisplayedWithText("2.) Combine dry ingredients.");

        // Click on recipe step.
        clickRecyclerViewItem(R.id.recipe_steps_recycle_view, 3);

        // Check all expected views are diaplyed.
        checkDisplayedWithId(R.id.video_player);
        checkDisplayedWithId(R.id.recipe_step_detail_text);
        checkDisplayedWithId(R.id.previous_step_btn);
        checkDisplayedWithId(R.id.next_step_btn);
    }

    @Test
    public void clickOnPreviousBtn_checkPreviousRecipeStepDetailsDisplayed() {

        // First scroll to the position and then click on it.
        scrollToRecyclerViewItem(R.id.recipe_names_recycle_view, 2);
        clickRecyclerViewItem(R.id.recipe_names_recycle_view, 2);

        // Scroll to the Recipe Step data.
        scrollToRecyclerViewItem(R.id.recipe_steps_recycle_view, 2);
        // Match the text and check that it's displayed.
        checkDisplayedWithText("1.) Starting prep");

        // Click on recipe step.
        clickRecyclerViewItem(R.id.recipe_steps_recycle_view, 2);

        // Check expected view displayed.
        checkDisplayedWithId(R.id.recipe_step_detail_text);
        checkDisplayedWithId(R.id.next_step_btn);

        // Click on previous button.
        performClickOnView(R.id.previous_step_btn);

        checkDisplayedWithText("Recipe Introduction");
    }

    @Test
    public void clickOnNextBtn_checkNextRecipeStepDetailsDisplayed() {

        // First scroll to the position and then click on it.
        scrollToRecyclerViewItem(R.id.recipe_names_recycle_view, 2);
        clickRecyclerViewItem(R.id.recipe_names_recycle_view, 2);

        // Scroll to the Recipe Step data.
        scrollToRecyclerViewItem(R.id.recipe_steps_recycle_view, 2);
        // Match the text and check that it's displayed.
        checkDisplayedWithText("1.) Starting prep");

        // Click on recipe step.
        clickRecyclerViewItem(R.id.recipe_steps_recycle_view, 2);

        // Check expected view displayed.
        checkDisplayedWithId(R.id.recipe_step_detail_text);
        checkDisplayedWithId(R.id.previous_step_btn);

        // Click on next button.
        performClickOnView(R.id.next_step_btn);

        checkDisplayedWithId(R.id.video_player);
        String recipeStepText = "2. Combine the cake flour, 400 grams (2 cups) of sugar, baking powder, " +
                "and 1 teaspoon of salt in the bowl of a stand mixer. Using the paddle attachment," +
                " beat at low speed until the dry ingredients are mixed together, about one minute";
        checkDisplayedWithText(recipeStepText);
    }

    private void scrollToRecyclerViewItem(int recyclerViewId, int scrollPosition) {
        onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.scrollToPosition(scrollPosition));
    }

    private void clickRecyclerViewItem(int recyclerViewId, int itemPosition) {
        onView(withId(recyclerViewId))
                .perform(actionOnItemAtPosition(itemPosition, recyclerClick()));
    }

    private void checkDisplayedWithText(String text) {
        onView(withText(text)).check(matches(isDisplayed()));
    }

    private void checkDisplayedWithId(int viewId) {
        onView(withId(viewId)).check(matches(isDisplayed()));
    }

    private void performClickOnView(int viewId) {
        onView(withId(viewId)).check(matches(isDisplayed())).perform(click());
    }
}
