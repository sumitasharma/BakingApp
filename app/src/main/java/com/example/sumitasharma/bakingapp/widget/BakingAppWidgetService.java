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
import java.util.Random;


public class BakingAppWidgetService extends IntentService {
    private static final String ACTION_DISPLAY_RECIPE_INGREDIENTS = "com.example.sumitasharma.bakingapp.action.display_ingredients";

    public BakingAppWidgetService() {
        super("BakingAppWidgetService");
    }

    public static void startActionUpdateBakingApp(Context context) {
        Intent intent = new Intent(context, BakingAppWidgetService.class);
        intent.setAction(ACTION_DISPLAY_RECIPE_INGREDIENTS);
        context.startService(intent);
    }

    private void handleActionDisplayRecipeIngredients() {
        int mRecipeIndex;
        int max = 0;
        ArrayList<Recipe> mRecipeArrayList = null;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));
        Log.i("BakingAppWidgetService", "Inside handleActionDisplayRecipeIngredients");

        try {
            mRecipeArrayList = BakingUtils.convertJsonToRecipeObjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mRecipeArrayList != null) {
            max = mRecipeArrayList.size();
        }
        Random random = new Random();
        mRecipeIndex = random.nextInt(max);

        Log.i("BakingAppWidgetService", "Adding randomIndex before calling Provider: " + mRecipeIndex);

        BakingAppWidgetProvider.updateBakingAppWidget(this, appWidgetManager, mRecipeIndex, mRecipeArrayList, appWidgetIds);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            if (ACTION_DISPLAY_RECIPE_INGREDIENTS.equals(intent.getAction())) {
                handleActionDisplayRecipeIngredients();
            }

        }

    }
}


