package com.example.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoritePlantsActivity extends AppCompatActivity {

    private List<Plant> mFavoritePlantsList = new ArrayList<>();
    private RecyclerView mFavoriteRecyclerView;
    private ProgressBar mFavoriteProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_plants);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Favorite plants");
        }
        mFavoriteRecyclerView = findViewById(R.id.favoriteRecyclerView);
        mFavoriteProgressBar = findViewById(R.id.favoriteProgressBarCyclic);

        // mFavoritePlantsList = getIntent().getParcelableArrayListExtra(PlantsActivity.EXTRA_PLANT);
        mFavoritePlantsList = FavoritePlantsManeger.getFavoritePlantsList();
        if (mFavoritePlantsList != null) {
            if (mFavoritePlantsList.size() == 0) {
                showEmptyListMessage();
            } else {
                setupFavoritePlantsRecyclerView();
            }
        }
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

    private void showEmptyListMessage() {
        TextView emptyListMessage = findViewById(R.id.emptyListTextView);
        mFavoriteProgressBar.setVisibility(View.GONE);
        emptyListMessage.setVisibility(View.VISIBLE);
    }

    private void setupFavoritePlantsRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mFavoriteRecyclerView.setLayoutManager(layoutManager);
        FavoritePlantsAdapter favoritePlantsAdapter = new FavoritePlantsAdapter();
        mFavoriteRecyclerView.setAdapter(favoritePlantsAdapter);
        favoritePlantsAdapter.submitList(mFavoritePlantsList);
        hideLoading();
    }

    private void hideLoading() {
        mFavoriteProgressBar.setVisibility(View.GONE);
        mFavoriteRecyclerView.setVisibility(View.VISIBLE);
    }
}