package com.lepquold;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lepquold.databinding.ActivityMainBinding;

/**
 * Main activity for the application, serving as the entry point.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Assuming you have a layout file named "activity_main.xml"
        setContentView(R.layout.activity_main);
    }

    // Lifecycle methods
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // Navigation methods
    public void toWardrobe() {
        Intent intent = new Intent(this, WardrobeActivity.class);
        startActivity(intent);
    }

    public void toHome() {
        // Optional: You're already in the MainActivity, so you might not need to navigate to it again.
        // This method is here for consistency with the other navigation methods.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toFashMe() {
        Intent intent = new Intent(this, GenerateOutfitActivity.class);
        startActivity(intent);
    }

    // Click handlers for navigation
    public void homeClick(View view) {
    }

    public void fashMeClick(View view) {
        toFashMe();
    }

    public void wardrobeClick(View view) {
        toWardrobe();
    }
}
