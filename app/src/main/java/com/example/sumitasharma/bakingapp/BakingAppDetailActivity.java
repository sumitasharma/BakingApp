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

import static com.example.sumitasharma.bakingapp.utils.BakingUtils.INDEX_VALUE;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.IS_TABLET;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.KEY_INGREDIENT;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.RECIPE_OBJECT;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEPS;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEP_VIDEO;


/**
 * This Java Class calls "activity_baking_app_detail.xml" which attaches fragment "IngredientAndStepFragment" which in
 * turn calls "recipe_ingredientdandstep_fragment.xml" if called from a phone or calls "activity_baking_app_detail.xml"
 * which attaches two fragments namely "IngredientAndStepFragment" and "StepVideoAndInstructionFragment" which in turn
 * calls "recipe_ingredientdandstep_fragment.xml" and "step_video_instruction_fragment.xml" respectively if called from
 * a Tablet.
 */

public class BakingAppDetailActivity extends AppCompatActivity implements IngredientAndStepFragment.onStepClickedListener, RecipeStepsAdapter.RecipeStepsClickListener, StepVideoAndInstructionFragment.PassTitle, StepVideoAndInstructionFragment.PassSavedInstanceState {

    private final static String TAG = BakingAppDetailActivity.class.getSimpleName();
    public int mIndex = 0;
    String mVideoURL = null;
    private boolean twopane = false;
    private ArrayList<Step> mStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_app_detail);
        Recipe mRecipe = null;
        Bundle bundle = this.getIntent().getExtras();

        /**
         * Checks if class is called from an Intent which was called by "BakingAppMainActivity" and
         * Recipe object was passed as an argument.
         */


        if (findViewById(R.id.recipe_tablet_linear_layout) != null) {
            //Checking if it is a tablet
            mIndex = 0;
            Log.i(TAG, "Inside onCreate and twopane true");
            twopane = true;
            if (savedInstanceState == null) {
                Recipe recipe = bundle.getParcelable(RECIPE_OBJECT);
                getSupportActionBar().setTitle(recipe.getName());

                Log.i(TAG, "index and mStep : " + recipe.getSteps());
                Log.i(TAG, "" + mIndex);
                Bundle argsForVideoAndInstruction = new Bundle();
                argsForVideoAndInstruction.putParcelableArrayList(STEPS, recipe.getSteps());
                argsForVideoAndInstruction.putInt(INDEX_VALUE, mIndex);
                argsForVideoAndInstruction.putBoolean(IS_TABLET, twopane);
                StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
                stepVideoAndInstructionFragment.setArguments(argsForVideoAndInstruction);
                FragmentManager fragmentManager = getSupportFragmentManager();
                // Add the fragment to its container using a FragmentManager and a Transaction
                fragmentManager.beginTransaction().add(R.id.baking_app_video_instruction_fragment, stepVideoAndInstructionFragment).commit();
            } else
                twopane = false;


        }

        if (bundle != null) {

            mRecipe = bundle.getParcelable(RECIPE_OBJECT);
            getSupportActionBar().setTitle(mRecipe.getName());
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
        /**
         *  Checking if the application is running on a Tablet or phone. Variable "twopane" is true if it is indeed a Tablet
         */

            if (savedInstanceState != null) {
                mIndex = savedInstanceState.getInt(INDEX_VALUE);
                mStep = savedInstanceState.getParcelableArrayList(STEPS);
                mVideoURL = savedInstanceState.getString(STEP_VIDEO);
                mIndex = savedInstanceState.getInt(INDEX_VALUE);
                mStep = savedInstanceState.getParcelableArrayList(STEPS);
                mVideoURL = savedInstanceState.getString(STEP_VIDEO);
                Log.i(TAG, "Index value in BakingAppDetailActivity and savedInstanceState not null :" + mIndex);
//            StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
//            Bundle argsForStepVideoAndInstructionFragment = new Bundle();
//            argsForStepVideoAndInstructionFragment.putInt(INDEX_VALUE, mIndex);
//            argsForStepVideoAndInstructionFragment.putParcelableArrayList(STEPS, mStep);
//            // twopane is expected true
//            argsForStepVideoAndInstructionFragment.putBoolean(IS_TABLET, true);
//            stepVideoAndInstructionFragment.setArguments(argsForStepVideoAndInstructionFragment);
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.baking_app_video_instruction_fragment, stepVideoAndInstructionFragment).commit();

            }
