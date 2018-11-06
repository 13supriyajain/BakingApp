package com.example.supjain.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.supjain.bakingapp.data.RecipeData;
import com.example.supjain.bakingapp.databinding.RecipeNamesListItemBinding;

import java.util.List;

/**
 * This is a custom Adapter class to set and display recipes.
 */
public class RecipeDataAdapter extends RecyclerView.Adapter<RecipeDataAdapter.RecipeDataAdapterViewHolder> {

    private List<RecipeData> recipeDataList;
    private RecipeAdapterOnClickHandler clickHandler;
    private Context context;

    public RecipeDataAdapter(RecipeAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public void setRecipeData(List<RecipeData> recipeDataList) {
        this.recipeDataList = recipeDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeDataAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RecipeNamesListItemBinding binding =
                RecipeNamesListItemBinding.inflate(layoutInflater, viewGroup, false);
        return new RecipeDataAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDataAdapterViewHolder holder, int i) {
        int viewPosition = holder.getAdapterPosition();
        RecipeData recipeData = recipeDataList.get(viewPosition);
        holder.bind(recipeData);
    }

    @Override
    public int getItemCount() {
        if (recipeDataList == null || recipeDataList.isEmpty())
            return 0;
        return recipeDataList.size();
    }

    public interface RecipeAdapterOnClickHandler {
        void mClick(RecipeData recipeData);
    }

    public class RecipeDataAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final RecipeNamesListItemBinding binding;

        public RecipeDataAdapterViewHolder(RecipeNamesListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(RecipeData data) {
            binding.setRecipe(data);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            clickHandler.mClick(recipeDataList.get(clickedPosition));
        }
    }
}
