package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapter.RestaurantAdapter;
import com.example.foodapp.data.SampleData;
import com.example.foodapp.model.FoodItem;
import com.example.foodapp.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        ImageView btnBack = findViewById(R.id.btnBackRestaurants);
        ImageView btnSearch = findViewById(R.id.btnSearchFromRestaurants);
        TextView tvTitle = findViewById(R.id.tvRestaurantsTitle);

        btnBack.setOnClickListener(v -> finish());
        btnSearch.setOnClickListener(v -> startActivity(new Intent(RestaurantsActivity.this, SearchActivity.class)));

        String categoryFilter = getIntent().getStringExtra("category");
        List<Restaurant> displayRestaurants;

        if (categoryFilter != null && !categoryFilter.isEmpty()) {
            if (tvTitle != null) {
                tvTitle.setText(categoryFilter + " Restaurants");
            }
            displayRestaurants = new ArrayList<>();
            for (Restaurant r : SampleData.getRestaurants()) {
                if (r.getCuisine().toLowerCase().contains(categoryFilter.toLowerCase())) {
                    displayRestaurants.add(r);
                } else {
                    for (FoodItem item : r.getFoodItems()) {
                        if (item.getCategory().equalsIgnoreCase(categoryFilter)) {
                            displayRestaurants.add(r);
                            break;
                        }
                    }
                }
            }
            if (displayRestaurants.isEmpty()) {
                displayRestaurants = SampleData.getRestaurants();
            }
        } else {
            displayRestaurants = SampleData.getRestaurants();
        }

        RecyclerView rvRestaurants = findViewById(R.id.rvAllRestaurants);
        rvRestaurants.setLayoutManager(new LinearLayoutManager(this));

        RestaurantAdapter adapter = new RestaurantAdapter(displayRestaurants, restaurant -> {
            Intent intent = new Intent(RestaurantsActivity.this, RestaurantDetailsActivity.class);
            intent.putExtra("restaurant", restaurant);
            startActivity(intent);
        });
        rvRestaurants.setAdapter(adapter);
    }
}
