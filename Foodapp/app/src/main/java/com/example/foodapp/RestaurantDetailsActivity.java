package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapter.FoodAdapter;
import com.example.foodapp.data.CartManager;
import com.example.foodapp.data.SampleData;
import com.example.foodapp.model.FoodItem;
import com.example.foodapp.model.Restaurant;

import java.util.List;
import java.util.Locale;

public class RestaurantDetailsActivity extends AppCompatActivity implements CartManager.OnCartChangeListener {

    private Restaurant restaurant;
    private LinearLayout barBottomCart;
    private Button btnViewCartBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
        if (restaurant == null) {
            restaurant = SampleData.getRestaurants().get(0);
        }

        ImageView btnBack = findViewById(R.id.btnBackRestDetails);
        btnBack.setOnClickListener(v -> finish());

        ImageView ivBanner = findViewById(R.id.ivRestDetailsBanner);
        TextView tvName = findViewById(R.id.tvRestDetailsName);
        TextView tvCuisine = findViewById(R.id.tvRestDetailsCuisine);
        TextView tvRating = findViewById(R.id.tvRestDetailsRating);
        TextView tvTime = findViewById(R.id.tvRestDetailsDeliveryTime);
        TextView tvFee = findViewById(R.id.tvRestDetailsDeliveryFee);
        TextView tvDesc = findViewById(R.id.tvRestDetailsDescription);

        ivBanner.setImageResource(restaurant.getImageResId());
        tvName.setText(restaurant.getName());
        tvCuisine.setText(restaurant.getCuisine());
        tvRating.setText(String.format(Locale.US, "%.1f", restaurant.getRating()));
        tvTime.setText(restaurant.getDeliveryTime());
        tvFee.setText(String.format(Locale.US, "$%.2f Delivery", restaurant.getDeliveryFee()));
        tvDesc.setText(restaurant.getDescription());

        barBottomCart = findViewById(R.id.barBottomCart);
        btnViewCartBar = findViewById(R.id.btnViewCartBar);
        btnViewCartBar.setOnClickListener(v -> startActivity(new Intent(RestaurantDetailsActivity.this, CartActivity.class)));

        RecyclerView rvMenu = findViewById(R.id.rvMenuFoodItems);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));

        List<FoodItem> foodItems = restaurant.getFoodItems();
        FoodAdapter adapter = new FoodAdapter(foodItems, new FoodAdapter.OnFoodClickListener() {
            @Override
            public void onFoodClick(FoodItem foodItem) {
                Intent intent = new Intent(RestaurantDetailsActivity.this, FoodDetailsActivity.class);
                intent.putExtra("foodItem", foodItem);
                startActivity(intent);
            }

            @Override
            public void onQuickAddClick(FoodItem foodItem) {
                CartManager.getInstance().addItem(foodItem, 1);
                Toast.makeText(RestaurantDetailsActivity.this, foodItem.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
            }
        });

        rvMenu.setAdapter(adapter);

        CartManager.getInstance().addListener(this);
        updateCartBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CartManager.getInstance().removeListener(this);
    }

    @Override
    public void onCartChanged() {
        updateCartBar();
    }

    private void updateCartBar() {
        int count = CartManager.getInstance().getItemCount();
        if (count > 0) {
            barBottomCart.setVisibility(View.VISIBLE);
            btnViewCartBar.setText(String.format(Locale.US, "View Cart (%d items) - $%.2f", count, CartManager.getInstance().getTotal()));
        } else {
            barBottomCart.setVisibility(View.GONE);
        }
    }
}
