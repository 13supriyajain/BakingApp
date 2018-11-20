package com.example.supjain.bakingapp.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

/**
 * Type Converter class for converting list of objects
 * (RecipeIngredientsData and RecipeStepsData) to String and vice versa.
 */
public class RecipeTypeConverters {

    @TypeConverter
    public static List<RecipeIngredientsData> stringToIngredientsList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<RecipeIngredientsData>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String ingredientsListToString(List<RecipeIngredientsData> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<RecipeIngredientsData>>() {
        }.getType();
        return gson.toJson(list, type);
    }

    @TypeConverter
    public static List<RecipeStepsData> stringToStepsList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<RecipeStepsData>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String stepsListToString(List<RecipeStepsData> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<RecipeStepsData>>() {
        }.getType();
        return gson.toJson(list, type);
    }
}
