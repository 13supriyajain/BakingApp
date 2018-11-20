package com.example.supjain.bakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supjain.bakingapp.Adapters.RecipeStepsAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This fragment inflates view for holding recipe ingredients list and steps list.
 */
public class RecipeStepsFragment extends Fragment {

    private RecipeStepsAdapter recipeStepsAdapter;

    public RecipeStepsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recipe_steps_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeStepsAdapter);
        recyclerView.setHasFixedSize(true);

        return rootView;
    }

    public void setRecipeStepsAdapter(RecipeStepsAdapter recipeStepsAdapter) {
        this.recipeStepsAdapter = recipeStepsAdapter;
    }
}
