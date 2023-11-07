package com.lepquold.model;

public class Outfit {
    private Clothing clothingForHead;
    private Clothing clothingForFace;
    private Clothing clothingForTorso;
    private Clothing clothingForLegs;
    private Clothing clothingForFeet;

    public void setClothingForHead(Clothing clothingForHead) {
        this.clothingForHead = clothingForHead;
    }

    public void setClothingForFace(Clothing clothingForFace) {
        this.clothingForFace = clothingForFace;
    }

    public void setClothingForTorso(Clothing clothingForTorso) {
        this.clothingForTorso = clothingForTorso;
    }

    public void setClothingForLegs(Clothing clothingForLegs) {
        this.clothingForLegs = clothingForLegs;
    }

    public void setClothingForFeet(Clothing clothingForFeet) {
        this.clothingForFeet = clothingForFeet;
    }

    public Clothing getClothingForHead() {
        return clothingForHead;
    }

    public Clothing getClothingForFace() {
        return clothingForFace;
    }

    public Clothing getClothingForTorso() {
        return clothingForTorso;
    }

    public Clothing getClothingForLegs() {
        return clothingForLegs;
    }

    public Clothing getClothingForFeet() {
        return clothingForFeet;
    }
}
