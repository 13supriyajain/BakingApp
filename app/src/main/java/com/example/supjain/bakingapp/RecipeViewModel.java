package com.example.supjain.bakingapp;

import android.app.Application;

import com.example.supjain.bakingapp.DB.RecipeRepository;
import com.example.supjain.bakingapp.data.RecipeData;

import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RecipeViewModel extends AndroidViewModel {

    public LiveData<List<RecipeData>> recipeList;
    public ObservableField<String> errorMsg;
    private RecipeRepository repository;

    public RecipeViewModel(Application application) {
        super(application);
        repository = new RecipeRepository(application);
        recipeList = repository.getRecipes();
    }

    void insertRecipes(List<RecipeData> recipeDataList) {
        repository.insertRecipes(recipeDataList);
    }

    void setErrorMsg(String errorMsg) {
        this.errorMsg = new ObservableField<>(errorMsg);
    }
}