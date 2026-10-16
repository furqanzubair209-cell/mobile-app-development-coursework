package com.example.foodapp.data;

import com.example.foodapp.model.CartItem;
import com.example.foodapp.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<CartItem> cartItems = new ArrayList<>();
    private final List<OnCartChangeListener> listeners = new ArrayList<>();
    private static final double DEFAULT_DELIVERY_FEE = 2.49;
    private String appliedVoucher = null;

    public interface OnCartChangeListener {
        void onCartChanged();
    }

    private CartManager() {}

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addListener(OnCartChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(OnCartChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (OnCartChangeListener listener : new ArrayList<>(listeners)) {
            listener.onCartChanged();
        }
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public void addItem(FoodItem foodItem, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getFoodItem().getId().equals(foodItem.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                notifyListeners();
                return;
            }
        }
        cartItems.add(new CartItem(foodItem, quantity));
        notifyListeners();
    }

    public void updateQuantity(FoodItem foodItem, int newQuantity) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getFoodItem().getId().equals(foodItem.getId())) {
                if (newQuantity <= 0) {
                    cartItems.remove(i);
                } else {
                    cartItems.get(i).setQuantity(newQuantity);
                }
                notifyListeners();
                return;
            }
        }
    }

    public void removeItem(FoodItem foodItem) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getFoodItem().getId().equals(foodItem.getId())) {
                cartItems.remove(i);
                notifyListeners();
                return;
            }
        }
    }

    public void clearCart() {
        cartItems.clear();
        appliedVoucher = null;
        notifyListeners();
    }

    public int getItemCount() {
        int count = 0;
        for (CartItem item : cartItems) {
            count += item.getQuantity();
        }
        return count;
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        for (CartItem item : cartItems) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    public double getDeliveryFee() {
        if (cartItems.isEmpty()) return 0.0;
        // FREEDEL voucher makes delivery free
        if ("FREEDEL".equals(appliedVoucher)) {
            return 0.0;
        }
        return DEFAULT_DELIVERY_FEE;
    }

    // Voucher system
    public void applyVoucher(String voucherCode) {
        this.appliedVoucher = voucherCode;
        notifyListeners();
    }

    public void removeVoucher() {
        this.appliedVoucher = null;
        notifyListeners();
    }

    public String getAppliedVoucher() {
        return appliedVoucher;
    }

    public double getVoucherDiscount() {
        if (appliedVoucher == null || cartItems.isEmpty()) return 0.0;
        double subtotal = getSubtotal();

        switch (appliedVoucher) {
            case "FOODIE50":
                // 50% off the subtotal
                return subtotal * 0.50;
            case "FREEDEL":
                // Free delivery - discount is the delivery fee amount
                return DEFAULT_DELIVERY_FEE;
            case "BURGER20":
                // 20% off the subtotal
                return subtotal * 0.20;
            default:
                return 0.0;
        }
    }

    public double getTotal() {
        double subtotal = getSubtotal();
        double deliveryFee = getDeliveryFee();
        double discount = getVoucherDiscount();

        // For FREEDEL, discount is already applied via getDeliveryFee() returning 0
        // So we only subtract discount for non-FREEDEL vouchers
        if ("FREEDEL".equals(appliedVoucher)) {
            return subtotal + deliveryFee;
        }

        double total = subtotal + deliveryFee - discount;
        return Math.max(0, total); // Never go below 0
    }
}
