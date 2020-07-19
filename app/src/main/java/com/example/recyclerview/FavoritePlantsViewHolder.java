package com.example.recyclerview;

import android.content.Context;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritePlantsViewHolder extends RecyclerView.ViewHolder{

    private ImageView mFavoritePlantImageView;
    private TextView mFavoritePlantTitle;
    private TextView mFavoritePlantDescription;
    private Context mContext;

    public FavoritePlantsViewHolder(@NonNull View itemView) {
        super(itemView);
        mFavoritePlantImageView = itemView.findViewById(R.id.favoriteImageView);
        mFavoritePlantTitle = itemView.findViewById(R.id.favoriteTitleTextView);
        mFavoritePlantDescription = itemView.findViewById(R.id.favoriteDescriptionTextView);
        mContext = itemView.getContext();
    }

    public void favoriteBind(Plant plant) {
        Glide.with(mContext)
                .load(plant.getImageUrl())
                .into(mFavoritePlantImageView);
        mFavoritePlantTitle.setText(plant.getName());
        mFavoritePlantDescription.setText(plant.getDescription());
    }
}