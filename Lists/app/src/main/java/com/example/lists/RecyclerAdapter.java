package com.example.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final Context context;
    private final List<Gadget> gadgets;

    public RecyclerAdapter(@NonNull Context context, @NonNull List<Gadget> gadgets) {
        this.context = context;
        this.gadgets = gadgets;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgLogo;
        final TextView txtName;
        final TextView txtCategory;
        final TextView txtRating;
        final TextView txtDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            txtName = itemView.findViewById(R.id.txtName);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_gadget_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Gadget item = gadgets.get(position);
        if (item != null) {
            holder.imgLogo.setImageResource(item.getIconResId());
            holder.txtName.setText(item.getName());
            holder.txtCategory.setText(item.getCategory());
            holder.txtRating.setText(item.getFormattedRating());
            holder.txtDescription.setText(item.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return gadgets.size();
    }
}
