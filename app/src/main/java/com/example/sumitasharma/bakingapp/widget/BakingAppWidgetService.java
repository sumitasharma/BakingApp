package com.example.sumitasharma.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.sumitasharma.bakingapp.utils.BakingUtils;
import com.example.sumitasharma.bakingapp.utils.Recipe;

import java.util.ArrayList;


public class BakingAppWidgetService extends IntentService {
    private static final String CHANGE_RECIPE = "change_recipe";
    int mIndex = 0;
    ArrayList<Recipe> mRecipe;

    public BakingAppWidgetService() {
        super("BakingAppWidgetService");
    }

    public static void startActionUpdateBakingApp(Context context) {
        Intent intent = new Intent(context, BakingAppWidgetService.class);
        intent.setAction(CHANGE_RECIPE);
        context.startService(intent);
    }

    public void getRecipeObjectFromJSON() {
        try {
            mRecipe = BakingUtils.convertJsonToRecipeObjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            if (CHANGE_RECIPE.equals(intent.getAction())) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));
                //Now update all widgets
                getRecipeObjectFromJSON();
                if (mIndex < mRecipe.size())
                    mIndex++;
                else
                    mIndex = 0;
                Log.i("", "recipe and index" + mRecipe);
                BakingAppWidgetProvider.updateBakingAppWidget(this, appWidgetManager, mIndex, mRecipe, appWidgetIds);
            }

        }

    }
}
