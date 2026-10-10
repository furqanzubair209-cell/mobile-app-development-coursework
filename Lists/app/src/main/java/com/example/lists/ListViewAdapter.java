package com.example.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Gadget> {
    private final List<Gadget> gadgets;

    public ListViewAdapter(@NonNull Context context, @NonNull List<Gadget> gadgets) {
        super(context, R.layout.item_gadget, gadgets);
        this.gadgets = gadgets;
    }

    private static class ViewHolder {
        ImageView imgLogo;
        TextView txtName;
        TextView txtCategory;
        TextView txtRating;
        TextView txtDescription;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView;
        ViewHolder holder;
        if (convertView == null) {
            itemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_gadget, parent, false);
            holder = new ViewHolder();
            holder.imgLogo = itemView.findViewById(R.id.imgLogo);
            holder.txtName = itemView.findViewById(R.id.txtName);
            holder.txtCategory = itemView.findViewById(R.id.txtCategory);
            holder.txtRating = itemView.findViewById(R.id.txtRating);
            holder.txtDescription = itemView.findViewById(R.id.txtDescription);
            itemView.setTag(holder);
        } else {
            itemView = convertView;
            holder = (ViewHolder) itemView.getTag();
        }

        Gadget item = gadgets.get(position);
        if (item != null) {
            holder.imgLogo.setImageResource(item.getIconResId());
            holder.txtName.setText(item.getName());
            holder.txtCategory.setText(item.getCategory());
            holder.txtRating.setText(item.getFormattedRating());
            holder.txtDescription.setText(item.getDescription());
        }

        return itemView;
    }
}
