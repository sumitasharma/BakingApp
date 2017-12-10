package com.example.sumitasharma.bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sumitasharma.bakingapp.fragments.IngredientAndStepFragment;
import com.example.sumitasharma.bakingapp.fragments.StepVideoAndInstructionFragment;
import com.example.sumitasharma.bakingapp.utils.Recipe;
import com.example.sumitasharma.bakingapp.utils.Step;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.BakingAppMainActivity.RECIPE_OBJECT;
import static com.example.sumitasharma.bakingapp.BakingAppMediaAndInstructionActivity.STEP_DESCRIPTION;
import static com.example.sumitasharma.bakingapp.BakingAppMediaAndInstructionActivity.STEP_VIDEO;
import static com.example.sumitasharma.bakingapp.fragments.IngredientAndStepFragment.INDEX_VALUE;

public class BakingAppDetailActivity extends AppCompatActivity implements IngredientAndStepFragment.PassValues {
    public final static String IS_TABLET = "is_tablet";
    public final static String INGREDIENT = "ingredients";
    public final static String STEPS = "steps";
    private final static String TAG = BakingAppDetailActivity.class.getSimpleName();
    public int mIndex = 0;
    boolean twopane;
    String mStepInstruction = null;
    String mVideoURL = null;
    private String mRecipeId;
    private ArrayList<Step> mStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_app_detail);
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt(INDEX_VALUE);
            mStep = savedInstanceState.getParcelableArrayList(STEPS);
            mVideoURL = savedInstanceState.getString(STEP_VIDEO);
        }

        //ArrayList<Recipe> recipes = null;
        Recipe mRecipe = null;
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {

            mRecipe = bundle.getParcelable(RECIPE_OBJECT);
            Log.i(TAG, "Inside recipe ingredient step detail : " + mRecipe.getIngredients().get(1).getIngredient());
            IngredientAndStepFragment ingredientAndStepFragment = new IngredientAndStepFragment();
            Bundle argsForIngredientsAndSteps = new Bundle();
            argsForIngredientsAndSteps.putParcelableArrayList(INGREDIENT, mRecipe.getIngredients());
            argsForIngredientsAndSteps.putParcelableArrayList(STEPS, mRecipe.getSteps());
            argsForIngredientsAndSteps.putBoolean(IS_TABLET, twopane);
            mStep = argsForIngredientsAndSteps.getParcelableArrayList(STEPS);
            ingredientAndStepFragment.setArguments(argsForIngredientsAndSteps);


            //ingredientAndStepFragment.passData(this,recipe.getIngredients(),recipe.getSteps());
            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.baking_app_detail_fragment, ingredientAndStepFragment).commit();
            if (findViewById(R.id.recipe_tablet_linear_layout) != null) {
                twopane = true;

//                mStep = bundle.getParcelableArrayList(STEPS);
//                mIndex = bundle.getInt(INDEX_VALUE);
                Log.i(TAG, "index and mStep : " + mStep);
                Log.i(TAG, "" + mIndex);
                mStepInstruction = mStep.get(mIndex).getDescription();
                mVideoURL = mStep.get(mIndex).getVideoURL();
                Bundle argsForVideoAndInstruction = new Bundle();
                argsForVideoAndInstruction.putParcelableArrayList(STEPS, mStep);
                argsForVideoAndInstruction.putString(STEP_DESCRIPTION, mStepInstruction);
                argsForVideoAndInstruction.putString(STEP_VIDEO, mVideoURL);
                argsForVideoAndInstruction.putInt(INDEX_VALUE, mIndex);
                Log.i(TAG, "Inside recipe ingredient step video instruction detail : " + mStepInstruction);
                StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
                stepVideoAndInstructionFragment.setArguments(argsForVideoAndInstruction);

                // Add the fragment to its container using a FragmentManager and a Transaction
                fragmentManager.beginTransaction().replace(R.id.baking_app_video_instruction_fragment, stepVideoAndInstructionFragment).commit();

            } else twopane = false;


        } else

        {
            Log.i(TAG, "Bundle is null");
        }
        Log.i(TAG, "Recipe name: " + mRecipe.getName());


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public void passValuesToActivity(ArrayList<Step> step, int index) {
        this.mStep = step;
        this.mIndex = index;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
