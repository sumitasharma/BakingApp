package com.example.sumitasharma.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sumitasharma.bakingapp.fragments.StepVideoAndInstructionFragment;
import com.example.sumitasharma.bakingapp.utils.Step;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.fragments.IngredientAndStepFragment.INDEX_VALUE;
import static com.example.sumitasharma.bakingapp.fragments.IngredientAndStepFragment.STEPS;


public class BakingAppMediaAndInstructionActivity extends AppCompatActivity {

    public static final String STEP_DESCRIPTION = "step_description";
    public static final String STEP_VIDEO = "step_video";
    private static final String TAG = BakingAppMediaAndInstructionActivity.class.getSimpleName();
    public int mIndex = 0;
    String mStepInstruction = null;
    String mVideoURL = null;
    private ArrayList<Step> mStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_app_instructionandvideo_detail);
        // if (savedInstanceState == null) {
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {

            mStep = bundle.getParcelableArrayList(STEPS);
            mIndex = bundle.getInt(INDEX_VALUE);

        }
        // }
//        else {
//
//            mStep = savedInstanceState.getParcelableArrayList(STEPS);
//            mIndex = savedInstanceState.getInt(INDEX_VALUE);
//            Log.i(TAG, "Retreiving from savedInstanceState mIndex: " + mIndex);
//        }
        mStepInstruction = mStep.get(mIndex).getDescription();
        mVideoURL = mStep.get(mIndex).getVideoURL();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STEPS, mStep);
        args.putString(STEP_DESCRIPTION, mStepInstruction);
        args.putString(STEP_VIDEO, mVideoURL);
        args.putInt(INDEX_VALUE, mIndex);
        Log.i(TAG, "Inside recipe ingredient step video instruction detail : " + mStepInstruction);
        StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
        stepVideoAndInstructionFragment.setArguments(args);

        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.baking_app_video_instruction_fragment, stepVideoAndInstructionFragment).commit();

    }


//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        // super.onRestoreInstanceState(savedInstanceState);
//        mStep = savedInstanceState.getParcelableArrayList(STEPS);
//        mIndex = savedInstanceState.getInt(INDEX_VALUE);
//
//
//    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        // super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList(STEPS, mStep);
//        outState.putInt(INDEX_VALUE, mIndex);
//
//
//    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.i(TAG, "onAttachFragment " + mIndex);
        fragment.getArguments();
        Log.i(TAG, "onAttachFragment " + mIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
