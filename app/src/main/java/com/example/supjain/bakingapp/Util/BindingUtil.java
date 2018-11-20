package com.example.supjain.bakingapp.Util;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.supjain.bakingapp.Adapters.RecipeDataAdapter;
import com.example.supjain.bakingapp.data.RecipeData;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BindingUtil {

    @SuppressLint("CrossDomainBindingAdapterIssue")
    @BindingAdapter({"imageUrl", "error"})
    public static void loadImage(ImageView view, String url, Drawable defaultImage) {
        if (!TextUtils.isEmpty(url))
            Picasso.with(view.getContext()).load(url).into(view);
        else
            view.setImageDrawable(defaultImage);
    }

    @SuppressLint("CrossDomainBindingAdapterIssue")
    @BindingAdapter({"data"})
    public static void setRecyclerViewDataList(RecyclerView view, List<RecipeData> data) {
        RecipeDataAdapter adapter = (RecipeDataAdapter) view.getAdapter();
        if (adapter != null)
            adapter.setRecipeData(data);
    }
}
