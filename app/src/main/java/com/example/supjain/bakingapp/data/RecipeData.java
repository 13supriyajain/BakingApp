package com.example.supjain.bakingapp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.supjain.bakingapp.RecipeStepsActivity;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * This class object holds all the recipe details.
 */
@Entity(tableName = "recipes")
@TypeConverters(RecipeTypeConverters.class)
public class RecipeData implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int recipeId;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String recipeName;

    @ColumnInfo(name = "ingredients")
    @SerializedName("ingredients")
    private List<RecipeIngredientsData> recipeIngredients;

    @ColumnInfo(name = "steps")
    @SerializedName("steps")
    private List<RecipeStepsData> recipeSteps;

    @ColumnInfo(name = "servings")
    @SerializedName("servings")
    private int recipeServings;

    @ColumnInfo(name = "image_url")
    @SerializedName("image")
    private String recipeImageUrl;

    public RecipeData(int recipeId, String recipeName, List<RecipeIngredientsData> recipeIngredients,
                      List<RecipeStepsData> recipeSteps, int recipeServings, String recipeImageUrl) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
        this.recipeServings = recipeServings;
        this.recipeImageUrl = recipeImageUrl;
    }

    public void setRecipeId(@NonNull int recipeId) {
        this.recipeId = recipeId;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setRecipeIngredients(List<RecipeIngredientsData> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public void setRecipeSteps(List<RecipeStepsData> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public void setRecipeServings(int recipeServings) {
        this.recipeServings = recipeServings;
    }

    public void setRecipeImageUrl(String recipeImageUrl) {
        this.recipeImageUrl = recipeImageUrl;
    }

    @NonNull
    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public List<RecipeIngredientsData> getRecipeIngredients() {
        return recipeIngredients;
    }

    public List<RecipeStepsData> getRecipeSteps() {
        return recipeSteps;
    }

    public int getRecipeServings() {
        return recipeServings;
    }

    public String getRecipeImageUrl() {
        return recipeImageUrl;
    }

    public static Creator<RecipeData> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeId);
        dest.writeString(recipeName);
        dest.writeList(recipeIngredients);
        dest.writeList(recipeSteps);
        dest.writeInt(recipeServings);
        dest.writeString(recipeImageUrl);
    }

    protected RecipeData(Parcel in) {
        recipeId = in.readInt();
        recipeName = in.readString();
        recipeIngredients = in.readArrayList(getClass().getClassLoader());
        recipeSteps = in.readArrayList(getClass().getClassLoader());
        recipeServings = in.readInt();
        recipeImageUrl = in.readString();
    }

    public static final Creator<RecipeData> CREATOR = new Creator<RecipeData>() {
        @Override
        public RecipeData createFromParcel(Parcel in) {
            return new RecipeData(in);
        }

        @Override
        public RecipeData[] newArray(int size) {
            return new RecipeData[size];
        }
    };
}
