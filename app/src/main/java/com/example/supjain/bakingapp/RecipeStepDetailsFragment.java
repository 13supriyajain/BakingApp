package com.example.supjain.bakingapp;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.supjain.bakingapp.data.RecipeStepsData;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment inflates layout file to display recipe step details like
 * recipe video, recipe description along with thumbnail etc.
 */

public class RecipeStepDetailsFragment extends Fragment {

    private static final String RECIPE_STEP_DATA_KEY = "RecipeStepDataKey";
    private static final String RECIPE_STEP_INDEX_KEY = "RecipeStepIndexKey";
    private static final String RECIPE_STEP_SEEK_POS_KEY = "RecipeStepSeekPosKey";
    private static final String TWO_PANE_VIEW_KEY = "TwoPaneViewKey";

    private PlayerView playerView;
    private SimpleExoPlayer player;
    private long playbackPosition;

    private ArrayList<RecipeStepsData> recipeStepsDataList;
    private RecipeStepsData currentRecipeStep;
    private int currentRecipeIndex;
    private boolean isTwoPaneDisplay;
    private RecipeStepsActivity parentActivity;
    private Resources resources;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            this.recipeStepsDataList = savedInstanceState.getParcelableArrayList(RECIPE_STEP_DATA_KEY);
            this.currentRecipeIndex = savedInstanceState.getInt(RECIPE_STEP_INDEX_KEY);
            this.playbackPosition = savedInstanceState.getLong(RECIPE_STEP_SEEK_POS_KEY);
            this.isTwoPaneDisplay = savedInstanceState.getBoolean(TWO_PANE_VIEW_KEY);
        }
        this.currentRecipeStep = recipeStepsDataList.get(currentRecipeIndex);

        parentActivity = (RecipeStepsActivity) getActivity();
        resources = getResources();

        View rootView = inflater.inflate(R.layout.fragment_recipe_step_details, container, false);

        playerView = rootView.findViewById(R.id.video_player);
        ViewGroup.LayoutParams params = playerView.getLayoutParams();
        if (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        else
            params.height = calculateVideoPlayerHeight();
        playerView.setLayoutParams(params);

        TextView stepDescriptionTextView = rootView.findViewById(R.id.recipe_step_detail_text);
        stepDescriptionTextView.setText(currentRecipeStep.getStepDescription());

        ImageView stepImageView = rootView.findViewById(R.id.recipe_step_detail_image);
        String thumbnailUrl = currentRecipeStep.getStepThumbnailUrl();
        if (!TextUtils.isEmpty(thumbnailUrl)) {
            Picasso.with(getContext())
                    .load(thumbnailUrl)
                    .into(stepImageView);

            // If Image url is invalid, hence no image is loaded in the ImageView,
            // then hide the ImageView.
            if (stepImageView.getDrawable() == null)
                stepImageView.setVisibility(View.GONE);
        }
        else
            stepImageView.setVisibility(View.GONE);

        Button previousStepButton = rootView.findViewById(R.id.previous_step_btn);
        if (currentRecipeIndex == 0) {
            previousStepButton.setEnabled(false);
            previousStepButton.setBackgroundColor(resources.getColor(R.color.gray));
            previousStepButton.setTextColor(resources.getColor(R.color.black));
        }

        previousStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTwoPaneDisplay)
                    parentActivity.replaceStepDetailsFragment(currentRecipeIndex - 1,
                        recipeStepsDataList, R.id.step_detail_fragment);
                else
                    parentActivity.replaceStepDetailsFragment(currentRecipeIndex - 1,
                            recipeStepsDataList, R.id.recipe_steps_fragment_container);
            }
        });

        Button nextStepButton = rootView.findViewById(R.id.next_step_btn);
        if (currentRecipeIndex == recipeStepsDataList.size() - 1) {
            nextStepButton.setEnabled(false);
            nextStepButton.setBackgroundColor(resources.getColor(R.color.gray));
            nextStepButton.setTextColor(resources.getColor(R.color.black));
        }

        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTwoPaneDisplay)
                    parentActivity.replaceStepDetailsFragment(currentRecipeIndex + 1,
                        recipeStepsDataList, R.id.step_detail_fragment);
                else
                    parentActivity.replaceStepDetailsFragment(currentRecipeIndex + 1,
                            recipeStepsDataList, R.id.recipe_steps_fragment_container);
            }
        });

        return rootView;
    }

    public void setRecipeStepsDataList(List<RecipeStepsData> dataList) {
        this.recipeStepsDataList = (ArrayList<RecipeStepsData>) dataList;
    }

    public void setCurrentRecipeIndex(int index) {
        this.currentRecipeIndex = index;
    }

    public void setTwoPaneDisplay(boolean twoPaneDisplay) {
        isTwoPaneDisplay = twoPaneDisplay;
    }

    private void initializePlayer(String videoUrl) {
        if (!TextUtils.isEmpty(videoUrl)) {
            if (player == null) {
                player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                        new DefaultTrackSelector(), new DefaultLoadControl());

                playerView.setPlayer(player);
                player.setPlayWhenReady(true);
            }
            // Prepare Media Source
            Uri uri = Uri.parse(videoUrl);
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource);
            player.seekTo(playbackPosition);
        } else {
            playerView.setVisibility(View.GONE);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("RecipeStepDetailsExoplayer")).
                createMediaSource(uri);
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelableArrayList(RECIPE_STEP_DATA_KEY, recipeStepsDataList);
        currentState.putInt(RECIPE_STEP_INDEX_KEY, currentRecipeIndex);
        playbackPosition = player.getCurrentPosition();
        currentState.putLong(RECIPE_STEP_SEEK_POS_KEY, playbackPosition);
        currentState.putBoolean(TWO_PANE_VIEW_KEY, isTwoPaneDisplay);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(currentRecipeStep.getStepVideoUrl());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer(currentRecipeStep.getStepVideoUrl());
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

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

//    @SuppressLint("InlinedApi")
//    private void hideSystemUi() {
//        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//    }

    private int calculateVideoPlayerHeight() {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        return (int) dpHeight;
    }
}
