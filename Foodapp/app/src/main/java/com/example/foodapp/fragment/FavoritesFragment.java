package com.example.foodapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.FoodDetailsActivity;
import com.example.foodapp.R;
import com.example.foodapp.adapter.FoodAdapter;
import com.example.foodapp.data.CartManager;
import com.example.foodapp.data.SampleData;
import com.example.foodapp.model.FoodItem;

import java.util.List;

public class FavoritesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvFavorites = view.findViewById(R.id.rvFavorites);
        if (rvFavorites != null) {
            rvFavorites.setLayoutManager(new LinearLayoutManager(requireContext()));

            // Show top-rated items as "favorites" in full-width list view
            List<FoodItem> allFood = SampleData.getAllFoodItems();
            List<FoodItem> favorites = allFood.subList(0, Math.min(6, allFood.size()));

            FoodAdapter adapter = new FoodAdapter(favorites, new FoodAdapter.OnFoodClickListener() {
                @Override
                public void onFoodClick(FoodItem foodItem) {
                    Intent intent = new Intent(requireActivity(), FoodDetailsActivity.class);
                    intent.putExtra("foodItem", foodItem);
                    startActivity(intent);
                }

                @Override
                public void onQuickAddClick(FoodItem foodItem) {
                    CartManager.getInstance().addItem(foodItem, 1);
                    Toast.makeText(requireContext(), foodItem.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
                }
            });

            rvFavorites.setAdapter(adapter);
        }
    }
}
