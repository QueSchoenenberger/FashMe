package com.lepquold.model;

import java.util.List;

public class Wardrobe {
    private int wardrobeID;
    private List<Clothing> clothes;

    public List<Clothing> getClothes() {
        return clothes;
    }

    public Wardrobe(List<Clothing> clothes) {
        this.clothes = clothes;
    }

    public void addItem(Clothing clothing) {
        clothes.add(clothing);
    }
}
