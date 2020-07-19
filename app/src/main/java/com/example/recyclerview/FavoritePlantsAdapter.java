package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

public class FavoritePlantsAdapter extends ListAdapter<Plant, FavoritePlantsViewHolder> {

    public FavoritePlantsAdapter() {
        super(Plant.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public FavoritePlantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavoritePlantsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritePlantsViewHolder holder, int position) {
        holder.favoriteBind(getItem(position));
    }
}