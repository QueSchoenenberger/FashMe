package com.lepquold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

    int outfitcount;

    private Bundle savedInsatnceSate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewiev_outfit);
        binding = ActivityRewievOutfitBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        this.savedInsatnceSate = savedInsatnceSate;
        outfitcount = 0;
    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        new WeatherApiTask(this).execute(intent.getStringExtra("location"));
    }

    @Override
    public void onWeatherInfoReceived(WeatherInfo weatherInfo) {
        Intent intent = getIntent();
        String styleString = intent.getStringExtra("style");
        Style selectedStyle = Style.Casual;
        if (styleString.equals("Formell-Business")){
            selectedStyle = Style.FormellBusiness;
        }
        if (styleString.equals("Smart-Casual")){
            selectedStyle = Style.SmartCasual;
        }
        if (styleString.equals("Leger")){
            selectedStyle = Style.Leger;
        }
        if (styleString.equals("Sportive")){
            selectedStyle = Style.Sportive;
        }
        if (styleString.equals("Vintage")){
            selectedStyle = Style.Vintage;
        }
        double temperature = weatherInfo.getTemperature();
        boolean isRaining = weatherInfo.isRaining();
        System.out.println(temperature);
        System.out.println(isRaining);
        OutfitRequest or = new OutfitRequest(temperature,isRaining,Style.Casual);
        OutfitGeneratorService os = new OutfitGeneratorService();
        Wardrobe wardrobe = getClothingItemsFromStorage();
        List<Outfit> outfits = os.generateOutfits(or,wardrobe,selectedStyle);
        if (outfits.size() > 0 && outfits.size() > outfitcount) {
            showOutfit(outfits.get(outfitcount));
        }else {
            Toast t = Toast.makeText(this,"Kein Passendes Outfit gefunden", Toast.LENGTH_SHORT);
            t.show();
        }
    }
    @Override
    public void onWeatherApiError(Exception e) {
        e.printStackTrace();
    }
    public void showOutfit(Outfit outfit){
        binding.textViewTursor.setText(outfit.getClothingForTorso().description);
        binding.textViewFeet.setText(outfit.getClothingForFeet().description);
        binding.textViewLeg.setText(outfit.getClothingForLegs().description);
        if (outfit.getClothingForHead() != null){
            binding.textViewHead.setText(outfit.getClothingForHead().description);
        }
        if (outfit.getClothingForFace() != null){
            binding.textViewFace.setText(outfit.getClothingForFace().description);
        }
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
        toFashMe();
    }
    public void wardrobeClick(View view){
        toWardrobe();
    }

    public void reloadClick(View view) {
        outfitcount++;
        onResume();
    }
}