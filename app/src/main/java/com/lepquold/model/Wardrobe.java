package com.lepquold.model;

import java.io.Serializable;
import java.util.List;

public class Wardrobe implements Serializable {
    private int wardrobeID;
    private List<Clothing> clothes;

    public Wardrobe(List<Clothing> clothes) {
        this.clothes = clothes;
    }

    public List<Clothing> getClothes() {
        return clothes;
    }

    public void addItem(Clothing clothing) {
        this.clothes.add(clothing);
    }
}
