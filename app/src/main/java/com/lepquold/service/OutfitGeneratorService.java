package com.lepquold.service;

import com.lepquold.model.BodyParts;
import com.lepquold.model.Clothing;
import com.lepquold.model.Outfit;
import com.lepquold.model.OutfitRequest;
import com.lepquold.model.Style;
import com.lepquold.model.Wardrobe;

import java.util.ArrayList;
import java.util.List;

public class OutfitGeneratorService {
    public List<Outfit> generateOutfits(OutfitRequest request, Wardrobe wardrobe, Style style) {
        List<Clothing> filteredClothes = filterClothes(request.getTemperature(), request.isRaining(), style, wardrobe.getClothes());
        List<Outfit> outfits = new ArrayList<>();

        List<Clothing> headClothes = new ArrayList<>();
        List<Clothing> faceClothes = new ArrayList<>();
        List<Clothing> torsoClothes = new ArrayList<>();
        List<Clothing> legsClothes = new ArrayList<>();
        List<Clothing> feetClothes = new ArrayList<>();
        List<Clothing> hatClothes = new ArrayList<>();

        // Separate clothes based on type and body part
        for (Clothing clothing : filteredClothes) {
            if (clothing.type.bodyPart == BodyParts.HEAD) {
                headClothes.add(clothing);
            } else if (clothing.type.bodyPart == BodyParts.FACE) {
                faceClothes.add(clothing);
            } else if (clothing.type.bodyPart == BodyParts.TORSO) {
                torsoClothes.add(clothing);
            } else if (clothing.type.bodyPart == BodyParts.LEGS) {
                legsClothes.add(clothing);
            } else if (clothing.type.bodyPart == BodyParts.FEET) {
                feetClothes.add(clothing);
            } else if (clothing.type.bodyPart == BodyParts.HEAD && clothing.type.name.equals("Hat")) {
                hatClothes.add(clothing);
            }
        }

        // Generate outfits with all possible combinations
        for (Clothing torso : torsoClothes) {
            for (Clothing legs : legsClothes) {
                for (Clothing feet : feetClothes) {
                    Outfit outfit = new Outfit();
                    outfit.setClothingForTorso(torso);
                    outfit.setClothingForLegs(legs);
                    outfit.setClothingForFeet(feet);

                    for (Clothing tshirtOrPullover : torsoClothes) {
                        outfit.setClothingForTorso(tshirtOrPullover);

                        for (Clothing hat : hatClothes) {
                            outfit.setClothingForHead(hat);

                            for (Clothing face : faceClothes) {
                                outfit.setClothingForFace(face);
                                outfits.add(new Outfit(outfit));
                            }

                            outfit.setClothingForFace(null); // Clear face
                        }

                        outfits.add(new Outfit(outfit));
                    }
                }
            }
        }

        return outfits;
    }

    private List<Clothing> filterClothes(double temperature, boolean isRaining, Style style, List<Clothing> clothes) {
        List<Clothing> filtered = new ArrayList<>();

        for (Clothing clothing : clothes) {
            boolean isWaterproof = clothing.isWaterProof;
            double clothingTemperature = clothing.temperature;

            if (isRaining && !isWaterproof) {
                continue;
            }

            if (clothingTemperature >= (temperature - 10) && clothingTemperature <= (temperature + 10) &&
                    clothing.style == style) {
                filtered.add(clothing);
            }
        }

        return filtered;
    }
}
