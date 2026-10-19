package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.data.CartManager;
import com.example.foodapp.data.SampleData;
import com.example.foodapp.model.FoodItem;

import java.util.Locale;

public class FoodDetailsActivity extends AppCompatActivity {

    private FoodItem foodItem;
    private int quantity = 1;
    private TextView tvQtyValue;
    private Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        foodItem = (FoodItem) getIntent().getSerializableExtra("foodItem");
        if (foodItem == null) {
            foodItem = SampleData.getAllFoodItems().get(0);
        }

        ImageView btnBack = findViewById(R.id.btnBackFoodDetails);
        btnBack.setOnClickListener(v -> finish());

        ImageView ivImage = findViewById(R.id.ivFoodDetailsImage);
        TextView tvName = findViewById(R.id.tvFoodDetailsName);
        TextView tvPrice = findViewById(R.id.tvFoodDetailsPrice);
        TextView tvDesc = findViewById(R.id.tvFoodDetailsDescription);

        tvQtyValue = findViewById(R.id.tvQtyValue);
        ImageView btnDecrease = findViewById(R.id.btnQtyDecrease);
        ImageView btnIncrease = findViewById(R.id.btnQtyIncrease);
        btnAddToCart = findViewById(R.id.btnAddToCartSubmit);

        ivImage.setImageResource(foodItem.getImageResId());
        tvName.setText(foodItem.getName());
        tvPrice.setText(String.format(Locale.US, "$%.2f", foodItem.getPrice()));
        tvDesc.setText(foodItem.getDescription());

        btnDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateQuantityAndPrice();
            }
        });

        btnIncrease.setOnClickListener(v -> {
            quantity++;
            updateQuantityAndPrice();
        });

        btnAddToCart.setOnClickListener(v -> {
            CartManager.getInstance().addItem(foodItem, quantity);
            Toast.makeText(FoodDetailsActivity.this, quantity + "x " + foodItem.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
            finish();
        });

        updateQuantityAndPrice();
    }

    private void updateQuantityAndPrice() {
        tvQtyValue.setText(String.valueOf(quantity));
        double totalPrice = foodItem.getPrice() * quantity;
        btnAddToCart.setText(String.format(Locale.US, "Add to Cart - $%.2f", totalPrice));
    }
}
