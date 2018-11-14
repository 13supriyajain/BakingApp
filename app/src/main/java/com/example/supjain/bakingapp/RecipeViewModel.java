package com.example.supjain.bakingapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;

import com.example.supjain.bakingapp.DB.RecipeRepository;
import com.example.supjain.bakingapp.data.RecipeData;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository repository;
    public LiveData<List<RecipeData>> recipeList;
    public ObservableField<String> errorMsg;

    public RecipeViewModel(Application application) {
        super(application);
        repository = new RecipeRepository(application);
        recipeList = repository.getRecipes();
    }

    public void insertRecipes(List<RecipeData> recipeDataList) {
        repository.insertRecipes(recipeDataList);
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = new ObservableField<>(errorMsg);
    }
}