package com.example.supjain.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.TaskStackBuilder;

import android.widget.RemoteViews;

import com.example.supjain.bakingapp.R;
import com.example.supjain.bakingapp.RecipeStepsActivity;
import com.example.supjain.bakingapp.data.RecipeData;
import com.example.supjain.bakingapp.data.RecipeIngredientsData;

import java.util.List;

public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                RecipeData data, int appWidgetId) {
        // Create an Intent to launch RecipeStepsActivity when clicked
        Intent intent = new Intent(context, RecipeStepsActivity.class);
        intent.putExtra(RecipeStepsActivity.RECIPE_DATA_OBJ_KEY, data);

        // Use TaskStackBuilder to build the back stack and get the PendingIntent
        PendingIntent pendingIntent =
                TaskStackBuilder.create(context)
                        // add all of RecipeStepsActivity's parents to the stack (MainActivity),
                        // followed by RecipeStepsActivity itself
                        .addNextIntentWithParentStack(intent)
                        .getPendingIntent(appWidgetId, PendingIntent.FLAG_UPDATE_CURRENT);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.widget_recipe_ingredients_list, pendingIntent);
        views.setTextViewText(R.id.widget_recipe_ingredients_list,
                getIngredientsList(data.getRecipeIngredients()));
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                        RecipeData data, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds)
            updateAppWidget(context, appWidgetManager, data, appWidgetId);
    }

    // This method takes list of ingredients data and return a String containing information
    // about all ingredients of the recipe, to be displayed in the widget.
    private static String getIngredientsList(List<RecipeIngredientsData> dataList) {
        String ingredientsList = "Ingredients: ";
        if (dataList != null && !dataList.isEmpty()) {
            for (RecipeIngredientsData ingredientsData : dataList)
                ingredientsList = ingredientsList + "\n\t\t\t" + ingredientsData.toString();
        }
        return ingredientsList;
    }
}
