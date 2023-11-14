package com.lepquold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.lepquold.helper.TypePopulator;
import com.lepquold.model.Clothing;
import com.lepquold.model.Style;
import com.lepquold.model.Type;
import com.lepquold.model.Wardrobe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Activity for creating and adding new clothing items to the wardrobe.
 */
public class CreateClothActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cloth);

        // Initialize spinners for type and style selection
        Spinner type = findViewById(R.id.typeCreate);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapterType);

        Spinner style = findViewById(R.id.styleCreate);
        ArrayAdapter<CharSequence> adapterStyle = ArrayAdapter.createFromResource(this, R.array.style, android.R.layout.simple_spinner_item);
        adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        style.setAdapter(adapterStyle);
    }

    // Navigation methods
    public void toWardrobe() {
        Intent intent = new Intent(this, WardrobeActivity.class);
        startActivity(intent);
    }

    public void toHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toFashMe() {
        Intent intent = new Intent(this, GenerateOutfitActivity.class);
        startActivity(intent);
    }

    // Click handlers for navigation
    public void homeClick(View view) {
        toHome();
    }

    public void fashMeClick(View view) {
        toFashMe();
    }

    public void wardrobeClick(View view) {
        toWardrobe();
    }

    // Error message display
    public void makeErrorMessage(String error) {
        Toast t = Toast.makeText(this, error, Toast.LENGTH_SHORT);
        t.show();
    }

    // Click handler for adding a new clothing item to the wardrobe
    public void addToWardrobeClick(View view) {
        EditText editText = findViewById(R.id.editTextDescription);
        String description = editText.getText().toString();

        if (description.length() < 1) {
            makeErrorMessage("Clothing must have a description");
            return;
        }

        // Get selected style from the spinner
        Style selectedStyle = Style.Casual;
        Spinner spinnerCreate = findViewById(R.id.styleCreate);
        String style = spinnerCreate.getSelectedItem().toString();
        switch (style) {
            case "Casual":
                selectedStyle = Style.Casual;
                break;
            case "Formal-Business":
                selectedStyle = Style.FormellBusiness;
                break;
            case "Smart-Casual":
                selectedStyle = Style.SmartCasual;
                break;
            case "Leger":
                selectedStyle = Style.Leger;
                break;
            case "Sportive":
                selectedStyle = Style.Sportive;
                break;
            case "Vintage":
                selectedStyle = Style.Vintage;
                break;
        }

        // Get selected type from the spinner
        Spinner spinnerType = findViewById(R.id.typeCreate);
        String typeName = spinnerType.getSelectedItem().toString();
        Type selectedType;
        try {
            selectedType = TypePopulator.getTypes().get(typeName);
        } catch (Exception e) {
            makeErrorMessage("Error while selecting the type");
            return;
        }

        // Get waterproof status from the switch
        Switch waterproofSwitch = findViewById(R.id.switch1);
        boolean waterproof = waterproofSwitch.isChecked();

        // Get temperature from the slider
        Slider slider = findViewById(R.id.slider);
        Float temperature = slider.getValue();

        // Create a new clothing object
        Clothing newClothing = new Clothing(description, temperature.doubleValue(), waterproof, selectedStyle, selectedType);

        // Persist the new clothing item
        persistClothingItem(newClothing);

        // Navigate to the WardrobeActivity
        toWardrobe();
    }

    // Method to persist a clothing item to SharedPreferences
    public void persistClothingItem(Clothing clothing) {
        try {
            // Fetching the stored data
            SharedPreferences sharedPreferences = getSharedPreferences("FashMeData", MODE_PRIVATE);
            String serializedObject = sharedPreferences.getString("Wardrobe", null);

            if (serializedObject == null) {
                // If no data is stored, create a new wardrobe and add the item
                Wardrobe newWardrobe = new Wardrobe(new ArrayList<>());
                newWardrobe.addItem(clothing);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                // Serialize the Wardrobe
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(newWardrobe);
                objectOutputStream.close();
                String serializedWardrobe = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                myEdit.putString("Wardrobe", serializedWardrobe);
                myEdit.apply();
            } else {
                // If data is stored, deserialize and update the wardrobe
                byte[] bytes = Base64.decode(serializedObject, Base64.DEFAULT);

                if (bytes == null) {
                    System.out.println("Error decoding the byte array");
                    return;
                }

                // Deserialize the byte array to a Wardrobe object
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Wardrobe wardrobe = (Wardrobe) objectInputStream.readObject();

                // Update the RecyclerView adapter with the clothing items
                wardrobe.addItem(clothing);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                // Serialize the updated Wardrobe
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(wardrobe);
                objectOutputStream.close();

                String serializedWardrobe = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                // Write all the data entered by the user in SharedPreferences and apply
                myEdit.putString("Wardrobe", serializedWardrobe);
                myEdit.apply();
            }
        } catch (Exception e) {
            System.out.println("Could not persist: " + e);
        }
    }
}
