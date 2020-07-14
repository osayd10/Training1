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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        PlantsAdapter mPlantsAdapter = new PlantsAdapter();
        recyclerView.setAdapter(mPlantsAdapter);
        List<Plant> plants = new ArrayList<>();
        ProgressBar progressBar = findViewById(R.id.progressBar_cyclic);
        progressBar.setProgress(10);
        try {
            @NonNull JSONArray jarray = new JSONArray(loadJSONFromAsset(this));


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
        mPlantsAdapter.submitList(plants);
        progressBar.setVisibility(View.GONE);

    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("Plants");
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