package com.example.database;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Locale;

public class ProductCatalogFragment extends Fragment {

    // Header & KPIs
    private TextView tvTotalCount;
    private TextView tvKpiTotalProducts, tvKpiActiveProducts, tvKpiInventoryValue, tvKpiRetailValue;
    private TextView tvKpiLowStock, tvKpiOutOfStock;

    // Search and Filter Controls
    private EditText etSearchQuery;
    private AutoCompleteTextView actvFilterCategory, actvFilterStockStatus, actvSortBy;
    private TextView tvRecordsStatus;

    // List and Empty State
    private LinearLayout containerRecords, layoutEmptyState;
    private TextView tvEmptyTitle, tvEmptySubtitle;
    private MaterialButton btnEmptyAction;

    private DatabaseHelper databaseHelper;

    public static final String[] FILTER_CATEGORIES = {
            "All Categories",
            "Electronics",
            "Computers & Tech",
            "Fashion & Apparel",
            "Home & Living",
            "Health & Beauty",
            "Sports & Fitness",
            "Toys & Hobbies",
            "Groceries & Food",
            "Automotive",
            "Books & Media"
    };

    public static final String[] FILTER_STOCK_STATUS = {
            "All Stock",
            "In Stock",
            "Low Stock",
            "Out of Stock"
    };

    public static final String[] SORT_OPTIONS = {
            "Newest First",
            "Price: Low to High",
            "Price: High to Low",
            "Stock: Low to High",
            "Name A-Z"
    };

    public ProductCatalogFragment() {
        // Required empty public constructor
    }

    public static ProductCatalogFragment newInstance() {
        return new ProductCatalogFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_catalog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof MainActivity) {
            databaseHelper = ((MainActivity) getActivity()).getDatabaseHelper();
        } else {
            databaseHelper = new DatabaseHelper(requireContext());
        }

        initializeViews(view);
        setupDropdownFilters();
        setupSearchAndFilterListeners();

