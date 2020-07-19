package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class PlantsAdapter extends ListAdapter<Plant, PlantsViewHolder> {

    private PlantClickedListener mPlantClickedListener;

    public PlantsAdapter(PlantClickedListener plantClickedListener) {
        super(new DiffUtil.ItemCallback<Plant>() {
            @Override
            public boolean areItemsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
                return oldItem.equals(newItem);
            }
        });
        mPlantClickedListener = plantClickedListener;
    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlantsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items_plant, parent, false), mPlantClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public interface PlantClickedListener {

        void onPlantClicked(int position);

        void onDeletePlant(int position);

        void onAddToFavoritePlants(int position);

    }
}
