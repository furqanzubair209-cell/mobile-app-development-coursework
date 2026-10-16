package com.example.foodapp;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.foodapp.data.CartManager;
import com.example.foodapp.fragment.CartFragment;
import com.example.foodapp.fragment.FavoritesFragment;
import com.example.foodapp.fragment.HomeFragment;
import com.example.foodapp.fragment.OffersFragment;
import com.example.foodapp.fragment.OrdersFragment;
import com.example.foodapp.fragment.ProfileFragment;
import com.example.foodapp.fragment.RestaurantsFragment;
import com.example.foodapp.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity implements CartManager.OnCartChangeListener {

    public static final int TAB_COUNT = 8;
    private final TextView[] tabViews = new TextView[TAB_COUNT];
    private int currentTab = 0;
    private HorizontalScrollView tabScrollView;
    private TextView tvCartBadge;

    private final String[] tabLabels = {
        "\uD83C\uDFE0 Home", "\uD83C\uDF7D Restaurants", "\uD83D\uDD0D Search", "\uD83C\uDF81 Offers",
        "\uD83D\uDED2 Cart", "\uD83D\uDCCB Orders", "\u2764 Favorites", "\uD83D\uDC64 Profile"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Header Cart Button & Badge
        tvCartBadge = findViewById(R.id.tvCartBadge);
        FrameLayout btnCartHeader = findViewById(R.id.btnCartHeader);
        if (btnCartHeader != null) {
            btnCartHeader.setOnClickListener(v -> navigateToFragment(4)); // Go to Cart Fragment
        }

        tabScrollView = findViewById(R.id.tabScrollView);
        LinearLayout tabContainer = findViewById(R.id.tabContainer);

        // Dynamically create 8 tab pills
        for (int i = 0; i < TAB_COUNT; i++) {
            final int tabIndex = i;
            TextView tab = new TextView(this);
            tab.setText(tabLabels[i]);
            tab.setTextSize(13);
            tab.setPadding(dp(16), dp(10), dp(16), dp(10));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(dp(4), dp(4), dp(4), dp(4));
            tab.setLayoutParams(params);

            tab.setOnClickListener(v -> navigateToFragment(tabIndex));

            tabContainer.addView(tab);
            tabViews[i] = tab;
        }

        // Listen for Cart updates to keep badge in sync
        CartManager.getInstance().addListener(this);
        updateCartBadge();

        // Load default fragment
        if (savedInstanceState == null) {
            navigateToFragment(0);
        }
    }

    public void navigateToFragment(int index) {
        if (index < 0 || index >= TAB_COUNT) return;

        Fragment fragment = getFragmentForIndex(index);
        if (fragment == null) return;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();

        updateTabStyles(index);
        currentTab = index;

        // Auto-scroll the pill into viewport smoothly
        if (tabScrollView != null && tabViews[index] != null) {
            tabScrollView.post(() -> {
                int scrollX = tabViews[index].getLeft() - dp(16);
                tabScrollView.smoothScrollTo(Math.max(0, scrollX), 0);
            });
        }
    }

    private Fragment getFragmentForIndex(int index) {
        switch (index) {
            case 0: return new HomeFragment();
            case 1: return new RestaurantsFragment();
            case 2: return new SearchFragment();
            case 3: return new OffersFragment();
            case 4: return new CartFragment();
            case 5: return new OrdersFragment();
            case 6: return new FavoritesFragment();
            case 7: return new ProfileFragment();
            default: return null;
        }
    }

    private void updateTabStyles(int selectedIndex) {
        for (int i = 0; i < TAB_COUNT; i++) {
            TextView tab = tabViews[i];
            if (tab == null) continue;

            if (i == selectedIndex) {
                tab.setBackgroundResource(R.drawable.bg_tab_selected);
                tab.setTextColor(getResources().getColor(R.color.white, null));
                tab.setTypeface(null, android.graphics.Typeface.BOLD);
            } else {
                tab.setBackgroundResource(R.drawable.bg_tab_unselected);
                tab.setTextColor(getResources().getColor(R.color.text_primary, null));
                tab.setTypeface(null, android.graphics.Typeface.BOLD);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CartManager.getInstance().removeListener(this);
    }

    @Override
    public void onCartChanged() {
        runOnUiThread(this::updateCartBadge);
    }

    private void updateCartBadge() {
        if (tvCartBadge == null) return;
        int count = CartManager.getInstance().getItemCount();
        if (count > 0) {
            tvCartBadge.setText(String.valueOf(count));
            tvCartBadge.setVisibility(View.VISIBLE);
        } else {
            tvCartBadge.setVisibility(View.GONE);
        }
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }
}
