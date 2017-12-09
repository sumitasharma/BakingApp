package com.example.sumitasharma.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sumitasharma.bakingapp.R;
import com.example.sumitasharma.bakingapp.utils.Step;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.BakingAppMediaAndInstructionActivity.STEP_DESCRIPTION;
import static com.example.sumitasharma.bakingapp.BakingAppMediaAndInstructionActivity.STEP_VIDEO;
import static com.example.sumitasharma.bakingapp.fragments.IngredientAndStepFragment.INDEX_VALUE;
import static com.example.sumitasharma.bakingapp.fragments.IngredientAndStepFragment.STEPS;


public class StepVideoAndInstructionFragment extends Fragment {
    Context mContext = null;
    ImageView mStepVideoTextView;
    int mIndex = 0;
    private String mInstruction = null;
    private String mVideo = null;
    private ArrayList<Step> mStep;
    private TextView mStepInstructionTextView;

    public StepVideoAndInstructionFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_video_instruction_fragment, container, false);
        mStepInstructionTextView = (TextView) rootView.findViewById(R.id.step_instruction_text_view);
        mStepVideoTextView = (ImageView) rootView.findViewById(R.id.step_video_image_view);
        Button prevStepButton = (Button) rootView.findViewById(R.id.prev_step);
        Button nextStepButton = (Button) rootView.findViewById(R.id.next_step);
        mStep = getArguments().getParcelableArrayList(STEPS);
        mInstruction = getArguments().getString(STEP_DESCRIPTION);
        mVideo = getArguments().getString(STEP_VIDEO);
        mIndex = getArguments().getInt(INDEX_VALUE);
        mStepInstructionTextView.setText(mInstruction);
//        if (!mVideo.isEmpty())
//            Picasso.with(mContext).load(mVideo).into(stepVideoTextView);

        prevStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStepInstructionTextView.setText(mStep.get(--mIndex).getDescription());
            }
        });
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStepInstructionTextView.setText(mStep.get(++mIndex).getDescription());
            }
        });
        return rootView;
    }

}

