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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lepquold.databinding.ActivityWardrobeBinding;
import com.lepquold.helper.ListAdapter;
import com.lepquold.model.Clothing;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

public class WardrobeActivity extends AppCompatActivity {
    private ActivityWardrobeBinding binding;
    private ListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWardrobeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize your adapter with an empty list or a persistent list
        adapter = new ListAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.hasExtra("clothing_item")) {
            Clothing newClothing = intent.getParcelableExtra("clothing_item");
            if (newClothing != null) {
                adapter.addItem(newClothing.description);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
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

    public void toAddClothingView() {
        Intent intent = new Intent(this, CreateClothActivity.class);
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
    public void buttonClick(View view) {
        toAddClothingView();
    }
}