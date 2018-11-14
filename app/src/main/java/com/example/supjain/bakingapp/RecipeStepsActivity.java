package com.example.supjain.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.supjain.bakingapp.Adapters.RecipeStepsAdapter;
import com.example.supjain.bakingapp.data.RecipeData;
import com.example.supjain.bakingapp.data.RecipeStepsData;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsAdapter.RecipeStepsAdapterOnClickHandler {

    public static final String RECIPE_DATA_OBJ_KEY = "RecipeDataObj";
    private RecipeData recipeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        // Only create new fragments when there is no previously saved state
        if(savedInstanceState == null) {
            Intent intent = getIntent();
            recipeData = intent.getParcelableExtra(RECIPE_DATA_OBJ_KEY);
            this.setTitle(recipeData.getRecipeName());

            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.setRecipeData(recipeData);
            recipeStepsFragment.setClickHandler(this);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_steps_fragment_container, recipeStepsFragment)
                    .commit();
        }
    }

    @Override
    public void mClick(RecipeStepsData recipeStepsData) {
        System.out.print(recipeStepsData);
    }
}
