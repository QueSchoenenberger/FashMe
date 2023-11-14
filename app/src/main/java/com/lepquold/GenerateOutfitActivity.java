package com.lepquold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

/**
 * Activity for generating outfit suggestions based on user input.
 */
public class GenerateOutfitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_outfit);

        // Initialize spinner for style selection
        Spinner styleSpinner = findViewById(R.id.styleSpinner);
        ArrayAdapter<CharSequence> adapterStyle = ArrayAdapter.createFromResource(this, R.array.style, android.R.layout.simple_spinner_item);
        adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(adapterStyle);
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

    // Click handlers for navigation
    public void homeClick(View view) {
        toHome();
    }

    public void wardrobeClick(View view) {
        toWardrobe();
    }

    // Click handler for generating outfit suggestions
    public void fashMeCLick(View view) {
        TextInputEditText inputStandort = findViewById(R.id.StandortInput);
        String standort = inputStandort.getText().toString();

        // Check if location is provided
        if (standort.length() < 1) {
            Toast t = Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        // Get selected style from the spinner
        Spinner styleSpinner = findViewById(R.id.styleSpinner);
        String style = styleSpinner.getSelectedItem().toString();

        // Start the ReviewOutfitActivity with location and style information
        Intent intent = new Intent(this, RewievOutfitActivity.class);
        intent.putExtra("location", standort);
        intent.putExtra("style", style);
        startActivity(intent);
    }
}
