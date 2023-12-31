package com.lepquold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.lepquold.databinding.ActivityRewievOutfitBinding;
import com.lepquold.helper.ClothingAdapter;
import com.lepquold.helper.TypeManager;
import com.lepquold.model.Outfit;
import com.lepquold.model.OutfitRequest;
import com.lepquold.model.Style;
import com.lepquold.model.Wardrobe;
import com.lepquold.model.WeatherInfo;
import com.lepquold.service.OutfitGeneratorService;
import com.lepquold.service.WeatherApiTask;
import com.lepquold.service.WeatherInfoListener;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * This activity is responsible for reviewing and displaying generated outfits based on weather conditions and user preferences.
 */
public class RewievOutfitActivity extends AppCompatActivity implements WeatherInfoListener {
    ClothingAdapter clothingAdapter = new ClothingAdapter();
    private ActivityRewievOutfitBinding binding;

    int outfitCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewiev_outfit);
        binding = ActivityRewievOutfitBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        outfitCount = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        new WeatherApiTask(this).execute(intent.getStringExtra("location"));
    }

    /**
     * Callback method triggered when weather information is received.
     *
     * @param weatherInfo The received weather information.
     */
    @Override
    public void onWeatherInfoReceived(WeatherInfo weatherInfo) {
        Intent intent = getIntent();
        String styleString = intent.getStringExtra("style");
        String location = intent.getStringExtra("location");
        double temperature = weatherInfo.getTemperature();
        boolean isRaining = weatherInfo.isRaining();


        Style selectedStyle = TypeManager.getSelectedStyle(styleString);

        List<Outfit> generatedOutfits = getOutfits(temperature, isRaining, selectedStyle);


        if (generatedOutfits.size() > 0 && generatedOutfits.size() > outfitCount) {
            showOutfit(generatedOutfits.get(outfitCount));
        } else {
            Toast t = Toast.makeText(this, "No suitable outfit found", Toast.LENGTH_SHORT);
            t.show();
        }
        showDetails(location, isRaining, styleString, temperature);
    }

    /**
     * Displays weather and style details on the user interface.
     *
     * @param location      The location for which the weather details are displayed.
     * @param isRaining     A boolean indicating whether it's raining at the specified location.
     * @param styleString   The style information to be displayed.
     * @param temperature   The temperature to be displayed in Celsius.
     */
    public void showDetails(String location, boolean isRaining, String styleString, double temperature){
        // Set the location, temperature, and style information on the UI
        binding.textViewOrt.setText(location);
        binding.textViewGrad.setText(Math.round(temperature) + "°C");

        // Set the rain status based on the isRaining boolean
        if (isRaining) {
            binding.textViewRegen.setText("It's Raining");
        } else {
            binding.textViewRegen.setText("It's Dry");
        }

        // Set the clothing style information on the UI
        binding.textViewStyle.setText(styleString);
    }
    /**
     * Callback method triggered when there is an error in the weather API call.
     *
     * @param e The exception representing the error.
     *
     */
    @Override
    public void onWeatherApiError(Exception e) {
        // Display a toast message indicating the weather data request error
        Toast t = Toast.makeText(this, "Error while asking for weather data", Toast.LENGTH_SHORT);
        t.show();

        // Navigate to the FashMe activity
        toFashMe();

        // Print the stack trace for debugging purposes
        e.printStackTrace();
    }

    /**
     * Displays the details of the generated outfit on the UI.
     *
     */
    public void showOutfit(Outfit outfit) {
        binding.textViewTursor.setText(outfit.getClothingForTorso().getDescription());
        binding.textViewFeet.setText(outfit.getClothingForFeet().getDescription());
        binding.textViewLeg.setText(outfit.getClothingForLegs().getDescription());
        if (outfit.getClothingForHead() != null) {
            binding.textViewHead.setText(outfit.getClothingForHead().getDescription());
        }
        if (outfit.getClothingForFace() != null) {
            binding.textViewFace.setText(outfit.getClothingForFace().getDescription());
        }
    }

    /**
     * Navigates to the Wardrobe activity.
     */
    public void toWardrobe() {
        Intent intent = new Intent(this, WardrobeActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the Home activity.
     */
    public void toHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the GenerateOutfit activity.
     */
    public void toFashMe() {
        Intent intent = new Intent(this, GenerateOutfitActivity.class);
        startActivity(intent);
    }

    /**
     * Retrieves clothing items from storage.
     *
     * @return The wardrobe containing the clothing items.
     */
    public Wardrobe getClothingItemsFromStorage() {
        try {
            // Fetching the stored data
            SharedPreferences sharedPreferences = getSharedPreferences("FashMeData", MODE_PRIVATE);
            String serializedObject = sharedPreferences.getString("Wardrobe", null);

            if (serializedObject == null) {
                System.out.println("No data stored in SharedPreferences");
                return null;
            }

            // Decode the string to a byte array
            byte[] bytes = Base64.decode(serializedObject, Base64.DEFAULT);

            if (bytes == null) {
                System.out.println("Error decoding the byte array");
                return null;
            }

            // Deserialize the byte array to an object
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Wardrobe wardrobe = (Wardrobe) objectInputStream.readObject();

            // Update the RecyclerView adapter with the clothing items
            if (wardrobe != null) {
                clothingAdapter.setClothingList(wardrobe.getClothes());
                clothingAdapter.notifyDataSetChanged();
            }
            return wardrobe;

        } catch (Exception e) {
            System.out.println("Could not read storage: " + e.getMessage());
        }
        return null;
    }

    /**
     * Click handler for the Home button.
     *
     * @param view The clicked view.
     */
    public void homeClick(View view) {
        toHome();
    }

    /**
     * Click handler for the FashMe button.
     *
     * @param view The clicked view.
     */
    public void fashMeClick(View view) {
        toFashMe();
    }

    /**
     * Click handler for the Wardrobe button.
     *
     * @param view The clicked view.
     */
    public void wardrobeClick(View view) {
        toWardrobe();
    }

    /**
     * Click handler for the Reload button, which reloads and displays the next outfit.
     *
     * @param view The clicked view.
     */
    public void reloadClick(View view) {
        outfitCount++;
        onResume();
    }

    private List<Outfit> getOutfits(double temperature, boolean isRaining, Style selectedStyle) {
        OutfitRequest or = new OutfitRequest(temperature, isRaining, Style.Casual);
        OutfitGeneratorService os = new OutfitGeneratorService();
        Wardrobe wardrobe = getClothingItemsFromStorage();
        return os.generateOutfits(or, wardrobe, selectedStyle);
    }
}
