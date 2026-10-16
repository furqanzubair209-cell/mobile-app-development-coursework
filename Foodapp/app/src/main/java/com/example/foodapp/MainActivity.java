package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapter.PopularFoodAdapter;
import com.example.foodapp.adapter.RestaurantAdapter;
import com.example.foodapp.data.CartManager;
import com.example.foodapp.data.SampleData;
import com.example.foodapp.model.FoodItem;
import com.example.foodapp.model.Restaurant;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CartManager.OnCartChangeListener {

    private TextView tvCartBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Cart Header Button and Badge
        tvCartBadge = findViewById(R.id.tvCartBadge);
        FrameLayout btnCartHeader = findViewById(R.id.btnCartHeader);
        if (btnCartHeader != null) {
            btnCartHeader.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CartActivity.class)));
        }

        // 2. Search Bar Clickable
        LinearLayout searchBarClickable = findViewById(R.id.searchBarClickable);
        if (searchBarClickable != null) {
            searchBarClickable.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SearchActivity.class)));
        }

        // 3. Navigation Grid (6 cards)
        MaterialCardView cardNavHome = findViewById(R.id.cardNavHome);
        if (cardNavHome != null) {
            cardNavHome.setOnClickListener(v -> Toast.makeText(MainActivity.this, "You are on Home screen", Toast.LENGTH_SHORT).show());
        }

        MaterialCardView cardNavRestaurants = findViewById(R.id.cardNavRestaurants);
        if (cardNavRestaurants != null) {
            cardNavRestaurants.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RestaurantsActivity.class)));
        }

        MaterialCardView cardNavSearch = findViewById(R.id.cardNavSearch);
        if (cardNavSearch != null) {
            cardNavSearch.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SearchActivity.class)));
        }

        MaterialCardView cardNavOffers = findViewById(R.id.cardNavOffers);
        if (cardNavOffers != null) {
            cardNavOffers.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, OffersActivity.class)));
        }

        MaterialCardView cardNavOrders = findViewById(R.id.cardNavOrders);
        if (cardNavOrders != null) {
            cardNavOrders.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, OrdersActivity.class)));
        }

        MaterialCardView cardNavProfile = findViewById(R.id.cardNavProfile);
        if (cardNavProfile != null) {
            cardNavProfile.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ProfileActivity.class)));
        }

        // 4. Category Clicks (Pizza, Burgers, Sushi, Noodles, Desserts, Drinks)
        View.OnClickListener categoryClickListener = v -> {
            String category = "Pizza";
            int vId = v.getId();
            if (vId == R.id.catPizza) category = "Pizza";
            else if (vId == R.id.catBurgers) category = "Burgers";
            else if (vId == R.id.catSushi) category = "Sushi";
            else if (vId == R.id.catNoodles) category = "Noodles";
            else if (vId == R.id.catDesserts) category = "Desserts";
            else if (vId == R.id.catDrinks) category = "Drinks";

            Intent intent = new Intent(MainActivity.this, RestaurantsActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
        };
        int[] categoryIds = {
            R.id.catPizza,
            R.id.catBurgers,
            R.id.catSushi,
            R.id.catNoodles,
            R.id.catDesserts,
            R.id.catDrinks
        };
        for (int id : categoryIds) {
            View catView = findViewById(id);
            if (catView != null) {
                catView.setOnClickListener(categoryClickListener);
            }
        }

        // 5. Promotional Banner Button
        Button btnClaimOffer = findViewById(R.id.btnClaimOffer);
        if (btnClaimOffer != null) {
            btnClaimOffer.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, OffersActivity.class)));
        }

        // 8 & 9. Popular Dishes Section (Horizontal RecyclerView, first 8 food items)
        View btnSeeAllProducts = findViewById(R.id.btnSeeAllProducts);
        if (btnSeeAllProducts != null) {
            btnSeeAllProducts.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SearchActivity.class)));
        }

        RecyclerView rvPopular = findViewById(R.id.rvPopularProducts);
        if (rvPopular != null) {
            rvPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            List<FoodItem> allFood = SampleData.getAllFoodItems();
            List<FoodItem> popularDishes = allFood.subList(0, Math.min(8, allFood.size()));
            PopularFoodAdapter popularAdapter = new PopularFoodAdapter(popularDishes, new PopularFoodAdapter.OnPopularFoodClickListener() {
                @Override
                public void onFoodClick(FoodItem foodItem) {
                    Intent intent = new Intent(MainActivity.this, FoodDetailsActivity.class);
                    intent.putExtra("foodItem", foodItem);
                    startActivity(intent);
                }

                @Override
                public void onQuickAddClick(FoodItem foodItem) {
                    CartManager.getInstance().addItem(foodItem, 1);
                    Toast.makeText(MainActivity.this, foodItem.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
                }
            });
            rvPopular.setAdapter(popularAdapter);
        }

        // 6 & 7. Featured Restaurants Section (Vertical RecyclerView, first 4 restaurants)
        View btnSeeAllRestaurants = findViewById(R.id.btnSeeAllRestaurants);
        if (btnSeeAllRestaurants != null) {
            btnSeeAllRestaurants.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RestaurantsActivity.class)));
        }

        RecyclerView rvFeatured = findViewById(R.id.rvFeaturedRestaurants);
        if (rvFeatured != null) {
            rvFeatured.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            List<Restaurant> allRestaurants = SampleData.getRestaurants();
            List<Restaurant> featuredRestaurants = allRestaurants.subList(0, Math.min(4, allRestaurants.size()));
            RestaurantAdapter restaurantAdapter = new RestaurantAdapter(featuredRestaurants, restaurant -> {
                Intent intent = new Intent(MainActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivity(intent);
            });
            rvFeatured.setAdapter(restaurantAdapter);
        }

        // 10. Register CartManager listener
        CartManager.getInstance().addListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CartManager.getInstance().removeListener(this);
    }

    @Override
    public void onCartChanged() {
        updateCartBadge();
    }

    private void updateCartBadge() {
        if (tvCartBadge == null) return;
        int count = CartManager.getInstance().getItemCount();
        if (count > 0) {
            tvCartBadge.setText(String.valueOf(count));
            tvCartBadge.setVisibility(View.VISIBLE);
        } else {
            tvCartBadge.setVisibility(View.GONE);
        }
    }
}
