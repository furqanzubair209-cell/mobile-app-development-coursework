package com.example.foodapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodapp.LoginActivity;
import com.example.foodapp.R;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout btnAddresses = view.findViewById(R.id.btnProfileAddresses);
        LinearLayout btnPayment = view.findViewById(R.id.btnProfilePayment);
        LinearLayout btnOrders = view.findViewById(R.id.btnProfileOrders);
        Button btnLogout = view.findViewById(R.id.btnProfileLogout);

        if (btnAddresses != null) {
            btnAddresses.setOnClickListener(v -> Toast.makeText(requireContext(), "Saved Addresses: 123 Market Street", Toast.LENGTH_SHORT).show());
        }
        if (btnPayment != null) {
            btnPayment.setOnClickListener(v -> Toast.makeText(requireContext(), "Payment Method: Visa ending in 4242", Toast.LENGTH_SHORT).show());
        }
        if (btnOrders != null) {
            btnOrders.setOnClickListener(v -> {
                if (getActivity() instanceof com.example.foodapp.MainActivity) {
                    ((com.example.foodapp.MainActivity) getActivity()).navigateToFragment(5); // Orders tab
                }
            });
        }

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
            });
        }
    }
}
