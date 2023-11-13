package com.lepquold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

public class GenerateOutfitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_outfit);
        Spinner type = findViewById(R.id.styleSpinner);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.style, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapterType);
    }
    public void toWardrobe(){
        Intent intent = new Intent(this,WardrobeActivity.class);
        startActivity(intent);
    }
    public void toHome(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void homeClick(View view){
        toHome();
    }
    public void fashMeClick(View view){
    }
    public void wardrobeClick(View view){
        toWardrobe();
    }

    public void fashMeCLick(View view) {
        TextInputEditText inputStandort = (TextInputEditText) findViewById(R.id.StandortInput);
        String Standort = inputStandort.getText().toString();

        Spinner styleSpinner = (Spinner) findViewById(R.id.styleSpinner);
        String style = styleSpinner.getSelectedItem().toString();

        startActivity(new Intent(this,RewievOutfitActivity.class));

    }
}