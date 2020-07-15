package com.example.recyclerview;

import android.os.Build;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Plant {

    @SerializedName("plantId")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("imageUrl")
    private String mImageUrl;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Plant plant = (Plant) object;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.equals(mName, plant.mName) &&
                    Objects.equals(mId, plant.mId);
        }
        return false;
    }

    public Plant(String id, String name, String description, String img) {
        mId = id;
        mName = name;
        mDescription = description;
        mImageUrl = img;
    }

    static DiffUtil.ItemCallback<Plant> DIFF_CALLBACK = new DiffUtil.ItemCallback<Plant>() {
        @Override
        public boolean areItemsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
            return oldItem.mId.equals(newItem.mId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
            return oldItem.equals(newItem);
        }
    };

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
