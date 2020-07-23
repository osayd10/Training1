package com.example.recyclerview.plants;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recyclerview.R;
import com.example.recyclerview.plants.details.PlantDetailsActivity;
import com.example.recyclerview.plants.favorite.FavoritePlantsActivity;
import com.example.recyclerview.plants.favorite.FavoritePlantsManeger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PlantsActivity extends AppCompatActivity implements PlantsAdapter.PlantClickedListener, SearchView.OnQueryTextListener {

    public static final String EXTRA_PLANT = PlantsActivity.class.getName() + "_PLANT_EXTRA";
    private android.widget.SearchView searchView;
    private List<Plant> mPlantsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mNoResultTextView;
    private PlantsAdapter mPlantsAdapter;
    private FavoritePlantsManeger mFavoritePlantsManeger;
    private List<Plant> mFilteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);

        Toolbar plantsActivityToolbar = findViewById(R.id.plantsActivityToolbar);
        setSupportActionBar(plantsActivityToolbar);
        setupPlantsRecyclerView();
        mFavoritePlantsManeger = FavoritePlantsManeger.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mNoResultTextView = findViewById(R.id.noResultTextView);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mNoResultTextView.setVisibility(View.GONE);
                mPlantsAdapter.submitList(new ArrayList<>(mPlantsList));
                return true;
            }
        });
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(() -> {
            mPlantsAdapter.submitList(new ArrayList<>(mPlantsList));
            return false;
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            Intent intent = new Intent(PlantsActivity.this, FavoritePlantsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_search) {
            searchView.setIconified(false);
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
        Plant plant = mPlantsAdapter.getCurrentList().get(position);
        Intent i = new Intent(PlantsActivity.this, PlantDetailsActivity.class);
        i.putExtra(EXTRA_PLANT, plant);
        startActivity(i);
    }

    @Override
    public void onDeletePlant(int position) {
        List<Plant> deleteList;
        if (mPlantsAdapter.getCurrentList().equals(mPlantsList)) {
            deleteList = new ArrayList<>(mPlantsList);
            Plant plant = deleteList.remove(position);
            mPlantsList = deleteList;
            mPlantsAdapter.submitList(new ArrayList<>(deleteList));
            mFavoritePlantsManeger.removeFavoritePlant(plant);
        } else if (mPlantsAdapter.getCurrentList().equals(mFilteredList)) {
            deleteList = new ArrayList<>(mFilteredList);
            Plant plant = deleteList.remove(position);
            mFilteredList = deleteList;
            mPlantsList.remove(plant);
            mPlantsAdapter.submitList(new ArrayList<>(deleteList));
            mFavoritePlantsManeger.removeFavoritePlant(plant);
        }
        Toast.makeText(this, "Item has been deleted.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddToFavoritePlants(int position) {
        Plant plant;
        Plant newPlant;
        List<Plant> newList;
        if (mPlantsAdapter.getCurrentList().equals(mPlantsList)) {
            plant = mPlantsList.get(position);
            newPlant = Plant.from(plant);
            newList = new ArrayList<>(mPlantsList);

            if (!newPlant.getIsFavorite()) {
                newList.set(position, newPlant.setIsFavorite(true));
                mFavoritePlantsManeger.addFavoritePlant(newPlant);
            } else {
                newList.set(position, newPlant.setIsFavorite(false));
                mFavoritePlantsManeger.removeFavoritePlant(plant);
            }
            mPlantsAdapter.submitList(newList, () -> mPlantsList.set(position, newPlant));
        } else if (mPlantsAdapter.getCurrentList().equals(mFilteredList)) {
            plant = mFilteredList.get(position);
            newPlant = Plant.from(plant);
            newList = new ArrayList<>(mFilteredList);

            if (!newPlant.getIsFavorite()) {
                newList.set(position, newPlant.setIsFavorite(true));
                mFavoritePlantsManeger.addFavoritePlant(newPlant);
            } else {
                newList.set(position, newPlant.setIsFavorite(false));
                mFavoritePlantsManeger.removeFavoritePlant(plant);
            }
            mFilteredList = newList;
            mPlantsAdapter.submitList(newList, () -> mPlantsList.set(mPlantsList.indexOf(plant), newPlant));
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filter(s);

        return false;
    }

    private void filter(String s) {
        List<Plant> filteredList = new ArrayList<>();
        int i;
        for (i = 0; i < mPlantsList.size(); i++) {
            if (mPlantsList.get(i).getName().toLowerCase().startsWith(s.toLowerCase()) || mPlantsList.get(i).getName().contains(s.toLowerCase())) {
                filteredList.add(mPlantsList.get(i));
                mRecyclerView.setVisibility(View.VISIBLE);
                mNoResultTextView.setVisibility(View.GONE);
            } else if (filteredList.isEmpty()) {
                mRecyclerView.setVisibility(View.GONE);
                mNoResultTextView.setVisibility(View.VISIBLE);
            }
        }
        mFilteredList = filteredList;
        mPlantsAdapter.submitList(new ArrayList<>(filteredList));
    }
}