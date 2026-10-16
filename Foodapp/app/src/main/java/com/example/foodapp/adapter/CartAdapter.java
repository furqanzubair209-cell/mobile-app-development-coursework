package com.example.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.model.CartItem;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public interface OnCartItemActionListener {
        void onQuantityChange(CartItem item, int newQuantity);
        void onItemRemove(CartItem item);
    }

    private final List<CartItem> cartItems;
    private final OnCartItemActionListener listener;

    public CartAdapter(List<CartItem> cartItems, OnCartItemActionListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.tvName.setText(cartItem.getFoodItem().getName());
        holder.tvPrice.setText(String.format(Locale.US, "$%.2f", cartItem.getTotalPrice()));
        holder.tvQty.setText(String.valueOf(cartItem.getQuantity()));
        holder.ivImage.setImageResource(cartItem.getFoodItem().getImageResId());

        holder.btnDecrease.setOnClickListener(v -> {
            if (listener != null) {
                listener.onQuantityChange(cartItem, cartItem.getQuantity() - 1);
            }
        });

        holder.btnIncrease.setOnClickListener(v -> {
            if (listener != null) {
                listener.onQuantityChange(cartItem, cartItem.getQuantity() + 1);
            }
        });

        holder.btnRemove.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemRemove(cartItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage, btnDecrease, btnIncrease, btnRemove;
        TextView tvName, tvPrice, tvQty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivItemCartImage);
            tvName = itemView.findViewById(R.id.tvItemCartName);
            tvPrice = itemView.findViewById(R.id.tvItemCartPrice);
            tvQty = itemView.findViewById(R.id.tvItemCartQty);
            btnDecrease = itemView.findViewById(R.id.btnItemCartDecrease);
            btnIncrease = itemView.findViewById(R.id.btnItemCartIncrease);
            btnRemove = itemView.findViewById(R.id.btnItemCartRemove);
        }
    }
}
