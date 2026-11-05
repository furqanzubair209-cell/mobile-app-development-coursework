package com.example.database;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Product implements Serializable {

    private int id;
    private String name;
    private String sku;
    private String barcode;
    private String category;
    private String brand;
    private String description;
    private double purchasePrice;
    private double sellingPrice;
    private double discountPercent;
    private int stockQuantity;
    private int lowStockThreshold;
    private String supplier;
    private String status; // Active, Inactive, Discontinued
    private String availability; // In Stock, Low Stock, Out of Stock
    private String imageUri;
    private String dateAdded;
    private String lastUpdated;

    public Product() {
        this.lowStockThreshold = 5;
        this.status = "Active";
        this.availability = "In Stock";
        this.dateAdded = getCurrentDateString();
        this.lastUpdated = getCurrentDateString();
    }

    public Product(int id, String name, String sku, String barcode, String category,
                   String brand, String description, double purchasePrice, double sellingPrice,
                   double discountPercent, int stockQuantity, int lowStockThreshold,
                   String supplier, String status, String availability, String imageUri,
                   String dateAdded, String lastUpdated) {
        this.id = id;
        this.name = name != null ? name.trim() : "";
        this.sku = sku != null ? sku.trim() : "";
        this.barcode = barcode != null ? barcode.trim() : "";
        this.category = category != null ? category.trim() : "Electronics";
        this.brand = brand != null ? brand.trim() : "";
        this.description = description != null ? description.trim() : "";
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.discountPercent = discountPercent;
        this.stockQuantity = stockQuantity;
        this.lowStockThreshold = lowStockThreshold > 0 ? lowStockThreshold : 5;
        this.supplier = supplier != null ? supplier.trim() : "";
        this.status = status != null ? status.trim() : "Active";
        this.availability = availability != null && !availability.trim().isEmpty() 
                ? availability.trim() : computeAvailability(this.stockQuantity, this.lowStockThreshold);
        this.imageUri = imageUri != null ? imageUri.trim() : "";
        this.dateAdded = (dateAdded != null && !dateAdded.isEmpty()) ? dateAdded : getCurrentDateString();
        this.lastUpdated = (lastUpdated != null && !lastUpdated.isEmpty()) ? lastUpdated : getCurrentDateString();
    }

    public static String computeAvailability(int quantity, int threshold) {
        if (quantity <= 0) {
            return "Out of Stock";
        } else if (quantity <= threshold) {
            return "Low Stock";
        } else {
            return "In Stock";
        }
    }

    public static String getCurrentDateString() {
        return new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
    }

    public double getProfitPerUnit() {
        return sellingPrice - purchasePrice;
    }

    public double getProfitMarginPercent() {
        if (sellingPrice <= 0) return 0.0;
        return ((sellingPrice - purchasePrice) / sellingPrice) * 100.0;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(double purchasePrice) { this.purchasePrice = purchasePrice; }

    public double getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }

    public double getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(double discountPercent) { this.discountPercent = discountPercent; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
        this.availability = computeAvailability(stockQuantity, this.lowStockThreshold);
    }

    public int getLowStockThreshold() { return lowStockThreshold; }
    public void setLowStockThreshold(int lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
        this.availability = computeAvailability(this.stockQuantity, lowStockThreshold);
    }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAvailability() {
        if (availability == null || availability.isEmpty()) {
            availability = computeAvailability(stockQuantity, lowStockThreshold);
        }
        return availability;
    }
    public void setAvailability(String availability) { this.availability = availability; }

    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }

    public String getDateAdded() { return dateAdded; }
    public void setDateAdded(String dateAdded) { this.dateAdded = dateAdded; }

    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
}
