package com.example.sumitasharma.bakingapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.sumitasharma.bakingapp.BakingAppMediaAndInstructionActivity;
import com.example.sumitasharma.bakingapp.R;
import com.example.sumitasharma.bakingapp.adapter.RecipeStepsAdapter;
import com.example.sumitasharma.bakingapp.utils.Ingredient;
import com.example.sumitasharma.bakingapp.utils.Recipe;
import com.example.sumitasharma.bakingapp.utils.Step;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.utils.BakingUtils.INDEX_VALUE;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.IS_TABLET;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.KEY_INGREDIENT;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEPS;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.TITLE;


public class IngredientAndStepFragment extends Fragment implements RecipeStepsAdapter.RecipeStepsClickListener {

    private static final String TAG = IngredientAndStepFragment.class.getSimpleName();
    public BakingAppMediaAndInstructionActivity activity;
    RecyclerView backingDetailAppRecyclerView;
    Context mContext;
    boolean mTwoPane;
    int mIndex;
    String mTitle;
    Activity mActivity;
    onStepClickedListener mCallback;
    private ArrayList<Step> mStep = null;
    private ArrayList<Ingredient> mIngredient = null;
    private Recipe mRecipe = null;
//    public PutTheTitle mPutTheTitle;

    public IngredientAndStepFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        // View rootView = inflater.inflate(R.layout.recipe_ingredientandstep_fragment, container, false);
        View rootView = inflater.inflate(R.layout.ingredients_and_step_fragment, container, false);
        mIngredient = getArguments().getParcelableArrayList(KEY_INGREDIENT);
        mStep = getArguments().getParcelableArrayList(STEPS);
        mTwoPane = getArguments().getBoolean(IS_TABLET);
        mTitle = getArguments().getString(TITLE);
//        TextView ingredientTextView = (TextView) rootView.findViewById(R.id.recipe_ingredients_ingredients);
//        TextView measurementTextView = (TextView) rootView.findViewById(R.id.recipe_ingredients_measurement);
//        TextView quantityTextView = (TextView) rootView.findViewById(R.id.recipe_ingredients_quantity);


        TableLayout ingredientsTableLayout = (TableLayout) rootView.findViewById(R.id.ingredients_table_layout);
        TableRow ingredientTitleRow = new TableRow(mContext);

        TextView ingredientTitleTextView = new TextView(mContext);
        ingredientTitleTextView.setTextSize(20);
        ingredientTitleTextView.setText("Ingredients");
        ingredientTitleTextView.setPadding(20, 10, 250, 80);
        ingredientTitleTextView.setTypeface(null, Typeface.BOLD_ITALIC);
        ingredientTitleRow.addView(ingredientTitleTextView);

        TextView measurementTitleTextView = new TextView(mContext);
        measurementTitleTextView.setTextSize(20);
        measurementTitleTextView.setText("Measurement");
        measurementTitleTextView.setPadding(10, 10, 60, 80);
        measurementTitleTextView.setTypeface(null, Typeface.BOLD_ITALIC);
        ingredientTitleRow.addView(measurementTitleTextView);

        TextView quantityTitleTextView = new TextView(mContext);
        quantityTitleTextView.setTextSize(20);
        quantityTitleTextView.setText("Quantity");
        quantityTitleTextView.setPadding(5, 10, 5, 80);
        quantityTitleTextView.setTypeface(null, Typeface.BOLD_ITALIC);
        ingredientTitleRow.addView(quantityTitleTextView);


        ingredientsTableLayout.addView(ingredientTitleRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));


        for (Ingredient ingredient : mIngredient) {
            TableRow ingredientTableRow = new TableRow(mContext);
            ingredientTableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView ingredientTextView = new TextView(mContext);
            String ingredientCapital = String.valueOf(ingredient.getIngredient().charAt(0)).toUpperCase() + ingredient.getIngredient().substring(1, ingredient.getIngredient().length());
            ingredientTextView.setText(ingredientCapital);
            ingredientTextView.setMaxWidth(150);
            ingredientTextView.setPadding(30, 10, 80, 0);
            ingredientTextView.setTypeface(null, Typeface.BOLD_ITALIC);
            TextView quantityTextView = new TextView(mContext);
            quantityTextView.setText(ingredient.getQuantity());
            quantityTextView.setTypeface(null, Typeface.BOLD_ITALIC);
            TextView measurementTextView = new TextView(mContext);
            measurementTextView.setText(ingredient.getMeasure());
            measurementTextView.setTypeface(null, Typeface.BOLD_ITALIC);
            ingredientTableRow.addView(ingredientTextView);
            ingredientTableRow.addView(quantityTextView);
            ingredientTableRow.addView(measurementTextView);
            ingredientsTableLayout.addView(ingredientTableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            Log.i(TAG, "recipe ingredient : " + ingredient.getIngredient());
        }
//        b.setText("Dynamic Button");
//        b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
///* Add Button to row. */
//        tr.addView(b);
///* Add row to TableLayout. */
////tr.setBackgroundResource(R.drawable.sf_gradient_03);
//        tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));


//        for (Ingredient ingredient : mIngredient) {
////            ingredientTextView.append("\n");
////            ingredientTextView.append("* ");
//            ingredientTextView.append(ingredient.getIngredient());
//          //  quantityTextView.append("\n");
//            quantityTextView.append(ingredient.getQuantity());
//          //  measurementTextView.append("\n");
//            measurementTextView.append(ingredient.getMeasure());
//
//            Log.i(TAG, "recipe ingredient : " + ingredient.getIngredient());
//        }
        backingDetailAppRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_steps_recycler_view);
        backingDetailAppRecyclerView.setHasFixedSize(true);
        backingDetailAppRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(mContext, this, mStep);
        backingDetailAppRecyclerView.setAdapter(recipeStepsAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        mContext = context;
        Log.i(TAG, "onAttach Called : " + activity);
        try {
            mCallback = (onStepClickedListener) context;
            // mPutTheTitle = (PutTheTitle) context;
            //   mSendSavedInstanceToActivity = (SendSavedInstanceToActivity) context;
        } catch (ClassCastException e) {
            Log.i(TAG, "implement onStepClickedListener");
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.i(TAG,"Title on resume is :"+mTitle);
//        mPutTheTitle.giveTheTitle(mTitle);
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(INDEX_VALUE, mIndex);
        outState.putParcelableArrayList(STEPS, mStep);
        outState.putBoolean(IS_TABLET, mTwoPane);
        outState.putString(TITLE, mTitle);
        Log.i(TAG, "Index value in onSaveInstanceState :" + mIndex);
        Log.i(TAG, "twopane value got from onSaveInstanceState:" + mTwoPane);
        Log.i(TAG, "mTitle value in onSaveInstance:" + mTitle);
//        mSendSavedInstanceToActivity.PassSavedInstanceToActivity(mIndex,mStep,mTwoPane);
    }

//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        savedInstanceState.putInt(INDEX_VALUE, mIndex);
//        savedInstanceState.putParcelableArrayList(STEPS, mStep);
//    }


    @Override
    public void onClickStepCard(int stepCardPosition, ArrayList<Step> stepArrayList) {
        mIndex = stepCardPosition;
        mCallback.onStepClickSelected(stepCardPosition, stepArrayList, mTwoPane);
    }

    public interface onStepClickedListener {
        void onStepClickSelected(int stepCardPosition, ArrayList<Step> stepArrayList, boolean twopane);
    }

//    public interface PutTheTitle {
//        void giveTheTitle(String title);
//    }
}
