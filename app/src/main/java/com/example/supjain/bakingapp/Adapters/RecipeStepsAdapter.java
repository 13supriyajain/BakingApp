package com.example.supjain.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.supjain.bakingapp.R;
import com.example.supjain.bakingapp.Util.RecipeDataUtil;
import com.example.supjain.bakingapp.data.RecipeData;
import com.example.supjain.bakingapp.data.RecipeIngredientsData;
import com.example.supjain.bakingapp.data.RecipeStepsData;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a custom Adapter class to set and display recipe Ingredients and steps.
 */
public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsDataAdapterViewHolder> {

    private Context context;
    private RecipeData recipeData;
    private boolean hasIngredientsList;
    private boolean hasStepsList;
    private RecipeStepsAdapterOnClickHandler clickHandler;

    public RecipeStepsAdapter(RecipeStepsAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecipeStepsDataAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.recipe_steps_list_item, parent, false);
        return new RecipeStepsDataAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsDataAdapterViewHolder holder, int position) {
        int viewPosition = holder.getAdapterPosition();

        List<RecipeStepsData> steps;
        // If there is no list of ingredient available, then directly start displaying list of steps.
        if (!hasIngredientsList) {
            if (hasStepsList) {
                steps = recipeData.getRecipeSteps();
                holder.displayStep(steps.get(viewPosition).getStepShortDescription());
            }
            else
                holder.displayError(context.getResources().getString(R.string.no_data_err_msg));
        }
        // Else set list of Ingredients in the first TextView of the recycler view,
        // and then list of steps afterwards.
        else {
            if (viewPosition == 0)
                holder.displayIngredients(context, recipeData.getRecipeIngredients());
            else {
                if (hasStepsList) {
                    steps = recipeData.getRecipeSteps();
                    holder.displayStep(steps.get(viewPosition - 1).getStepShortDescription());
                }
                else
                    holder.displayError(context.getResources().getString(R.string.no_data_err_msg));
            }
        }
    }

    public void setRecipeData(RecipeData data)
    {
        this.recipeData = data;
        if (recipeData != null)
        {
            List<RecipeIngredientsData> ingredients = recipeData.getRecipeIngredients();
            if (ingredients != null && !ingredients.isEmpty())
                hasIngredientsList = true;

            List<RecipeStepsData> steps = recipeData.getRecipeSteps();
            if (steps != null && !steps.isEmpty())
                hasStepsList = true;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (recipeData == null)
            return 0;
        if (!hasStepsList && !hasIngredientsList)
            return 0;
        if (hasIngredientsList && !hasStepsList)
            return 1;
        List<RecipeStepsData> steps = recipeData.getRecipeSteps();
        if (hasStepsList && !hasIngredientsList)
            return steps.size();
        return steps.size() + 1; // Add one for list of Ingredients text (first item of the list)
    }

    public interface RecipeStepsAdapterOnClickHandler {
        void mClick(int index);
    }

    public class RecipeStepsDataAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        final TextView stepsTextView;
        final ImageView rightChevronIcon;
        final ListView ingredientsListView;

        RecipeStepsDataAdapterViewHolder(View view) {
            super(view);
            stepsTextView = view.findViewById(R.id.recipe_step_text);
            rightChevronIcon = view.findViewById(R.id.recipe_step_more_btn);
            ingredientsListView = view.findViewById(R.id.recipe_ingredients_list);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick (View v) {
            int clickedPosition = getAdapterPosition();

            if (hasIngredientsList && clickedPosition != 0)
                clickHandler.mClick(clickedPosition - 1);
            else
                clickHandler.mClick(clickedPosition);
        }

        private void setupListView(Context context, List<RecipeIngredientsData> data) {
            ArrayList<String> ingredientsList =
                    RecipeDataUtil.getListOfIngredients(data);
            ArrayAdapter adapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_list_item_1, ingredientsList);
            ingredientsListView.setAdapter(adapter);
        }

        void displayIngredients(Context context, List<RecipeIngredientsData> data) {
            setupListView(context, data);
            ingredientsListView.setVisibility(View.VISIBLE);
            stepsTextView.setVisibility(View.GONE);
            rightChevronIcon.setVisibility(View.GONE);
        }

        void displayStep(String stepText) {
            stepsTextView.setText(stepText);
            ingredientsListView.setVisibility(View.GONE);
            stepsTextView.setVisibility(View.VISIBLE);
            rightChevronIcon.setVisibility(View.VISIBLE);
        }

        void displayError(String errMsg) {
            stepsTextView.setText(errMsg);
            stepsTextView.setVisibility(View.VISIBLE);
            ingredientsListView.setVisibility(View.GONE);
            rightChevronIcon.setVisibility(View.GONE);
        }
    }
}
