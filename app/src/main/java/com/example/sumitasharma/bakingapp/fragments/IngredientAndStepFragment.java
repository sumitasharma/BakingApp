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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sumitasharma.bakingapp.R;
import com.example.sumitasharma.bakingapp.adapter.RecipeStepsAdapter;
import com.example.sumitasharma.bakingapp.utils.Ingredient;
import com.example.sumitasharma.bakingapp.utils.Step;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.utils.BakingUtils.INDEX_VALUE;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.IS_TABLET;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.KEY_INGREDIENT;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEPS;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.TITLE;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.isOnline;


public class IngredientAndStepFragment extends Fragment implements RecipeStepsAdapter.RecipeStepsClickListener {

    private static final String TAG = IngredientAndStepFragment.class.getSimpleName();
    //public BakingAppMediaAndInstructionActivity activity;
    //Activity mActivity;
    private PutTheDataInActivity mPutTheDataToActivity;
    private Context mContext;
    private boolean mTwoPane;
    private int mIndex;
    private String mTitle;
    private onStepClickedListener mCallback;
    private ArrayList<Step> mStep = null;
    //private Recipe mRecipe = null;

    public IngredientAndStepFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.ingredients_and_step_fragment, container, false);
        ArrayList<Ingredient> mIngredient = getArguments().getParcelableArrayList(KEY_INGREDIENT);
        mStep = getArguments().getParcelableArrayList(STEPS);
        mTwoPane = getArguments().getBoolean(IS_TABLET);
        mTitle = getArguments().getString(TITLE);

        // Programmatically setting the TableLayout
        TableLayout ingredientsTableLayout = (TableLayout) rootView.findViewById(R.id.ingredients_table_layout);
        TableRow ingredientTitleRow = new TableRow(mContext);

        // Setting the Titles of the Table
        TextView ingredientTitleTextView = new TextView(mContext);
        ingredientTitleTextView.setTextSize(20);
        ingredientTitleTextView.setText(R.string.ingredients);
        ingredientTitleTextView.setGravity(Gravity.START);
        ingredientTitleTextView.setPadding(20, 10, 80, 10);
        ingredientTitleTextView.setTextColor(getResources().getColor(R.color.cardview_dark_background));
        ingredientTitleTextView.setTypeface(null, Typeface.BOLD_ITALIC);
        ingredientTitleRow.addView(ingredientTitleTextView);

        TextView quantityTitleTextView = new TextView(mContext);
        quantityTitleTextView.setTextSize(20);
        quantityTitleTextView.setText(R.string.quantity);
        quantityTitleTextView.setGravity(Gravity.CENTER);
        quantityTitleTextView.setPadding(20, 10, 80, 10);
        quantityTitleTextView.setTextColor(getResources().getColor(R.color.cardview_dark_background));
        quantityTitleTextView.setTypeface(null, Typeface.BOLD_ITALIC);
        ingredientTitleRow.addView(quantityTitleTextView);

        TextView measurementTitleTextView = new TextView(mContext);
        measurementTitleTextView.setTextSize(20);
        measurementTitleTextView.setText(R.string.measurement);
        measurementTitleTextView.setGravity(Gravity.END);
        measurementTitleTextView.setPadding(10, 10, 10, 80);
        measurementTitleTextView.setTextColor(getResources().getColor(R.color.cardview_dark_background));
        measurementTitleTextView.setTypeface(null, Typeface.BOLD_ITALIC);
        ingredientTitleRow.addView(measurementTitleTextView);


        ingredientsTableLayout.addView(ingredientTitleRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        int i = 1;
        // Populating the rows with the Ingredient data
        for (Ingredient ingredient : mIngredient) {

            TableRow ingredientTableRow = new TableRow(mContext);
            ingredientTableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


            TextView ingredientTextView = new TextView(mContext);
            ingredientTextView.setTextSize(15);
            ingredientTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
            ingredientTextView.setTextColor(getResources().getColor(R.color.cardview_dark_background));
            String ingredientCapital = String.valueOf(ingredient.getIngredient().charAt(0)).toUpperCase() + ingredient.getIngredient().substring(1, ingredient.getIngredient().length());
            ingredientTextView.setText("" + i++ + ".  ");
            ingredientTextView.append(ingredientCapital);
            ingredientTextView.setMaxWidth(300);
            ingredientTextView.setPadding(20, 10, 10, 10);
            ingredientTextView.setGravity(Gravity.START);


            TextView quantityTextView = new TextView(mContext);
            quantityTextView.setTextSize(15);
            quantityTextView.setText(ingredient.getQuantity());
            quantityTextView.setGravity(Gravity.CENTER);
            quantityTextView.setTypeface(null, Typeface.BOLD_ITALIC);
            quantityTextView.setPadding(20, 10, 10, 10);
            quantityTextView.setTextColor(getResources().getColor(R.color.cardview_dark_background));


            TextView measurementTextView = new TextView(mContext);
            measurementTextView.setTextSize(15);
            measurementTextView.setText(ingredient.getMeasure());
            measurementTextView.setTypeface(null, Typeface.BOLD_ITALIC);
            measurementTextView.setPadding(20, 10, 120, 10);
            measurementTextView.setGravity(Gravity.END);
            measurementTextView.setTextColor(getResources().getColor(R.color.cardview_dark_background));


            ingredientTableRow.addView(ingredientTextView);
            ingredientTableRow.addView(quantityTextView);
            ingredientTableRow.addView(measurementTextView);

            ingredientsTableLayout.addView(ingredientTableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            Log.i(TAG, "recipe ingredient : " + ingredient.getIngredient());
        }
        RecyclerView backingDetailAppRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_steps_recycler_view);
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
            mPutTheDataToActivity = (PutTheDataInActivity) context;
        } catch (ClassCastException e) {
            Log.i(TAG, "implement onStepClickedListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "Title on resume is :" + mTitle);
        mPutTheDataToActivity.giveTheDataToActivity(mTitle, mStep, mIndex);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mPutTheDataToActivity.giveTheDataToActivity(mTitle, mStep, mIndex);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(INDEX_VALUE, mIndex);
        outState.putParcelableArrayList(STEPS, mStep);
        outState.putBoolean(IS_TABLET, mTwoPane);
        outState.putString(TITLE, mTitle);
//        Log.i(TAG, "Index value in onSaveInstanceState :" + mIndex);
//        Log.i(TAG, "TwoPane value got from onSaveInstanceState:" + mTwoPane);
//        Log.i(TAG, "mTitle value in onSaveInstance:" + mTitle);
        mPutTheDataToActivity.giveTheDataToActivity(mTitle, mStep, mIndex);
    }


    @Override
    public void onClickStepCard(int stepCardPosition, ArrayList<Step> stepArrayList) {
        if (!isOnline(mContext)) {
            Toast.makeText(mContext, "Kindly check your Internet Connectivity", Toast.LENGTH_LONG).show();
        } else {
            mIndex = stepCardPosition;
            mCallback.onStepClickSelected(stepCardPosition, stepArrayList, mTwoPane);
        }
    }


    public interface onStepClickedListener {
        void onStepClickSelected(int stepCardPosition, ArrayList<Step> stepArrayList, boolean twopane);
    }

    public interface PutTheDataInActivity {
        void giveTheDataToActivity(String title, ArrayList<Step> stepArrayList, int index);
    }

}

