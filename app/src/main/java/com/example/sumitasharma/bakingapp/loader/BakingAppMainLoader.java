package com.example.sumitasharma.bakingapp.loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.sumitasharma.bakingapp.utils.BakingUtils;
import com.example.sumitasharma.bakingapp.utils.Recipe;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class BakingAppMainLoader extends AsyncTaskLoader<String> {

    private final Context mContext;
    private final AsyncResponse mAsyncResponse;
    private ArrayList<Recipe> mRecipe;

    public BakingAppMainLoader(Context context, ArrayList<Recipe> recipes, BakingAppMainLoader.AsyncResponse asyncResponse) {
        super(context);
        this.mContext = context;
        this.mRecipe = recipes;
        this.mAsyncResponse = asyncResponse;
        //Log.i(TAG, "Constructor called. BakingLoader called");
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

        //Log.i(TAG, "Post Execute Function... Baking App");
        if (recipes != null) {
            Log.i(TAG, "Baking App.... onPostExecute. Recipe not null");
            mRecipe = recipes;
            mAsyncResponse.processFinish(mRecipe);

        } else {
            Log.i(TAG, "Post Execute Function. Recipe details null");
        }
    }

    @Override
    public String loadInBackground() {
        if (!isOnline())
            return null;
        URL bakingURL = BakingUtils.buildUrl();
        try {
            BakingUtils.getResponseFromHttpUrl(bakingURL);
            mRecipe = BakingUtils.convertJsonToRecipeObjects();
        } catch (IOException | JSONException e) {
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


