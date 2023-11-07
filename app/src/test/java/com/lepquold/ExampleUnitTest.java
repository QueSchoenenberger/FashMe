package com.lepquold;

import org.junit.Test;

import static org.junit.Assert.*;

import com.lepquold.model.BodyParts;
import com.lepquold.model.Clothing;
import com.lepquold.model.Outfit;
import com.lepquold.model.OutfitRequest;
import com.lepquold.model.Style;
import com.lepquold.model.Type;
import com.lepquold.model.Wardrobe;
import com.lepquold.service.OutfitGeneratorService;

import java.util.ArrayList;
import java.util.List;


public class ExampleUnitTest {
    @Test
    public void generateOutfit_generates_correct_outfit() {
        Style casual = new Style("casual");
        Type thshirtType = new Type("T-Shirt", BodyParts.TORSO);
        Type pantsType = new Type("Pants", BodyParts.LEGS);
        Type shoeType = new Type("Shoes", BodyParts.FEET);
        Type hatType = new Type("Hat", BodyParts.HEAD);

        Clothing tshirt = new Clothing("white T-Shirt", 23.0F, false, casual, thshirtType);
        Clothing shoe = new Clothing("shoe", 23.0F, false, casual, shoeType);
        Clothing jeans = new Clothing("jeans", 19.0F, false, casual, pantsType);
        Clothing hat = new Clothing("hat", 25.0F, false, casual, hatType);

        List<Clothing> clothes = new ArrayList<>();
        clothes.add(tshirt);
        clothes.add(shoe);
        clothes.add(jeans);
        clothes.add(hat);


        Wardrobe wardrobe = new Wardrobe(clothes);
        OutfitRequest request = new OutfitRequest();
        request.temperature = 20;
        request.isRaining = false;

        OutfitGeneratorService service = new OutfitGeneratorService();

        Outfit outfit = service.generateOutfit(request, wardrobe);
    }
}