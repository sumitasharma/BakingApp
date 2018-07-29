package com.example.sumitasharma.bakingapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

import timber.log.Timber;

import static com.example.sumitasharma.bakingapp.utils.BakingUtils.INDEX_VALUE;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.IS_TABLET;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.PLAYER_POSITION;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEPS;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEP_VIDEO;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.isOnline;


public class StepVideoAndInstructionFragment extends Fragment {

    private static final String TAG = StepVideoAndInstructionFragment.class.getSimpleName();
    private View rootView;
    private PassTitle mPassTitle;
    private PassSavedInstanceState mPassSavedInstanceState;
    private Context mContext = getContext();
    private int mIndex;
    private boolean mPlayWhenReady = true;
    private int mCurrentWindow;
    private long mPlaybackPosition;
    private String mVideo = null;
    private ArrayList<Step> mStep;
    private TextView mStepInstructionTextView;
    private SimpleExoPlayer mStepVideoPlayer;
    private SimpleExoPlayerView mStepVideoPlayerView;
    private TextView mVideoNotAvailableText;
    private boolean mTwoPane;
    private long playerPosition;

    public StepVideoAndInstructionFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.step_video_instruction_fragment, container, false);
        mStepVideoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.step_video_player_view);
        mVideoNotAvailableText = (TextView) rootView.findViewById(R.id.video_not_available);
        mStepVideoPlayerView.setVisibility(View.VISIBLE);
        mVideoNotAvailableText.setVisibility(View.INVISIBLE);
        if (savedInstanceState != null) {
            // Checking if the data  is coming from savedInstance
            mStep = savedInstanceState.getParcelableArrayList(STEPS);
            //mVideo = savedInstanceState.getString(STEP_VIDEO);
            mIndex = savedInstanceState.getInt(INDEX_VALUE);
            mTwoPane = savedInstanceState.getBoolean(IS_TABLET);
            mPlaybackPosition = savedInstanceState.getLong(PLAYER_POSITION);
        } else {
            // Data is coming from arguments passed from the activity it is attached to -
            // BakingAppMediaAndInstructionActivity(phone) or BakingAppDetailActivity(tablet)

            mStep = getArguments().getParcelableArrayList(STEPS);
            mIndex = getArguments().getInt(INDEX_VALUE);
            mTwoPane = getArguments().getBoolean(IS_TABLET);
        }


        // Checking if the mode is portrait/tablet or landscape. If portrait/tablet, need to set up
        // previous and next button.


        Button mPrevStepButton;
        Button mNextStepButton;
        if (rootView.findViewById(R.id.portrait_mode_linear_layout) != null || mTwoPane) {
            //Following is done if the mode is Portrait or Tablet
            //Timber.i( "Found Phone in Portrait mode or Tablet");

            mStepInstructionTextView = (TextView) rootView.findViewById(R.id.step_instruction_text_view);
            mPrevStepButton = (Button) rootView.findViewById(R.id.prev_step);
            mNextStepButton = (Button) rootView.findViewById(R.id.next_step);
            mStepInstructionTextView.setVisibility(View.VISIBLE);
            mPrevStepButton.setVisibility(View.VISIBLE);
            mNextStepButton.setVisibility(View.VISIBLE);
            mStepInstructionTextView.setText(mStep.get(mIndex).getDescription());
            Timber.i("Index value received " + mIndex);
            mPrevStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    releasePlayer();

                    if (mIndex > 0) {
                        mIndex--;
                        mPassTitle.sendTitleForActionBar(mStep.get(mIndex).getShortDescription());
                        mStepInstructionTextView.setText(mStep.get(mIndex).getDescription());
                        mVideo = mStep.get(mIndex).getVideoURL();
                        if (!TextUtils.isEmpty(mVideo)) {
                            //if (!mVideo.isEmpty()) {
                            initializePlayer();
                        } else {
                            mStepVideoPlayerView.setVisibility(View.INVISIBLE);
                            mVideoNotAvailableText.setVisibility(View.VISIBLE);
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
                        mStepInstructionTextView.setText(mStep.get(mIndex).getDescription());
                        mVideo = mStep.get(mIndex).getVideoURL();
                        if (!TextUtils.isEmpty(mVideo)) {
                            //if (!mVideo.isEmpty()) {
                            initializePlayer();
                        } else {
                            mStepVideoPlayerView.setVisibility(View.INVISIBLE);
                            mVideoNotAvailableText.setVisibility(View.VISIBLE);
                        }
                    } else {
                        mIndex = mStep.size();
                    }
                }
            });
        } else {
            //Change the layout to display only the media player
            Timber.i("Landscape mode detected in Phone");
            mStepInstructionTextView = (TextView) rootView.findViewById(R.id.step_instruction_text_view);
            mPrevStepButton = (Button) rootView.findViewById(R.id.prev_step);
            mNextStepButton = (Button) rootView.findViewById(R.id.next_step);
            mStepInstructionTextView.setVisibility(View.INVISIBLE);

            mPrevStepButton.setVisibility(View.INVISIBLE);
            mNextStepButton.setVisibility(View.INVISIBLE);
        }

        if (mIndex < mStep.size() - 1 && mIndex >= 0)

        {
            if (!TextUtils.isEmpty(mStep.get(mIndex).getVideoURL())) {
                // if (!mStep.get(mIndex).getVideoURL().isEmpty()) {
                Timber.i("VideoURL value in if loop : " + mStep.get(mIndex).getVideoURL());
                mVideo = mStep.get(mIndex).getVideoURL();
            } else {
                Timber.i("Video not available");
                mStepInstructionTextView.setText(mStep.get(mIndex).getDescription());
                mStepVideoPlayerView.setVisibility(View.INVISIBLE);
                mVideoNotAvailableText.setVisibility(View.VISIBLE);
            }
        }
        return rootView;
    }

    private void initializePlayer() {
        if (!isOnline(mContext)) {
            Snackbar snackbar = Snackbar.make(rootView.findViewById(R.id.myCoordinatorLayout), R.string.internet_connectivity,
                    Snackbar.LENGTH_SHORT);
            snackbar.show();
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.BLUE);
            // Toast.makeText(mContext, "Kindly check your Internet Connectivity", Toast.LENGTH_LONG).show();
        }
        if (!TextUtils.isEmpty(mStep.get(mIndex).getVideoURL())) {
            // if (!mStep.get(mIndex).getVideoURL().isEmpty()) {
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
        mContext = getContext();
        Timber.i("onStart Called, sendingSavedInstance data, mIndex:" + mIndex);
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.i("onResume Called, sendingSavedInstance data, mIndex:" + mIndex);
        mPassSavedInstanceState.sendPassSavedInstanceState(mIndex, mStep, mVideo, mTwoPane);
        if (!mTwoPane)
            mPassTitle.sendTitleForActionBar(mStep.get(mIndex).getShortDescription());
        Timber.i("Index value onClick onResume " + mIndex);
        // hideSystemUi();
        if ((Util.SDK_INT <= 23 || mStepVideoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.i("onPause Called, sendingSavedInstance data, mIndex:" + mIndex);
        Timber.i("Index value onClick onPause " + mIndex);
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.i("onStop Called, sendingSavedInstance data, mIndex:" + mIndex);
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

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
        Timber.i("onAttach Called, sendingSavedInstance data, mIndex:" + mIndex);

        mPassTitle = (PassTitle) context;
        mPassSavedInstanceState = (PassSavedInstanceState) context;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!TextUtils.isEmpty(mStep.get(mIndex).getVideoURL())) {
            //if (!mStep.get(mIndex).getVideoURL().isEmpty()) {
            mPlaybackPosition = mStepVideoPlayer.getCurrentPosition();
        }
        // Saving last displayed data. mIndex would increase or decrease on next or prev button
        // respectively, hence keeping mIndex inbound.
        if (mIndex >= mStep.size() - 1)
            mIndex--;
        else if (mIndex < 0)
            mIndex++;
        outState.putInt(INDEX_VALUE, mIndex);
        outState.putParcelableArrayList(STEPS, mStep);
        outState.putString(STEP_VIDEO, mVideo);
        outState.putBoolean(IS_TABLET, mTwoPane);
        outState.putLong(PLAYER_POSITION, mPlaybackPosition);
        mPassTitle.sendTitleForActionBar(mStep.get(mIndex).getShortDescription());
    }


    public interface PassTitle {
        void sendTitleForActionBar(String title);
    }

    public interface PassSavedInstanceState {
        void sendPassSavedInstanceState(int index, ArrayList<Step> stepArrayList, String videoURL, boolean mTwoPane);
    }

}



