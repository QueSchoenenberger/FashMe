package com.lepquold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.material.slider.Slider;
import com.lepquold.Types.Types;
import com.lepquold.databinding.ActivityCreateClothBinding;
import com.lepquold.databinding.ActivityMainBinding;
import com.lepquold.model.BodyParts;
import com.lepquold.model.Clothing;
import com.lepquold.model.Style;
import com.lepquold.model.Type;
import com.lepquold.model.Wardrobe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CreateClothActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cloth);

        Spinner type = findViewById(R.id.typeCreate);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapterType);

        Spinner style = findViewById(R.id.styleCreate);
        ArrayAdapter<CharSequence> adapterStyle = ArrayAdapter.createFromResource(this, R.array.style, android.R.layout.simple_spinner_item);
        adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        style.setAdapter(adapterStyle);
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

    public void homeClick(View view){
        toHome();
    }
    public void fashMeClick(View view){
        toFashMe();
    }
    public void wardrobeClick(View view){
        toWardrobe();
    }
    public void makeErrorMessage(String error){
     //   binding.textViewError.setText(error);
    }


    public void addToWardrobeClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextDescription);
        String discription = editText.getText().toString();

        if (discription.length() < 1){
            makeErrorMessage("Clothing must have a discription");
            return;
        }
        Style selectedStyle = Style.Casual;
        Spinner spinnerCreate = (Spinner) findViewById(R.id.styleCreate);
        String style = spinnerCreate.getSelectedItem().toString();
        if (style.equals("Casual")){
            selectedStyle = Style.Casual;
        }
        if (style.equals("Formell-Business")){
            selectedStyle = Style.FormellBusiness;
        }
        if (style.equals("Smart-Casual")){
            selectedStyle = Style.SmartCasual;
        }
        if (style.equals("Leger")){
            selectedStyle = Style.Leger;
        }
        if (style.equals("Sportive")){
            selectedStyle = Style.Sportive;
        }
        if (style.equals("Vintage")){
            selectedStyle = Style.Vintage;
        }

        Spinner spinnerType = (Spinner) findViewById(R.id.typeCreate);
        String type = spinnerType.getSelectedItem().toString();
        Type selectedType;
        try {
            selectedType = Types.getTypes().get(type);
        }catch (Exception e) {
            makeErrorMessage("Error while selecting the type");
            return;
        }

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        boolean waterproof = switch1.isActivated();

        Slider slider = (Slider) findViewById(R.id.slider);
        Float temperture = slider.getValue();

        Clothing c = new Clothing(discription,temperture.doubleValue(),waterproof,selectedStyle,selectedType);

        persistClothingItem(c);
        // Add item
        Intent intent = new Intent(this, WardrobeActivity.class);
        startActivity(intent);
    }

    public void persistClothingItem(Clothing clothing) {
        try {
            // Fetching the stored data
            SharedPreferences sharedPreferences = getSharedPreferences("FashMeData", MODE_PRIVATE);
            String serializedObject = sharedPreferences.getString("Wardrobe", null);

            if (serializedObject == null) {
                System.out.println("No data stored in SharedPreferences");
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
                // Decode the string to a byte array
                byte[] bytes = Base64.decode(serializedObject, Base64.DEFAULT);

                if (bytes == null) {
                    System.out.println("Error decoding the byte array");
                    return;
                }

                // Deserialize the byte array to an object
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Wardrobe wardrobe = (Wardrobe) objectInputStream.readObject();

                // Update the RecyclerView adapter with the clothing items
                wardrobe.addItem(clothing);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                // Serialize the Wardrobe
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(wardrobe);
                objectOutputStream.close();

                String serializedWardrobe = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                // write all the data entered by the user in SharedPreference and apply
                myEdit.putString("Wardrobe", serializedWardrobe);
                myEdit.apply();
            }
        } catch (Exception e){
            System.out.println("Could not persist: " + e);
        }
    }
}