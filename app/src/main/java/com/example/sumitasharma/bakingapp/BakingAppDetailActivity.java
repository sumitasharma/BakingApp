package com.example.sumitasharma.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sumitasharma.bakingapp.adapter.RecipeStepsAdapter;
import com.example.sumitasharma.bakingapp.fragments.IngredientAndStepFragment;
import com.example.sumitasharma.bakingapp.fragments.StepVideoAndInstructionFragment;
import com.example.sumitasharma.bakingapp.utils.Recipe;
import com.example.sumitasharma.bakingapp.utils.Step;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.BakingAppMainActivity.RECIPE_OBJECT;
import static com.example.sumitasharma.bakingapp.fragments.IngredientAndStepFragment.INDEX_VALUE;
import static com.example.sumitasharma.bakingapp.fragments.IngredientAndStepFragment.STEPS;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.IS_TABLET;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.KEY_INGREDIENT;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEP_VIDEO;

public class BakingAppDetailActivity extends AppCompatActivity implements IngredientAndStepFragment.onStepClickedListener, RecipeStepsAdapter.RecipeStepsClickListener {

    private final static String TAG = BakingAppDetailActivity.class.getSimpleName();
    public int mIndex = 0;
    String mStepInstruction = null;
    String mVideoURL = null;
    private boolean twopane = false;
    private String mRecipeId;
    private ArrayList<Step> mStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_app_detail);
        Recipe mRecipe = null;
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {

            mRecipe = bundle.getParcelable(RECIPE_OBJECT);
            Log.i(TAG, "Inside recipe ingredient step detail : " + mRecipe.getIngredients().get(1).getIngredient());
            IngredientAndStepFragment ingredientAndStepFragment = new IngredientAndStepFragment();
            Bundle argsForIngredientsAndSteps = new Bundle();
            argsForIngredientsAndSteps.putParcelableArrayList(KEY_INGREDIENT, mRecipe.getIngredients());
            argsForIngredientsAndSteps.putParcelableArrayList(STEPS, mRecipe.getSteps());
            argsForIngredientsAndSteps.putBoolean(IS_TABLET, twopane);
            // mStep = argsForIngredientsAndSteps.getParcelableArrayList(STEPS);
            ingredientAndStepFragment.setArguments(argsForIngredientsAndSteps);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.baking_app_detail_fragment, ingredientAndStepFragment).commit();
        }

        if (findViewById(R.id.recipe_tablet_linear_layout) != null) {
            Log.i(TAG, "Inside onCreate and twopane true");
            twopane = true;
            if (savedInstanceState != null) {
                mIndex = savedInstanceState.getInt(INDEX_VALUE);
                mStep = savedInstanceState.getParcelableArrayList(STEPS);
                mVideoURL = savedInstanceState.getString(STEP_VIDEO);
            }
            Recipe recipe = bundle.getParcelable(RECIPE_OBJECT);
            mIndex = 0;
            Log.i(TAG, "index and mStep : " + recipe.getSteps());
            Log.i(TAG, "" + mIndex);
//                mStepInstruction = recipe.getSteps().get(mIndex).getDescription();
//                mVideoURL = mStep.get(mIndex).getVideoURL();
            Bundle argsForVideoAndInstruction = new Bundle();
            argsForVideoAndInstruction.putParcelableArrayList(STEPS, recipe.getSteps());
//                argsForVideoAndInstruction.putString(STEP_DESCRIPTION, mStepInstruction);
//                argsForVideoAndInstruction.putString(STEP_VIDEO, mVideoURL);
            argsForVideoAndInstruction.putInt(INDEX_VALUE, mIndex);
            //Log.i(TAG, "Inside recipe ingredient step video instruction detail : " + r);
            StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
            stepVideoAndInstructionFragment.setArguments(argsForVideoAndInstruction);
            FragmentManager fragmentManager = getSupportFragmentManager();
            // Add the fragment to its container using a FragmentManager and a Transaction
            fragmentManager.beginTransaction().add(R.id.baking_app_video_instruction_fragment, stepVideoAndInstructionFragment).commit();

        } else {
            twopane = false;
        }
    }

//        if (savedInstanceState != null) {
//            mIndex = savedInstanceState.getInt(INDEX_VALUE);
//            mStep = savedInstanceState.getParcelableArrayList(STEPS);
//            mVideoURL = savedInstanceState.getString(STEP_VIDEO);
//        }

        //ArrayList<Recipe> recipes = null;
