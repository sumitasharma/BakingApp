package com.example.sumitasharma.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sumitasharma.bakingapp.fragments.IngredientAndStepFragment;
import com.example.sumitasharma.bakingapp.utils.Recipe;

import static com.example.sumitasharma.bakingapp.BakingAppMainActivity.RECIPE_OBJECT;

public class BakingAppDetailActivity extends AppCompatActivity {
    public final static String INGREDIENT = "ingredients";
    public final static String STEPS = "steps";
    private final static String TAG = BakingAppDetailActivity.class.getSimpleName();
    boolean twopane;
    private String mRecipeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_app_detail);

        //ArrayList<Recipe> recipes = null;
        Recipe mRecipe = null;
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {

            mRecipe = bundle.getParcelable(RECIPE_OBJECT);
            Log.i(TAG, "Inside recipe ingredient step detail : " + mRecipe.getIngredients().get(1).getIngredient());
            IngredientAndStepFragment ingredientAndStepFragment = new IngredientAndStepFragment();
            Bundle args = new Bundle();
            args.putParcelableArrayList(INGREDIENT, mRecipe.getIngredients());
            args.putParcelableArrayList(STEPS, mRecipe.getSteps());
            ingredientAndStepFragment.setArguments(args);
            //ingredientAndStepFragment.passData(this,recipe.getIngredients(),recipe.getSteps());
            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.baking_app_detail_fragment, ingredientAndStepFragment).commit();


        } else {
            Log.i(TAG, "Bundle is null");
        }
        Log.i(TAG, "Recipe name: " + mRecipe.getName());


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onCreate(savedInstanceState);
    }
}
