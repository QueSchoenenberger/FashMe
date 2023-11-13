package com.lepquold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.material.slider.Slider;
import com.lepquold.databinding.ActivityCreateClothBinding;
import com.lepquold.databinding.ActivityMainBinding;
import com.lepquold.model.BodyParts;
import com.lepquold.model.Clothing;
import com.lepquold.model.Style;
import com.lepquold.model.Type;

import com.lepquold.helper.ListAdapter;

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


    public void addToWardrobeClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextDescription);
        String discription = editText.getText().toString();

        Spinner spinnerCreate = (Spinner) findViewById(R.id.styleCreate);
        String style = spinnerCreate.getSelectedItem().toString();

        Spinner spinnerType = (Spinner) findViewById(R.id.typeCreate);
        String type = spinnerType.getSelectedItem().toString();

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        boolean waterproof = switch1.isActivated();

        Slider slider = (Slider) findViewById(R.id.slider);
        Float temperture = slider.getValue();

        Type tshirt = new Type(type, BodyParts.TORSO);
        Style styleStyle = new Style(style);

        Clothing c = new Clothing(discription,temperture.doubleValue(),waterproof,styleStyle,tshirt);

        // Add item
        Intent intent = new Intent(this, WardrobeActivity.class);
        intent.putExtra("clothing_item", c);
        startActivity(intent);
    }
}