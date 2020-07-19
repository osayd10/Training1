package com.example.recyclerview;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlantsViewHolder extends RecyclerView.ViewHolder {

    private ImageView mPlantImageView;
    private TextView mPlantTitle;
    private TextView mPlantDescription;
    private Context mContext;
    private Button mAddToFavoritePlantsButton;
    private ImageView mFavoriteStar;

    public PlantsViewHolder(@NonNull View itemView, PlantsAdapter.PlantClickedListener plantClickedListener) {
        super(itemView);
        setupViewHolder(plantClickedListener);

    }

    private void setupViewHolder(PlantsAdapter.PlantClickedListener plantClickedListener){
        mFavoriteStar = (ImageView) itemView.findViewById(R.id.favoriteStar);
        mPlantImageView = itemView.findViewById(R.id.plantImageView);
        mPlantTitle = itemView.findViewById(R.id.plantTitleTextView);
        mPlantDescription = itemView.findViewById(R.id.plantDescriptionTextView);

        Button deletePlantButton = itemView.findViewById(R.id.deletePlantButton);
        mAddToFavoritePlantsButton = itemView.findViewById(R.id.favoritePlantButton);
        mContext = itemView.getContext();

        itemView.setOnClickListener(v -> plantClickedListener.onPlantClicked(getAdapterPosition()));
        deletePlantButton.setOnClickListener(v -> plantClickedListener.onDeletePlant(getAdapterPosition()));
        mAddToFavoritePlantsButton.setOnClickListener(v -> plantClickedListener.onAddToFavoritePlants(getAdapterPosition()));
    }

    public void bind(Plant plant) {
        Glide.with(mContext)
                .load(plant.getImageUrl())
                .into(mPlantImageView);
        mPlantTitle.setText(plant.getName());
        mPlantDescription.setText(plant.getDescription());

        if (plant.getIsFavorite()) {
            mFavoriteStar.setVisibility(View.VISIBLE);
            mAddToFavoritePlantsButton.setText(R.string.unfavorite);
        } else {
            mFavoriteStar.setVisibility(View.GONE);
            mAddToFavoritePlantsButton.setText(R.string.favorite);
        }
    }
}
