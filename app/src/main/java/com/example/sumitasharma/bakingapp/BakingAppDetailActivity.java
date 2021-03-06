package com.example.sumitasharma.bakingapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.sumitasharma.bakingapp.fragments.IngredientAndStepFragment;
import com.example.sumitasharma.bakingapp.fragments.StepVideoAndInstructionFragment;
import com.example.sumitasharma.bakingapp.utils.Recipe;
import com.example.sumitasharma.bakingapp.utils.Step;

import java.util.ArrayList;

import timber.log.Timber;

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

public class BakingAppDetailActivity extends AppCompatActivity implements IngredientAndStepFragment.onStepClickedListener, StepVideoAndInstructionFragment.PassTitle, StepVideoAndInstructionFragment.PassSavedInstanceState, IngredientAndStepFragment.PutTheDataInActivity {

    private final static String TAG = BakingAppDetailActivity.class.getSimpleName();
    private int mIndex = 0;
    //private String mVideoURL = null;
    private boolean mTwoPane = false;
    private ArrayList<Step> mStep;
    private String mTitle;
    private String mTwoPaneTitle;
    //private StepVideoAndInstructionFragment mStepVideoAndInstructionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_app_detail);
        Recipe mRecipe;
        Bundle bundle = this.getIntent().getExtras();

        //Checking if it is a tablet
        mTwoPane = findViewById(R.id.recipe_tablet_linear_layout) != null;
        if (savedInstanceState != null) {
            // Timber.i( "SavedInstance state is not null, Getting data from SavedInstanceState");
            Timber.i("SavedInstance state is not null, Getting data from SavedInstanceState");
            mIndex = savedInstanceState.getInt(INDEX_VALUE);
            mStep = savedInstanceState.getParcelableArrayList(STEPS);
            mTitle = savedInstanceState.getString(TITLE);
        } else {
            //  Timber.i( "Bundle is not null, Getting data from bundle");
            Timber.i("Bundle is not null, Getting data from bundle");
            if (bundle != null) {
                mRecipe = bundle.getParcelable(RECIPE_OBJECT);
                if (mRecipe != null) {
                    mTitle = mRecipe.getName();
                    //Timber.i( "Title Received in BakingAppDetailActivity:" + mTitle);
                    Timber.i("Title Received in BakingAppDetailActivity:" + mTitle);
                    mTwoPaneTitle = mRecipe.getName();
                    mStep = mRecipe.getSteps();
                }
                getSupportActionBar().setTitle(mTitle);
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
                    getSupportActionBar().setTitle(mTwoPaneTitle);
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
        }


        // Checks if class is called from an Intent which was called by "BakingAppMainActivity" and
        // Recipe object was passed as an argument.

    }


    @Override
    public void onStepClickSelected(int stepCardPosition, ArrayList<Step> stepArrayList, boolean twopane) {
        Timber.i("Inside onStepClickSelected Interface defined in Fragment called");
        this.mIndex = stepCardPosition;
        this.mStep = stepArrayList;
        this.mTwoPane = twopane;
        if (!twopane) {
            //Timber.i( "Found Phone, starting new activity, mIndex" + stepCardPosition);
            Intent intent = new Intent();
            intent.setClass(this, BakingAppMediaAndInstructionActivity.class);
            Bundle args = new Bundle();
            //Timber.i( "onStepClickSelected: Sending mStep Description :" + mStep.get(0).getDescription());
            args.putParcelableArrayList(STEPS, mStep);
            args.putInt(INDEX_VALUE, stepCardPosition);
            intent.putExtras(args);
            startActivity(intent);
        } else {
            //Timber.i( "Found Tablet, sending data to fragment, ,mIndex" + mIndex);
            //getFragmentManager().getFragment("","")

            StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
            Bundle argsForStepVideoAndInstructionFragment = new Bundle();
            //Timber.i( "Index value from onStepClickSelected :" + mIndex);
            argsForStepVideoAndInstructionFragment.putInt(INDEX_VALUE, mIndex);
            argsForStepVideoAndInstructionFragment.putParcelableArrayList(STEPS, mStep);
            // mTwoPane is expected true
            argsForStepVideoAndInstructionFragment.putBoolean(IS_TABLET, true);
            stepVideoAndInstructionFragment.setArguments(argsForStepVideoAndInstructionFragment);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.baking_app_video_instruction_fragment, stepVideoAndInstructionFragment).commit();

        }

    }

    @Override
    public void sendTitleForActionBar(String title) {
        if (mTwoPane)
            getSupportActionBar().setTitle(mTwoPaneTitle);
        else
            getSupportActionBar().setTitle(title);
    }

    @Override
    public void sendPassSavedInstanceState(int index, ArrayList<Step> stepArrayList, String videoURL, boolean mTwoPane) {
        this.mStep = stepArrayList;
        this.mIndex = index;
        //this.mVideoURL = videoURL;
        this.mTwoPane = mTwoPane;
    }

    @Override
    public void giveTheDataToActivity(String title, ArrayList<Step> stepArrayList, int index) {
        this.mTitle = title;
        this.mStep = stepArrayList;
        this.mIndex = index;

        if (mTwoPane)
            getSupportActionBar().setTitle(mTwoPaneTitle);
        else
            getSupportActionBar().setTitle(title);

    }

}


