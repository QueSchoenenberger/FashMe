package com.lepquold.helper;

import com.lepquold.model.BodyParts;
import com.lepquold.model.Type;

import java.util.HashMap;

/**
 * Helper class for populating clothing types.
 */
public class TypePopulator {

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
}