        loadDashboardAndProducts();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDashboardAndProducts();
    }

    private void initializeViews(View view) {
        tvTotalCount = view.findViewById(R.id.tvTotalCount);
        tvKpiTotalProducts = view.findViewById(R.id.tvKpiTotalProducts);
        tvKpiActiveProducts = view.findViewById(R.id.tvKpiActiveProducts);
        tvKpiInventoryValue = view.findViewById(R.id.tvKpiInventoryValue);
        tvKpiRetailValue = view.findViewById(R.id.tvKpiRetailValue);
        tvKpiLowStock = view.findViewById(R.id.tvKpiLowStock);
        tvKpiOutOfStock = view.findViewById(R.id.tvKpiOutOfStock);

        etSearchQuery = view.findViewById(R.id.etSearchQuery);
        actvFilterCategory = view.findViewById(R.id.actvFilterCategory);
        actvFilterStockStatus = view.findViewById(R.id.actvFilterStockStatus);
        actvSortBy = view.findViewById(R.id.actvSortBy);
        tvRecordsStatus = view.findViewById(R.id.tvRecordsStatus);

        containerRecords = view.findViewById(R.id.containerRecords);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        tvEmptyTitle = view.findViewById(R.id.tvEmptyTitle);
        tvEmptySubtitle = view.findViewById(R.id.tvEmptySubtitle);
        btnEmptyAction = view.findViewById(R.id.btnEmptyAction);

        btnEmptyAction.setOnClickListener(v -> {
            if (isFiltersActive()) {
                // Reset filters
                resetFilters();
            } else {
                // Navigate to Form
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToAdmission(null);
                }
            }
        });
    }

    private void setupDropdownFilters() {
        if (getContext() == null) return;

        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_dropdown_item_1line, FILTER_CATEGORIES);
        actvFilterCategory.setAdapter(catAdapter);
        actvFilterCategory.setText(FILTER_CATEGORIES[0], false);
        actvFilterCategory.setOnClickListener(v -> actvFilterCategory.showDropDown());

        ArrayAdapter<String> stockAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_dropdown_item_1line, FILTER_STOCK_STATUS);
        actvFilterStockStatus.setAdapter(stockAdapter);
        actvFilterStockStatus.setText(FILTER_STOCK_STATUS[0], false);
        actvFilterStockStatus.setOnClickListener(v -> actvFilterStockStatus.showDropDown());

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_dropdown_item_1line, SORT_OPTIONS);
        actvSortBy.setAdapter(sortAdapter);
        actvSortBy.setText(SORT_OPTIONS[0], false);
        actvSortBy.setOnClickListener(v -> actvSortBy.showDropDown());
    }

    private void setupSearchAndFilterListeners() {
        // Live search text watcher
        etSearchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                refreshProductList();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Filter category selection
        actvFilterCategory.setOnItemClickListener((parent, view, position, id) -> refreshProductList());

        // Filter stock status selection
        actvFilterStockStatus.setOnItemClickListener((parent, view, position, id) -> refreshProductList());

        // Sort by selection
        actvSortBy.setOnItemClickListener((parent, view, position, id) -> refreshProductList());
    }

    private boolean isFiltersActive() {
        String query = etSearchQuery.getText().toString().trim();
        String cat = actvFilterCategory.getText().toString().trim();
        String stock = actvFilterStockStatus.getText().toString().trim();
        return !query.isEmpty() || (!cat.isEmpty() && !cat.equalsIgnoreCase(FILTER_CATEGORIES[0]))
                || (!stock.isEmpty() && !stock.equalsIgnoreCase(FILTER_STOCK_STATUS[0]));
    }

    private void resetFilters() {
        etSearchQuery.setText("");
        setupDropdownFilters();
        refreshProductList();
    }

    public void loadDashboardAndProducts() {
        if (databaseHelper == null) return;

        // Update Dashboard KPIs
        DashboardStats stats = databaseHelper.getDashboardStats();
        if (tvKpiTotalProducts != null) tvKpiTotalProducts.setText(String.valueOf(stats.getTotalProducts()));
        if (tvKpiActiveProducts != null) tvKpiActiveProducts.setText(getString(R.string.format_kpi_active, stats.getActiveProducts()));
        if (tvKpiInventoryValue != null) tvKpiInventoryValue.setText(String.format(Locale.getDefault(), "$%,.2f", stats.getTotalInventoryValue()));
        if (tvKpiRetailValue != null) tvKpiRetailValue.setText(String.format(Locale.getDefault(), "Retail: $%,.2f", stats.getTotalRetailValue()));
        if (tvKpiLowStock != null) tvKpiLowStock.setText(String.valueOf(stats.getLowStockProducts()));
        if (tvKpiOutOfStock != null) tvKpiOutOfStock.setText(String.valueOf(stats.getOutOfStockProducts()));

        if (tvTotalCount != null) {
            tvTotalCount.setText(stats.getTotalProducts() == 1 ? "1 Product" : getString(R.string.format_kpi_products_count, stats.getTotalProducts()));
        }

        refreshProductList();
    }

    public void refreshProductList() {
        if (databaseHelper == null || containerRecords == null || getContext() == null) return;

        String searchQuery = etSearchQuery != null ? etSearchQuery.getText().toString().trim() : "";
        String category = actvFilterCategory != null ? actvFilterCategory.getText().toString().trim() : "";
        String stockStatus = actvFilterStockStatus != null ? actvFilterStockStatus.getText().toString().trim() : "";
        String sortBy = actvSortBy != null ? actvSortBy.getText().toString().trim() : "";

        List<Product> products = databaseHelper.getAllProducts(searchQuery, category, stockStatus, sortBy);

        containerRecords.removeAllViews();

        if (products.isEmpty()) {
            layoutEmptyState.setVisibility(View.VISIBLE);
            containerRecords.setVisibility(View.GONE);
            tvRecordsStatus.setText(R.string.showing_zero_products);

            if (isFiltersActive()) {
                tvEmptyTitle.setText(R.string.title_no_matching_products);
                tvEmptySubtitle.setText(R.string.subtitle_no_matching_products);
                btnEmptyAction.setText(R.string.btn_clear_filters);
                btnEmptyAction.setIconResource(R.drawable.ic_clear);
            } else {
                tvEmptyTitle.setText(R.string.title_inventory_is_empty);
                tvEmptySubtitle.setText(R.string.subtitle_inventory_is_empty);
                btnEmptyAction.setText(R.string.btn_add_first_product);
                btnEmptyAction.setIconResource(R.drawable.ic_edit);
            }
            return;
        }

        layoutEmptyState.setVisibility(View.GONE);
        containerRecords.setVisibility(View.VISIBLE);
        tvRecordsStatus.setText(products.size() == 1 ? "Showing 1 product" : getString(R.string.format_showing_products, products.size()));

        LayoutInflater inflater = LayoutInflater.from(requireContext());

        for (Product product : products) {
            View cardView = inflater.inflate(R.layout.item_product_card, containerRecords, false);

            TextView tvProductName = cardView.findViewById(R.id.tvProductName);
            TextView tvProductIdBadge = cardView.findViewById(R.id.tvProductIdBadge);
            TextView tvProductBrand = cardView.findViewById(R.id.tvProductBrand);
            TextView tvAvailabilityBadge = cardView.findViewById(R.id.tvAvailabilityBadge);
            TextView tvStatusBadge = cardView.findViewById(R.id.tvStatusBadge);
            TextView tvSkuBadge = cardView.findViewById(R.id.tvSkuBadge);
            TextView tvCategoryBadge = cardView.findViewById(R.id.tvCategoryBadge);
            TextView tvStockUnits = cardView.findViewById(R.id.tvStockUnits);
            TextView tvSellingPrice = cardView.findViewById(R.id.tvSellingPrice);
            TextView tvCostPrice = cardView.findViewById(R.id.tvCostPrice);
            TextView tvProfitMargin = cardView.findViewById(R.id.tvProfitMargin);
            TextView tvThumbFallback = cardView.findViewById(R.id.tvThumbFallback);
            ShapeableImageView ivProductThumb = cardView.findViewById(R.id.ivProductThumb);
            ImageView btnQuickEdit = cardView.findViewById(R.id.btnQuickEdit);
            ImageView btnQuickDelete = cardView.findViewById(R.id.btnQuickDelete);

            // Populate Text Data
            tvProductName.setText(product.getName());
            tvProductIdBadge.setText(getString(R.string.format_product_id_badge, product.getId()));

            String brandInfo = product.getBrand();
            if (!product.getSupplier().isEmpty()) {
                brandInfo = (brandInfo.isEmpty() ? "" : brandInfo + " • ") + product.getSupplier();
            }
            if (brandInfo.isEmpty()) brandInfo = getString(R.string.msg_standard_product);
            tvProductBrand.setText(brandInfo);

            // Availability Badge
            String avail = product.getAvailability();
            tvAvailabilityBadge.setText(avail);
            if ("Out of Stock".equalsIgnoreCase(avail)) {
                tvAvailabilityBadge.setBackgroundResource(R.drawable.bg_badge_rose);
                tvAvailabilityBadge.setTextColor(requireContext().getColor(R.color.danger_rose));
            } else if ("Low Stock".equalsIgnoreCase(avail)) {
                tvAvailabilityBadge.setBackgroundResource(R.drawable.bg_badge_amber);
                tvAvailabilityBadge.setTextColor(requireContext().getColor(R.color.warning_amber));
            } else {
                tvAvailabilityBadge.setBackgroundResource(R.drawable.bg_badge_emerald);
                tvAvailabilityBadge.setTextColor(requireContext().getColor(R.color.success_emerald));
            }

            // Status Badge
            String status = product.getStatus();
            tvStatusBadge.setText(status);
            if ("Inactive".equalsIgnoreCase(status)) {
                tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_amber);
                tvStatusBadge.setTextColor(requireContext().getColor(R.color.warning_amber));
            } else if ("Discontinued".equalsIgnoreCase(status)) {
                tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_rose);
                tvStatusBadge.setTextColor(requireContext().getColor(R.color.danger_rose));
            } else {
                tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_violet);
                tvStatusBadge.setTextColor(requireContext().getColor(R.color.accent_violet));
            }

            tvSkuBadge.setText(product.getSku());
            tvCategoryBadge.setText(product.getCategory());

            // Stock Count text
            if (product.getStockQuantity() <= 0) {
                tvStockUnits.setText(R.string.format_zero_units_left);
                tvStockUnits.setTextColor(requireContext().getColor(R.color.danger_rose));
            } else if (product.getStockQuantity() <= product.getLowStockThreshold()) {
                tvStockUnits.setText(getString(R.string.format_low_stock_units, product.getStockQuantity()));
                tvStockUnits.setTextColor(requireContext().getColor(R.color.warning_amber));
            } else {
                tvStockUnits.setText(getString(R.string.format_in_stock_units, product.getStockQuantity()));
                tvStockUnits.setTextColor(requireContext().getColor(R.color.text_primary));
            }

            // Financials
            tvSellingPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getSellingPrice()));
            tvCostPrice.setText(String.format(Locale.getDefault(), "Cost: $%.2f", product.getPurchasePrice()));

            double unitProfit = product.getProfitPerUnit();
            double marginPercent = product.getProfitMarginPercent();
            tvProfitMargin.setText(String.format(Locale.getDefault(), "%s%.1f%% ($%.2f)",
                    (unitProfit >= 0 ? "+" : "-"), marginPercent, Math.abs(unitProfit)));

            if (unitProfit < 0) {
                tvProfitMargin.setTextColor(requireContext().getColor(R.color.danger_rose));
            } else if (marginPercent < 15.0) {
                tvProfitMargin.setTextColor(requireContext().getColor(R.color.warning_amber));
            } else {
                tvProfitMargin.setTextColor(requireContext().getColor(R.color.success_emerald));
            }

            // Product Thumbnail Image or Initial Fallback
            boolean photoLoaded = false;
            if (!product.getImageUri().isEmpty()) {
                try {
                    Uri photoUri = Uri.parse(product.getImageUri());
                    ivProductThumb.setImageURI(photoUri);
                    if (ivProductThumb.getDrawable() != null) {
                        ivProductThumb.setVisibility(View.VISIBLE);
                        tvThumbFallback.setVisibility(View.GONE);
                        photoLoaded = true;
                    }
                } catch (Exception ignored) {}
            }

            if (!photoLoaded) {
                ivProductThumb.setVisibility(View.GONE);
                tvThumbFallback.setVisibility(View.VISIBLE);
                String initial = (product.getName() != null && !product.getName().trim().isEmpty())
                        ? product.getName().trim().substring(0, 1).toUpperCase(Locale.getDefault())
                        : "P";
                tvThumbFallback.setText(initial);
            }

            // Action: Edit Product
            View.OnClickListener editAction = v -> {
                Bundle productBundle = new Bundle();
                productBundle.putInt("id", product.getId());
                productBundle.putString("name", product.getName());
                productBundle.putString("sku", product.getSku());
                productBundle.putString("barcode", product.getBarcode());
                productBundle.putString("category", product.getCategory());
                productBundle.putString("brand", product.getBrand());
                productBundle.putString("description", product.getDescription());
                productBundle.putDouble("purchase_price", product.getPurchasePrice());
                productBundle.putDouble("selling_price", product.getSellingPrice());
                productBundle.putDouble("discount_percent", product.getDiscountPercent());
                productBundle.putInt("stock_quantity", product.getStockQuantity());
                productBundle.putInt("low_stock_threshold", product.getLowStockThreshold());
                productBundle.putString("supplier", product.getSupplier());
                productBundle.putString("status", product.getStatus());
                productBundle.putString("availability", product.getAvailability());
                productBundle.putString("image_uri", product.getImageUri());
                productBundle.putString("date_added", product.getDateAdded());
                productBundle.putString("last_updated", product.getLastUpdated());

                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToAdmission(productBundle);
                }
            };

            cardView.setOnClickListener(editAction);
            btnQuickEdit.setOnClickListener(editAction);

            // Action: Delete Product
            btnQuickDelete.setOnClickListener(v -> {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Delete Product #" + product.getId())
                        .setMessage("Are you sure you want to delete '" + product.getName() + "' (SKU: " + product.getSku() + ") from inventory?")
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("Delete", (dialog, which) -> {
                            boolean deleted = databaseHelper.deleteProduct(product.getId());
                            if (deleted) {
                                showSnackbar("🗑️ Product #" + product.getId() + " Deleted", true);
                                loadDashboardAndProducts();
                            } else {
                                showSnackbar("❌ Failed to delete product #" + product.getId(), false);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });

            containerRecords.addView(cardView);
        }
    }

    private void showSnackbar(String message, boolean isSuccess) {
        if (getView() == null) return;
        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
        if (!isSuccess) {
            snackbar.setBackgroundTint(requireContext().getColor(R.color.danger_rose));
        } else {
            snackbar.setBackgroundTint(requireContext().getColor(R.color.primary_navy));
        }
        snackbar.show();
    }
}
