
/*
** Copyright 2013 Square, Inc.

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/


package com.example.sumitasharma.bakingapp.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sumitasharma.bakingapp.R;
import com.example.sumitasharma.bakingapp.utils.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class BakingAppMainAdapter extends RecyclerView.Adapter<BakingAppMainAdapter.RecyclerViewHolderBakeCard> {
    private final static String TAG = BakingAppMainAdapter.class.getSimpleName();
    private final Context mContext;
    private final BakingAppClickListener mBackingAppClickListener;
    private final ArrayList<Recipe> mRecipe;


    public BakingAppMainAdapter(Context context, BakingAppClickListener bakingAppClickListener, ArrayList<Recipe> recipe) {
        Log.i(TAG, "Inside BakingAppMainAdapter");
        this.mContext = context;
        this.mRecipe = recipe;
        this.mBackingAppClickListener = bakingAppClickListener;
    }

    @Override
    public RecyclerViewHolderBakeCard onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baking_main_view_holder, parent, false);
        return new RecyclerViewHolderBakeCard(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderBakeCard holder, int position) {
        Log.i(TAG, "mRecipe.get(position).getImage():" + mRecipe.get(position).getImage());
        Picasso.with(mContext).load(mRecipe.get(position).getImage()).into(holder.mBakingMainImage);
        holder.mBakingMainName.setText(mRecipe.get(position).getName());
        holder.mBakingMainServing.setText(mRecipe.get(position).getServings());
    }

    @Override
    public int getItemCount() {
        //Log.i(TAG, "getItemCount Called. Size is:" + mRecipe.size());
        return mRecipe.size();
    }

    public interface BakingAppClickListener {
        void onClickBakeCard(int bakeCardPosition);
    }

    class RecyclerViewHolderBakeCard extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CardView mBakingCardView;
        private final ImageView mBakingMainImage;
        private final TextView mBakingMainName;
        private final TextView mBakingMainServing;

        RecyclerViewHolderBakeCard(View itemView) {
            super(itemView);
            mBakingCardView = (CardView) itemView.findViewById(R.id.baking_main_card_view_holder);
            mBakingCardView.setOnClickListener(this);
            mBakingMainImage = (ImageView) itemView.findViewById(R.id.baking_main_view_image);
            mBakingMainImage.setOnClickListener(this);
            mBakingMainName = (TextView) itemView.findViewById(R.id.baking_main_view_name);
            mBakingMainName.setOnClickListener(this);
            mBakingMainServing = (TextView) itemView.findViewById(R.id.baking_main_view_serving);
            mBakingMainServing.setOnClickListener(this);
        }

        /**
         * Function attaches onClick to viewHolder position
         *
         * @param v view
         */
        @Override
        public void onClick(View v) {
            int onClickPosition = getAdapterPosition();
            mBackingAppClickListener.onClickBakeCard(onClickPosition);
        }
    }
}
