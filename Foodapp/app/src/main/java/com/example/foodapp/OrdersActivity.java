package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapter.OrderAdapter;
import com.example.foodapp.data.CartManager;
import com.example.foodapp.data.SampleData;

public class OrdersActivity extends AppCompatActivity {

    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ImageView btnBack = findViewById(R.id.btnBackOrders);
        btnBack.setOnClickListener(v -> finish());

        RecyclerView rvOrders = findViewById(R.id.rvOrdersList);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OrderAdapter(SampleData.getSampleOrders(), order -> {
            // Reorder action: Add top food item from sample data to cart & open CartActivity
            if (!SampleData.getAllFoodItems().isEmpty()) {
                CartManager.getInstance().addItem(SampleData.getAllFoodItems().get(0), 1);
                Toast.makeText(OrdersActivity.this, "Reordered items added to Cart!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OrdersActivity.this, CartActivity.class));
            }
        });

        rvOrders.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
