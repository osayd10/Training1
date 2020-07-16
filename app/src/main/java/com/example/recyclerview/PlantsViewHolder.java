package com.example.recyclerview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlantsViewHolder extends RecyclerView.ViewHolder {
    private ImageView mPlantImageView;
    private TextView mPlantTitle;
    private TextView mPlantDescription;
    public Context context;
    PlantsAdapter.PlantsOnClickListener plantsOnClickListener;

    public PlantsViewHolder(@NonNull View itemView, PlantsAdapter.PlantsOnClickListener plantsOnClickListener) {
        super(itemView);
        mPlantImageView = itemView.findViewById(R.id.PlantImageView);
        mPlantTitle = itemView.findViewById(R.id.PlantTitle);
        mPlantDescription = itemView.findViewById(R.id.PlantDescription);
        context = itemView.getContext();
        this.plantsOnClickListener = plantsOnClickListener;
        itemView.setOnClickListener(v -> plantsOnClickListener.plantsOnClick(getAdapterPosition()));
    }

    public void bindTo(Plant plant) {
        Glide.with(context)
                .load(plant.getImageUrl())
                .into(mPlantImageView);
        mPlantTitle.setText(plant.getName());
        mPlantDescription.setText(plant.getDescription());
    }
}
