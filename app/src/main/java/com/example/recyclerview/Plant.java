package com.example.recyclerview;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Plant implements Parcelable {

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

    public static final Creator<Plant> CREATOR = new Creator<Plant>() {
        @Override
        public Plant createFromParcel(Parcel in) {
            return new Plant(in);
        }

        @Override
        public Plant[] newArray(int size) {
            return new Plant[size];
        }
    };


    protected Plant(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mDescription = in.readString();
        mImageUrl = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mName);
        parcel.writeString(mDescription);
        parcel.writeString(mImageUrl);
    }
}
