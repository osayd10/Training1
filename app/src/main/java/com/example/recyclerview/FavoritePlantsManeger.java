package com.example.recyclerview;

import java.util.ArrayList;
import java.util.List;

public class FavoritePlantsManeger {

    private static List<Plant> mFavoritePlantsList;

    private FavoritePlantsManeger() {

    }

    public static List<Plant> getFavoritePlantsList() {
        if (mFavoritePlantsList == null) {
            mFavoritePlantsList = new ArrayList<>();
        }
        return mFavoritePlantsList;
    }
}
