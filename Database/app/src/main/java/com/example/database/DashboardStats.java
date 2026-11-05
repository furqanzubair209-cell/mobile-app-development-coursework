package com.example.database;

public class DashboardStats {
    private final int totalProducts;
    private final int activeProducts;
    private final int lowStockProducts;
    private final int outOfStockProducts;
    private final double totalInventoryValue;
    private final double totalRetailValue;

    public DashboardStats(int totalProducts, int activeProducts, int lowStockProducts,
                          int outOfStockProducts, double totalInventoryValue, double totalRetailValue) {
        this.totalProducts = totalProducts;
        this.activeProducts = activeProducts;
        this.lowStockProducts = lowStockProducts;
        this.outOfStockProducts = outOfStockProducts;
        this.totalInventoryValue = totalInventoryValue;
        this.totalRetailValue = totalRetailValue;
    }

    public int getTotalProducts() { return totalProducts; }
    public int getActiveProducts() { return activeProducts; }
    public int getLowStockProducts() { return lowStockProducts; }
    public int getOutOfStockProducts() { return outOfStockProducts; }
    public double getTotalInventoryValue() { return totalInventoryValue; }
    public double getTotalRetailValue() { return totalRetailValue; }
}
