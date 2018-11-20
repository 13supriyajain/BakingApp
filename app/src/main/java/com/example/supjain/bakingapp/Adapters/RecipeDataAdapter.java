package com.example.supjain.bakingapp.Adapters;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.supjain.bakingapp.BR;
import com.example.supjain.bakingapp.data.RecipeData;
import com.example.supjain.bakingapp.databinding.RecipeNamesListItemBinding;

import java.util.List;

/**
 * This is a custom Adapter class to set and display recipes.
 */
public class RecipeDataAdapter extends RecyclerView.Adapter<RecipeDataAdapter.RecipeDataAdapterViewHolder> {

    private List<RecipeData> recipeDataList;
    private RecipeAdapterOnClickHandler clickHandler;

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
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding =
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

        private final ViewDataBinding binding;

        RecipeDataAdapterViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        void bind(RecipeData data) {
            binding.setVariable(BR.recipe, data);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            clickHandler.mClick(recipeDataList.get(clickedPosition));
        }
    }
}
