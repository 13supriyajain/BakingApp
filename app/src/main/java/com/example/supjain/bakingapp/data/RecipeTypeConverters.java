package com.example.supjain.bakingapp.data;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Type Converter class for converting list of objects
 * (RecipeIngredientsData and RecipeStepsData) to String and vice versa.
 */

public class RecipeTypeConverters {

    @TypeConverter
    public static List<RecipeIngredientsData> stringToIngredientsList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<RecipeIngredientsData>>() {}.getType();
        List<RecipeIngredientsData> ingredients = gson.fromJson(json, type);
        return ingredients;
    }

    @TypeConverter
    public static String ingredientsListToString(List<RecipeIngredientsData> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<RecipeIngredientsData>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }

    @TypeConverter
    public static List<RecipeStepsData> stringToStepsList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<RecipeStepsData>>() {}.getType();
        List<RecipeStepsData> stepsList = gson.fromJson(json, type);
        return stepsList;
    }

    @TypeConverter
    public static String stepsListToString(List<RecipeStepsData> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<RecipeStepsData>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
