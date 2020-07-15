package com.example.recyclerview;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

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

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupPlantsRecyclerView();
    }

    private List<Plant> getPlantsFromJson() {
        List<Plant> plants = new ArrayList<>();
        try {
            JSONArray jarray = new JSONArray(loadJSONFromAsset(this));

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject plantJson = jarray.getJSONObject(i);
                String plantId = plantJson.getString("plantId");
                String name = plantJson.getString("name");
                String description = plantJson.getString("description");
                String imageUrl = plantJson.getString("imageUrl");
                plants.add(new Plant(plantId, name, description, imageUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return plants;
    }

    private void setupPlantsRecyclerView() {
        mRecyclerView = findViewById(R.id.RecyclerView);
        mProgressBar = findViewById(R.id.progressBar_cyclic);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        PlantsAdapter plantsAdapter = new PlantsAdapter();
        mRecyclerView.setAdapter(plantsAdapter);

        plantsAdapter.submitList(getPlantsFromJson());
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
}