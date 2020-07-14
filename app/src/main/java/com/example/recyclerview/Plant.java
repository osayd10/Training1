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

    public Plant(String Id, String Name, String Description, String img) {
        mId = Id;
        mName = Name;
        mDescription = Description;
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

    public String getmName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
