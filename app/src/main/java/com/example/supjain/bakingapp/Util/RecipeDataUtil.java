package com.example.supjain.bakingapp.Util;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.example.supjain.bakingapp.R;
import com.example.supjain.bakingapp.data.RecipeData;
import com.example.supjain.bakingapp.data.RecipeIngredientsData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 *  This is a helper class containing helper methods to fetch and extract recipe data from query url.
 */
public class RecipeDataUtil {

    private static final String QUERY_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    private static Retrofit retrofitInstance;

    public static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = new retrofit2.Retrofit.Builder()
                    .baseUrl(QUERY_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

    // Calculate number of columns to be displayed in GridView
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    // Iterate over list of RecipeIngredientsData Object and convert them into list of strings,
    // containing information about each ingredient.
    public static ArrayList<String> getListOfIngredients(Resources resource,
                                                         List<RecipeIngredientsData> dataList){
        ArrayList<String> ingredientsList = null;
        if (dataList != null && !dataList.isEmpty()) {
            ingredientsList = new ArrayList<>();
            ingredientsList.add(resource.getString(R.string.ingredients_list_title_text));
            for (RecipeIngredientsData ingredientsData : dataList)
                ingredientsList.add(ingredientsData.toString());
        }
        return ingredientsList;
    }

    // Check if device has network connection
    public static boolean hasNetworkConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public interface RecipeDataFetchService {
        @GET("baking.json")
        Call<RecipeData[]> getRecipeData();
    }
}
