package com.lepquold.helper;

import com.lepquold.model.BodyParts;
import com.lepquold.model.Style;
import com.lepquold.model.Type;

import java.util.HashMap;

/**
 * Helper class for types.
 */
public class TypeManager {

    /**
     * Get a HashMap containing predefined clothing types.
     *
     * @return HashMap containing clothing types.
     */
    public static HashMap<String, Type> getTypes() {
        HashMap<String, Type> types = new HashMap<>();
        types.put("T-Shirt", new Type("T-Shirt", BodyParts.TORSO));
        types.put("Pullover", new Type("Pullover", BodyParts.TORSO));
        types.put("Jacket", new Type("Jacket", BodyParts.TORSO));
        types.put("Pants", new Type("Pants", BodyParts.LEGS));
        types.put("Hat", new Type("Hat", BodyParts.HEAD));
        types.put("Glasses", new Type("Glasses", BodyParts.FACE));
        types.put("Shoe", new Type("Shoe", BodyParts.FEET));
        return types;
    }
    // Method to get the selected style based on the spinner selection
    public static Style getSelectedStyle(String input){
        switch (input) {
            case "Formal-Business":
                return Style.FormalBusiness;
            case "Smart-Casual":
                return Style.SmartCasual;
            case "Leger":
                return Style.Leger;
            case "Sportive":
                return Style.Sportive;
            case "Vintage":
                return Style.Vintage;
            default:
                return Style.Casual;
        }
    }



}
