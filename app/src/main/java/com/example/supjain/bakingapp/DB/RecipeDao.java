package com.example.supjain.bakingapp.DB;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.supjain.bakingapp.data.RecipeData;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipes")
    LiveData<List<RecipeData>> getAllRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipes(List<RecipeData> recipeDataList);
}
