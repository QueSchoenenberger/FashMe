//package com.lepquold;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//import com.lepquold.model.BodyParts;
//import com.lepquold.model.Clothing;
//import com.lepquold.model.Outfit;
//import com.lepquold.model.OutfitRequest;
//import com.lepquold.model.Style;
//import com.lepquold.model.Type;
//import com.lepquold.model.Wardrobe;
//import com.lepquold.service.OutfitGeneratorService;
//import com.lepquold.service.WeatherService;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class ExampleUnitTest {
//    @Test
//    public void generateOutfit_generates_correct_outfit() {
//        Style casual = new Style("casual");
//        Type tshirtType = new Type("T-Shirt", BodyParts.TORSO);
//        Type pantsType = new Type("Pants", BodyParts.LEGS);
//        Type shoeType = new Type("Shoes", BodyParts.FEET);
//        Type hatType = new Type("Hat", BodyParts.HEAD);
//
//        Clothing tshirt = new Clothing("white T-Shirt", 23.00, false, casual, tshirtType);
//        Clothing tshirt2 = new Clothing("green T-Shirt", 25.00, false, casual, tshirtType);
//        Clothing shoe2 = new Clothing("black shoes", 20.00, true, casual, shoeType);
//        Clothing shoe = new Clothing("white shoes", 23.00, false, casual, shoeType);
//        Clothing jeans = new Clothing("jeans", 19.00, false, casual, pantsType);
//        Clothing jeans2 = new Clothing("ripped jeans", 24.00, false, casual, pantsType);
//        Clothing hat = new Clothing("black hat", 25.00, false, casual, hatType);
//
//        List<Clothing> clothes = new ArrayList<>();
//        clothes.add(tshirt);
//        clothes.add(tshirt2);
//        clothes.add(shoe);
//        clothes.add(shoe2);
//        clothes.add(jeans);
//        clothes.add(jeans2);
//        clothes.add(hat);
//
//        Wardrobe wardrobe = new Wardrobe(clothes);
//        OutfitRequest request = new OutfitRequest();
//        request.temperature = 20.00;
//        request.isRaining = false;
//        request.style = casual;
//
//        OutfitGeneratorService service = new OutfitGeneratorService();
//
//        List<Outfit> outfits = service.generateOutfits(request, wardrobe);
//        System.out.println(outfits);
//    }
//
//    @Test
//    public void getTemperatureOfLocation() throws IOException {
//        WeatherService service = new WeatherService();
//        OutfitRequest request = service.getTemperatureForLocation("Zuerich");
//        System.out.println(request);
//    }
//}