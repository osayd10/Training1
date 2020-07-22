package com.example.recyclerview.plants.details;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.recyclerview.R;
import com.example.recyclerview.databinding.ActivityPlantsDetailsBinding;
import com.example.recyclerview.plants.Plant;
import com.example.recyclerview.plants.PlantsActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class PlantDetailsActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPlantsDetailsBinding activityPlantsDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_plants_details);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ImageView mDetailsImageView = findViewById(R.id.detailsImageView);
        Context context = this;

        Plant plant = getIntent().getParcelableExtra(PlantsActivity.EXTRA_PLANT);
        if (plant != null) {
            Glide.with(context)
                    .load(plant.getImageUrl())
                    .into(mDetailsImageView);
        }
        activityPlantsDetailsBinding.setPlant(plant);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent backIntent = new Intent(getApplicationContext(), PlantsActivity.class);
        backIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(backIntent, 0);
        return true;
    }

}