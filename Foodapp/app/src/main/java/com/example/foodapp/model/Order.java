package com.example.foodapp.model;

import java.io.Serializable;

public class Order implements Serializable {
    private String orderId;
    private String restaurantName;
    private String date;
    private String itemsSummary;
    private double totalPrice;
    private String status;

    public Order(String orderId, String restaurantName, String date, String itemsSummary, double totalPrice, String status) {
        this.orderId = orderId;
        this.restaurantName = restaurantName;
        this.date = date;
        this.itemsSummary = itemsSummary;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String getOrderId() { return orderId; }
    public String getRestaurantName() { return restaurantName; }
    public String getDate() { return date; }
    public String getItemsSummary() { return itemsSummary; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
}
