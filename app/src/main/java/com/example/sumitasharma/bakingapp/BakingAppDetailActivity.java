package com.example.sumitasharma.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sumitasharma.bakingapp.utils.Recipe;

import static com.example.sumitasharma.bakingapp.BakingAppMainActivity.RECIPE_OBJECT;

public class BakingAppDetailActivity extends AppCompatActivity {
    private final static String TAG = BakingAppDetailActivity.class.getSimpleName();
    boolean twopane;
    private String mRecipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_app_detail);

        //ArrayList<Recipe> recipes = null;
        Recipe recipe = null;
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null)
            recipe = bundle.getParcelable(RECIPE_OBJECT);
        else
            Log.i(TAG, "Bundle is null");
        Log.i(TAG, "Recipe name: " + recipe.getName());

    }
}
