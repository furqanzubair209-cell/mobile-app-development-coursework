package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapter.CartAdapter;
import com.example.foodapp.data.CartManager;
import com.example.foodapp.data.SampleData;
import com.example.foodapp.model.CartItem;
import com.example.foodapp.model.Order;

import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity implements CartManager.OnCartChangeListener {

    private CartAdapter adapter;
    private TextView tvSubtotal, tvDeliveryFee, tvTotal, btnClearCart;
    private Button btnPlaceOrder, btnBrowseRestaurants;
    private LinearLayout layoutEmptyCart, barBottomCheckout;
    private ScrollView scrollViewCart;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ImageView btnBack = findViewById(R.id.btnBackCart);
        btnBack.setOnClickListener(v -> finish());

        btnClearCart = findViewById(R.id.btnClearCart);
        tvSubtotal = findViewById(R.id.tvCartSubtotal);
        tvDeliveryFee = findViewById(R.id.tvCartDeliveryFee);
        tvTotal = findViewById(R.id.tvCartTotal);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnBrowseRestaurants = findViewById(R.id.btnBrowseRestaurantsFromEmpty);

        layoutEmptyCart = findViewById(R.id.layoutEmptyCart);
        barBottomCheckout = findViewById(R.id.barBottomCheckout);
        scrollViewCart = findViewById(R.id.scrollViewCart);

        RecyclerView rvCart = findViewById(R.id.rvCartItems);
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        cartItems = CartManager.getInstance().getCartItems();
        adapter = new CartAdapter(cartItems, new CartAdapter.OnCartItemActionListener() {
            @Override
            public void onQuantityChange(CartItem item, int newQuantity) {
                CartManager.getInstance().updateQuantity(item.getFoodItem(), newQuantity);
            }

            @Override
            public void onItemRemove(CartItem item) {
                CartManager.getInstance().removeItem(item.getFoodItem());
            }
        });

        rvCart.setAdapter(adapter);

        btnClearCart.setOnClickListener(v -> {
            CartManager.getInstance().clearCart();
            Toast.makeText(CartActivity.this, "Cart cleared", Toast.LENGTH_SHORT).show();
        });

        btnBrowseRestaurants.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, RestaurantsActivity.class));
            finish();
        });

        btnPlaceOrder.setOnClickListener(v -> {
            if (CartManager.getInstance().getItemCount() == 0) {
                Toast.makeText(CartActivity.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            double total = CartManager.getInstance().getTotal();
            String orderId = "ORD-" + (10000 + (int) (Math.random() * 90000));
            String itemsSummary = cartItems.size() + " item(s) ordered";

            Order newOrder = new Order(orderId, "Foodie Express", "Just now", itemsSummary, total, "In Transit");
            SampleData.addOrder(newOrder);

            new AlertDialog.Builder(CartActivity.this)
                    .setTitle("Order Placed Successfully! \uD83C\uDF89")
                    .setMessage("Your order #" + orderId + " has been sent to the restaurant and is now in transit!")
                    .setPositiveButton("View Order", (dialog, which) -> {
                        CartManager.getInstance().clearCart();
                        Intent intent = new Intent(CartActivity.this, OrdersActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    })
                    .setCancelable(false)
                    .show();
        });

        CartManager.getInstance().addListener(this);
        updateCartUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CartManager.getInstance().removeListener(this);
    }

    @Override
    public void onCartChanged() {
        updateCartUI();
    }

    private void updateCartUI() {
        cartItems.clear();
        cartItems.addAll(CartManager.getInstance().getCartItems());
        adapter.notifyDataSetChanged();

        if (cartItems.isEmpty()) {
            layoutEmptyCart.setVisibility(View.VISIBLE);
            scrollViewCart.setVisibility(View.GONE);
            barBottomCheckout.setVisibility(View.GONE);
            btnClearCart.setVisibility(View.GONE);
        } else {
            layoutEmptyCart.setVisibility(View.GONE);
            scrollViewCart.setVisibility(View.VISIBLE);
            barBottomCheckout.setVisibility(View.VISIBLE);
            btnClearCart.setVisibility(View.VISIBLE);

            double subtotal = CartManager.getInstance().getSubtotal();
            double fee = CartManager.getInstance().getDeliveryFee();
            double total = CartManager.getInstance().getTotal();

            tvSubtotal.setText(String.format(Locale.US, "$%.2f", subtotal));
            tvDeliveryFee.setText(String.format(Locale.US, "$%.2f", fee));
            tvTotal.setText(String.format(Locale.US, "$%.2f", total));
            btnPlaceOrder.setText(String.format(Locale.US, "Place Order - $%.2f", total));
        }
    }
}
