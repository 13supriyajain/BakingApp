package com.example.supjain.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * This class object holds all the ingredient details for each recipe.
 */
public class RecipeIngredientsData implements Parcelable {

    public static final Creator<RecipeIngredientsData> CREATOR = new Creator<RecipeIngredientsData>() {
        @Override
        public RecipeIngredientsData createFromParcel(Parcel in) {
            return new RecipeIngredientsData(in);
        }

        @Override
        public RecipeIngredientsData[] newArray(int size) {
            return new RecipeIngredientsData[size];
        }
    };
    @SerializedName("quantity")
    private float ingredientQuantity;
    @SerializedName("measure")
    private String ingredientMeasure;
    @SerializedName("ingredient")
    private String ingredientName;

    public RecipeIngredientsData(float ingredientQuantity, String ingredientMeasure, String ingredientName) {
        this.ingredientQuantity = ingredientQuantity;
        this.ingredientMeasure = ingredientMeasure;
        this.ingredientName = ingredientName;
    }

    protected RecipeIngredientsData(Parcel in) {
        ingredientQuantity = in.readFloat();
        ingredientMeasure = in.readString();
        ingredientName = in.readString();
    }

    @Override
    public String toString() {
        return ingredientName + " - " + ingredientQuantity + " " + ingredientMeasure;
    }

    public float getIngredientQuantity() {
        return ingredientQuantity;
    }

    public void setIngredientQuantity(float ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
    }

    public String getIngredientMeasure() {
        return ingredientMeasure;
    }

    public void setIngredientMeasure(String ingredientMeasure) {
        this.ingredientMeasure = ingredientMeasure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(ingredientQuantity);
        dest.writeString(ingredientMeasure);
        dest.writeString(ingredientName);
    }
}
