package com.example.sumitasharma.bakingapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.TITLE;


/**
 * This Java Class calls "activity_baking_app_detail.xml" which attaches fragment "IngredientAndStepFragment" which in
 * turn calls "recipe_ingredientdandstep_fragment.xml" if called from a phone or calls "activity_baking_app_detail.xml"
 * which attaches two fragments namely "IngredientAndStepFragment" and "StepVideoAndInstructionFragment" which in turn
 * calls "recipe_ingredientdandstep_fragment.xml" and "step_video_instruction_fragment.xml" respectively if called from
 * a Tablet.
 */

public class BakingAppDetailActivity extends AppCompatActivity implements IngredientAndStepFragment.onStepClickedListener, StepVideoAndInstructionFragment.PassTitle, StepVideoAndInstructionFragment.PassSavedInstanceState, IngredientAndStepFragment.PutTheTitle {

    private final static String TAG = BakingAppDetailActivity.class.getSimpleName();
    public int mIndex = 0;
    String mVideoURL = null;
    private boolean mTwoPane = false;
    private ArrayList<Step> mStep;
    private String mTitle;

    //private StepVideoAndInstructionFragment mStepVideoAndInstructionFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_app_detail);
        Recipe mRecipe = null;
        Bundle bundle = this.getIntent().getExtras();
        Log.i(TAG, "onCreate called");
        if (findViewById(R.id.recipe_tablet_linear_layout) != null) {
            //Checking if it is a tablet
            Log.i(TAG, "Found Tablet interface, setting twoPane to true");
            mTwoPane = true;
        } else {
            Log.i(TAG, "Found Phone interface, setting twoPane to false");
            mTwoPane = false;
        }
        if (savedInstanceState != null) {
            Log.i(TAG, "SavedInstance state is not null, Getting data from SavedInstanceState");
            mIndex = savedInstanceState.getInt(INDEX_VALUE);
            mStep = savedInstanceState.getParcelableArrayList(STEPS);
            mTitle = savedInstanceState.getString(TITLE);
            // mTwoPane = savedInstanceState.getBoolean(IS_TABLET);
            Log.i(TAG, "Index value got from SavedInstance:" + mIndex);
            Log.i(TAG, "mStep value got from SavedInstance:" + mStep);
            Log.i(TAG, "mTwoPane value got from SavedInstance:" + mTwoPane);
            Log.i(TAG, "mTitle from savedInstanceState:" + mTitle);

        } else {
            Log.i(TAG, "Bundle is not null, Getting data from bundle");
            mRecipe = bundle.getParcelable(RECIPE_OBJECT);
            mTitle = mRecipe.getName();
            getSupportActionBar().setTitle(mTitle);
            mStep = mRecipe.getSteps();
            Log.i(TAG, "mIndex value got from bundle:" + mIndex);
            Log.i(TAG, "mTwoPane value got from bundle:" + mTwoPane);
            Log.i(TAG, "Inside recipe ingredient step detail : " + mRecipe.getIngredients().get(1).getIngredient());
            IngredientAndStepFragment ingredientAndStepFragment = new IngredientAndStepFragment();
            Bundle argsForIngredientsAndSteps = new Bundle();
            argsForIngredientsAndSteps.putParcelableArrayList(KEY_INGREDIENT, mRecipe.getIngredients());
            argsForIngredientsAndSteps.putParcelableArrayList(STEPS, mRecipe.getSteps());
            argsForIngredientsAndSteps.putBoolean(IS_TABLET, mTwoPane);
            argsForIngredientsAndSteps.putString(TITLE, mTitle);
            ingredientAndStepFragment.setArguments(argsForIngredientsAndSteps);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.baking_app_detail_fragment, ingredientAndStepFragment).commit();


            if (mTwoPane) {

                getSupportActionBar().setTitle(mTitle);


                Log.i(TAG, "Sending data to StepVideoAndInstructionFragment, mIndex" + mIndex + ",mTwoPane:" + mTwoPane);
                Log.i(TAG, "mTwoPane value got from Tablet StepVideoAndInstructionFragment:" + mTwoPane);
                Bundle argsForVideoAndInstruction = new Bundle();
                argsForVideoAndInstruction.putParcelableArrayList(STEPS, mStep);
                argsForVideoAndInstruction.putInt(INDEX_VALUE, mIndex);
                argsForVideoAndInstruction.putBoolean(IS_TABLET, mTwoPane);
                StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
                stepVideoAndInstructionFragment.setArguments(argsForVideoAndInstruction);
                fragmentManager = getSupportFragmentManager();
                // Add the fragment to its container using a FragmentManager and a Transaction
                fragmentManager.beginTransaction().replace(R.id.baking_app_video_instruction_fragment, stepVideoAndInstructionFragment).commit();
            }
        }


        // Checks if class is called from an Intent which was called by "BakingAppMainActivity" and
        // Recipe object was passed as an argument.

    }


