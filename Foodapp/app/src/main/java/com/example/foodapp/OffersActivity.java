package com.example.foodapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.data.CartManager;

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

        btnVoucher1.setOnClickListener(v -> {
            CartManager.getInstance().applyVoucher("FOODIE50");
            Toast.makeText(this, "\uD83C\uDF89 Voucher FOODIE50 Applied! 50% OFF", Toast.LENGTH_SHORT).show();
            btnVoucher1.setText("\u2713 FOODIE50 Applied");
            btnVoucher1.setEnabled(false);
            btnVoucher1.setAlpha(0.6f);
        });

        btnVoucher2.setOnClickListener(v -> {
            CartManager.getInstance().applyVoucher("FREEDEL");
            Toast.makeText(this, "\uD83D\uDE9A Voucher FREEDEL Applied! Free Delivery", Toast.LENGTH_SHORT).show();
            btnVoucher2.setText("\u2713 FREEDEL Applied");
            btnVoucher2.setEnabled(false);
            btnVoucher2.setAlpha(0.6f);
        });

        btnVoucher3.setOnClickListener(v -> {
            CartManager.getInstance().applyVoucher("BURGER20");
            Toast.makeText(this, "\uD83C\uDF54 Voucher BURGER20 Applied! 20% OFF", Toast.LENGTH_SHORT).show();
            btnVoucher3.setText("\u2713 BURGER20 Applied");
            btnVoucher3.setEnabled(false);
            btnVoucher3.setAlpha(0.6f);
        });

        // Update button states based on currently applied voucher
        String currentVoucher = CartManager.getInstance().getAppliedVoucher();
        if (currentVoucher != null) {
            switch (currentVoucher) {
                case "FOODIE50":
                    btnVoucher1.setText("\u2713 FOODIE50 Applied");
                    btnVoucher1.setEnabled(false);
                    btnVoucher1.setAlpha(0.6f);
                    break;
                case "FREEDEL":
                    btnVoucher2.setText("\u2713 FREEDEL Applied");
                    btnVoucher2.setEnabled(false);
                    btnVoucher2.setAlpha(0.6f);
                    break;
                case "BURGER20":
                    btnVoucher3.setText("\u2713 BURGER20 Applied");
                    btnVoucher3.setEnabled(false);
                    btnVoucher3.setAlpha(0.6f);
                    break;
            }
        }
    }
}
