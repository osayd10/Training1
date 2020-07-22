package com.example.recyclerview.plants.favorite;

import com.example.recyclerview.plants.Plant;

import java.util.LinkedHashSet;
import java.util.Set;

public class FavoritePlantsManeger {

    private static FavoritePlantsManeger sInstance;
    private Set<Plant> mFavoritePlantsList = new LinkedHashSet<>();

    private FavoritePlantsManeger() {

    }

    public static FavoritePlantsManeger getInstance() {
        if (sInstance == null) {
            sInstance = new FavoritePlantsManeger();
        }
        return sInstance;
    }

    public Set<Plant> getFavoritePlantsList() {
        return mFavoritePlantsList;
    }

    public void addFavoritePlant(Plant plant) {
        mFavoritePlantsList.add(plant);
    }

    public void removeFavoritePlant(Plant plant) {
        mFavoritePlantsList.remove(plant);
    }
}
