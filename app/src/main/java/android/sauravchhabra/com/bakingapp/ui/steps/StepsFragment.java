package android.sauravchhabra.com.bakingapp.ui.steps;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.sauravchhabra.com.bakingapp.R;
import android.sauravchhabra.com.bakingapp.idlingresource.SimpleIdlingResource;
import android.sauravchhabra.com.bakingapp.model.Steps;
import android.sauravchhabra.com.bakingapp.utils.Constants;
import android.sauravchhabra.com.bakingapp.utils.IsTabletOrLandscape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple fragment class to bind the view
 */
public class StepsFragment extends Fragment implements ExoPlayer.EventListener {

    @BindView(R.id.description_tv_steps)
    TextView mDescription;

    @BindView(R.id.next_button_steps)
    Button mNextButton;

    @BindView(R.id.prev_button_steps)
    Button mPrevButton;

    @BindView(R.id.video_ep_steps)
    SimpleExoPlayerView mExoPlayer;

    @BindView(R.id.button_container)
    RelativeLayout mLayoutContainer;

    SimpleExoPlayer mSimpleExoPlayer;
    MediaSessionCompat mMediaSession;
    PlaybackStateCompat.Builder mStateBuilder;

    private static int mSteps;
    private static long mLastPosition;
    private boolean isVideoReady = true;
    private String videoUrl;
    private List<Steps> stepList = new ArrayList<>();

    private static final String STEP_ARG = "step_arg";
    private static final String MEDIA_SESSION_TAG = "steps_media_session";

    private static final String VIDEO_LAST_POSITION = "video_last_position";
    private static final String VIDEO_IS_READY = "video_is_ready";
    private static final String STEP_INDEX = "step_index";

    @javax.annotation.Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    private void setIdlingResource(boolean isIdleNow) {
        if (mIdlingResource != null) {
            mIdlingResource.setIdle(isIdleNow);
        }
    }


    // Required empty public constructor
    public StepsFragment() {
    }

