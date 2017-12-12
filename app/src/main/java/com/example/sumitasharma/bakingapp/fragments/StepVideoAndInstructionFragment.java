package com.example.sumitasharma.bakingapp.fragments;

import android.annotation.SuppressLint;
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

import static com.example.sumitasharma.bakingapp.fragments.IngredientAndStepFragment.INDEX_VALUE;
import static com.example.sumitasharma.bakingapp.fragments.IngredientAndStepFragment.STEPS;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEP_DESCRIPTION;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEP_VIDEO;


public class StepVideoAndInstructionFragment extends Fragment {

    private static final String TAG = StepVideoAndInstructionFragment.class.getSimpleName();
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


    public StepVideoAndInstructionFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_video_instruction_fragment, container, false);
        mStepVideoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.step_video_player_view);
        String mInstruction = null;
        if (savedInstanceState != null) {
            Log.i(TAG, "savedInstanceState in Fragment is not null");
            mStep = savedInstanceState.getParcelableArrayList(STEPS);
            mVideo = savedInstanceState.getString(STEP_VIDEO);
            mIndex = savedInstanceState.getInt(INDEX_VALUE);
            Log.i(TAG, "mIndex received from savedInstance: " + mIndex);
        } else {

            Log.i(TAG, "savedInstanceState in Fragment is null");
            mStep = getArguments().getParcelableArrayList(STEPS);
            //  mVideo = getArguments().getString(STEP_VIDEO);
            mIndex = getArguments().getInt(INDEX_VALUE);

        }
        if (rootView.findViewById(R.id.portrait_mode_linear_layout) != null) {
            mStepInstructionTextView = (TextView) rootView.findViewById(R.id.step_instruction_text_view);
            mPrevStepButton = (Button) rootView.findViewById(R.id.prev_step);
            mNextStepButton = (Button) rootView.findViewById(R.id.next_step);
            mInstruction = getArguments().getString(STEP_DESCRIPTION);
            mStepInstructionTextView.setText(mInstruction);
            Log.i(TAG, "Index value received " + mIndex);
            mPrevStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIndex > 0) {
                        mIndex--;
                        Log.i(TAG, "Index value onClick prev button " + mIndex);
                        mStepInstructionTextView.setText(mStep.get(mIndex).getDescription());
                        mVideo = mStep.get(mIndex).getVideoURL();
                        if (!mVideo.isEmpty())
                            initializePlayer();
                    } else
                        mIndex = 0;
                }
            });
            mNextStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIndex < mStep.size() - 1) {
                        mIndex++;
                        Log.i(TAG, "Index value onClick Next Button " + mIndex);
                        mStepInstructionTextView.setText(mStep.get(mIndex).getDescription());
                        mVideo = mStep.get(mIndex).getVideoURL();
                        if (!mVideo.isEmpty())
                            initializePlayer();
                    } else
                        mIndex = mStep.size();
                }
            });
        }
        if (!mStep.get(mIndex).getVideoURL().isEmpty()) {
            Log.i(TAG, "index value in if loop : " + mIndex);
            mVideo = mStep.get(mIndex).getVideoURL();
            initializePlayer();
        }
        return rootView;
    }

    private void initializePlayer() {
        mStepVideoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mStepVideoPlayerView.setPlayer(mStepVideoPlayer);

        mStepVideoPlayer.setPlayWhenReady(mPlayWhenReady);
        mStepVideoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
        Uri uri = Uri.parse(mStep.get(mIndex).getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        mStepVideoPlayer.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || mStepVideoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mStepVideoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
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
        Log.i(TAG, "onAttach " + mIndex);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState called: mIndex" + mIndex);
        outState.putInt(INDEX_VALUE, mIndex);
        outState.putParcelableArrayList(STEPS, mStep);
        outState.putString(STEP_VIDEO, mVideo);
    }

    public interface mPassIndex {
        void passIndexValue(int index);
    }

}

