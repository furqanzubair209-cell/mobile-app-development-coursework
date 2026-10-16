package com.example.foodapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private FoodAdapter foodAdapter;
    private final List<FoodItem> filteredList = new ArrayList<>();
    private final List<FoodItem> allFoods = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText etQuery = view.findViewById(R.id.etSearchQuery);
        RecyclerView rvResults = view.findViewById(R.id.rvSearchResults);
        if (rvResults != null) {
            rvResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        }

        allFoods.clear();
        allFoods.addAll(SampleData.getAllFoodItems());
        filteredList.clear();
        filteredList.addAll(allFoods);

        foodAdapter = new FoodAdapter(filteredList, new FoodAdapter.OnFoodClickListener() {
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

        if (rvResults != null) {
            rvResults.setAdapter(foodAdapter);
        }

        if (etQuery != null) {
            etQuery.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterSearch(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void filterSearch(String query) {
        filteredList.clear();
        if (query.trim().isEmpty()) {
            filteredList.addAll(allFoods);
        } else {
            String lower = query.toLowerCase().trim();
            for (FoodItem item : allFoods) {
                if (item.getName().toLowerCase().contains(lower) ||
                    item.getDescription().toLowerCase().contains(lower) ||
                    item.getCategory().toLowerCase().contains(lower)) {
                    filteredList.add(item);
                }
            }
        }
        if (foodAdapter != null) {
            foodAdapter.notifyDataSetChanged();
        }
    }
}
