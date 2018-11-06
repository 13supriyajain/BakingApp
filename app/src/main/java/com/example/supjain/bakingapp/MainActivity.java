package com.example.supjain.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.supjain.bakingapp.Adapters.RecipeDataAdapter;
import com.example.supjain.bakingapp.Util.RecipeDataUtil;
import com.example.supjain.bakingapp.data.RecipeData;
import com.example.supjain.bakingapp.databinding.ActivityMainBinding;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RecipeDataAdapter.RecipeAdapterOnClickHandler {

    private RecipeViewModel recipeViewModel;
    private RecipeDataAdapter recipeDataAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getAllRecipes().observe(this, new Observer<List<RecipeData>>() {
            @Override
            public void onChanged(@Nullable final List<RecipeData> list) {
                showRecipeData(list);
                binding.setViewModel(recipeViewModel);
                binding.executePendingBindings();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recipe_names_recycle_view);
        recipeDataAdapter = new RecipeDataAdapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                RecipeDataUtil.calculateNoOfColumns(this));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recipeDataAdapter);
        recyclerView.setHasFixedSize(true);

        fetchAndSetRecipeList();

        binding.setViewModel(recipeViewModel);
        binding.executePendingBindings();
    }

    private void fetchAndSetRecipeList() {
        showProgressBar();
        if (RecipeDataUtil.hasNetworkConnection(this)) {
            Retrofit retrofit = RecipeDataUtil.getRetrofitInstance();
            RecipeDataUtil.RecipeDataFetchService service = retrofit.create(RecipeDataUtil.RecipeDataFetchService.class);
            Call<RecipeData[]> call = service.getRecipeData();
            call.enqueue(new Callback<RecipeData[]>() {
                @Override
                public void onResponse(Call<RecipeData[]> call, Response<RecipeData[]> response) {
                    List<RecipeData> recipeList = Arrays.asList(response.body());
                    recipeViewModel.insertRecipes(recipeList);
                }

                @Override
                public void onFailure(Call<RecipeData[]> call, Throwable t) {
                    t.printStackTrace();
                    showErrorMessage(getResources().getString(R.string.no_data_err_msg));
                }
            });
        } else {
            showErrorMessage(getResources().getString(R.string.no_connection_err_msg));
        }
    }

    @Override
    public void mClick(RecipeData data) {
        System.out.print("Id: "+data.getRecipeId());
    }

    private void showRecipeData(List<RecipeData> list) {
        recipeViewModel.setShowErrorMsg(false);
        recipeViewModel.setShowProgressbar(false);
        recipeViewModel.setShowRecyclerView(true);
        recipeViewModel.setRecipeList(list);
    }

    private void showErrorMessage(String errMsg) {
        recipeViewModel.setShowErrorMsg(true);
        recipeViewModel.setShowProgressbar(false);
        recipeViewModel.setShowRecyclerView(false);
        recipeViewModel.setErrorMsg(errMsg);
    }

    private void showProgressBar() {
        recipeViewModel.setShowErrorMsg(false);
        recipeViewModel.setShowProgressbar(true);
        recipeViewModel.setShowRecyclerView(false);
    }
}