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

import java.time.Instant;
import java.util.ArrayList;

public class WardrobeActivity extends AppCompatActivity {
    private ActivityWardrobeBinding binding;
    private ListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWardrobeBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_wardrobe);

        // List
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListAdapter(new ArrayList<>());
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

    public void addToList() {
        adapter.addItem("New Item");
        recyclerView.setAdapter(adapter);
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