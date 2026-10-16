package com.example.foodapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodapp.R;
import com.example.foodapp.data.CartManager;

public class OffersFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnVoucher1 = view.findViewById(R.id.btnCopyVoucher1);
        Button btnVoucher2 = view.findViewById(R.id.btnCopyVoucher2);
        Button btnVoucher3 = view.findViewById(R.id.btnCopyVoucher3);

        if (btnVoucher1 != null) {
            btnVoucher1.setOnClickListener(v -> {
                CartManager.getInstance().applyVoucher("FOODIE50");
                Toast.makeText(requireContext(), "\uD83C\uDF89 Voucher FOODIE50 Applied! 50% OFF", Toast.LENGTH_SHORT).show();
                btnVoucher1.setText("\u2713 FOODIE50 Applied");
                btnVoucher1.setEnabled(false);
                btnVoucher1.setAlpha(0.6f);
            });
        }

        if (btnVoucher2 != null) {
            btnVoucher2.setOnClickListener(v -> {
                CartManager.getInstance().applyVoucher("FREEDEL");
                Toast.makeText(requireContext(), "\uD83D\uDE9A Voucher FREEDEL Applied! Free Delivery", Toast.LENGTH_SHORT).show();
                btnVoucher2.setText("\u2713 FREEDEL Applied");
                btnVoucher2.setEnabled(false);
                btnVoucher2.setAlpha(0.6f);
            });
        }

        if (btnVoucher3 != null) {
            btnVoucher3.setOnClickListener(v -> {
                CartManager.getInstance().applyVoucher("BURGER20");
                Toast.makeText(requireContext(), "\uD83C\uDF54 Voucher BURGER20 Applied! 20% OFF", Toast.LENGTH_SHORT).show();
                btnVoucher3.setText("\u2713 BURGER20 Applied");
                btnVoucher3.setEnabled(false);
                btnVoucher3.setAlpha(0.6f);
            });
        }

        // Update button states based on currently applied voucher
        String currentVoucher = CartManager.getInstance().getAppliedVoucher();
        if (currentVoucher != null) {
            switch (currentVoucher) {
                case "FOODIE50":
                    if (btnVoucher1 != null) {
                        btnVoucher1.setText("\u2713 FOODIE50 Applied");
                        btnVoucher1.setEnabled(false);
                        btnVoucher1.setAlpha(0.6f);
                    }
                    break;
                case "FREEDEL":
                    if (btnVoucher2 != null) {
                        btnVoucher2.setText("\u2713 FREEDEL Applied");
                        btnVoucher2.setEnabled(false);
                        btnVoucher2.setAlpha(0.6f);
                    }
                    break;
                case "BURGER20":
                    if (btnVoucher3 != null) {
                        btnVoucher3.setText("\u2713 BURGER20 Applied");
                        btnVoucher3.setEnabled(false);
                        btnVoucher3.setAlpha(0.6f);
                    }
                    break;
            }
        }
    }
}
