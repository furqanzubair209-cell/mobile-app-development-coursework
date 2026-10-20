package com.example.foodapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.MainActivity;
import com.example.foodapp.R;
import com.example.foodapp.adapter.CartAdapter;
import com.example.foodapp.data.CartManager;
import com.example.foodapp.data.SampleData;
import com.example.foodapp.model.CartItem;
import com.example.foodapp.model.Order;

import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment implements CartManager.OnCartChangeListener {

    private CartAdapter adapter;
    private TextView tvSubtotal, tvDeliveryFee, tvTotal, tvVoucherDiscount, tvVoucherLabel, btnClearCart;
    private Button btnPlaceOrder, btnBrowseRestaurants;
    private LinearLayout layoutEmptyCart, barBottomCheckout, layoutVoucherRow;
    private ScrollView scrollViewCart;
    private List<CartItem> cartItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnClearCart = view.findViewById(R.id.btnClearCart);
        tvSubtotal = view.findViewById(R.id.tvCartSubtotal);
        tvDeliveryFee = view.findViewById(R.id.tvCartDeliveryFee);
        tvTotal = view.findViewById(R.id.tvCartTotal);
        tvVoucherDiscount = view.findViewById(R.id.tvVoucherDiscount);
        tvVoucherLabel = view.findViewById(R.id.tvVoucherLabel);
        layoutVoucherRow = view.findViewById(R.id.layoutVoucherRow);
        btnPlaceOrder = view.findViewById(R.id.btnPlaceOrder);
        btnBrowseRestaurants = view.findViewById(R.id.btnBrowseRestaurantsFromEmpty);

        layoutEmptyCart = view.findViewById(R.id.layoutEmptyCart);
        barBottomCheckout = view.findViewById(R.id.barBottomCheckout);
        scrollViewCart = view.findViewById(R.id.scrollViewCart);

        RecyclerView rvCart = view.findViewById(R.id.rvCartItems);
        if (rvCart != null) {
            rvCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        }

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

        if (rvCart != null) {
            rvCart.setAdapter(adapter);
        }

        if (btnClearCart != null) {
            btnClearCart.setOnClickListener(v -> {
                CartManager.getInstance().clearCart();
                Toast.makeText(requireContext(), "Cart cleared", Toast.LENGTH_SHORT).show();
            });
        }

        if (btnBrowseRestaurants != null) {
            btnBrowseRestaurants.setOnClickListener(v -> {
                if (getActivity() instanceof com.example.foodapp.MainActivity) {
                    ((com.example.foodapp.MainActivity) getActivity()).navigateToFragment(1); // Restaurants tab
                }
            });
        }

        if (btnPlaceOrder != null) {
            btnPlaceOrder.setOnClickListener(v -> {
                if (CartManager.getInstance().getItemCount() == 0) {
                    Toast.makeText(requireContext(), "Your cart is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                double total = CartManager.getInstance().getTotal();
                String orderId = "ORD-" + (10000 + (int) (Math.random() * 90000));
                String itemsSummary = cartItems.size() + " item(s) ordered";

                int imageRes = (!cartItems.isEmpty() && cartItems.get(0).getFoodItem() != null)
                        ? cartItems.get(0).getFoodItem().getImageResId()
                        : R.drawable.rest_burger_barn;
                Order newOrder = new Order(orderId, "Foodie Express", "Just now", itemsSummary, total, "In Transit", imageRes);
                SampleData.addOrder(newOrder);

                new AlertDialog.Builder(requireContext())
                        .setTitle("Order Placed Successfully! \uD83C\uDF89")
                        .setMessage("Your order #" + orderId + " has been sent to the restaurant and is now in transit!")
                        .setPositiveButton("View Orders", (dialog, which) -> {
                            CartManager.getInstance().clearCart();
                            if (getActivity() instanceof MainActivity) {
                                ((MainActivity) getActivity()).navigateToFragment(5); // Orders tab
                            }
                        })
                        .setCancelable(false)
                        .show();
            });
        }

        CartManager.getInstance().addListener(this);
        updateCartUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CartManager.getInstance().removeListener(this);
    }

    @Override
    public void onCartChanged() {
        updateCartUI();
    }

    private void updateCartUI() {
        if (adapter == null) return;
        cartItems.clear();
        cartItems.addAll(CartManager.getInstance().getCartItems());
        adapter.notifyDataSetChanged();

        if (cartItems.isEmpty()) {
            if (layoutEmptyCart != null) layoutEmptyCart.setVisibility(View.VISIBLE);
            if (scrollViewCart != null) scrollViewCart.setVisibility(View.GONE);
            if (barBottomCheckout != null) barBottomCheckout.setVisibility(View.GONE);
            if (btnClearCart != null) btnClearCart.setVisibility(View.GONE);
        } else {
            if (layoutEmptyCart != null) layoutEmptyCart.setVisibility(View.GONE);
            if (scrollViewCart != null) scrollViewCart.setVisibility(View.VISIBLE);
            if (barBottomCheckout != null) barBottomCheckout.setVisibility(View.VISIBLE);
            if (btnClearCart != null) btnClearCart.setVisibility(View.VISIBLE);

            double subtotal = CartManager.getInstance().getSubtotal();
            double discount = CartManager.getInstance().getVoucherDiscount();
            double fee = CartManager.getInstance().getDeliveryFee();
            double total = CartManager.getInstance().getTotal();

            if (tvSubtotal != null) tvSubtotal.setText(String.format(Locale.US, "$%.2f", subtotal));
            if (tvDeliveryFee != null) tvDeliveryFee.setText(String.format(Locale.US, "$%.2f", fee));
            if (tvTotal != null) tvTotal.setText(String.format(Locale.US, "$%.2f", total));

            // Show voucher discount row
            if (discount > 0) {
                if (layoutVoucherRow != null) layoutVoucherRow.setVisibility(View.VISIBLE);
                String voucher = CartManager.getInstance().getAppliedVoucher();
                if (tvVoucherLabel != null) tvVoucherLabel.setText("Voucher (" + voucher + ")");
                if (tvVoucherDiscount != null) tvVoucherDiscount.setText(String.format(Locale.US, "-$%.2f", discount));
            } else {
                if (layoutVoucherRow != null) layoutVoucherRow.setVisibility(View.GONE);
            }

            if (btnPlaceOrder != null) btnPlaceOrder.setText(String.format(Locale.US, "Place Order - $%.2f", total));
        }
    }
}
