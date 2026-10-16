package com.example.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.model.Restaurant;

import java.util.List;
import java.util.Locale;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    public interface OnRestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant);
    }

    private final List<Restaurant> restaurants;
    private final OnRestaurantClickListener listener;

    public RestaurantAdapter(List<Restaurant> restaurants, OnRestaurantClickListener listener) {
        this.restaurants = restaurants;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.tvName.setText(restaurant.getName());
        holder.tvCuisine.setText(restaurant.getCuisine());
        holder.tvRating.setText(String.format(Locale.US, "%.1f", restaurant.getRating()));
        holder.tvDeliveryTime.setText(restaurant.getDeliveryTime());
        holder.tvDeliveryFee.setText(String.format(Locale.US, "$%.2f Delivery", restaurant.getDeliveryFee()));
        holder.tvPriceLevel.setText(restaurant.getPriceLevel());
        holder.ivImage.setImageResource(restaurant.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRestaurantClick(restaurant);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvCuisine, tvRating, tvDeliveryTime, tvDeliveryFee, tvPriceLevel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivItemRestImage);
            tvName = itemView.findViewById(R.id.tvItemRestName);
            tvCuisine = itemView.findViewById(R.id.tvItemRestCuisine);
            tvRating = itemView.findViewById(R.id.tvItemRestRating);
            tvDeliveryTime = itemView.findViewById(R.id.tvItemRestDeliveryTime);
            tvDeliveryFee = itemView.findViewById(R.id.tvItemRestDeliveryFee);
            tvPriceLevel = itemView.findViewById(R.id.tvItemRestPriceLevel);
        }
    }
}
