package com.example.supjain.bakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        binding.setViewModel(recipeViewModel);

        RecyclerView recyclerView = findViewById(R.id.recipe_names_recycle_view);
        RecipeDataAdapter recipeDataAdapter = new RecipeDataAdapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                RecipeDataUtil.calculateNoOfColumns(this));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recipeDataAdapter);
        recyclerView.setHasFixedSize(true);

        fetchAndSetRecipeList();
        binding.executePendingBindings();
    }

    private void fetchAndSetRecipeList() {
        if (RecipeDataUtil.hasNetworkConnection(this)) {
            Retrofit retrofit = RecipeDataUtil.getRetrofitInstance();
            RecipeDataUtil.RecipeDataFetchService service = retrofit.create(RecipeDataUtil.RecipeDataFetchService.class);
            Call<RecipeData[]> call = service.getRecipeData();
            call.enqueue(new Callback<RecipeData[]>() {
                @Override
                public void onResponse(Call<RecipeData[]> call, Response<RecipeData[]> response) {
                    List<RecipeData> recipeList = Arrays.asList(response.body());
                    recipeViewModel.insertRecipes(recipeList);
                    recipeViewModel.setErrorMsg(null);
                }

                @Override
                public void onFailure(Call<RecipeData[]> call, Throwable t) {
                    t.printStackTrace();
                    if (recipeViewModel.recipeList.getValue() == null || recipeViewModel.recipeList.getValue().isEmpty()) {
                        recipeViewModel.setErrorMsg(getResources().getString(R.string.no_data_err_msg));
                    }
                    else
                        recipeViewModel.setErrorMsg(null);
                }
            });
        } else {
            if (recipeViewModel.recipeList.getValue() == null || recipeViewModel.recipeList.getValue().isEmpty())
                recipeViewModel.setErrorMsg(getResources().getString(R.string.no_connection_err_msg));
            else
                recipeViewModel.setErrorMsg(null);
        }
    }

    @Override
    public void mClick(RecipeData data) {
        Intent intent = new Intent(this, RecipeStepsActivity.class);
        intent.putExtra(RecipeStepsActivity.RECIPE_DATA_OBJ_KEY, data);
        startActivity(intent);
    }
}