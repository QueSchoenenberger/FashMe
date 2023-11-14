package com.lepquold;

import org.junit.Test;
import static org.junit.Assert.*;

import com.lepquold.helper.TypePopulator;
import com.lepquold.model.*;
import com.lepquold.service.OutfitGeneratorService;
import com.lepquold.service.WeatherService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExampleUnitTest {

    @Test
    public void generateOutfit_generates_correct_outfit() {
        // Set up clothing items
        Style casual = Style.Casual;
        Type tshirtType = TypePopulator.getTypes().get("T-Shirt");
        Type pantsType = TypePopulator.getTypes().get("Pants");
        Type shoeType = TypePopulator.getTypes().get("Shoe");
        Type hatType = TypePopulator.getTypes().get("Hat");

        Clothing tshirt = new Clothing("white T-Shirt", 23.00, false, casual, tshirtType);
        Clothing tshirt2 = new Clothing("green T-Shirt", 25.00, false, casual, tshirtType);
        Clothing shoe2 = new Clothing("black shoes", 20.00, true, casual, shoeType);
        Clothing shoe = new Clothing("white shoes", 23.00, false, casual, shoeType);
        Clothing jeans = new Clothing("jeans", 19.00, false, casual, pantsType);
        Clothing jeans2 = new Clothing("ripped jeans", 24.00, false, casual, pantsType);
        Clothing hat = new Clothing("black hat", 25.00, false, casual, hatType);

        // Create a wardrobe with the clothing items
        List<Clothing> clothes = new ArrayList<>();
        clothes.add(tshirt);
        clothes.add(tshirt2);
        clothes.add(shoe);
        clothes.add(shoe2);
        clothes.add(jeans);
        clothes.add(jeans2);
        clothes.add(hat);

        Wardrobe wardrobe = new Wardrobe(clothes);

        // Create an outfit request
        OutfitRequest request = new OutfitRequest(20.00, false, Style.Casual);

        // Generate outfits
        OutfitGeneratorService service = new OutfitGeneratorService();
        List<Outfit> outfits = service.generateOutfits(request, wardrobe, Style.Casual);

        // Perform assertions or validations based on the expected outcome
        assertNotNull(outfits);
        assertFalse(outfits.isEmpty());
        System.out.println(outfits); // Print the generated outfits
    }

    @Test
    public void getTemperatureOfLocation() throws IOException {
        // Test fetching temperature and rain status for a location
        WeatherService service = new WeatherService();
        WeatherInfo info = service.getTemperatureAndRainStatus("Zuerich");

        // Perform assertions or validations based on the expected outcome
        assertNotNull(info);
        System.out.println(info.getTemperature() + "\n" + info.isRaining()); // Print the temperature and rain status
    }
}