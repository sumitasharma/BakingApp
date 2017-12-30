package com.example.sumitasharma.bakingapp.loader;


import android.content.Context;
import android.content.Loader;
import android.util.Log;

class RecipeIngredientAndStepLoader extends Loader {
    private final static String TAG = RecipeIngredientAndStepLoader.class.getSimpleName();

    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    private RecipeIngredientAndStepLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
        Log.i(TAG, "Inside onStartLoading Recipe");
    }
}


