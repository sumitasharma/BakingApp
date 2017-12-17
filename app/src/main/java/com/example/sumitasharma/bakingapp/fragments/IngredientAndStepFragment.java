package com.example.sumitasharma.bakingapp.fragments;

import android.app.Activity;
import android.content.Context;
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

import static com.example.sumitasharma.bakingapp.utils.BakingUtils.INDEX_VALUE;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.IS_TABLET;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.KEY_INGREDIENT;
import static com.example.sumitasharma.bakingapp.utils.BakingUtils.STEPS;


public class IngredientAndStepFragment extends Fragment implements RecipeStepsAdapter.RecipeStepsClickListener {

    private static final String TAG = IngredientAndStepFragment.class.getSimpleName();
    public BakingAppMediaAndInstructionActivity activity;
    RecyclerView backingDetailAppRecyclerView;
    Context mContext;
    boolean twoPane;
    int mIndex;
    Activity mActivity;
    onStepClickedListener mCallback;
    private ArrayList<Step> mStep = null;
    private ArrayList<Ingredient> mIngredient = null;
    private Recipe mRecipe = null;


    public IngredientAndStepFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.recipe_ingredientandstep_fragment, container, false);
        mIngredient = getArguments().getParcelableArrayList(KEY_INGREDIENT);
        mStep = getArguments().getParcelableArrayList(STEPS);
        twoPane = getArguments().getBoolean(IS_TABLET);
        TextView ingredientTextView = (TextView) rootView.findViewById(R.id.recipe_ingredients_ingredients);
        TextView measurementTextView = (TextView) rootView.findViewById(R.id.recipe_ingredients_measurement);
        TextView quantityTextView = (TextView) rootView.findViewById(R.id.recipe_ingredients_quantity);

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
        } catch (ClassCastException e) {
            Log.i(TAG, "implement onStepClickedListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(INDEX_VALUE, mIndex);
        outState.putParcelableArrayList(STEPS, mStep);
    }

//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        savedInstanceState.putInt(INDEX_VALUE, mIndex);
//        savedInstanceState.putParcelableArrayList(STEPS, mStep);
//    }

    @Override
    public void onClickStepCard(int stepCardPosition, ArrayList<Step> stepArrayList) {
        mCallback.onStepClickSelected(stepCardPosition, stepArrayList);
    }

    public interface onStepClickedListener {
        void onStepClickSelected(int stepCardPosition, ArrayList<Step> stepArrayList);
    }
}
