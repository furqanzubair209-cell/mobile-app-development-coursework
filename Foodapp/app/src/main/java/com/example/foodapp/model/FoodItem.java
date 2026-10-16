package com.example.foodapp.model;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private String id;
    private String restaurantId;
    private String name;
    private String description;
    private double price;
    private int imageResId;
    private String category;

    public FoodItem(String id, String restaurantId, String name, String description, double price, int imageResId, String category) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
        this.category = category;
    }

    public String getId() { return id; }
    public String getRestaurantId() { return restaurantId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getImageResId() { return imageResId; }
    public String getCategory() { return category; }
}
