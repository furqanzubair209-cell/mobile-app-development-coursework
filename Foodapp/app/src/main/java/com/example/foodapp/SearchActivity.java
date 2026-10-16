package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapter.FoodAdapter;
import com.example.foodapp.data.CartManager;
import com.example.foodapp.data.SampleData;
import com.example.foodapp.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private FoodAdapter foodAdapter;
    private final List<FoodItem> filteredList = new ArrayList<>();
    private final List<FoodItem> allFoods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageView btnBack = findViewById(R.id.btnBackSearch);
        btnBack.setOnClickListener(v -> finish());

        EditText etQuery = findViewById(R.id.etSearchQuery);
        RecyclerView rvResults = findViewById(R.id.rvSearchResults);
        rvResults.setLayoutManager(new LinearLayoutManager(this));

        allFoods.addAll(SampleData.getAllFoodItems());
        filteredList.addAll(allFoods);

        foodAdapter = new FoodAdapter(filteredList, new FoodAdapter.OnFoodClickListener() {
            @Override
            public void onFoodClick(FoodItem foodItem) {
                Intent intent = new Intent(SearchActivity.this, FoodDetailsActivity.class);
                intent.putExtra("foodItem", foodItem);
                startActivity(intent);
            }

            @Override
            public void onQuickAddClick(FoodItem foodItem) {
                CartManager.getInstance().addItem(foodItem, 1);
                android.widget.Toast.makeText(SearchActivity.this, foodItem.getName() + " added to cart!", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        rvResults.setAdapter(foodAdapter);

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
        foodAdapter.notifyDataSetChanged();
    }
}
