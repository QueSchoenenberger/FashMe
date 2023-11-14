package com.lepquold.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Clothing implements Parcelable, Serializable {
    private String name;
    private String description;
     double temperature;
    private boolean isWaterProof;
    private Style style;
    private Type type;

    public Clothing(String description, double temperature, boolean isWaterProof, Style style, Type type) {
        this.description = description;
        this.temperature = temperature;
        this.isWaterProof = isWaterProof;
        this.style = style;
        this.type = type;
    }

    protected Clothing(Parcel in) {
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
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(temperature);
        dest.writeByte((byte) (isWaterProof ? 1 : 0));
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getTemperature() {
        return temperature;
    }

    public boolean isWaterProof() {
        return isWaterProof;
    }

    public Style getStyle() {
        return style;
    }

    public Type getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setWaterProof(boolean waterProof) {
        isWaterProof = waterProof;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
