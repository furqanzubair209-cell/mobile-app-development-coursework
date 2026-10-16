package com.example.foodapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OffersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        ImageView btnBack = findViewById(R.id.btnBackOffers);
        btnBack.setOnClickListener(v -> finish());

        Button btnVoucher1 = findViewById(R.id.btnCopyVoucher1);
        Button btnVoucher2 = findViewById(R.id.btnCopyVoucher2);
        Button btnVoucher3 = findViewById(R.id.btnCopyVoucher3);

        btnVoucher1.setOnClickListener(v -> Toast.makeText(this, "Voucher FOODIE50 Applied!", Toast.LENGTH_SHORT).show());
        btnVoucher2.setOnClickListener(v -> Toast.makeText(this, "Voucher FREEDEL Applied!", Toast.LENGTH_SHORT).show());
        btnVoucher3.setOnClickListener(v -> Toast.makeText(this, "Voucher BURGER20 Applied!", Toast.LENGTH_SHORT).show());
    }
}
