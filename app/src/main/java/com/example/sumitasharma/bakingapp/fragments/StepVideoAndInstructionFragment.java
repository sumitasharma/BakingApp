package com.example.sumitasharma.bakingapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sumitasharma.bakingapp.R;
import com.example.sumitasharma.bakingapp.utils.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.utils.BakingUtils.INDEX_VALUE;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.IS_TABLET;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEPS;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEP_DESCRIPTION;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEP_VIDEO;


public class StepVideoAndInstructionFragment extends Fragment {

    private static final String TAG = StepVideoAndInstructionFragment.class.getSimpleName();
    public PassTitle mPassTitle;
    public PassSavedInstanceState mPassSavedInstanceState;
    Context mContext = getContext();
    int mIndex = 0;
    private boolean mPlayWhenReady = true;
    private int mCurrentWindow;
    private long mPlaybackPosition;
    private String mVideo = null;
    private ArrayList<Step> mStep;
    private Button mPrevStepButton;
    private Button mNextStepButton;
    private TextView mStepInstructionTextView;
    private SimpleExoPlayer mStepVideoPlayer;
    private SimpleExoPlayerView mStepVideoPlayerView;
    private TextView mVideoNotAvailableText;
    private boolean mTwoPane;

    public StepVideoAndInstructionFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_video_instruction_fragment, container, false);
        mStepVideoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.step_video_player_view);
        String mInstruction = null;
        mVideoNotAvailableText = (TextView) rootView.findViewById(R.id.video_not_available);
        mStepVideoPlayerView.setVisibility(View.VISIBLE);
        mVideoNotAvailableText.setVisibility(View.INVISIBLE);


        /**
         * Getting the data from savedInstanceState or from the Arguments passed from the Activity
         */

        if (savedInstanceState != null) {
            //Checking if the data  is coming from savedInstance
            Log.i(TAG, "savedInstanceState in Fragment is not null");
            mStep = savedInstanceState.getParcelableArrayList(STEPS);
            mVideo = savedInstanceState.getString(STEP_VIDEO);
            mIndex = savedInstanceState.getInt(INDEX_VALUE);
            Log.i(TAG, "Index value from savedInstance: " + mIndex);
        } else {
            //Data is coming from arguments passed from the activity it is attached to - BakingAppMediaAndInstructionActivity(phone) or BakingAppDetailActivity(tablet)
            Log.i(TAG, "savedInstanceState in Fragment is null");
            mStep = getArguments().getParcelableArrayList(STEPS);
            //  mVideo = getArguments().getString(STEP_VIDEO);
            mIndex = getArguments().getInt(INDEX_VALUE);
            mTwoPane = getArguments().getBoolean(IS_TABLET);
            Log.i(TAG, "Index value from arguments: " + mIndex);
            Log.i(TAG, "Inside fragment. Value of twopane before ifLoop is : " + mTwoPane);

        }


        /**
         * Checking if the mode is portrait or landscape/tablet. If portrait, need to set up previous and next button.
         */

        if (rootView.findViewById(R.id.portrait_mode_linear_layout) != null || mTwoPane) {
            //Following is done if the mode is portrait

            mStepInstructionTextView = (TextView) rootView.findViewById(R.id.step_instruction_text_view);
            mPrevStepButton = (Button) rootView.findViewById(R.id.prev_step);
            mNextStepButton = (Button) rootView.findViewById(R.id.next_step);
            mStepInstructionTextView.setVisibility(View.VISIBLE);
            mPrevStepButton.setVisibility(View.VISIBLE);
            mNextStepButton.setVisibility(View.VISIBLE);
            mInstruction = getArguments().getString(STEP_DESCRIPTION);
            mStepInstructionTextView.setText(mInstruction);
            // Log.i(TAG, "Inside fragment. Value of twopane is : " + mTwoPane);
            Log.i(TAG, "Index value received " + mIndex);
            mPrevStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    releasePlayer();

                    if (mIndex > 0) {
                        mIndex--;
                        mPassTitle.sendTitleForActionBar(mStep.get(mIndex).getShortDescription());
                        Log.i(TAG, "Index value onClick prev button " + mIndex);
                        mStepInstructionTextView.setText(mStep.get(mIndex).getDescription());
                        mVideo = mStep.get(mIndex).getVideoURL();
                        if (!mVideo.isEmpty()) {
                            Log.i(TAG, "VideoURL value in if loop : " + mStep.get(mIndex).getVideoURL());
                            initializePlayer();
                        } else {
                            mStepVideoPlayerView.setVisibility(View.INVISIBLE);
                            mVideoNotAvailableText.setVisibility(View.VISIBLE);
                            Log.i(TAG, "VideoURL value in if loop : " + mStep.get(mIndex).getVideoURL());
                        }
                    } else {
                        mIndex = 0;
                    }
                }
            });
            mNextStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    releasePlayer();

                    if (mIndex < mStep.size() - 1) {
                        mIndex++;
                        mPassTitle.sendTitleForActionBar(mStep.get(mIndex).getShortDescription());
                        Log.i(TAG, "Index value onClick Next Button " + mIndex);
                        mStepInstructionTextView.setText(mStep.get(mIndex).getDescription());
                        mVideo = mStep.get(mIndex).getVideoURL();
                        if (!mVideo.isEmpty()) {
                            Log.i(TAG, "VideoURL value in if loop : " + mStep.get(mIndex).getVideoURL());
                            initializePlayer();
                        } else {
                            mStepVideoPlayerView.setVisibility(View.INVISIBLE);
                            mVideoNotAvailableText.setVisibility(View.VISIBLE);

                            Log.i(TAG, "VideoURL value in if loop : " + mStep.get(mIndex).getVideoURL());
                        }
                    } else {
                        mIndex = mStep.size();
                    }
                }
            });
        } else {
            //Change the layout to display only the media player
            mStepInstructionTextView = (TextView) rootView.findViewById(R.id.step_instruction_text_view);
            mPrevStepButton = (Button) rootView.findViewById(R.id.prev_step);
            mNextStepButton = (Button) rootView.findViewById(R.id.next_step);
            mStepInstructionTextView.setVisibility(View.INVISIBLE);

            mPrevStepButton.setVisibility(View.INVISIBLE);
            mNextStepButton.setVisibility(View.INVISIBLE);
        }

        if (mIndex < mStep.size() - 1 && mIndex >= 0)

        {
            if (!mStep.get(mIndex).getVideoURL().isEmpty()) {
                Log.i(TAG, "VideoURL value in if loop : " + mStep.get(mIndex).getVideoURL());
                mVideo = mStep.get(mIndex).getVideoURL();
                //  initializePlayer();
            } else {
                Log.i(TAG, "Video not available");
                mStepVideoPlayerView.setVisibility(View.INVISIBLE);
                mVideoNotAvailableText.setVisibility(View.VISIBLE);
            }
        }
        return rootView;
    }

    private void initializePlayer() {
        if (!mStep.get(mIndex).getVideoURL().isEmpty()) {
            mVideoNotAvailableText.setVisibility(View.INVISIBLE);
            mStepVideoPlayerView.setVisibility(View.VISIBLE);
            mStepVideoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            mStepVideoPlayerView.setPlayer(mStepVideoPlayer);

            mStepVideoPlayer.setPlayWhenReady(mPlayWhenReady);
            mStepVideoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
            Uri uri = Uri.parse(mStep.get(mIndex).getVideoURL());
            MediaSource mediaSource = buildMediaSource(uri);
            mStepVideoPlayer.prepare(mediaSource, true, false);
        } else {
            mStepVideoPlayerView.setVisibility(View.INVISIBLE);
            mVideoNotAvailableText.setVisibility(View.VISIBLE);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPassSavedInstanceState.sendPassSavedInstanceState(mIndex, mStep, mVideo);
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPassSavedInstanceState.sendPassSavedInstanceState(mIndex, mStep, mVideo);
        Log.i(TAG, "Index value onClick onResume " + mIndex);
        // hideSystemUi();
        if ((Util.SDK_INT <= 23 || mStepVideoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPassSavedInstanceState.sendPassSavedInstanceState(mIndex, mStep, mVideo);
        Log.i(TAG, "Index value onClick onPause " + mIndex);
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "Index value onClick onStop " + mIndex);
        mPassSavedInstanceState.sendPassSavedInstanceState(mIndex, mStep, mVideo);
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

//    @SuppressLint("InlinedApi")
//    private void hideSystemUi() {
//        mStepVideoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//    }

    private void releasePlayer() {
        if (mStepVideoPlayer != null) {
            mPlaybackPosition = mStepVideoPlayer.getCurrentPosition();
            mCurrentWindow = mStepVideoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mStepVideoPlayer.getPlayWhenReady();
            mStepVideoPlayer.release();
            mStepVideoPlayer = null;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach " + mIndex);
        mPassTitle = (PassTitle) context;
        mPassSavedInstanceState = (PassSavedInstanceState) context;
        Log.i(TAG, "Index value onClick onAttach " + mIndex);
        mPassSavedInstanceState.sendPassSavedInstanceState(mIndex, mStep, mVideo);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "Index value onClick onSaveInstance " + mIndex);
        outState.putInt(INDEX_VALUE, mIndex);
        outState.putParcelableArrayList(STEPS, mStep);
        outState.putString(STEP_VIDEO, mVideo);
        mPassSavedInstanceState.sendPassSavedInstanceState(mIndex, mStep, mVideo);
        mPassTitle.sendTitleForActionBar(mStep.get(mIndex).getShortDescription());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mPassSavedInstanceState.sendPassSavedInstanceState(mIndex, mStep, mVideo);
        mPassTitle.sendTitleForActionBar(mStep.get(mIndex).getShortDescription());
    }

    public interface PassTitle {
        void sendTitleForActionBar(String title);
    }

    public interface PassSavedInstanceState {
        void sendPassSavedInstanceState(int index, ArrayList<Step> stepArrayList, String videoURL);
    }

}

