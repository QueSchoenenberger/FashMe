package com.lepquold.service;

import com.lepquold.model.BodyParts;
import com.lepquold.model.Clothing;
import com.lepquold.model.Outfit;
import com.lepquold.model.OutfitRequest;
import com.lepquold.model.Style;
import com.lepquold.model.Wardrobe;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for generating outfits based on user preferences,
 * wardrobe contents, and weather conditions.
 */
public class OutfitGeneratorService {

    /**
     * Generates a list of outfits based on the provided request, wardrobe, and style.
     *
     * @param request   The outfit request containing temperature, raining status, etc.
     * @param wardrobe  The wardrobe containing available clothing items.
     * @param style     The desired style for the outfits.
     * @return          A list of generated outfits.
     */
    public List<Outfit> generateOutfits(OutfitRequest request, Wardrobe wardrobe, Style style) {
        List<Clothing> filteredClothes = filterClothes(request.getTemperature(), request.isRaining(), style, wardrobe.getClothes());
        List<Outfit> outfits = new ArrayList<>();

        // Separate clothes based on type and body part
        List<Clothing> headClothes = new ArrayList<>();
        List<Clothing> faceClothes = new ArrayList<>();
        List<Clothing> torsoClothes = new ArrayList<>();
        List<Clothing> legsClothes = new ArrayList<>();
        List<Clothing> feetClothes = new ArrayList<>();
        List<Clothing> hatClothes = new ArrayList<>();

        for (Clothing clothing : filteredClothes) {
            if (clothing.getType().getBodyPart() == BodyParts.HEAD) {
                headClothes.add(clothing);
            } else if (clothing.getType().getBodyPart()  == BodyParts.FACE) {
                faceClothes.add(clothing);
            } else if (clothing.getType().getBodyPart()  == BodyParts.TORSO) {
                torsoClothes.add(clothing);
            } else if (clothing.getType().getBodyPart()  == BodyParts.LEGS) {
                legsClothes.add(clothing);
            } else if (clothing.getType().getBodyPart()  == BodyParts.FEET) {
                feetClothes.add(clothing);
            } else if (clothing.getType().getBodyPart()  == BodyParts.HEAD && clothing.getType().getName().equals("Hat")) {
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

    /**
     * Filters the list of clothes based on temperature, raining status, and style.
     *
     * @param temperature   The current temperature.
     * @param isRaining     Indicates whether it is raining.
     * @param style         The desired style.
     * @param clothes       The list of available clothing items.
     * @return              The filtered list of clothes.
     */
    private List<Clothing> filterClothes(double temperature, boolean isRaining, Style style, List<Clothing> clothes) {
        List<Clothing> filtered = new ArrayList<>();

        for (Clothing clothing : clothes) {
            boolean isWaterproof = clothing.isWaterProof();
            double clothingTemperature = clothing.getTemperature();

            if (isRaining && !isWaterproof) {
                continue;
            }

            if (clothingTemperature >= (temperature - 10) && clothingTemperature <= (temperature + 10) &&
                    clothing.getStyle() == style) {
                filtered.add(clothing);
            }
        }

        return filtered;
    }
}
