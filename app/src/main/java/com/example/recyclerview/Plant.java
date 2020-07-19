package com.example.recyclerview;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

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
    private boolean mIsFavorite;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Plant plant = (Plant) object;
        return mName.equals(plant.mName) && mId.equals(plant.mId) && mDescription.equals(plant.mDescription)
                && mImageUrl.equals(plant.mImageUrl) && mIsFavorite == plant.mIsFavorite;
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

    public static Plant from(Plant plant) {
        return new Plant()
                .setId(plant.getId())
                .setName(plant.getName())
                .setDescription(plant.getDescription())
                .setImageUrl(plant.getImageUrl())
                .setIsFavorite(plant.getIsFavorite());
    }

    public Plant() {

    }

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

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getId() {
        return mId;
    }

    public boolean getIsFavorite() {
        return mIsFavorite;
    }

    public Plant setId(String id) {
        mId = id;
        return this;
    }

    public Plant setName(String name) {
        mName = name;
        return this;
    }

    public Plant setDescription(String description) {
        mDescription = description;
        return this;
    }

    public Plant setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
        return this;
    }

    public Plant setIsFavorite(boolean IsFavorite) {
        mIsFavorite = IsFavorite;
        return this;
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
