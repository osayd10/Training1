package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

public class PlantsAdapter extends ListAdapter<Plant, PlantsViewHolder> {

    public PlantsAdapter() {
        super(Plant.DIFF_CALLBACK);

    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new PlantsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsViewHolder holder, int position) {

        Plant plant = getItem(position);
        if (plant != null) {
            holder.bindTo(plant);
        }

    }

}
