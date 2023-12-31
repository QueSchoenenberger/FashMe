package com.lepquold;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lepquold.databinding.ActivityWardrobeBinding;
import com.lepquold.helper.ClothingAdapter;
import com.lepquold.model.Clothing;
import com.lepquold.model.Wardrobe;
import com.lepquold.service.OnDeleteClickListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Activity responsible for displaying the user's wardrobe and allowing the user to navigate to other features.
 */
public class WardrobeActivity extends AppCompatActivity implements OnDeleteClickListener {
    private ActivityWardrobeBinding binding;
    private RecyclerView recyclerViewClothes;
    private ClothingAdapter clothingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using ViewBinding
        binding = ActivityWardrobeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the RecyclerView and its adapter
        initialiseAdapterAndRecyclerView();

        // Set the delete click listener in the adapter
        clothingAdapter.setOnDeleteClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Fetch clothing items from storage and update the RecyclerView adapter
        getClothingItemsFromStorage();
    }

    /**
     * Navigates to the Wardrobe activity.
     */
    public void toWardrobe() {
        Intent intent = new Intent(this, WardrobeActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the Home activity.
     */
    public void toHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the GenerateOutfit activity.
     */
    public void toFashMe() {
        Intent intent = new Intent(this, GenerateOutfitActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the CreateClothActivity to add new clothing items.
     */
    public void toAddClothingView() {
        Intent intent = new Intent(this, CreateClothActivity.class);
        startActivity(intent);
    }

    /**
     * Fetches clothing items from storage and updates the RecyclerView adapter.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void getClothingItemsFromStorage() {
        try {
            // Fetching the stored data from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("FashMeData", MODE_PRIVATE);
            String serializedObject = sharedPreferences.getString("Wardrobe", null);

            if (serializedObject == null) {
                System.out.println("No data stored in SharedPreferences");
                return;
            }

            // Decode the string to a byte array
            byte[] bytes = Base64.decode(serializedObject, Base64.DEFAULT);

            if (bytes == null) {
                System.out.println("Error decoding the byte array");
                return;
            }

            // Deserialize the byte array to a Wardrobe object
            Wardrobe wardrobe = getWardrobeFromByteArray(bytes);

            // Update the RecyclerView adapter with the clothing items
            if (wardrobe != null) {
                clothingAdapter.setClothingList(wardrobe.getClothes());
                clothingAdapter.notifyDataSetChanged();
            }

        } catch (Exception e) {
            System.out.println("Could not read storage: " + e.getMessage());
        }
    }

    private void initialiseAdapterAndRecyclerView() {
        // Initialize the RecyclerView and set its layout manager and adapter
        recyclerViewClothes = binding.recyclerViewClothes;
        recyclerViewClothes.setLayoutManager(new LinearLayoutManager(this));
        clothingAdapter = new ClothingAdapter();
        recyclerViewClothes.setAdapter(clothingAdapter);
    }

    private Wardrobe getWardrobeFromByteArray(byte[] bytes) throws IOException, ClassNotFoundException {
        // Deserialize the byte array to a Wardrobe object
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Wardrobe) objectInputStream.readObject();
    }

    // Click handlers for various buttons

    /**
     * Click handler for the Home button.
     *
     * @param view The clicked view.
     */
    public void homeClick(View view) {
        toHome();
    }

    /**
     * Click handler for the FashMe button.
     *
     * @param view The clicked view.
     */
    public void fashMeClick(View view) {
        toFashMe();
    }

    /**
     * Click handler for the Wardrobe button.
     *
     * @param view The clicked view.
     */
    public void wardrobeClick(View view) {
        toWardrobe();
    }

    /**
     * Click handler for the Add Clothing button, which navigates to the CreateClothActivity.
     *
     * @param view The clicked view.
     */
    public void buttonClick(View view) {
        toAddClothingView();
    }

    @Override
    public void onDeleteClick(int position) {
        // Handle the delete action here
        // Get the clothing item to be deleted
        Clothing deletedClothing = clothingAdapter.getClothingList().get(position);

        // Remove the item from the dataset
        clothingAdapter.getClothingList().remove(position);

        // Notify the adapter of the change
        clothingAdapter.notifyItemRemoved(position);

        // Update the SharedPreferences with the modified dataset
        persistUpdatedWardrobe(clothingAdapter.getClothingList());
    }

    private void persistUpdatedWardrobe(List<Clothing> updatedClothingList) {
        try {
            // Serialize the updated Wardrobe
            Wardrobe updatedWardrobe = new Wardrobe(updatedClothingList);
            String serializedWardrobe = getSerializedWardrobe(updatedWardrobe);

            // Write the updated data to SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("FashMeData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Wardrobe", serializedWardrobe);
            editor.apply();
        } catch (Exception e) {
            System.out.println("Could not persist updated wardrobe: " + e.getMessage());
        }
    }

    private String getSerializedWardrobe(Wardrobe wardrobe) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(wardrobe);
        objectOutputStream.close();
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

}
