package com.example.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.data.SampleData;
import com.example.foodapp.model.FoodItem;
import com.example.foodapp.model.Restaurant;

import java.util.List;
import java.util.Locale;

public class PopularFoodAdapter extends RecyclerView.Adapter<PopularFoodAdapter.ViewHolder> {

    public interface OnPopularFoodClickListener {
        void onFoodClick(FoodItem foodItem);
        void onQuickAddClick(FoodItem foodItem);
    }

    private final List<FoodItem> foodItems;
    private final OnPopularFoodClickListener listener;

    public PopularFoodAdapter(List<FoodItem> foodItems, OnPopularFoodClickListener listener) {
        this.foodItems = foodItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodItem foodItem = foodItems.get(position);
        holder.tvName.setText(foodItem.getName());
        holder.tvPrice.setText(String.format(Locale.US, "$%.2f", foodItem.getPrice()));
        holder.tvRestaurant.setText(getRestaurantName(foodItem.getRestaurantId()));
        holder.ivImage.setImageResource(foodItem.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFoodClick(foodItem);
            }
        });

        holder.btnAdd.setOnClickListener(v -> {
            if (listener != null) {
                listener.onQuickAddClick(foodItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItems != null ? foodItems.size() : 0;
    }

    public String getRestaurantName(String restaurantId) {
        if (restaurantId == null || restaurantId.isEmpty()) {
            return "";
        }
        Restaurant restaurant = SampleData.getRestaurantById(restaurantId);
        return restaurant != null ? restaurant.getName() : "";
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvRestaurant, tvPrice;
        Button btnAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivPopularFoodImage);
            tvName = itemView.findViewById(R.id.tvPopularFoodName);
            tvRestaurant = itemView.findViewById(R.id.tvPopularFoodRestaurant);
            tvPrice = itemView.findViewById(R.id.tvPopularFoodPrice);
            btnAdd = itemView.findViewById(R.id.btnPopularFoodAdd);
        }
    }
}
