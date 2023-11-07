package com.lepquold.service;

import com.lepquold.model.BodyParts;
import com.lepquold.model.Clothing;
import com.lepquold.model.Outfit;
import com.lepquold.model.OutfitRequest;
import com.lepquold.model.Wardrobe;

import java.util.ArrayList;
import java.util.List;

public class OutfitGeneratorService {
    public Outfit generateOutfit(OutfitRequest request, Wardrobe wardrobe) {
        List<Clothing> filteredClothes = filterClothes(request.temperature, request.isRaining, wardrobe.getClothes());
        Outfit outfit = new Outfit();

        for (Clothing clothing : filteredClothes) {
            BodyParts bodyPart = clothing.type.bodyPart;
            String dressCode = clothing.style.dressCode;

            if (bodyPart == BodyParts.FACE && outfit.getClothingForFace() == null && dressCode.equals(request.style.dressCode)) {
                outfit.setClothingForFace(clothing);
            } else if (bodyPart == BodyParts.HEAD && outfit.getClothingForHead() == null && dressCode.equals(request.style.dressCode)) {
                outfit.setClothingForHead(clothing);
            } else if (bodyPart == BodyParts.TORSO && outfit.getClothingForTorso() == null && dressCode.equals(request.style.dressCode)) {
                outfit.setClothingForTorso(clothing);
            } else if (bodyPart == BodyParts.LEGS && outfit.getClothingForLegs() == null && dressCode.equals(request.style.dressCode)) {
                outfit.setClothingForLegs(clothing);
            } else if (bodyPart == BodyParts.FEET && outfit.getClothingForFeet() == null && dressCode.equals(request.style.dressCode)) {
                outfit.setClothingForFeet(clothing);
            }
        }

        return outfit;
    }


    private List<Clothing> filterClothes(float temperature, boolean isRaining, List<Clothing> clothes) {
        List<Clothing> filtered = new ArrayList<>();

        for (Clothing clothing : clothes) {
            boolean isWaterproof = clothing.isWaterProof;
            float clothingTemperature = clothing.temperature;

            if (isRaining && !isWaterproof) {
                continue;
            }

            if (clothingTemperature >= (temperature - 10) && clothingTemperature <= (temperature + 10)) {
                filtered.add(clothing);
            }
        }

        return filtered;
    }

}
