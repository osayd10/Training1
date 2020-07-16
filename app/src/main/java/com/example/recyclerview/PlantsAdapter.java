package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

public class PlantsAdapter extends ListAdapter<Plant, PlantsViewHolder> {

    private PlantsOnClickListener mPlantsOnClickListener;

    public PlantsAdapter(PlantsOnClickListener plantsOnClickListener) {
        super(Plant.DIFF_CALLBACK);
        this.mPlantsOnClickListener = plantsOnClickListener;
    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlantsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plants_list_items, parent, false), mPlantsOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    public interface PlantsOnClickListener {
        void plantsOnClick(int position);
    }
}
