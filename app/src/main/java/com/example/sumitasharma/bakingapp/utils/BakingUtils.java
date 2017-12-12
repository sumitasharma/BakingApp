package com.example.sumitasharma.bakingapp.utils;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class BakingUtils {
    public final static String IS_TABLET = "is_tablet";
    public final static String KEY_INGREDIENT = "ingredient";
    public static final String STEP_DESCRIPTION = "step_description";
    public static final String STEP_VIDEO = "step_video";
    private static final String TAG = BakingUtils.class.getSimpleName();
    private final static String DEFAULT_BAKING_IMAGE_PATH = "https://cdn.pixabay.com/photo/2017/10/04/18/27/oven-baked-cheese-2817144_1280.jpg";
    private final static String CONTENT_JSON_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String INGREDIENTS = "ingredients";
    private final static String STEPS = "steps";
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
        Log.i(TAG, "Inside convertJsonToRecipeObjects in BakingUtils");
        try {
            urlResponse = getResponseFromHttpUrl(jsonURL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray bakingDataArray = new JSONArray(urlResponse);
        //JSONArray bakingDataArray = bakingDataObject.getJSONArray(bakingDataObject.toString());
        // JSONArray recipeArray = bakingDataObject.getJSONArray(0);

        Log.i(TAG, "Inside ConvertJson" + bakingDataArray.toString());
        Log.i(TAG, "Inside ConvertJson: Length is:" + bakingDataArray.length());
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
        Log.i(TAG, "Results Size is:" + results.size());
        for (Recipe result : results) {
            Log.i(TAG, "Recipe Name:" + result.getName());
        }
        return results;
    }

    private static String getDefaultorActualImage(String imagePath) {
        String returnedImage = imagePath;
        if (returnedImage.isEmpty()) {
            returnedImage = DEFAULT_BAKING_IMAGE_PATH;
        }
        return returnedImage;
    }


}