//    @Override
//    public void onClickStepCard(int stepCardPosition, ArrayList<Step> stepArrayList) {
//        Log.i(TAG, "onClickStepCard - Interface defined in Adapter called");
//        this.mIndex = stepCardPosition;
//        this.mStep = stepArrayList;
//        if (!this.mTwoPane) {
//            Log.i(TAG, "Found Phone, starting new activity, mIndex" + stepCardPosition);
//            Intent intent = new Intent();
//            intent.setClass(this, BakingAppMediaAndInstructionActivity.class);
//            Bundle args = new Bundle();
//            args.putParcelableArrayList(STEPS, mStep);
//            args.putInt(INDEX_VALUE, stepCardPosition);
//            intent.putExtras(args);
//            startActivity(intent);
//        } else {
//
//            Log.i(TAG, "Found Tablet, sending data to fragment, ,mIndex" + mIndex);
//            StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
//            Bundle argsForStepVideoAndInstructionFragment = new Bundle();
//            Log.i(TAG, "Index value from onClickStepCard :" + mIndex);
//            argsForStepVideoAndInstructionFragment.putInt(INDEX_VALUE, mIndex);
//            argsForStepVideoAndInstructionFragment.putParcelableArrayList(STEPS, mStep);
//            // mTwoPane is expected true
//            argsForStepVideoAndInstructionFragment.putBoolean(IS_TABLET, true);
//            stepVideoAndInstructionFragment.setArguments(argsForStepVideoAndInstructionFragment);
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.baking_app_video_instruction_fragment, stepVideoAndInstructionFragment).commit();
//        }
//    }

    @Override
    public void onStepClickSelected(int stepCardPosition, ArrayList<Step> stepArrayList, boolean twopane) {
        Log.i(TAG, "Inside onStepClickSelected Interface defined in Fragment called");
        this.mIndex = stepCardPosition;
        this.mStep = stepArrayList;
        this.mTwoPane = twopane;
        if (!twopane) {
            Log.i(TAG, "Found Phone, starting new activity, mIndex" + stepCardPosition);
            Intent intent = new Intent();
            intent.setClass(this, BakingAppMediaAndInstructionActivity.class);
            Bundle args = new Bundle();
            Log.i(TAG, "onStepClickSelected: Sending mStep Description :" + mStep.get(0).getDescription());
            args.putParcelableArrayList(STEPS, mStep);
            args.putInt(INDEX_VALUE, stepCardPosition);
            intent.putExtras(args);
            startActivity(intent);
        } else {
            Log.i(TAG, "Found Tablet, sending data to fragment, ,mIndex" + mIndex);
            //getFragmentManager().getFragment("","")

            StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
            Bundle argsForStepVideoAndInstructionFragment = new Bundle();
            //Log.i(TAG, "Index value from onStepClickSelected :" + mIndex);
            argsForStepVideoAndInstructionFragment.putInt(INDEX_VALUE, mIndex);
            argsForStepVideoAndInstructionFragment.putParcelableArrayList(STEPS, mStep);
            // mTwoPane is expected true
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
    public void sendPassSavedInstanceState(int index, ArrayList<Step> stepArrayList, String videoURL, boolean mTwoPane) {
        this.mStep = stepArrayList;
        this.mIndex = index;
        this.mVideoURL = videoURL;
        this.mTwoPane = mTwoPane;
        Log.i(TAG, "Index value received from Interface sendPassSavedInstanceState on DetailActivity: " + mIndex);
        Log.i(TAG, "mStep value received from Interface sendPassSavedInstanceState on DetailActivity: " + mStep);
    }

    @Override
    public void giveTheTitle(String title) {
        this.mTitle = title;
        Log.i(TAG, "Title recieved :" + mTitle);
    }
//
//    @Override
//    public void giveTheTitle(String title) {
//        mTitle = title;
//        Log.i(TAG,"Title recieved :"+mTitle);
//    }

//    @Override
//    public void PassSavedInstanceToActivity(int index, ArrayList<Step> step, boolean mTwoPane) {
//        this.mStep = step;
//        this.mIndex = index;
//        this.mTwoPane = mTwoPane;
//        Log.i(TAG, "Index value received from Interface PassSavedInstanceToActivity on DetailActivity: " + mIndex);
//        Log.i(TAG, "mStep value received from Interface PassSavedInstanceToActivity on DetailActivity: " + mStep);
//    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putString(TITLE,mTitle);
//    }


}
