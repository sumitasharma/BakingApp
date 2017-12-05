package com.example.sumitasharma.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sumitasharma.bakingapp.R;


public class RecipeIngredientAndStepFragment extends Fragment {
    public RecipeIngredientAndStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_ingredientandstep_fragment, container, false);
        return rootView;
    }
}
