package com.lepquold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lepquold.helper.ListAdapter;

import java.util.ArrayList;

public class CreateClothActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cloth);

        Spinner type = findViewById(R.id.type);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapterType);

        Spinner style = findViewById(R.id.style);
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



}