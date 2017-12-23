package com.example.sumitasharma.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sumitasharma.bakingapp.fragments.StepVideoAndInstructionFragment;
import com.example.sumitasharma.bakingapp.utils.Step;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.utils.BakingUtils.INDEX_VALUE;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEPS;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEP_DESCRIPTION;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEP_VIDEO;


public class BakingAppMediaAndInstructionActivity extends AppCompatActivity implements StepVideoAndInstructionFragment.PassTitle, StepVideoAndInstructionFragment.PassSavedInstanceState {

    private static final String TAG = BakingAppMediaAndInstructionActivity.class.getSimpleName();
    int mIndex = 0;
    String mStepInstruction = null;
    String mVideoURL = null;
    String mTitle;
    private ArrayList<Step> mStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_app_instructionandvideo_detail);
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt(INDEX_VALUE);
            mStep = savedInstanceState.getParcelableArrayList(STEPS);

            try {
                Log.i(TAG, "Title is : " + mStep.get(mIndex).getShortDescription());
                getSupportActionBar().setTitle(mStepInstruction);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Index value from onCreate, savedInstance not null, Media Activity" + mIndex);
        } else {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                Log.i(TAG, "onCreate: Bundle is not null");
                mStep = bundle.getParcelableArrayList(STEPS);
                Log.i(TAG, "onCreate: Got mStep Description :" + mStep.get(mIndex).getShortDescription());
                mTitle = mStep.get(mIndex).getShortDescription();
                mIndex = bundle.getInt(INDEX_VALUE);

            } else {
                Log.i(TAG, "onCreate: Bundle is null");
            }
            mStepInstruction = mStep.get(mIndex).getDescription();
            if (mStepInstruction.isEmpty())
                getSupportActionBar().setTitle(mStep.get(mIndex).getShortDescription());
            else
                getSupportActionBar().setTitle(mStepInstruction);


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
    }


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

    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.i(TAG, "Index value from onAttachFragment: " + mIndex);
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
        Log.i(TAG, "Index value received from Interface sendPassSavedInstanceState on activity: " + mIndex);
    }
}
