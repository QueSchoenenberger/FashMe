package com.lepquold.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Clothing implements Parcelable, Serializable {
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

    protected Clothing(Parcel in) {
        clothingID = in.readInt();
        name = in.readString();
        description = in.readString();
        temperature = in.readDouble();
        isWaterProof = in.readByte() != 0;
    }

    public static final Creator<Clothing> CREATOR = new Creator<Clothing>() {
        @Override
        public Clothing createFromParcel(Parcel in) {
            return new Clothing(in);
        }

        @Override
        public Clothing[] newArray(int size) {
            return new Clothing[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(clothingID);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(temperature);
        dest.writeByte((byte) (isWaterProof ? 1 : 0));
    }
}
