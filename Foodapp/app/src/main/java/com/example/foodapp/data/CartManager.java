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
        return cartItems.isEmpty() ? 0.0 : DEFAULT_DELIVERY_FEE;
    }

    public double getTotal() {
        return getSubtotal() + getDeliveryFee();
    }
}
