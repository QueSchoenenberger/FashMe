package com.lepquold.Types;

import com.lepquold.model.BodyParts;
import com.lepquold.model.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Types {
    public static HashMap<String,Type> getTypes(){
        HashMap<String,Type> types = new HashMap<>();
        types.put("T-Shirt",new Type("T-Shirt", BodyParts.TORSO));
        types.put("Pullover",new Type("Pollover",BodyParts.TORSO));
        types.put("Jacket",new Type("Jacket",BodyParts.TORSO));
        types.put("Pants",new Type("Pants",BodyParts.LEGS));
        types.put("Hat",new Type("Hat",BodyParts.HEAD));
        types.put("Glasses",new Type("Glasses",BodyParts.FACE));
        types.put("Shoe",new Type("Shoe",BodyParts.FEET));
        return types;
    }
}
