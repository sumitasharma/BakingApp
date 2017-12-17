package com.example.sumitasharma.bakingapp.loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sumitasharma.bakingapp.adapter.BakingAppMainAdapter;
import com.example.sumitasharma.bakingapp.utils.BakingUtils;
import com.example.sumitasharma.bakingapp.utils.Recipe;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class BakingAppMainLoader extends AsyncTaskLoader<String> {

    private final Context mContext;
    private AsyncResponse mAsyncResponse;
    private ArrayList<Recipe> mRecipe;

    public BakingAppMainLoader(Context context, RecyclerView recyclerView, ArrayList<Recipe> recipes, BakingAppMainAdapter.BakingAppClickListener listener, BakingAppMainLoader.AsyncResponse asyncResponse) {
        super(context);
        this.mContext = context;
        this.mRecipe = recipes;
        this.mAsyncResponse = asyncResponse;
        Log.i(TAG, "Constructor called. BakingLoader called");
    }

    /**
     * Checks Internet Connectivity
     *
     * @return true if the Internet Connection is available, false otherwise.
     */
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    /**
     * Setting all the details in the XML file
     */
    private void onPostExecuteLoading(ArrayList<Recipe> recipes) {

        Log.i(TAG, "Post Execute Function... Baking App");
        if (recipes != null) {
            Log.i(TAG, "Baking App.... onPostExecute. Recipe not null");
            mRecipe = recipes;
            //moviesAdapter = new MoviesAdapter(movieDetails,this.mContext,mClickPositionListener);
                /* Setting the adapter in onPostExecuteLoading so the Movies Detail array isn't empty */
            //mMoviesRecyclerView.setAdapter(moviesAdapter);
            mAsyncResponse.processFinish(mRecipe);

        } else {
//            Toast.makeText(mContext, "No Internet Connection or API Limit exceeded.Connect and then choose from Sort By Menu", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Post Execute Function. Recipe details null");
        }
    }

    @Override
    public String loadInBackground() {
        if (!isOnline())
            return null;
        URL bakingURL = BakingUtils.buildUrl();
        try {
            Log.i(TAG, "BakingAppMain Loader... loadInBackground");
            BakingUtils.getResponseFromHttpUrl(bakingURL);
            Log.i(TAG, "BakingAppMain Loader... loadInBackground internet");
            mRecipe = BakingUtils.convertJsonToRecipeObjects();
            Log.i(TAG, "BakingAppMain Loader... loadInBackground recipe, mRecipeSize is:" + mRecipe.size());
        } catch (IOException | JSONException e) {
            Log.i(TAG, "Inside loadInBackground Exception");
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        onPostExecuteLoading(mRecipe);
        return null;
    }

    public interface AsyncResponse {
        void processFinish(ArrayList<Recipe> recipes);
    }
}
