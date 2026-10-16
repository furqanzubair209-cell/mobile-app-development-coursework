package com.example.foodapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.CartActivity;
import com.example.foodapp.R;
import com.example.foodapp.adapter.OrderAdapter;
import com.example.foodapp.data.CartManager;
import com.example.foodapp.data.SampleData;

public class OrdersFragment extends Fragment {

    private OrderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvOrders = view.findViewById(R.id.rvOrdersList);
        if (rvOrders != null) {
            rvOrders.setLayoutManager(new LinearLayoutManager(requireContext()));

            adapter = new OrderAdapter(SampleData.getSampleOrders(), order -> {
                if (!SampleData.getAllFoodItems().isEmpty()) {
                    CartManager.getInstance().addItem(SampleData.getAllFoodItems().get(0), 1);
                    Toast.makeText(requireContext(), "Reordered items added to Cart!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(requireActivity(), CartActivity.class));
                }
            });

            rvOrders.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