    public static StepsFragment newInstance(Steps steps) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(STEP_ARG, steps);

        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setArguments(bundle);
        return stepsFragment;
    }

    @Override
    public void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIdlingResource();

        setIdlingResource(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, viewGroup, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupToolbarName(getActivity().getString(R.string.instructions));


        // Is it tablet mode
        if (getArguments().getSerializable(STEP_ARG) != null) {
            Steps step = (Steps) getArguments().getSerializable(STEP_ARG);
            mDescription.setText(step.getDescription());
            String videoUrl = step.getVideoURL();
            mLayoutContainer.setVisibility(View.GONE);

            mLastPosition = 0;
            isVideoReady = true;
            playVideo(videoUrl, false);
        }
        // Is is portrait mode
        else {
            if (getActivity().getIntent() != null) {
                stepList = (List<Steps>) getActivity().getIntent().getExtras().getSerializable(Constants.STEPS);
                if (savedInstanceState == null) {
                    mSteps = (getActivity().getIntent().getExtras().getInt(Constants.STEPS_INDEX));
                    mDescription.setText(stepList.get(mSteps).getDescription());
                    videoUrl = stepList.get(mSteps).getVideoURL();
                }

                // Is it landscape mode
                if (IsTabletOrLandscape.isLandscapeDisplay(getActivity())) {
                    if(savedInstanceState != null) {
                        mLastPosition = savedInstanceState.getLong(VIDEO_LAST_POSITION, 0);
                        isVideoReady = savedInstanceState.getBoolean(VIDEO_IS_READY, true);
                        mSteps = savedInstanceState.getInt(STEP_INDEX, 0);
                    }
                    videoUrl = stepList.get(mSteps).getVideoURL();
                    playVideo(videoUrl, true);
                } else {
                    videoUrl = stepList.get(mSteps).getVideoURL();
                    mDescription.setText(stepList.get(mSteps).getDescription());
                    playVideo(videoUrl, false);
                }

                Intent resultIntent = new Intent();

                mNextButton.setOnClickListener(v -> {
                    if (mSteps != stepList.size() - 1) {
                        mSteps++;
                        releasePlayer();
                        nextPrevBtnClickConfig();
                    } else {
                        Toast.makeText(getActivity(), "Last Step", Toast.LENGTH_SHORT).show();
                    }

                    setResult(mSteps, resultIntent);
                });

                mPrevButton.setOnClickListener(v -> {
                    if (mSteps != 0) {
                        mSteps--;
                        releasePlayer();
                        nextPrevBtnClickConfig();
                    } else {
                        Toast.makeText(getActivity(), "First Step", Toast.LENGTH_SHORT).show();
                    }

                    setResult(mSteps, resultIntent);
                });
            }
        }

        setIdlingResource(true);
    }

    private void nextPrevBtnClickConfig() {
        mDescription.setText(stepList.get(mSteps).getDescription());
        mLastPosition = 0;
        isVideoReady = true;
        playVideo(stepList.get(mSteps).getVideoURL(), false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(VIDEO_LAST_POSITION, mLastPosition);
        outState.putBoolean(VIDEO_IS_READY, isVideoReady);
        outState.putInt(STEP_INDEX, mSteps);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        resumePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void setupToolbarName(String name) {
        if (getActivity() != null) {
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(name);
            }
        }
    }

    private void setResult(int stepIndex, Intent intent) {
        intent.putExtra(Constants.STEP_INDEX_RESULT, stepIndex);
        getActivity().setResult(Activity.RESULT_OK, intent);
    }

    private void playVideo(String videoUrl, boolean isFullScreen) {
        releasePlayer();
        if (!videoUrl.equals("") && !videoUrl.isEmpty()) {
            mExoPlayer.setVisibility(View.VISIBLE);
            initializeMediaSession();
            initializePlayer(Uri.parse(videoUrl));

            if (isFullScreen) {
                playVideoFullscreen();
            }

        } else {
            mExoPlayer.setVisibility(View.GONE);
        }
    }

    private void expandVideoView(SimpleExoPlayerView exoPlayer) {
        exoPlayer.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        exoPlayer.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(getActivity(), MEDIA_SESSION_TAG);

        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallback());
        mMediaSession.setActive(true);
    }

    private void initializePlayer(Uri mediaUri) {
        if (mSimpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
            mExoPlayer.setPlayer(mSimpleExoPlayer);
            mSimpleExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getActivity(), "RecipeSteps");
            MediaSource mediaSource = new ExtractorMediaSource(
                    mediaUri,
                    new DefaultDataSourceFactory(
                            getContext(), userAgent),
                    new DefaultExtractorsFactory(), null, null);

            mSimpleExoPlayer.prepare(mediaSource);
            mSimpleExoPlayer.setPlayWhenReady(isVideoReady);
            mSimpleExoPlayer.seekTo(mLastPosition);

            setIdlingResource(false);
        }
    }

    private void playVideoFullscreen() {
        expandVideoView(mExoPlayer);
        mLayoutContainer.setVisibility(View.GONE);
        hideSystemUI();
    }

    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void releasePlayer() {
        if (mSimpleExoPlayer != null) {
            mLastPosition = mSimpleExoPlayer.getCurrentPosition();
            isVideoReady = mSimpleExoPlayer.getPlayWhenReady();
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }

        if (mMediaSession != null) {
            mMediaSession.setActive(false);
        }
    }

    private void pausePlayer() {
        if (mSimpleExoPlayer != null) {
            mLastPosition = mSimpleExoPlayer.getCurrentPosition();
            isVideoReady = mSimpleExoPlayer.getPlayWhenReady();
            mSimpleExoPlayer.setPlayWhenReady(false);
        }
    }

    private void resumePlayer() {
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(isVideoReady);
        }
    }

    // Exo player media session callback class
    public class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mSimpleExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mSimpleExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mSimpleExoPlayer.seekTo(0);
        }
    }


    // Exo player event listeners
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, mSimpleExoPlayer.getCurrentPosition(), 1f);
        } else if (playbackState == ExoPlayer.STATE_READY) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, mSimpleExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
