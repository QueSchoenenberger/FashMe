package com.lepquold;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.lepquold.databinding.ActivityWardrobeBinding;

import java.time.Instant;

public class WardrobeActivity extends AppCompatActivity {
    private ActivityWardrobeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWardrobeBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_wardrobe);
    }
    private void toMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void homeClick(View view){
        toMain();
    }
}