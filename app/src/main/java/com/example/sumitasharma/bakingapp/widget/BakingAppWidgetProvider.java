package com.example.sumitasharma.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.sumitasharma.bakingapp.BakingAppDetailActivity;
import com.example.sumitasharma.bakingapp.R;
import com.example.sumitasharma.bakingapp.utils.Ingredient;
import com.example.sumitasharma.bakingapp.utils.Recipe;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.utils.BakingUtils.RECIPE_OBJECT;


/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    int mRecipeIndex;
    ArrayList<Recipe> mRecipeArrayList;

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int index, ArrayList<Recipe> recipeArrayList,
                                        int appWidgetId) {

        Log.i("BakingAppWidgetProvider", "Inside updateAppWidget");
        // Construct the RemoteViews object
        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        widgetView.setTextViewText(R.id.baking_app_recipe_title_text_view, recipeArrayList.get(index).getName());
        ArrayList<Ingredient> ingredientArrayList = recipeArrayList.get(index).getIngredients();
        StringBuffer ingredient = new StringBuffer(context.getResources().getString(R.string.ingredients));
        ingredient.append("\n");

        //Counter to print ingredient numbers.
        int i = 1;
        for (Ingredient ingredientText : ingredientArrayList) {
            ingredient.append("\n");
            ingredient.append(i);
            ingredient.append("  ");
            StringBuilder ingredientTextCapital = new StringBuilder(String.valueOf(ingredientText.getIngredient().charAt(0)).toUpperCase() + ingredientText.getIngredient().substring(1, ingredientText.getIngredient().length()));
            ingredient.append(ingredientTextCapital);
            i++;

        }
        widgetView.setTextViewText(R.id.baking_app_widget_ingredient_view, ingredient);

        //Create and Launch BakingAppMainActivity when clicked
        Intent intent = new Intent(context, BakingAppDetailActivity.class);
        Bundle b = new Bundle();
        Log.i("widget", "random number :" + index);
        b.putParcelable(RECIPE_OBJECT, recipeArrayList.get(index));
        intent.putExtras(b);
        Log.i("BakingAppWidgetProvider", "Got Index in Provider, putting in intent:" + index);
        //Log.i("","inside intent bundle"+recipeArrayList.get(index).getName());
        intent.setClass(context, BakingAppDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //Click Handler on Widget to launch Pending Intent
        widgetView.setOnClickPendingIntent(R.id.baking_app_recipe_title_text_view, pendingIntent);
        widgetView.setOnClickPendingIntent(R.id.baking_app_widget_ingredient_view, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, widgetView);
    }


    static void updateBakingAppWidget(Context context, AppWidgetManager appWidgetManager, int index, ArrayList<Recipe> recipeArrayList,
                                      int[] appWidgetIds) {
        Log.i("BakingAppWidgetProvider", "Inside updateBakingAppWidget");

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, index, recipeArrayList, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        BakingAppWidgetService.startActionUpdateBakingApp(context);


    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}



