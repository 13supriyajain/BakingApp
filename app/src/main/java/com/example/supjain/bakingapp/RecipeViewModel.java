package com.example.supjain.bakingapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.supjain.bakingapp.DB.RecipeRepository;
import com.example.supjain.bakingapp.data.RecipeData;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository repository;
    private LiveData<List<RecipeData>> recipeDataList;

    public String errorMsg;
    public boolean showErrorMsg;
    public boolean showProgressbar;
    public boolean showRecyclerView;
    public List<RecipeData> recipeList;

    public RecipeViewModel(Application application) {
        super(application);
        repository = new RecipeRepository(application);
        recipeDataList = repository.getRecipes();
    }

    LiveData<List<RecipeData>> getAllRecipes() {
        return recipeDataList;
    }

    public void setRecipeList(List<RecipeData> list) {
        this.recipeList = list;
    }

    public void insertRecipes(List<RecipeData> recipeDataList) {
        repository.insertRecipes(recipeDataList);
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setShowErrorMsg(boolean showErrorMsg) {
        this.showErrorMsg = showErrorMsg;
    }

    public void setShowProgressbar(boolean showProgressbar) {
        this.showProgressbar = showProgressbar;
    }

    public void setShowRecyclerView(boolean showRecyclerView) {
        this.showRecyclerView = showRecyclerView;
    }
}
