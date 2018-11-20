package com.example.supjain.bakingapp;

import android.appwidget.AppWidgetManager;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import android.content.ComponentName;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.supjain.bakingapp.Adapters.RecipeDataAdapter;
import com.example.supjain.bakingapp.Util.RecipeDataUtil;
import com.example.supjain.bakingapp.Widget.RecipeWidgetProvider;
import com.example.supjain.bakingapp.data.RecipeData;

import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RecipeDataAdapter.RecipeAdapterOnClickHandler {

    private RecipeViewModel recipeViewModel;
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private boolean widgetConfigCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            setResult(RESULT_CANCELED);
            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
                widgetConfigCall = false;
            else
                widgetConfigCall = true;
        }

        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        binding.setVariable(BR.viewModel, recipeViewModel);

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

    // Fetch latest recipe data from server via network call on background thread,
    // In case of an error show appropriate error message.
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
                    } else
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
        // Update all widgets
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateAppWidgets(this, appWidgetManager, data, appWidgetIds);

        if (widgetConfigCall) {
            // Finish configuring widget
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        } else {
            // Start RecipeStepsActivity to display selected recipe's steps and ingredients
            Intent intent = new Intent(this, RecipeStepsActivity.class);
            intent.putExtra(RecipeStepsActivity.RECIPE_DATA_OBJ_KEY, data);
            startActivity(intent);
        }
    }
}