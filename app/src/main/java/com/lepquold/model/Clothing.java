package com.lepquold.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Clothing implements Parcelable {
    private int clothingID;
    public String name;
    public String description;
    public double temperature;
    public boolean isWaterProof;
    public Style style;
    public Type type;

    public Clothing(String description, double temperature, boolean isWaterProof, Style style, Type type) {
        this.description = description;
        this.temperature = temperature;
        this.isWaterProof = isWaterProof;
        this.style = style;
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }
}
