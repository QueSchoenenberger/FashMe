package com.lepquold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.lepquold.databinding.ActivityRewievOutfitBinding;
import com.lepquold.helper.ClothingAdapter;
import com.lepquold.model.Outfit;
import com.lepquold.model.OutfitRequest;
import com.lepquold.model.Style;
import com.lepquold.model.Wardrobe;
import com.lepquold.model.WeatherInfo;
import com.lepquold.service.OutfitGeneratorService;
import com.lepquold.service.WeatherApiTask;
import com.lepquold.service.WeatherInfoListener;
import com.lepquold.service.WeatherService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class RewievOutfitActivity extends AppCompatActivity implements WeatherInfoListener {
    ClothingAdapter clothingAdapter = new ClothingAdapter();
    private ActivityRewievOutfitBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewiev_outfit);
        binding = ActivityRewievOutfitBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
    @Override
    protected void onResume() {
        super.onResume();

        new WeatherApiTask(this).execute("Zürich");
    }

    @Override
    public void onWeatherInfoReceived(WeatherInfo weatherInfo) {
        double temperature = weatherInfo.getTemperature();
        boolean isRaining = weatherInfo.isRaining();
        System.out.println(temperature);
        System.out.println(isRaining);
    }

    @Override
    public void onWeatherApiError(Exception e) {
        e.printStackTrace();
    }

    public void showOutfit(Outfit outfit){
        binding.textViewFace.setText(outfit.getClothingForFace().description);
    }

    public void toWardrobe(){
        Intent intent = new Intent(this,WardrobeActivity.class);
        startActivity(intent);
    }
    public void toHome(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void toFashMe(){
        Intent intent = new Intent(this,GenerateOutfitActivity.class);
        startActivity(intent);
    }

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

    public void homeClick(View view){
        toHome();
    }
    public void fashMeClick(View view){
    }
    public void wardrobeClick(View view){
        toWardrobe();
    }

    public void reloadClick(View view) {
    }
}