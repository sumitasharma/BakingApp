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
import com.example.sumitasharma.bakingapp.utils.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.sumitasharma.bakingapp.utils.BakingUtils.DEFAULT_THUMBNAIL;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecyclerViewHolderStepCard> {

    private final static String TAG = BakingAppMainAdapter.class.getSimpleName();
    private Context mContext;
    private RecipeStepsClickListener mRecipeStepsClickListener;
    private ArrayList<Step> mStep;

    public RecipeStepsAdapter(Context context, RecipeStepsClickListener recipeStepsClickListener, ArrayList<Step> step) {
        Log.i(TAG, "Inside RecipeStepsAdapter Constructor");
        this.mContext = context;
        this.mStep = step;
        this.mRecipeStepsClickListener = recipeStepsClickListener;
    }

    @Override
    public RecyclerViewHolderStepCard onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_steps_viewholder, parent, false);
        return new RecyclerViewHolderStepCard(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderStepCard holder, int position) {
        holder.mStepTextView.setText(mStep.get(position).getShortDescription());
        //Log.i(TAG,"Position:"+position + "Thumbnail:" + mStep.get(position).getThumbnailURL());
        if (!mStep.get(position).getThumbnailURL().isEmpty() && !mStep.get(position).getThumbnailURL().contains("mp4")) {
            Picasso.with(mContext).load(mStep.get(position).getThumbnailURL()).into(holder.mThumbnail);
            Log.i(TAG, "Position:" + position + "Thumbnail:" + mStep.get(position).getThumbnailURL());


            // SHOW AMIT THE ERROR

        } else {
            Picasso.with(mContext).load(DEFAULT_THUMBNAIL).into(holder.mThumbnail);
        }

    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount Called. Size is:" + mStep.size());
        return mStep.size();
    }

    public interface RecipeStepsClickListener {
        void onClickStepCard(int stepCardPosition, ArrayList<Step> stepArrayList);
    }

    class RecyclerViewHolderStepCard extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CardView mStepCardView;
        private TextView mStepTextView;
        private ImageView mThumbnail;


        RecyclerViewHolderStepCard(View itemView) {
            super(itemView);
            mStepCardView = (CardView) itemView.findViewById(R.id.recipe_step_card_view_holder);
            mStepCardView.setOnClickListener(this);
            mStepTextView = (TextView) itemView.findViewById(R.id.recipe_step_text_view_holder);
            mStepTextView.setOnClickListener(this);
            mThumbnail = (ImageView) itemView.findViewById(R.id.step_thumbnail_view_holder);
            mThumbnail.setOnClickListener(this);
        }

        /**
         * Function attaches onClick to viewHolder position
         *
         * @param v view
         */
        @Override
        public void onClick(View v) {
            int onClickPosition = getAdapterPosition();
            mRecipeStepsClickListener.onClickStepCard(onClickPosition, mStep);
        }
    }
}

