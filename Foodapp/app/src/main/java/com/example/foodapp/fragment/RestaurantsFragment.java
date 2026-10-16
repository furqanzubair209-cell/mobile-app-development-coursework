package com.example.foodapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.RestaurantDetailsActivity;
import com.example.foodapp.adapter.RestaurantAdapter;
import com.example.foodapp.data.SampleData;
import com.example.foodapp.model.FoodItem;
import com.example.foodapp.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsFragment extends Fragment {

    private String categoryFilter;

    public static RestaurantsFragment newInstance(String category) {
        RestaurantsFragment fragment = new RestaurantsFragment();
        if (category != null) {
            Bundle args = new Bundle();
            args.putString("category", category);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurants, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            categoryFilter = getArguments().getString("category");
        }

        ImageView btnSearch = view.findViewById(R.id.btnSearchFromRestaurants);
        TextView tvTitle = view.findViewById(R.id.tvRestaurantsTitle);

        if (btnSearch != null) {
            btnSearch.setOnClickListener(v -> {
                if (getActivity() instanceof com.example.foodapp.MainActivity) {
                    ((com.example.foodapp.MainActivity) getActivity()).navigateToFragment(2);
                }
            });
        }

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

        RecyclerView rvRestaurants = view.findViewById(R.id.rvAllRestaurants);
        if (rvRestaurants != null) {
            rvRestaurants.setLayoutManager(new LinearLayoutManager(requireContext()));

            RestaurantAdapter adapter = new RestaurantAdapter(displayRestaurants, restaurant -> {
                Intent intent = new Intent(requireActivity(), RestaurantDetailsActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivity(intent);
            });
            rvRestaurants.setAdapter(adapter);
        }
    }
}
