package com.example.supjain.bakingapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.supjain.bakingapp.R;
import com.example.supjain.bakingapp.data.RecipeData;
import com.example.supjain.bakingapp.data.RecipeIngredientsData;
import com.example.supjain.bakingapp.data.RecipeStepsData;

import java.util.List;

/**
 * This is a custom Adapter class to set and display recipe Ingredients and steps.
 */
public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsDataAdapterViewHolder> {

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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_steps_list_item, parent, false);
        return new RecipeStepsDataAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsDataAdapterViewHolder holder, int position) {
        int viewPosition = holder.getAdapterPosition();

        int showRightChevron = View.VISIBLE;
        List<RecipeStepsData> steps = recipeData.getRecipeSteps();
        // If there is no list of ingredient available, then directly start displaying list of steps.
        if (!hasIngredientsList && hasStepsList)
            holder.stepsTextView.setText(steps.get(viewPosition).getStepShortDescription());
        // Else set list of Ingredients in the first TextView of the recycler view,
        // and then list of steps afterwards.
        else if (hasIngredientsList && hasStepsList) {
            if (viewPosition == 0) {
                String ingredientsListTextValue = getIngredientsListText();
                holder.stepsTextView.setText(ingredientsListTextValue);
                showRightChevron = View.GONE;
            }
            else
                holder.stepsTextView.setText(steps.get(viewPosition - 1).getStepShortDescription());
        } else if (hasIngredientsList && !hasStepsList) {
            if (viewPosition == 0) {
                String ingredientsListTextValue = getIngredientsListText();
                holder.stepsTextView.setText(ingredientsListTextValue);
                showRightChevron = View.GONE;
            }
        } else {
            holder.stepsTextView.setText(R.string.no_data_err_msg);
            showRightChevron = View.GONE;
        }

        holder.rightChevronIcon.setVisibility(showRightChevron);
    }

    // Iterate over list of ingredients to form a single string object,
    // holding all ingredients information, separated by appropriate delimiters.
    private String getIngredientsListText() {
        List<RecipeIngredientsData> recipeIngredients = recipeData.getRecipeIngredients();

        String ingredientsListText = "Ingredients:";
        for (RecipeIngredientsData ingredientsData : recipeIngredients)
            ingredientsListText = ingredientsListText + "\n\t\t\t" +
                    ingredientsData.getIngredientName() + " - " +
                    ingredientsData.getIngredientQuantity() + " " +
                    ingredientsData.getIngredientMeasure();
        return ingredientsListText;
    }

    public void setRecipeData(RecipeData data)
    {
        this.recipeData = data;
        if (recipeData != null)
        {
            List<RecipeIngredientsData> ingredients = recipeData.getRecipeIngredients();

            if (ingredients != null || !ingredients.isEmpty())
                hasIngredientsList = true;

            List<RecipeStepsData> steps = recipeData.getRecipeSteps();

            if (steps != null || !steps.isEmpty())
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
        void mClick(RecipeStepsData recipeStepsData);
    }

    public class RecipeStepsDataAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final TextView stepsTextView;
        public final ImageView rightChevronIcon;

        public RecipeStepsDataAdapterViewHolder(View view) {
            super(view);
            stepsTextView = view.findViewById(R.id.recipe_step_text);
            rightChevronIcon = view.findViewById(R.id.recipe_step_more_btn);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick (View v) {
            int clickedPosition = getAdapterPosition();

            if (hasIngredientsList && clickedPosition != 0)
                  clickHandler.mClick(recipeData.getRecipeSteps().get(clickedPosition - 1));
            else
                clickHandler.mClick(recipeData.getRecipeSteps().get(clickedPosition));
        }
    }
}
