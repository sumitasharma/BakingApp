package com.example.sumitasharma.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.sumitasharma.bakingapp.BakingAppDetailActivity;
import com.example.sumitasharma.bakingapp.BakingAppMainActivity;
import com.example.sumitasharma.bakingapp.R;
import com.example.sumitasharma.bakingapp.utils.Recipe;

import java.util.ArrayList;


/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int index, ArrayList<Recipe> recipeArrayList,
                                        int appWidgetId) {

        Log.i("BakingAppWidgetProvider", "Inside updateAppWidget");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        //Set the Image to Baking Dish
        //  views.setTextViewText(R.id.baking_app_widget_text_view,recipeArrayList.get(index).getName());
        views.setImageViewResource(R.id.baking_app_widget_image_view, R.drawable.baking_image);
        //Create and Launch BakingAppMainActivity when clicked
        Intent intent = new Intent(context, BakingAppDetailActivity.class);
//        Bundle b = new Bundle();
//        b.putParcelable(RECIPE_OBJECT, recipeArrayList.get(index));
//        intent.putExtras(b);
//        Log.i("","inside intent bundle"+recipeArrayList.get(index).getName());
        intent.setClass(context, BakingAppMainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //Click Handler on Widget to launch Pending Intent
        views.setOnClickPendingIntent(R.id.baking_app_widget_text_view, pendingIntent);
        views.setOnClickPendingIntent(R.id.baking_app_widget_image_view, pendingIntent);
//        Picasso.with(context).load(DEFAULT_THUMBNAIL).into(R.id.baking_app_widget_image_view,0, appWidgetIdsArrayList);
//       views.setImageViewBitmap(R.id.baking_app_widget_image_view,DEFAULT_THUMBNAIL);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateBakingAppWidget(Context context, AppWidgetManager appWidgetManager,
                                             int index, ArrayList<Recipe> recipeArrayList, int[] appWidgetIds) {
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

