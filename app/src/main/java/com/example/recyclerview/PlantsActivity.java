package com.example.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PlantsActivity extends AppCompatActivity implements PlantsAdapter.PlantClickedListener {

    public static final String EXTRA_PLANT = PlantsActivity.class.getName() + "_PLANT_EXTRA";
    // private List<Plant> mFavoritePlantsList = new ArrayList<>();
    private List<Plant> mPlantsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private PlantsAdapter mPlantsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);
        setTitle("Plants");
        setupPlantsRecyclerView();
        FavoritePlantsManeger.getFavoritePlantsList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            Intent intent = new Intent(PlantsActivity.this, FavoritePlantsActivity.class);
            // intent.putExtra(EXTRA_PLANT, (Serializable) mFavoritePlantsList);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Plant> getPlantsFromJson() {
        try {
            JSONArray jarray = new JSONArray(loadJSONFromAsset(this));
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject plantJson = jarray.getJSONObject(i);
                String plantId = plantJson.getString("plantId");
                String name = plantJson.getString("name");
                String description = plantJson.getString("description");
                String imageUrl = plantJson.getString("imageUrl");
                mPlantsList.add(new Plant(plantId, name, description, imageUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mPlantsList;
    }

    private void setupPlantsRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progressBarCyclic);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mPlantsAdapter = new PlantsAdapter(this);
        mRecyclerView.setAdapter(mPlantsAdapter);
        mPlantsAdapter.submitList(getPlantsFromJson());
        hideLoading();
    }

    private void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("plants.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                json = new String(buffer, StandardCharsets.UTF_8);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onPlantClicked(int position) {
        Plant plant = mPlantsList.get(position);
        Intent i = new Intent(PlantsActivity.this, PlantDetailsActivity.class);
        i.putExtra(EXTRA_PLANT, plant);
        startActivity(i);
    }

    @Override
    public void onDeletePlant(int position) {
        mPlantsList.remove(position);
        mPlantsAdapter.submitList(new ArrayList<>(mPlantsList));

        Toast.makeText(this, "Item has been deleted.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddToFavoritePlants(int position) {
        Plant plant = mPlantsList.get(position);
        Plant newPlant = Plant.from(plant);
        List<Plant> newList = new ArrayList<>(mPlantsList);

        if (!newPlant.getIsFavorite()) {
            newList.set(position, newPlant.setIsFavorite(true));
            //mFavoritePlantsList.add(newPlant);
            FavoritePlantsManeger.getFavoritePlantsList().add(newPlant);
        } else {
            newList.set(position, newPlant.setIsFavorite(false));
            // mFavoritePlantsList.remove(plant);
            FavoritePlantsManeger.getFavoritePlantsList().remove(plant);
        }
        mPlantsAdapter.submitList(newList, () -> mPlantsList.set(position, newPlant));
    }
}