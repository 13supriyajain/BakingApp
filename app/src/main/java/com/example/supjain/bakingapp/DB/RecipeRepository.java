package com.example.supjain.bakingapp.DB;

import android.app.Application;
import android.os.AsyncTask;

import com.example.supjain.bakingapp.data.RecipeData;

import java.util.List;

import androidx.lifecycle.LiveData;

public class RecipeRepository {

    private RecipeDao recipeDao;
    private LiveData<List<RecipeData>> recipeDataList;

    public RecipeRepository(Application application) {
        RecipeDatabase db = RecipeDatabase.getDatabase(application);
        recipeDao = db.recipeDao();
        recipeDataList = recipeDao.getAllRecipes();
    }

    public LiveData<List<RecipeData>> getRecipes() {
        return recipeDataList;
    }

    public void insertRecipes(List<RecipeData> recipeDataList) {
        new RecipeAsyncTask(recipeDao).execute(recipeDataList);
    }

    private static class RecipeAsyncTask extends AsyncTask<List<RecipeData>, Void, Void> {

        private RecipeDao mAsyncTaskDao;

        RecipeAsyncTask(RecipeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<RecipeData>... params) {

            mAsyncTaskDao.insertRecipes(params[0]);
            return null;
        }
    }
}
