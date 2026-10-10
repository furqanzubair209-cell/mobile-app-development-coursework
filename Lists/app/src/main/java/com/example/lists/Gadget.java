package com.example.lists;

import java.util.Locale;

public class Gadget {
    private final String name;
    private final String description;
    private final String category;
    private final double rating;
    private final int reviewCount;
    private final int iconResId;

    public Gadget(String name, String description, String category, double rating, int reviewCount, int iconResId) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getFormattedRating() {
        return String.format(Locale.US, "★ %.1f (%d reviews)", rating, reviewCount);
    }

    public int getIconResId() {
        return iconResId;
    }
}
