package com.lepquold.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lepquold.R;
import com.lepquold.model.Clothing;

import java.util.ArrayList;
import java.util.List;

public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.ClothingViewHolder> {
    private List<Clothing> clothingList = new ArrayList<>();

    // Provide a reference to the views for each data item
    public static class ClothingViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTextView;

        public ClothingViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ClothingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your layout for each clothing item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothing_list_item, parent, false);
        return new ClothingViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ClothingViewHolder holder, int position) {
        // Bind data to views for each clothing item
        Clothing clothing = clothingList.get(position);
        holder.descriptionTextView.setText(clothing.description);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return clothingList.size();
    }

    // Method to update the clothing list
    public void setClothingList(List<Clothing> clothingList) {
        this.clothingList = clothingList;
    }
}
