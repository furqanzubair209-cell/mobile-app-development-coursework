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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.FoodDetailsActivity;
import com.example.foodapp.MainActivity;
import com.example.foodapp.R;
import com.example.foodapp.RestaurantDetailsActivity;
import com.example.foodapp.adapter.PopularFoodAdapter;
import com.example.foodapp.adapter.RestaurantAdapter;
import com.example.foodapp.data.CartManager;
import com.example.foodapp.data.SampleData;
import com.example.foodapp.model.FoodItem;
import com.example.foodapp.model.Restaurant;

import java.util.List;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Search Bar Clickable -> Switch to Search fragment
        LinearLayout searchBarClickable = view.findViewById(R.id.searchBarClickable);
        if (searchBarClickable != null) {
            searchBarClickable.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToFragment(2); // Search tab
                }
            });
        }

        // Category Clicks -> Switch to Restaurants tab
        View.OnClickListener categoryClickListener = v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToFragment(1); // Restaurants tab
            }
        };
        int[] categoryIds = {
            R.id.catPizza, R.id.catBurgers, R.id.catSushi,
            R.id.catNoodles, R.id.catDesserts, R.id.catDrinks
        };
        for (int id : categoryIds) {
            View catView = view.findViewById(id);
            if (catView != null) {
                catView.setOnClickListener(categoryClickListener);
            }
        }

        // Promotional Banner Card & Button -> Switch to Offers tab
        View bannerPromo = view.findViewById(R.id.bannerPromo);
        if (bannerPromo != null) {
            bannerPromo.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToFragment(3); // Offers tab
                }
            });
        }

        Button btnClaimOffer = view.findViewById(R.id.btnClaimOffer);
        if (btnClaimOffer != null) {
            btnClaimOffer.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToFragment(3); // Offers tab
                }
            });
        }

        // Popular Dishes "See All >" -> Switch to Search tab
        View btnSeeAllProducts = view.findViewById(R.id.btnSeeAllProducts);
        if (btnSeeAllProducts != null) {
            btnSeeAllProducts.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToFragment(2); // Search tab
                }
            });
        }

        // Popular Dishes RecyclerView
        RecyclerView rvPopular = view.findViewById(R.id.rvPopularProducts);
        if (rvPopular != null) {
            rvPopular.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            List<FoodItem> allFood = SampleData.getAllFoodItems();
            List<FoodItem> popularDishes = allFood.subList(0, Math.min(8, allFood.size()));
            PopularFoodAdapter popularAdapter = new PopularFoodAdapter(popularDishes, new PopularFoodAdapter.OnPopularFoodClickListener() {
                @Override
                public void onFoodClick(FoodItem foodItem) {
                    Intent intent = new Intent(requireActivity(), FoodDetailsActivity.class);
                    intent.putExtra("foodItem", foodItem);
                    startActivity(intent);
                }

                @Override
                public void onQuickAddClick(FoodItem foodItem) {
                    CartManager.getInstance().addItem(foodItem, 1);
                    Toast.makeText(requireContext(), foodItem.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
                }
            });
            rvPopular.setAdapter(popularAdapter);
        }

        // Featured Restaurants "See All >" -> Switch to Restaurants tab
        View btnSeeAllRestaurants = view.findViewById(R.id.btnSeeAllRestaurants);
        if (btnSeeAllRestaurants != null) {
            btnSeeAllRestaurants.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToFragment(1); // Restaurants tab
                }
            });
        }

        // Featured Restaurants RecyclerView
        RecyclerView rvFeatured = view.findViewById(R.id.rvFeaturedRestaurants);
        if (rvFeatured != null) {
            rvFeatured.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
            List<Restaurant> allRestaurants = SampleData.getRestaurants();
            List<Restaurant> featuredRestaurants = allRestaurants.subList(0, Math.min(4, allRestaurants.size()));
            RestaurantAdapter restaurantAdapter = new RestaurantAdapter(featuredRestaurants, restaurant -> {
                Intent intent = new Intent(requireActivity(), RestaurantDetailsActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivity(intent);
            });
            rvFeatured.setAdapter(restaurantAdapter);
        }
    }
}
