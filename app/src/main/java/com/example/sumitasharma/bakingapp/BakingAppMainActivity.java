package com.example.sumitasharma.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sumitasharma.bakingapp.adapter.BakingAppMainAdapter;
import com.example.sumitasharma.bakingapp.adapter.BakingAppMainAdapter.BakingAppClickListener;
import com.example.sumitasharma.bakingapp.loader.BakingAppMainLoader;
import com.example.sumitasharma.bakingapp.utils.Recipe;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.utils.BakingUtils.RECIPE_OBJECT;

public class BakingAppMainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks, BakingAppMainLoader.AsyncResponse, BakingAppClickListener {

    private static final String TAG = BakingAppMainActivity.class.getSimpleName();
    private final LoaderManager.LoaderCallbacks<String[]> callback = BakingAppMainActivity.this;
    public int mLoaderId = 101;
    RecyclerView bakingAppRecyclerView;
    Context mContext;
    private ArrayList<Recipe> mRecipe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_baking);
        bakingAppRecyclerView = (RecyclerView) findViewById(R.id.baking_app_main_recycler_view);
        if (getResources().getBoolean(R.bool.isTablet)) {
            bakingAppRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            bakingAppRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
        initializeLoader(mLoaderId, this);
    }

    public void initializeLoader(int loaderId, Context context) {
        Log.i(TAG, "Inside initializeLoader");
        this.mLoaderId = loaderId;
        this.mContext = context;
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> movieSearchLoader = loaderManager.getLoader(mLoaderId);
        if (movieSearchLoader == null) {
            loaderManager.initLoader(mLoaderId, null, callback);
        } else {
            loaderManager.restartLoader(mLoaderId, null, callback);
        }
        // getSupportLoaderManager().initLoader(mLoaderId,null,this);
        //Loader<String> bakingLoader = loaderManager.getLoader(mLoaderId);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "Inside onCreateLoader for Baking App Main Activity");
        return new BakingAppMainLoader(this, bakingAppRecyclerView, mRecipe, this, this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Log.i(TAG, "Inside onLoadFinished in BakingAppMainActivity" + mRecipe.get(0).getName());
        //ImageView bakingImage = (ImageView) findViewById(R.id.baking_main_view_image);
        BakingAppMainAdapter bakingAppMainAdapter = new BakingAppMainAdapter(mContext, this, mRecipe);
        bakingAppRecyclerView.setAdapter(bakingAppMainAdapter);
        //movieSynopsis.setText(mMovieDetail.getSynopsis());
        //Picasso.with(mContext).load(mRecipe[0].getImage()).into(bakingImage);

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onClickBakeCard(int bakeCardPosition) {
        Log.i(TAG, "onClickBakeCard : bakecardposition " + bakeCardPosition);
        //String recipeId = String.valueOf(mRecipe.get(bakeCardPosition).getId());
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putParcelable(RECIPE_OBJECT, mRecipe.get(bakeCardPosition));
        intent.putExtras(b);
        intent.setClass(this, BakingAppDetailActivity.class);
        startActivity(intent);
        Log.i(TAG, "Sending data :" + mRecipe.get(bakeCardPosition).getName());
        Log.i(TAG, "Sending data :" + mRecipe.get(bakeCardPosition).getIngredients());

    }

    @Override
    public void processFinish(ArrayList<Recipe> recipes) {
        Log.i(TAG, "processFinish Called");
        this.mRecipe = recipes;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(this.mLoaderId, null, callback);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getSupportLoaderManager().restartLoader(this.mLoaderId, null, callback);
    }

}