//        Recipe mRecipe = null;
//        Bundle bundle = this.getIntent().getExtras();
//        if (bundle != null) {
//
//            mRecipe = bundle.getParcelable(RECIPE_OBJECT);
//            Log.i(TAG, "Inside recipe ingredient step detail : " + mRecipe.getIngredients().get(1).getIngredient());
//            IngredientAndStepFragment ingredientAndStepFragment = new IngredientAndStepFragment();
//            Bundle argsForIngredientsAndSteps = new Bundle();
//            argsForIngredientsAndSteps.putParcelableArrayList(KEY_INGREDIENT, mRecipe.getIngredients());
//            argsForIngredientsAndSteps.putParcelableArrayList(STEPS, mRecipe.getSteps());
//            argsForIngredientsAndSteps.putBoolean(IS_TABLET, twopane);
//            mStep = argsForIngredientsAndSteps.getParcelableArrayList(STEPS);
//            ingredientAndStepFragment.setArguments(argsForIngredientsAndSteps);


    //ingredientAndStepFragment.passData(this,recipe.getIngredients(),recipe.getSteps());
    // Add the fragment to its container using a FragmentManager and a Transaction
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().add(R.id.baking_app_detail_fragment, ingredientAndStepFragment).commit();
//            if (findViewById(R.id.recipe_tablet_linear_layout) != null) {
    // twopane = true;

//                mStep = bundle.getParcelableArrayList(STEPS);
//                mIndex = bundle.getInt(INDEX_VALUE);
//                Log.i(TAG, "index and mStep : " + mStep);
//                Log.i(TAG, "" + mIndex);
//                mStepInstruction = mStep.get(mIndex).getDescription();
//                mVideoURL = mStep.get(mIndex).getVideoURL();
//                Bundle argsForVideoAndInstruction = new Bundle();
//                argsForVideoAndInstruction.putParcelableArrayList(STEPS, mStep);
//                argsForVideoAndInstruction.putString(STEP_DESCRIPTION, mStepInstruction);
//                argsForVideoAndInstruction.putString(STEP_VIDEO, mVideoURL);
//                argsForVideoAndInstruction.putInt(INDEX_VALUE, mIndex);
//                Log.i(TAG, "Inside recipe ingredient step video instruction detail : " + mStepInstruction);
//                StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
//                stepVideoAndInstructionFragment.setArguments(argsForVideoAndInstruction);
//
//                // Add the fragment to its container using a FragmentManager and a Transaction
//                fragmentManager.beginTransaction().replace(R.id.baking_app_video_instruction_fragment, stepVideoAndInstructionFragment).commit();

//            } else twopane = false;
//
//
//        } else
//
//        {
//            Log.i(TAG, "Bundle is null");
//        }
//        Log.i(TAG, "Recipe name: " + mRecipe.getName());getName




    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

//
//    @Override
//    public void onStepClickedListener(ArrayList<Step> step, int index) {
////        if (!twoPane) {
////            Intent intent = new Intent();
////            intent.setClass(getActivity(), BakingAppMediaAndInstructionActivity.class);
////            intent.putExtras(args);
////            startActivity(intent);
////        } else {
//        this.mStep = step;
//        this.mIndex = index;
//    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClickStepCard(int stepCardPosition, ArrayList<Step> stepArrayList) {
        Log.i(TAG, "onClickStepCard - Interface defined in Adapter  called");
        this.mIndex = stepCardPosition;
        this.mStep = stepArrayList;
        if (!twopane) {
            Intent intent = new Intent();
            intent.setClass(this, BakingAppMediaAndInstructionActivity.class);
            Bundle args = new Bundle();
            args.putParcelableArrayList(STEPS, mStep);
            args.putInt(INDEX_VALUE, stepCardPosition);
            intent.putExtras(args);
            startActivity(intent);
        } else {
            StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
            Bundle argsForStepVideoAndInstructionFragment = new Bundle();
            argsForStepVideoAndInstructionFragment.putInt(INDEX_VALUE, mIndex);
            argsForStepVideoAndInstructionFragment.putParcelableArrayList(STEPS, mStep);
            // twopane is expected true
            argsForStepVideoAndInstructionFragment.putBoolean(IS_TABLET, twopane);
            stepVideoAndInstructionFragment.setArguments(argsForStepVideoAndInstructionFragment);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.baking_app_video_instruction_fragment, stepVideoAndInstructionFragment).commit();

        }
    }

    @Override
    public void onStepClickSelected(int stepCardPosition, ArrayList<Step> stepArrayList) {
        Log.i(TAG, "Inside onStepClickSelected Interface defined in Fragment called");
        this.mIndex = stepCardPosition;
        this.mStep = stepArrayList;
        if (!twopane) {
            Intent intent = new Intent();
            intent.setClass(this, BakingAppMediaAndInstructionActivity.class);
            Bundle args = new Bundle();
            Log.i(TAG, "onStepClickSelected: Sending mStep Description :" + mStep.get(0).getDescription());
            args.putParcelableArrayList(STEPS, mStep);
            args.putInt(INDEX_VALUE, stepCardPosition);
            intent.putExtras(args);
            startActivity(intent);
        } else {
            StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
            Bundle argsForStepVideoAndInstructionFragment = new Bundle();
            argsForStepVideoAndInstructionFragment.putInt(INDEX_VALUE, mIndex);
            argsForStepVideoAndInstructionFragment.putParcelableArrayList(STEPS, mStep);
            // twopane is expected true
            argsForStepVideoAndInstructionFragment.putBoolean(IS_TABLET, twopane);
            stepVideoAndInstructionFragment.setArguments(argsForStepVideoAndInstructionFragment);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.baking_app_video_instruction_fragment, stepVideoAndInstructionFragment).commit();

        }

    }
}
