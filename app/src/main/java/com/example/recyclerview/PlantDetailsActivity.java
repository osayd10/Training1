package com.example.recyclerview;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.recyclerview.databinding.ActivityPlantsDetailsBinding;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class PlantDetailsActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPlantsDetailsBinding activityPlantsDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_plants_details);

        ImageView mDetailsImageView = findViewById(R.id.detailsimageView);
        Context context = this;
        Plant plant = getIntent().getParcelableExtra(MainActivity.EXTRA_PLANT);
        if (plant != null) {
            Glide.with(context)
                    .load(plant.getImageUrl())
                    .into(mDetailsImageView);
        }
        activityPlantsDetailsBinding.setPlant(plant);
    }
}