//            if (findViewById(R.id.recipe_tablet_linear_layout) != null) {
//                //Checking if it is a tablet
//                mIndex = 0;
//                Log.i(TAG, "Inside onCreate and twopane true");
//                twopane = true;

//            if (savedInstanceState != null) {
//                mIndex = savedInstanceState.getInt(INDEX_VALUE);
//                mStep = savedInstanceState.getParcelableArrayList(STEPS);
//                mVideoURL = savedInstanceState.getString(STEP_VIDEO);
//            }
//                Recipe recipe = bundle.getParcelable(RECIPE_OBJECT);
//                getSupportActionBar().setTitle(recipe.getName());
//
//                Log.i(TAG, "index and mStep : " + recipe.getSteps());
//                Log.i(TAG, "" + mIndex);
//                Bundle argsForVideoAndInstruction = new Bundle();
//                argsForVideoAndInstruction.putParcelableArrayList(STEPS, recipe.getSteps());
//                argsForVideoAndInstruction.putInt(INDEX_VALUE, mIndex);
//                argsForVideoAndInstruction.putBoolean(IS_TABLET, twopane);
//                StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
//                stepVideoAndInstructionFragment.setArguments(argsForVideoAndInstruction);
//                fragmentManager = getSupportFragmentManager();
//                // Add the fragment to its container using a FragmentManager and a Transaction
//                fragmentManager.beginTransaction().add(R.id.baking_app_video_instruction_fragment, stepVideoAndInstructionFragment).commit();
//
//            } else
//            twopane = false;
//        }
//        else if (savedInstanceState != null) {
//            mIndex = savedInstanceState.getInt(INDEX_VALUE);
//            mStep = savedInstanceState.getParcelableArrayList(STEPS);
//            mVideoURL = savedInstanceState.getString(STEP_VIDEO);
//            Log.i(TAG,"Index value in BakingAppDetailActivity and savedInstanceState not null :"+mIndex);
////            StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
////            Bundle argsForStepVideoAndInstructionFragment = new Bundle();
////            argsForStepVideoAndInstructionFragment.putInt(INDEX_VALUE, mIndex);
////            argsForStepVideoAndInstructionFragment.putParcelableArrayList(STEPS, mStep);
////            // twopane is expected true
////            argsForStepVideoAndInstructionFragment.putBoolean(IS_TABLET, true);
////            stepVideoAndInstructionFragment.setArguments(argsForStepVideoAndInstructionFragment);
////            FragmentManager fragmentManager = getSupportFragmentManager();
////            fragmentManager.beginTransaction().replace(R.id.baking_app_video_instruction_fragment, stepVideoAndInstructionFragment).commit();
//
//        }

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


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
            Log.i(TAG, "Index value from onClickStepCard :" + mIndex);
            argsForStepVideoAndInstructionFragment.putInt(INDEX_VALUE, mIndex);
            argsForStepVideoAndInstructionFragment.putParcelableArrayList(STEPS, mStep);
            // twopane is expected true
            argsForStepVideoAndInstructionFragment.putBoolean(IS_TABLET, true);
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
            Log.i(TAG, "Index value from onStepClickSelected :" + mIndex);
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
    public void sendTitleForActionBar(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void sendPassSavedInstanceState(int index, ArrayList<Step> stepArrayList, String videoURL) {
        this.mStep = stepArrayList;
        this.mIndex = index;
        this.mVideoURL = videoURL;
        Log.i(TAG, "Index value received from Interface sendPassSavedInstanceState on DetailActivity: " + mIndex);
    }

}
