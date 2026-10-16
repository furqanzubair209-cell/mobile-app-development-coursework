package com.example.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.model.Order;

import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    public interface OnReorderClickListener {
        void onReorderClick(Order order);
    }

    private final List<Order> orders;
    private final OnReorderClickListener listener;

    public OrderAdapter(List<Order> orders, OnReorderClickListener listener) {
        this.orders = orders;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.tvRestaurantName.setText(order.getRestaurantName());
        holder.tvStatusBadge.setText(order.getStatus());
        holder.tvDate.setText(String.format(Locale.US, "%s • %s", order.getDate(), order.getOrderId()));
        holder.tvItemsSummary.setText(order.getItemsSummary());
        holder.tvPrice.setText(String.format(Locale.US, "Total: $%.2f", order.getTotalPrice()));

        holder.btnReorder.setOnClickListener(v -> {
            if (listener != null) {
                listener.onReorderClick(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRestaurantName, tvStatusBadge, tvDate, tvItemsSummary, tvPrice;
        Button btnReorder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRestaurantName = itemView.findViewById(R.id.tvOrderRestaurantName);
            tvStatusBadge = itemView.findViewById(R.id.tvOrderStatusBadge);
            tvDate = itemView.findViewById(R.id.tvOrderDate);
            tvItemsSummary = itemView.findViewById(R.id.tvOrderItemsSummary);
            tvPrice = itemView.findViewById(R.id.tvOrderPrice);
            btnReorder = itemView.findViewById(R.id.btnReorder);
        }
    }
}
