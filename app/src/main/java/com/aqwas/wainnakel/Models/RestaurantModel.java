package com.aqwas.wainnakel.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.Nullable;

public class RestaurantModel {

    @SerializedName("name")
    @Expose
    @Nullable
    private String name;

    @SerializedName("cat")
    @Expose
    @Nullable
    private String category;

    @SerializedName("rating")
    @Expose
    @Nullable
    private String rating;

    @SerializedName("lat")
    @Expose
    @Nullable
    private String latitude;

    @SerializedName("lon")
    @Expose
    @Nullable
    private String longitude;

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getCategory() {
        return category;
    }

    public void setCategory(@Nullable String category) {
        this.category = category;
    }

    @Nullable
    public String getRating() {
        return rating;
    }

    public void setRating(@Nullable String rating) {
        this.rating = rating;
    }

    @Nullable
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(@Nullable String latitude) {
        this.latitude = latitude;
    }

    @Nullable
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(@Nullable String longitude) {
        this.longitude = longitude;
    }

}
