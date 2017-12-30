package com.example.sumitasharma.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.sumitasharma.bakingapp.adapter.BakingAppMainAdapter;
import com.example.sumitasharma.bakingapp.adapter.BakingAppMainAdapter.BakingAppClickListener;
import com.example.sumitasharma.bakingapp.loader.BakingAppMainLoader;
import com.example.sumitasharma.bakingapp.utils.Recipe;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.utils.BakingUtils.RECIPE_OBJECT;

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
        return new BakingAppMainLoader(this, mRecipe, this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (!isOnline()) {
            Toast.makeText(mContext, "Kindly check your Internet Connectivity", Toast.LENGTH_LONG).show();
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
        //Log.i(TAG, "onClickBakeCard : bakecardposition " + bakeCardPosition);
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
        if (isOnline()) {
            this.mRecipe = recipes;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isOnline()) {
            Toast.makeText(mContext, "Kindly check your Internet Connectivity", Toast.LENGTH_LONG).show();
            return;
        }
        getSupportLoaderManager().restartLoader(this.mLoaderId, null, callback);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getSupportLoaderManager().restartLoader(this.mLoaderId, null, callback);
    }

    /**
     * Checks Internet Connectivity
     *
     * @return true if the Internet Connection is available, false otherwise.
     */
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}


