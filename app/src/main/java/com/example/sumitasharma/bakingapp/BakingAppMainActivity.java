package com.example.sumitasharma.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sumitasharma.bakingapp.adapter.BakingAppMainAdapter;
import com.example.sumitasharma.bakingapp.adapter.BakingAppMainAdapter.BakingAppClickListener;
import com.example.sumitasharma.bakingapp.loader.BakingAppMainLoader;
import com.example.sumitasharma.bakingapp.utils.Recipe;

import java.util.ArrayList;

import timber.log.Timber;

import static com.example.sumitasharma.bakingapp.utils.BakingUtils.RECIPE_OBJECT;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.isOnline;

public class BakingAppMainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks, BakingAppMainLoader.AsyncResponse, BakingAppClickListener {

    private static final String TAG = BakingAppMainActivity.class.getSimpleName();
    private final LoaderManager.LoaderCallbacks<String[]> callback = BakingAppMainActivity.this;
    private int mLoaderId = 101;
    private Context mContext;
    private RecyclerView bakingAppRecyclerView;
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

    private void initializeLoader(int loaderId, Context context) {
        // Timber.i( "Inside initializeLoader");
        Timber.i("Inside initializeLoader");
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
        //  Timber.i( "Inside onCreateLoader for Baking App Main Activity");
        Timber.i("Inside onCreateLoader for Baking App Main Activity");
        return new BakingAppMainLoader(this, mRecipe, this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (!isOnline(mContext)) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.internet_connectivity,
                    Snackbar.LENGTH_SHORT);
            snackbar.show();
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.BLUE);
            // Toast.makeText(mContext, "Kindly check your Internet Connectivity", Toast.LENGTH_LONG).show();
        } else {
            BakingAppMainAdapter bakingAppMainAdapter = new BakingAppMainAdapter(mContext, this, mRecipe);
            bakingAppRecyclerView.setAdapter(bakingAppMainAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onClickBakeCard(int bakeCardPosition) {
        //String recipeId = String.valueOf(mRecipe.get(bakeCardPosition).getId());
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putParcelable(RECIPE_OBJECT, mRecipe.get(bakeCardPosition));
        intent.putExtras(b);
        intent.setClass(this, BakingAppDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void processFinish(ArrayList<Recipe> recipes) {
        if (isOnline(mContext)) {
            this.mRecipe = recipes;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isOnline(mContext)) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.internet_connectivity,
                    Snackbar.LENGTH_SHORT);
            snackbar.show();
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.BLUE);
            //Toast.makeText(mContext, "Kindly check your Internet Connectivity", Toast.LENGTH_LONG).show();
            return;
        }
        getSupportLoaderManager().restartLoader(this.mLoaderId, null, callback);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getSupportLoaderManager().restartLoader(this.mLoaderId, null, callback);
    }


}


