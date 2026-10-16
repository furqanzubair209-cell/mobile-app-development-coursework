package com.example.foodapp.model;

import java.io.Serializable;
import java.util.List;

public class Restaurant implements Serializable {
    private String id;
    private String name;
    private String cuisine;
    private double rating;
    private String deliveryTime;
    private double deliveryFee;
    private String priceLevel;
    private int imageResId;
    private String description;
    private List<FoodItem> foodItems;

    public Restaurant(String id, String name, String cuisine, double rating, String deliveryTime, double deliveryFee, String priceLevel, int imageResId, String description, List<FoodItem> foodItems) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.deliveryFee = deliveryFee;
        this.priceLevel = priceLevel;
        this.imageResId = imageResId;
        this.description = description;
        this.foodItems = foodItems;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCuisine() { return cuisine; }
    public double getRating() { return rating; }
    public String getDeliveryTime() { return deliveryTime; }
    public double getDeliveryFee() { return deliveryFee; }
    public String getPriceLevel() { return priceLevel; }
    public int getImageResId() { return imageResId; }
    public String getDescription() { return description; }
    public List<FoodItem> getFoodItems() { return foodItems; }
}
