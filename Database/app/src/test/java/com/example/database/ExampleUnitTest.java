package com.example.database;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {

    @Test
    public void testProductProfitCalculation() {
        Product p = new Product();
        p.setPurchasePrice(50.00);
        p.setSellingPrice(80.00);

        assertEquals(30.00, p.getProfitPerUnit(), 0.001);
        assertEquals(37.5, p.getProfitMarginPercent(), 0.001);
    }

    @Test
    public void testProductZeroSellingPriceMargin() {
        Product p = new Product();
        p.setPurchasePrice(50.00);
        p.setSellingPrice(0.00);

        assertEquals(0.0, p.getProfitMarginPercent(), 0.001);
    }

    @Test
    public void testAvailabilityComputation() {
        // Out of Stock test
        assertEquals("Out of Stock", Product.computeAvailability(0, 5));
        assertEquals("Out of Stock", Product.computeAvailability(-3, 5));

        // Low Stock test
        assertEquals("Low Stock", Product.computeAvailability(1, 5));
        assertEquals("Low Stock", Product.computeAvailability(5, 5));

        // In Stock test
        assertEquals("In Stock", Product.computeAvailability(6, 5));
        assertEquals("In Stock", Product.computeAvailability(100, 5));
    }

    @Test
    public void testProductSetStockQuantityUpdatesAvailability() {
        Product p = new Product();
        p.setLowStockThreshold(4);

        p.setStockQuantity(10);
        assertEquals("In Stock", p.getAvailability());

        p.setStockQuantity(3);
        assertEquals("Low Stock", p.getAvailability());

        p.setStockQuantity(0);
        assertEquals("Out of Stock", p.getAvailability());
    }

    @Test
    public void testDashboardStatsModel() {
        DashboardStats stats = new DashboardStats(10, 8, 2, 1, 4500.50, 7800.00);
        assertEquals(10, stats.getTotalProducts());
        assertEquals(8, stats.getActiveProducts());
        assertEquals(2, stats.getLowStockProducts());
        assertEquals(1, stats.getOutOfStockProducts());
        assertEquals(4500.50, stats.getTotalInventoryValue(), 0.001);
        assertEquals(7800.00, stats.getTotalRetailValue(), 0.001);
    }
}
