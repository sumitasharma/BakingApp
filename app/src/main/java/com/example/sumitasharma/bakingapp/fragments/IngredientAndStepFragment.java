package com.example.sumitasharma.bakingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sumitasharma.bakingapp.BakingAppMediaAndInstructionActivity;
import com.example.sumitasharma.bakingapp.R;
import com.example.sumitasharma.bakingapp.adapter.RecipeStepsAdapter;
import com.example.sumitasharma.bakingapp.utils.Ingredient;
import com.example.sumitasharma.bakingapp.utils.Recipe;
import com.example.sumitasharma.bakingapp.utils.Step;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.BakingAppDetailActivity.INGREDIENT;


public class IngredientAndStepFragment extends Fragment implements RecipeStepsAdapter.RecipeStepsClickListener {

    // private final LoaderManager.LoaderCallbacks<String[]> callback = IngredientAndStepFragment.this;
    //public static int mLoaderIdRecipe = 102;
    public static final String INDEX_VALUE = "index_value";
    public static final String STEPS = "steps";
    private static final String TAG = IngredientAndStepFragment.class.getSimpleName();
    public BakingAppMediaAndInstructionActivity activity;
    RecyclerView backingDetailAppRecyclerView;
    Context mContext;
    private ArrayList<Step> mStep = null;
    private ArrayList<Ingredient> mIngredient = null;
    private Recipe mRecipe = null;

//    @Override
//    public void onAttach(Context context){
//        super.onAttach(mContext);
//        BakingAppMediaAndInstructionActivity activity;
//        if (context instanceof BakingAppMediaAndInstructionActivity){
//            activity=(BakingAppMediaAndInstructionActivity) context;
//        }
//    }


    public IngredientAndStepFragment() {

    }

//    public void passData(Context context, ArrayList<Ingredient> ingredients, ArrayList<Step> steps) {
//        this.mContext = context;
//        this.mIngredient = ingredients;
//        this.mStep = steps;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.recipe_ingredientandstep_fragment, container, false);
        mIngredient = getArguments().getParcelableArrayList(INGREDIENT);
        mStep = getArguments().getParcelableArrayList(STEPS);
        TextView ingredientTextView = (TextView) rootView.findViewById(R.id.recipe_ingredients_ingredients);
        TextView measurementTextView = (TextView) rootView.findViewById(R.id.recipe_ingredients_measurement);
        TextView quantityTextView = (TextView) rootView.findViewById(R.id.recipe_ingredients_quantity);
        ingredientTextView.setText("Ingredients");
        ingredientTextView.append("\n");
        measurementTextView.setText("Measurement");
        measurementTextView.append("\n");
        quantityTextView.setText("Quantity");
        quantityTextView.append("\n");

        for (Ingredient ingredient : mIngredient) {
            ingredientTextView.append("\n");
            ingredientTextView.append(ingredient.getIngredient());
            quantityTextView.append("\n");
            quantityTextView.append(ingredient.getQuantity());
            measurementTextView.append("\n");
            measurementTextView.append(ingredient.getMeasure());

            Log.i(TAG, "recipe ingredient : " + ingredient.getIngredient());
        }
        backingDetailAppRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_steps_recycler_view);
        backingDetailAppRecyclerView.setHasFixedSize(true);
//        if (getResources().getBoolean(R.bool.isTablet)) {
        backingDetailAppRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(mContext, this, mStep);
        backingDetailAppRecyclerView.setAdapter(recipeStepsAdapter);
//        } else {
//            backingDetailAppRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        }
        //  initializeLoader(mLoaderIdRecipe, mContext);
        return rootView;
    }
//    public void initializeLoader(int loaderId, Context context) {
//        Log.i(TAG, "Inside initializeLoader of IngredientAndStepFragment");
//        this.mLoaderIdRecipe = loaderId;
//        this.mContext = context;
//        LoaderManager loaderManager = getLoaderManager();
//       // Loader<String> loaderRecipe = loaderManager.getLoader(mLoaderIdRecipe);
//        if (loaderManager.getLoader(mLoaderIdRecipe) == null) {
//            Log.i(TAG, "Inside initializeLoader of IngredientAndStepFragment loaderRecipe null");
//            loaderManager.initLoader(mLoaderIdRecipe, null, callback);
//        } else {
//            Log.i(TAG, "Inside initializeLoader of IngredientAndStepFragment loaderRecipe not null");
//            loaderManager.restartLoader(mLoaderIdRecipe, null, callback);
//        }
//    }
//
//    @Override
//    public Loader onCreateLoader(int id, Bundle args) {
//        return new RecipeIngredientAndStepLoader(mContext);
//    }
//
//    @Override
//    public void onLoadFinished(Loader loader, Object data) {
//        Log.i(TAG, "Inside onLoadFinished in Recipe Step : " + mStep.get(0).getShortDescription());
//        //ImageView bakingImage = (ImageView) findViewById(R.id.baking_main_view_image);
//        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(mContext,this,mStep);
//        backingDetailAppRecyclerView.setAdapter(recipeStepsAdapter);
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader loader) {
//
//    }

    @Override
    public void onClickStepCard(int stepCardPosition) {
        StepVideoAndInstructionFragment stepVideoAndInstructionFragment = new StepVideoAndInstructionFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STEPS, mStep);
        args.putInt(INDEX_VALUE, stepCardPosition);
        //stepVideoAndInstructionFragment.setArguments(args);
        Intent intent = new Intent();
        intent.setClass(getActivity(), BakingAppMediaAndInstructionActivity.class);
        intent.putExtras(args);
        startActivity(intent);

//Inflate the fragment
        // getFragmentManager().beginTransaction().add(R.id.step_video_card_view, stepVideoAndInstructionFragment).commit();
    }

}
