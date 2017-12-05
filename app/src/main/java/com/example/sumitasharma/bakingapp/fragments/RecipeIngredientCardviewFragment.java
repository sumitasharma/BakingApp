package com.example.sumitasharma.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sumitasharma.bakingapp.R;
import com.example.sumitasharma.bakingapp.utils.Ingredient;
import com.example.sumitasharma.bakingapp.utils.Recipe;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.BakingAppMainActivity.RECIPE_ID;


public class RecipeIngredientCardviewFragment extends Fragment {
    private int mRecipeId;
    private ArrayList<Recipe> mRecipeObjectArray;

    public RecipeIngredientCardviewFragment() {
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_ingredient_cardview_fragment, container, false);
        TextView quantity = (TextView) rootView.findViewById(R.id.recipe_ingredient_quantity);
        TextView measurement = (TextView) rootView.findViewById(R.id.recipe_ingredient_measurement);
        TextView description = (TextView) rootView.findViewById(R.id.recipe_ingredient_ingredient_description);
        Ingredient[] mIngredient = null;
        if (savedInstanceState != null) {
            mRecipeId = savedInstanceState.getInt(RECIPE_ID);
            //mRecipeObjectArray = (ArrayList<Recipe>) savedInstanceState.get;
            //  for(int i=0; i <)
//            setImageResource(mImages.get(mIndex));
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mIndex >= mImages.size() - 1) {
//                        mIndex = -1;
//                    }
//                    mIndex = mIndex + 1;
//                    imageView.setImageResource(mImages.get(mIndex));
//
//                }
//
//            });
//        } else
//            Log.i("", "error");
        }

        quantity.setText(mIngredient[mRecipeId].getQuantity());
        measurement.setText(mIngredient[mRecipeId].getMeasure());
        description.setText(mIngredient[mRecipeId].getIngredient());
        return rootView;
    }

}
