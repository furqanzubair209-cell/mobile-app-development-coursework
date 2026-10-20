package com.example.foodapp.model;

import com.example.foodapp.R;

import java.io.Serializable;

public class Order implements Serializable {
    private String orderId;
    private String restaurantName;
    private String date;
    private String itemsSummary;
    private double totalPrice;
    private String status;
    private int imageResId;

    public Order(String orderId, String restaurantName, String date, String itemsSummary, double totalPrice, String status, int imageResId) {
        this.orderId = orderId;
        this.restaurantName = restaurantName;
        this.date = date;
        this.itemsSummary = itemsSummary;
        this.totalPrice = totalPrice;
        this.status = status;
        this.imageResId = imageResId;
    }

    public Order(String orderId, String restaurantName, String date, String itemsSummary, double totalPrice, String status) {
        this(orderId, restaurantName, date, itemsSummary, totalPrice, status, R.drawable.food_burger_cheeseburger);
    }

    public String getOrderId() { return orderId; }
    public String getRestaurantName() { return restaurantName; }
    public String getDate() { return date; }
    public String getItemsSummary() { return itemsSummary; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public int getImageResId() { return imageResId; }
}
