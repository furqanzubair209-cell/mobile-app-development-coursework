package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView btnBack = findViewById(R.id.btnBackProfile);
        btnBack.setOnClickListener(v -> finish());

        LinearLayout btnAddresses = findViewById(R.id.btnProfileAddresses);
        LinearLayout btnPayment = findViewById(R.id.btnProfilePayment);
        LinearLayout btnOrders = findViewById(R.id.btnProfileOrders);
        Button btnLogout = findViewById(R.id.btnProfileLogout);

        btnAddresses.setOnClickListener(v -> Toast.makeText(this, "Saved Addresses: 123 Market Street", Toast.LENGTH_SHORT).show());
        btnPayment.setOnClickListener(v -> Toast.makeText(this, "Payment Method: Visa ending in 4242", Toast.LENGTH_SHORT).show());
        btnOrders.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, OrdersActivity.class)));

        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
