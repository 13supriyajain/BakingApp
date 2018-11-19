package com.example.supjain.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.supjain.bakingapp.Adapters.RecipeStepsAdapter;
import com.example.supjain.bakingapp.data.RecipeData;
import com.example.supjain.bakingapp.data.RecipeStepsData;

import java.util.List;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsAdapter.RecipeStepsAdapterOnClickHandler {

    public static final String RECIPE_DATA_OBJ_KEY = "RecipeDataObj";
    private static final String RECIPE_STEPS_FRAGMENT_TAG = "RecipeStepsFragment";
    private static final String RECIPE_STEPS_DETAILS_FRAGMENT_TAG = "RecipeStepDetailsFragment";

    private RecipeData recipeData;
    private boolean isTwoPaneDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        Intent intent = getIntent();
        recipeData = intent.getParcelableExtra(RECIPE_DATA_OBJ_KEY);
        this.setTitle(recipeData.getRecipeName());

        if (findViewById(R.id.two_pane_display) != null) {
            isTwoPaneDisplay = true;
            // Only create new fragments when there is no previously saved state
            if (savedInstanceState == null) {
                addStepListFragment(R.id.step_list_fragment);
                replaceStepDetailsFragment(0, recipeData.getRecipeSteps(),
                        R.id.step_detail_fragment);
            }
        } else {
            isTwoPaneDisplay = false;
            if (savedInstanceState == null)
                addStepListFragment(R.id.recipe_steps_fragment_container);
        }
    }

    @Override
    public void mClick(int currentRecipeStepIndex) {
        if (isTwoPaneDisplay)
            replaceStepDetailsFragment(currentRecipeStepIndex, recipeData.getRecipeSteps(),
                    R.id.step_detail_fragment);
        else
            replaceStepDetailsFragment(currentRecipeStepIndex, recipeData.getRecipeSteps(),
                R.id.recipe_steps_fragment_container);
    }

    public void replaceStepDetailsFragment(int currentRecipeStepIndex, List<RecipeStepsData> dataList, int containerId) {
        setSelectedRecipeStepIndex(currentRecipeStepIndex);

        RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
        recipeStepDetailsFragment.setRecipeStepsDataList(dataList);
        recipeStepDetailsFragment.setCurrentRecipeIndex(currentRecipeStepIndex);
        recipeStepDetailsFragment.setTwoPaneDisplay(isTwoPaneDisplay);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(containerId, recipeStepDetailsFragment, RECIPE_STEPS_DETAILS_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    private void addStepListFragment(int containerId) {
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        recipeStepsFragment.setRecipeData(recipeData);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(containerId, recipeStepsFragment, RECIPE_STEPS_FRAGMENT_TAG)
                .commit();
    }

    public void setSelectedRecipeStepIndex(int index) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(RECIPE_STEPS_FRAGMENT_TAG);
        if (fragment instanceof RecipeStepsFragment)
            ((RecipeStepsFragment) fragment).setSelectedRecipeStepIndex(index);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(RECIPE_STEPS_DETAILS_FRAGMENT_TAG);
        if (fragment instanceof RecipeStepDetailsFragment) {
            int stepIndex = ((RecipeStepDetailsFragment) fragment).getCurrentRecipeIndex();
            setSelectedRecipeStepIndex(stepIndex);
        }
    }
}
