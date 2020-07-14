package com.example.recyclerview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlantsViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView title;
    private TextView description;
    public Context context;

    public PlantsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
        context = itemView.getContext();
    }

    public void bindTo(Plant plant) {

        Glide.with(context)
                .load(plant.getImageUrl())
                .into(imageView);
        title.setText(plant.getmName());
        description.setText(plant.getmDescription());


    }

}
