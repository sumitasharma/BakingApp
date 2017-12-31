package com.example.sumitasharma.bakingapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Defining all Constants and getting data from Baking JSON Object
 */

public class BakingUtils {
    //public static final String RECIPE_ID = "recipe_id";
    public static final String TITLE = "title";
    public static final String RECIPE_OBJECT = "recipe_object";
    public static final String WIDGET_RECIPE_OBJECT = "widget_recipe_object";
    public static final String PLAYER_POSITION = "player_position";
    public static final String CURRENT_POSITION_RECYCLER_VIEW = "current_position_recycler_view";
    public static final String CURRENT_EXPANDED_POSITION = "current_expanded_position";
    public static final String SAVED_LAYOUT_MANAGER = "saved_layout_manager";
    public static final String DEFAULT_THUMBNAIL = "https://membership.cyberlink.com/prog/learning-center/img/thumbnail-play-button.png";
    public static final String INDEX_VALUE = "index_value";
    public static final String STEPS = "steps";
    public final static String IS_TABLET = "is_tablet";
    public final static String KEY_INGREDIENT = "ingredient";
    public static final String STEP_DESCRIPTION = "step_description";
    public static final String STEP_VIDEO = "step_video";
    private final static String DEFAULT_BAKING_IMAGE_PATH = "https://cdn.pixabay.com/photo/2017/10/04/18/27/oven-baked-cheese-2817144_1280.jpg";
    private final static String CONTENT_JSON_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String INGREDIENTS = "ingredients";
    // private final static String STEPS = "steps";
    private final static String IMAGE = "image";
    private final static String SERVINGS = "servings";
    private final static String QUANTITY = "quantity";
    private final static String MEASURE = "measure";
    private final static String INGREDIENT = "ingredient";
    private final static String STEP_ID = "id";
    private final static String SHORT_DESCRIPTION = "shortDescription";
    private final static String DESCRIPTION = "description";
    private final static String videoURL = "videoURL";
    private final static String thumbnailURL = "thumbnailURL";

    /**
     * Builds the URL for Movies
     */
    public static URL buildUrl() {
        Uri builtUri = Uri.parse(CONTENT_JSON_URL).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    // Connecting to Internet
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    // Convert JSON string to MovieDetails for SortBy returning MovieDetails[] array for Main Activity
    public static ArrayList<Recipe> convertJsonToRecipeObjects() throws JSONException {
        //Convert fullJsonMoviesData to JsonObject
        String urlResponse = null;
        URL jsonURL = buildUrl();
        try {
            urlResponse = getResponseFromHttpUrl(jsonURL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray bakingDataArray = new JSONArray(urlResponse);
        ArrayList<Recipe> results = new ArrayList<>();
        for (int i = 0; i < bakingDataArray.length(); i++) {
            Recipe recipe = new Recipe();
            recipe.setId(bakingDataArray.getJSONObject(i).getInt(ID));
            recipe.setName(bakingDataArray.getJSONObject(i).getString(NAME));
            recipe.setImage(getDefaultorActualImage(bakingDataArray.getJSONObject(i).getString(IMAGE)));
            recipe.setServings(bakingDataArray.getJSONObject(i).getString(SERVINGS));
            JSONArray ingredientsJSONArray = bakingDataArray.getJSONObject(i).getJSONArray(INGREDIENTS);
            ArrayList<Ingredient> ingredientsArray = new ArrayList<>();
            for (int j = 0; j < ingredientsJSONArray.length(); j++) {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredient(ingredientsJSONArray.getJSONObject(j).getString(INGREDIENT));
                ingredient.setMeasure(ingredientsJSONArray.getJSONObject(j).getString(MEASURE));
                ingredient.setQuantity(ingredientsJSONArray.getJSONObject(j).getString(QUANTITY));
                ingredientsArray.add(ingredient);
            }
            recipe.setIngredients(ingredientsArray);
            JSONArray stepJSONArray = bakingDataArray.getJSONObject(i).getJSONArray(STEPS);
            ArrayList<Step> stepArray = new ArrayList<>();
            for (int k = 0; k < stepJSONArray.length(); k++) {
                Step step = new Step();
                step.setId(stepJSONArray.getJSONObject(k).getString(STEP_ID));
                step.setDescription(stepJSONArray.getJSONObject(k).getString(DESCRIPTION));
                step.setShortDescription(stepJSONArray.getJSONObject(k).getString(SHORT_DESCRIPTION));
                step.setThumbnailURL(stepJSONArray.getJSONObject(k).getString(thumbnailURL));
                step.setVideoURL(stepJSONArray.getJSONObject(k).getString(videoURL));
                stepArray.add(step);
            }
            recipe.setSteps(stepArray);
            results.add(recipe);
        }
        return results;
    }

    private static String getDefaultorActualImage(String imagePath) {
        String returnedImage = imagePath;
        if (TextUtils.isEmpty(returnedImage)) {
            // if (returnedImage.isEmpty()) {
            returnedImage = DEFAULT_BAKING_IMAGE_PATH;
        }
        return returnedImage;
    }

    /**
     * Checks Internet Connectivity
     *
     * @return true if the Internet Connection is available, false otherwise.
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } else
            return false;
    }

}


