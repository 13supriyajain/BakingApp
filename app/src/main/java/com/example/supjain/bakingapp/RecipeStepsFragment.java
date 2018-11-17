package com.example.supjain.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supjain.bakingapp.Adapters.RecipeStepsAdapter;
import com.example.supjain.bakingapp.Adapters.RecipeStepsAdapter.RecipeStepsAdapterOnClickHandler;
import com.example.supjain.bakingapp.data.RecipeData;

/**
 * This fragment inflates view for holding recipe ingredients list and steps list.
 */

public class RecipeStepsFragment extends Fragment {

    private RecipeData recipeData;
    private static final String RECIPE_DATA_SAVED_KEY = "RecipeDataSavedKey";

    public RecipeStepsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            this.recipeData = savedInstanceState.getParcelable(RECIPE_DATA_SAVED_KEY);
        }

        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recipe_steps_recycle_view);
        RecipeStepsAdapterOnClickHandler clickHandler = (RecipeStepsAdapterOnClickHandler) getActivity();
        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(clickHandler);
        recipeStepsAdapter.setRecipeData(recipeData);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeStepsAdapter);
        recyclerView.setHasFixedSize(true);
        return rootView;
    }

    public void setRecipeData(RecipeData data) {
        this.recipeData = data;
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelable(RECIPE_DATA_SAVED_KEY, recipeData);
    }
}
