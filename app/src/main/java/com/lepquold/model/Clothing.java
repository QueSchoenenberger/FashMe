package com.lepquold.model;

public class Clothing {
    private int clothingID;
    public String name;
    private String description;
    public float temperature;
    public boolean isWaterProof;
    public Style style;
    public Type type;

    public Clothing(String description, float temperature, boolean isWaterProof, Style style, Type type) {
        this.description = description;
        this.temperature = temperature;
        this.isWaterProof = isWaterProof;
        this.style = style;
        this.type = type;
    }
}
