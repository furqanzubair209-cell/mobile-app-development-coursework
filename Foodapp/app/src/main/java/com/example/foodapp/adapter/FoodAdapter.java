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
import com.example.foodapp.model.FoodItem;

import java.util.List;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    public interface OnFoodClickListener {
        void onFoodClick(FoodItem foodItem);
        void onQuickAddClick(FoodItem foodItem);
    }

    private final List<FoodItem> foodItems;
    private final OnFoodClickListener listener;

    public FoodAdapter(List<FoodItem> foodItems, OnFoodClickListener listener) {
        this.foodItems = foodItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodItem foodItem = foodItems.get(position);
        holder.tvName.setText(foodItem.getName());
        holder.tvDescription.setText(foodItem.getDescription());
        holder.tvPrice.setText(String.format(Locale.US, "$%.2f", foodItem.getPrice()));
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
        return foodItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvDescription, tvPrice;
        Button btnAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivItemFoodImage);
            tvName = itemView.findViewById(R.id.tvItemFoodName);
            tvDescription = itemView.findViewById(R.id.tvItemFoodDescription);
            tvPrice = itemView.findViewById(R.id.tvItemFoodPrice);
            btnAdd = itemView.findViewById(R.id.btnItemFoodAdd);
        }
    }
}
