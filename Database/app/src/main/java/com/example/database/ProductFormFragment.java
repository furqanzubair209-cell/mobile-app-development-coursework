package com.example.database;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;

import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;
import java.util.Random;

public class ProductFormFragment extends Fragment {

    private NestedScrollView fragmentScrollView;

    // Edit Mode Banner
    private MaterialCardView cardEditMode;
    private TextView tvEditModeTitle, tvEditModeDates;
    private MaterialButton btnCancelEdit;

    // Top Icon-Only Action Bar Buttons (Logo only, no text)
    private MaterialButton btnInsert, btnUpdate, btnDelete, btnView, btnClear;

    // Image Upload Views
    private ShapeableImageView ivProductPreview;
    private TextView tvImageStatus;
    private MaterialButton btnSelectImage, btnRemoveImage;
    private String selectedImageUri = "";

    // Section A: Product Info
    private EditText etProductId, etProductName, etProductSku, etBrand, etDescription;
    private TextInputLayout tilProductId, tilProductName, tilProductSku, tilCategory;
    private AutoCompleteTextView actvCategory;
    private MaterialButton btnGenerateSku;

    // Section B: Pricing & Margins
    private EditText etPurchasePrice, etSellingPrice, etDiscountPercent;
    private TextInputLayout tilPurchasePrice, tilSellingPrice, tilDiscountPercent;
    private TextView tvMarginStatus, tvProfitPerUnit, tvProfitMarginPercent, tvEffectivePrice;

    // Section C: Inventory Management
    private EditText etStockQuantity, etLowStockThreshold, etSupplier, etBarcode;
    private TextInputLayout tilStockQuantity, tilLowStockThreshold, tilSupplier, tilBarcode;
    private TextView tvLiveStockBadge;

    // Section D: Lifecycle Status
    private RadioButton rbActive, rbInactive, rbDiscontinued;

    private TextView tvTotalCount;

    private DatabaseHelper databaseHelper;
    private ActivityResultLauncher<String> photoPickerLauncher;

    private int editingProductId = -1;
    private String dateAddedBackup = "";

    public static final String[] CATEGORY_OPTIONS = {
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

    public ProductFormFragment() {
        // Required empty constructor
    }

    public static ProductFormFragment newInstance() {
        return new ProductFormFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null && isAdded()) {
                        try {
                            requireContext().getContentResolver().takePersistableUriPermission(
                                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        } catch (Exception ignored) {}

                        selectedImageUri = uri.toString();
                        displaySelectedImage(uri);
                        showSnackbar("📷 Product photo attached successfully", true);
                    }
                }
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentScrollView = view.findViewById(R.id.fragmentScrollView);

        if (getActivity() instanceof MainActivity) {
            databaseHelper = ((MainActivity) getActivity()).getDatabaseHelper();
        } else {
            databaseHelper = new DatabaseHelper(requireContext());
        }

        initializeViews(view);
        setupCategoryDropdown();
        setupDynamicCalculators();
        setupImagePicker();
        setupActionListeners();

        updateCountBadge();

        // Check if pre-filled arguments passed for editing
        if (getArguments() != null && !getArguments().isEmpty()) {
            populateFormFromArguments(getArguments());
        }
    }

    private void initializeViews(View view) {
        // Header & Edit Banner
        tvTotalCount = view.findViewById(R.id.tvTotalCount);
        cardEditMode = view.findViewById(R.id.cardEditMode);
        tvEditModeTitle = view.findViewById(R.id.tvEditModeTitle);
        tvEditModeDates = view.findViewById(R.id.tvEditModeDates);
        btnCancelEdit = view.findViewById(R.id.btnCancelEdit);

        // TOP ICON-ONLY ACTION BAR BUTTONS
        btnInsert = view.findViewById(R.id.btnInsert);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnView = view.findViewById(R.id.btnView);
        btnClear = view.findViewById(R.id.btnClear);

        // Image Section
        ivProductPreview = view.findViewById(R.id.ivProductPreview);
        tvImageStatus = view.findViewById(R.id.tvImageStatus);
        btnSelectImage = view.findViewById(R.id.btnSelectImage);
        btnRemoveImage = view.findViewById(R.id.btnRemoveImage);

        // Section A: Product Info
        etProductId = view.findViewById(R.id.etProductId);
        etProductName = view.findViewById(R.id.etProductName);
        etProductSku = view.findViewById(R.id.etProductSku);
        etBrand = view.findViewById(R.id.etBrand);
        etDescription = view.findViewById(R.id.etDescription);
        actvCategory = view.findViewById(R.id.actvCategory);
        btnGenerateSku = view.findViewById(R.id.btnGenerateSku);

        tilProductId = view.findViewById(R.id.tilProductId);
        tilProductName = view.findViewById(R.id.tilProductName);
        tilProductSku = view.findViewById(R.id.tilProductSku);
        tilCategory = view.findViewById(R.id.tilCategory);

        // Section B: Pricing & Margins
        etPurchasePrice = view.findViewById(R.id.etPurchasePrice);
        etSellingPrice = view.findViewById(R.id.etSellingPrice);
        etDiscountPercent = view.findViewById(R.id.etDiscountPercent);
        tilPurchasePrice = view.findViewById(R.id.tilPurchasePrice);
        tilSellingPrice = view.findViewById(R.id.tilSellingPrice);
        tilDiscountPercent = view.findViewById(R.id.tilDiscountPercent);

        tvMarginStatus = view.findViewById(R.id.tvMarginStatus);
        tvProfitPerUnit = view.findViewById(R.id.tvProfitPerUnit);
        tvProfitMarginPercent = view.findViewById(R.id.tvProfitMarginPercent);
        tvEffectivePrice = view.findViewById(R.id.tvEffectivePrice);

        // Section C: Stock & Inventory
        etStockQuantity = view.findViewById(R.id.etStockQuantity);
        etLowStockThreshold = view.findViewById(R.id.etLowStockThreshold);
        etSupplier = view.findViewById(R.id.etSupplier);
        etBarcode = view.findViewById(R.id.etBarcode);

        tilStockQuantity = view.findViewById(R.id.tilStockQuantity);
        tilLowStockThreshold = view.findViewById(R.id.tilLowStockThreshold);
        tilSupplier = view.findViewById(R.id.tilSupplier);
        tilBarcode = view.findViewById(R.id.tilBarcode);
        tvLiveStockBadge = view.findViewById(R.id.tvLiveStockBadge);

        // Default threshold
        etLowStockThreshold.setText("5");

        // Section D: Lifecycle Status
        rbActive = view.findViewById(R.id.rbActive);
        rbInactive = view.findViewById(R.id.rbInactive);
        rbDiscontinued = view.findViewById(R.id.rbDiscontinued);

        // Soft keyboard auto-scroll for fields so focused input is placed cleanly at top of screen
        View.OnFocusChangeListener autoScrollListener = (v, hasFocus) -> {
            if (hasFocus) {
                scrollToView(v);
            }
        };

        if (etBarcode != null) etBarcode.setOnFocusChangeListener(autoScrollListener);
        if (etSupplier != null) etSupplier.setOnFocusChangeListener(autoScrollListener);
        if (etLowStockThreshold != null) etLowStockThreshold.setOnFocusChangeListener(autoScrollListener);
        if (etStockQuantity != null) etStockQuantity.setOnFocusChangeListener(autoScrollListener);
        if (etDiscountPercent != null) etDiscountPercent.setOnFocusChangeListener(autoScrollListener);
        if (etPurchasePrice != null) etPurchasePrice.setOnFocusChangeListener(autoScrollListener);
        if (etSellingPrice != null) etSellingPrice.setOnFocusChangeListener(autoScrollListener);
        if (etDescription != null) etDescription.setOnFocusChangeListener(autoScrollListener);
    }

    private void scrollToView(View view) {
        if (fragmentScrollView == null || view == null) return;
        fragmentScrollView.postDelayed(() -> {
            try {
                Rect rect = new Rect();
                view.getDrawingRect(rect);
                fragmentScrollView.offsetDescendantRectToMyCoords(view, rect);
                fragmentScrollView.smoothScrollTo(0, Math.max(0, rect.top - 120));
            } catch (Exception ignored) {}
        }, 200);
    }

    private void setupCategoryDropdown() {
        if (getContext() == null || actvCategory == null) return;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                CATEGORY_OPTIONS
        );
        actvCategory.setAdapter(adapter);
        actvCategory.setOnClickListener(v -> actvCategory.showDropDown());
    }

    private void setupImagePicker() {
        View.OnClickListener pickPhotoAction = v -> {
            try {
                photoPickerLauncher.launch("image/*");
            } catch (Exception e) {
                showSnackbar("Unable to open photo picker", false);
            }
        };

        btnSelectImage.setOnClickListener(pickPhotoAction);
        ivProductPreview.setOnClickListener(pickPhotoAction);

        btnRemoveImage.setOnClickListener(v -> {
            selectedImageUri = "";
            ivProductPreview.setImageResource(R.drawable.ic_photo);
            tvImageStatus.setText(getString(R.string.status_no_image));
            btnRemoveImage.setVisibility(View.GONE);
            showSnackbar("Image removed", true);
        });
    }

    private void displaySelectedImage(Uri uri) {
        try {
            ivProductPreview.setImageURI(uri);
            if (ivProductPreview.getDrawable() != null) {
                tvImageStatus.setText(getString(R.string.status_custom_photo));
                btnRemoveImage.setVisibility(View.VISIBLE);
                return;
            }
        } catch (Exception ignored) {}

        ivProductPreview.setImageResource(R.drawable.ic_photo);
        tvImageStatus.setText(getString(R.string.status_default_photo));
        btnRemoveImage.setVisibility(View.GONE);
    }

    /**
     * Real-time listeners for live financial calculation and live stock badge
     */
    private void setupDynamicCalculators() {
        TextWatcher priceWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                recalculateFinancials();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        etPurchasePrice.addTextChangedListener(priceWatcher);
        etSellingPrice.addTextChangedListener(priceWatcher);
        etDiscountPercent.addTextChangedListener(priceWatcher);

        TextWatcher stockWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                updateLiveStockBadge();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        etStockQuantity.addTextChangedListener(stockWatcher);
        etLowStockThreshold.addTextChangedListener(stockWatcher);
    }

    private void recalculateFinancials() {
        double cost = parseDoubleSafe(etPurchasePrice.getText().toString());
        double selling = parseDoubleSafe(etSellingPrice.getText().toString());
        double discount = parseDoubleSafe(etDiscountPercent.getText().toString());

        double effectiveSelling = selling;
        if (discount > 0 && discount <= 100) {
            effectiveSelling = selling - (selling * (discount / 100.0));
        }

        double unitProfit = effectiveSelling - cost;
        double marginPercent = effectiveSelling > 0 ? (unitProfit / effectiveSelling) * 100.0 : 0.0;

        tvProfitPerUnit.setText(String.format(Locale.getDefault(), (unitProfit >= 0 ? "+$%.2f" : "-$%.2f"), Math.abs(unitProfit)));
        tvProfitMarginPercent.setText(String.format(Locale.getDefault(), "%.1f%%", marginPercent));
        tvEffectivePrice.setText(String.format(Locale.getDefault(), "$%.2f", effectiveSelling));

        if (unitProfit < 0) {
            tvMarginStatus.setText(getString(R.string.margin_negative));
            tvMarginStatus.setBackgroundResource(R.drawable.bg_badge_rose);
            tvMarginStatus.setTextColor(requireContext().getColor(R.color.danger_rose));
            tvProfitPerUnit.setTextColor(requireContext().getColor(R.color.danger_rose));
        } else if (marginPercent < 15.0) {
            tvMarginStatus.setText(getString(R.string.margin_low));
            tvMarginStatus.setBackgroundResource(R.drawable.bg_badge_amber);
            tvMarginStatus.setTextColor(requireContext().getColor(R.color.warning_amber));
            tvProfitPerUnit.setTextColor(requireContext().getColor(R.color.warning_amber));
        } else {
            tvMarginStatus.setText(getString(R.string.margin_healthy));
            tvMarginStatus.setBackgroundResource(R.drawable.bg_badge_emerald);
            tvMarginStatus.setTextColor(requireContext().getColor(R.color.success_emerald));
            tvProfitPerUnit.setTextColor(requireContext().getColor(R.color.success_emerald));
        }
    }

    private void updateLiveStockBadge() {
        int stock = parseIntSafe(etStockQuantity.getText().toString());
        int threshold = parseIntSafe(etLowStockThreshold.getText().toString());
        if (threshold <= 0) threshold = 5;

        String availability = Product.computeAvailability(stock, threshold);
        tvLiveStockBadge.setText(availability);

        if ("Out of Stock".equalsIgnoreCase(availability)) {
            tvLiveStockBadge.setBackgroundResource(R.drawable.bg_badge_rose);
            tvLiveStockBadge.setTextColor(requireContext().getColor(R.color.danger_rose));
        } else if ("Low Stock".equalsIgnoreCase(availability)) {
            tvLiveStockBadge.setBackgroundResource(R.drawable.bg_badge_amber);
            tvLiveStockBadge.setTextColor(requireContext().getColor(R.color.warning_amber));
        } else {
            tvLiveStockBadge.setBackgroundResource(R.drawable.bg_badge_emerald);
            tvLiveStockBadge.setTextColor(requireContext().getColor(R.color.success_emerald));
        }
    }

    private void setupActionListeners() {
        // Cancel Edit Mode
        btnCancelEdit.setOnClickListener(v -> {
            clearForm();
            showSnackbar("Exited Edit Mode", true);
        });

        // SKU Generator
        btnGenerateSku.setOnClickListener(v -> {
            String category = actvCategory.getText().toString().trim();
            String prefix = "SKU";
            if (!category.isEmpty()) {
                String clean = category.replaceAll("[^a-zA-Z]", "").toUpperCase(Locale.getDefault());
                prefix = "SKU-" + (clean.length() >= 3 ? clean.substring(0, 3) : clean);
            } else {
                prefix = "SKU-PRD";
            }
            int randomNum = 1000 + new Random().nextInt(9000);
            String generatedSku = prefix + "-" + randomNum;
            etProductSku.setText(generatedSku);
            tilProductSku.setError(null);
            showSnackbar("✨ Generated unique code: " + generatedSku, true);
        });

        // 1. SAVE / ENROLL PRODUCT BUTTON (Green Icon Only)
        btnInsert.setOnClickListener(v -> handleSaveProduct());

        // 2. UPDATE BUTTON (Primary Blue Icon Only)
        btnUpdate.setOnClickListener(v -> handleUpdateProduct());

        // 3. DELETE BUTTON (Red Icon Only)
        btnDelete.setOnClickListener(v -> handleDeleteProduct());

        // 4. VIEW RECORDS / DASHBOARD BUTTON (Icon Only)
        btnView.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToRecords();
            }
        });

        // 5. CLEAR / RESET BUTTON (Icon Only)
        btnClear.setOnClickListener(v -> {
            clearForm();
            showSnackbar("Form Cleared", true);
        });
    }

    /**
     * 1. SAVE PRODUCT (INSERT NEW RECORD)
     */
    private void handleSaveProduct() {
        clearErrors();

        String name = etProductName.getText().toString().trim();
        String sku = etProductSku.getText().toString().trim();
        String category = actvCategory.getText().toString().trim();
        String brand = etBrand.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String purchaseStr = etPurchasePrice.getText().toString().trim();
        String sellingStr = etSellingPrice.getText().toString().trim();
        String discountStr = etDiscountPercent.getText().toString().trim();
        String stockStr = etStockQuantity.getText().toString().trim();
        String thresholdStr = etLowStockThreshold.getText().toString().trim();
        String supplier = etSupplier.getText().toString().trim();
        String barcode = etBarcode.getText().toString().trim();
        String status = getSelectedStatus();

        if (TextUtils.isEmpty(name)) {
            tilProductName.setError("Product name is required");
            etProductName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(sku)) {
            tilProductSku.setError("SKU code is required");
            etProductSku.requestFocus();
            return;
        }

        if (databaseHelper.isSkuExists(sku, 0)) {
            tilProductSku.setError("SKU '" + sku + "' already exists! Please use a unique SKU.");
            etProductSku.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(category)) {
            tilCategory.setError("Please select a product category");
            return;
        }

        if (TextUtils.isEmpty(purchaseStr)) {
            tilPurchasePrice.setError("Cost price is required");
            etPurchasePrice.requestFocus();
            return;
        }

        double purchasePrice = parseDoubleSafe(purchaseStr);
        if (purchasePrice < 0) {
            tilPurchasePrice.setError("Cost price cannot be negative");
            etPurchasePrice.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(sellingStr)) {
            tilSellingPrice.setError("Selling price is required");
            etSellingPrice.requestFocus();
            return;
        }

        double sellingPrice = parseDoubleSafe(sellingStr);
        if (sellingPrice <= 0) {
            tilSellingPrice.setError("Selling price must be greater than $0.00");
            etSellingPrice.requestFocus();
            return;
        }

        double discountPercent = parseDoubleSafe(discountStr);
        if (discountPercent < 0 || discountPercent > 100) {
            tilDiscountPercent.setError("Discount must be between 0% and 100%");
            etDiscountPercent.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(stockStr)) {
            tilStockQuantity.setError("Stock quantity is required");
            etStockQuantity.requestFocus();
            return;
        }

        int stockQuantity = parseIntSafe(stockStr);
        if (stockQuantity < 0) {
            tilStockQuantity.setError("Stock cannot be negative");
            etStockQuantity.requestFocus();
            return;
        }

        int threshold = parseIntSafe(thresholdStr);
        if (threshold <= 0) threshold = 5;

        String availability = Product.computeAvailability(stockQuantity, threshold);
        String today = Product.getCurrentDateString();

        Product newProduct = new Product(
                0, name, sku, barcode, category, brand, description,
                purchasePrice, sellingPrice, discountPercent, stockQuantity, threshold,
                supplier, status, availability, selectedImageUri, today, today
        );

        long newId = databaseHelper.insertProduct(newProduct);
        if (newId != -1) {
            showSnackbar("🎉 Product Enrolled Successfully! (ID #" + newId + ")", true);
            clearForm();
            updateCountBadge();
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToRecords();
            }
        } else {
            showSnackbar("❌ Product entry failed. Please check inputs and retry.", false);
        }
    }

    /**
     * 2. UPDATE PRODUCT RECORD
     */
    private void handleUpdateProduct() {
        clearErrors();

        String idStr = etProductId.getText().toString().trim();
        int targetId = parseIntSafe(idStr);
        if (targetId <= 0 && editingProductId > 0) {
            targetId = editingProductId;
        }

        if (targetId <= 0) {
            tilProductId.setError("Enter Product ID to update");
            etProductId.requestFocus();
            return;
        }

        String name = etProductName.getText().toString().trim();
        String sku = etProductSku.getText().toString().trim();
        String category = actvCategory.getText().toString().trim();
        String brand = etBrand.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String purchaseStr = etPurchasePrice.getText().toString().trim();
        String sellingStr = etSellingPrice.getText().toString().trim();
        String discountStr = etDiscountPercent.getText().toString().trim();
        String stockStr = etStockQuantity.getText().toString().trim();
        String thresholdStr = etLowStockThreshold.getText().toString().trim();
        String supplier = etSupplier.getText().toString().trim();
        String barcode = etBarcode.getText().toString().trim();
        String status = getSelectedStatus();

        if (TextUtils.isEmpty(name)) {
            tilProductName.setError("Product name is required");
            etProductName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(sku)) {
            tilProductSku.setError("SKU code is required");
            etProductSku.requestFocus();
            return;
        }

        if (databaseHelper.isSkuExists(sku, targetId)) {
            tilProductSku.setError("SKU '" + sku + "' already exists for another product!");
            etProductSku.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(category)) {
            tilCategory.setError("Please select a product category");
            return;
        }

        double purchasePrice = parseDoubleSafe(purchaseStr);
        if (purchasePrice < 0) {
            tilPurchasePrice.setError("Cost price cannot be negative");
            etPurchasePrice.requestFocus();
            return;
        }

        double sellingPrice = parseDoubleSafe(sellingStr);
        if (sellingPrice <= 0) {
            tilSellingPrice.setError("Selling price must be greater than $0.00");
            etSellingPrice.requestFocus();
            return;
        }

        double discountPercent = parseDoubleSafe(discountStr);
        int stockQuantity = parseIntSafe(stockStr);
        int threshold = parseIntSafe(thresholdStr);
        if (threshold <= 0) threshold = 5;

        String availability = Product.computeAvailability(stockQuantity, threshold);
        String today = Product.getCurrentDateString();

        Product updatedProduct = new Product(
                targetId, name, sku, barcode, category, brand, description,
                purchasePrice, sellingPrice, discountPercent, stockQuantity, threshold,
                supplier, status, availability, selectedImageUri,
                dateAddedBackup.isEmpty() ? today : dateAddedBackup, today
        );

        boolean success = databaseHelper.updateProduct(updatedProduct);
        if (success) {
            showSnackbar("✨ Product #" + targetId + " updated successfully!", true);
            clearForm();
            updateCountBadge();
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToRecords();
            }
        } else {
            showSnackbar("⚠️ Update failed: Product ID #" + targetId + " not found", false);
        }
    }

    /**
     * 3. DELETE PRODUCT
     */
    private void handleDeleteProduct() {
        clearErrors();

        String idStr = etProductId.getText().toString().trim();
        int targetId = parseIntSafe(idStr);
        if (targetId <= 0 && editingProductId > 0) {
            targetId = editingProductId;
        }

        if (targetId <= 0) {
            tilProductId.setError("Enter Product ID to delete");
            etProductId.requestFocus();
            return;
        }

        final int idToDelete = targetId;

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete Product #" + idToDelete)
                .setMessage("Are you sure you want to permanently delete Product ID #" + idToDelete + " from the inventory database?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Delete", (dialog, which) -> {
                    boolean deleted = databaseHelper.deleteProduct(idToDelete);
                    if (deleted) {
                        showSnackbar("🗑️ Product #" + idToDelete + " Deleted", true);
                        clearForm();
                        updateCountBadge();
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).navigateToRecords();
                        }
                    } else {
                        showSnackbar("❌ Delete failed: Product ID #" + idToDelete + " not found", false);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void populateFormFromArguments(Bundle args) {
        if (args == null || !isAdded()) return;

        editingProductId = args.getInt("id", -1);
        if (editingProductId <= 0) {
            String idStr = args.getString("id", "-1");
            editingProductId = parseIntSafe(idStr);
        }

        if (editingProductId <= 0) return;

        String name = args.getString("name", "");
        String sku = args.getString("sku", "");
        String barcode = args.getString("barcode", "");
        String category = args.getString("category", "");
        String brand = args.getString("brand", "");
        String description = args.getString("description", "");
        double purchase = args.getDouble("purchase_price", 0.0);
        double selling = args.getDouble("selling_price", 0.0);
        double discount = args.getDouble("discount_percent", 0.0);
        int stock = args.getInt("stock_quantity", 0);
        int threshold = args.getInt("low_stock_threshold", 5);
        String supplier = args.getString("supplier", "");
        String status = args.getString("status", "Active");
        String img = args.getString("image_uri", "");
        dateAddedBackup = args.getString("date_added", "");
        String lastUpdated = args.getString("last_updated", "");

        etProductId.setText(String.valueOf(editingProductId));
        etProductName.setText(name);
        etProductSku.setText(sku);
        etBarcode.setText(barcode);
        etBrand.setText(brand);
        etDescription.setText(description);
        etPurchasePrice.setText(String.format(Locale.getDefault(), "%.2f", purchase));
        etSellingPrice.setText(String.format(Locale.getDefault(), "%.2f", selling));
        etDiscountPercent.setText(discount > 0 ? String.format(Locale.getDefault(), "%.1f", discount) : "");
        etStockQuantity.setText(String.valueOf(stock));
        etLowStockThreshold.setText(String.valueOf(threshold));
        etSupplier.setText(supplier);

        setupCategoryDropdown();
        if (!category.isEmpty()) {
            actvCategory.setText(category, false);
        } else {
            actvCategory.setText(CATEGORY_OPTIONS[0], false);
        }

        // Status
        if ("Inactive".equalsIgnoreCase(status)) {
            rbInactive.setChecked(true);
        } else if ("Discontinued".equalsIgnoreCase(status)) {
            rbDiscontinued.setChecked(true);
        } else {
            rbActive.setChecked(true);
        }

        // Photo
        selectedImageUri = img != null ? img : "";
        if (!selectedImageUri.isEmpty()) {
            displaySelectedImage(Uri.parse(selectedImageUri));
        } else {
            ivProductPreview.setImageResource(R.drawable.ic_photo);
            tvImageStatus.setText(getString(R.string.status_no_image));
            btnRemoveImage.setVisibility(View.GONE);
        }

        // Edit Banner UI changes
        cardEditMode.setVisibility(View.VISIBLE);
        tvEditModeTitle.setText(getString(R.string.format_editing_product_title, editingProductId, name));
        tvEditModeDates.setText(getString(R.string.format_editing_product_dates,
                dateAddedBackup.isEmpty() ? "Recent" : dateAddedBackup,
                lastUpdated.isEmpty() ? "Recent" : lastUpdated));

        recalculateFinancials();
        updateLiveStockBadge();
        clearErrors();

        if (fragmentScrollView != null) {
            fragmentScrollView.post(() -> fragmentScrollView.smoothScrollTo(0, 0));
        }

        showSnackbar("✏️ Loaded Product #" + editingProductId + " for editing", true);
    }

    private String getSelectedStatus() {
        if (rbInactive.isChecked()) return "Inactive";
        if (rbDiscontinued.isChecked()) return "Discontinued";
        return "Active";
    }

    private void clearForm() {
        editingProductId = -1;
        dateAddedBackup = "";

        etProductId.setText("");
        etProductName.setText("");
        etProductSku.setText("");
        etBarcode.setText("");
        etBrand.setText("");
        etDescription.setText("");
        etPurchasePrice.setText("");
        etSellingPrice.setText("");
        etDiscountPercent.setText("");
        etStockQuantity.setText("");
        etLowStockThreshold.setText("5");
        etSupplier.setText("");

        setupCategoryDropdown();
        actvCategory.setText(CATEGORY_OPTIONS[0], false);

        rbActive.setChecked(true);

        selectedImageUri = "";
        ivProductPreview.setImageResource(R.drawable.ic_photo);
        tvImageStatus.setText(getString(R.string.status_no_image));
        btnRemoveImage.setVisibility(View.GONE);

        cardEditMode.setVisibility(View.GONE);

        recalculateFinancials();
        updateLiveStockBadge();
        clearErrors();
    }

    private void clearErrors() {
        tilProductId.setError(null);
        tilProductName.setError(null);
        tilProductSku.setError(null);
        tilCategory.setError(null);
        tilPurchasePrice.setError(null);
        tilSellingPrice.setError(null);
        tilDiscountPercent.setError(null);
        tilStockQuantity.setError(null);
        tilLowStockThreshold.setError(null);
        tilSupplier.setError(null);
        tilBarcode.setError(null);
    }

    private void updateCountBadge() {
        if (databaseHelper != null && tvTotalCount != null) {
            int count = databaseHelper.getRecordsCount();
            tvTotalCount.setText(count == 1 ? "1 Product" : getString(R.string.format_kpi_products_count, count));
        }
    }

    private double parseDoubleSafe(String val) {
        if (val == null || val.trim().isEmpty()) return 0.0;
        try {
            return Double.parseDouble(val.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    private int parseIntSafe(String val) {
        if (val == null || val.trim().isEmpty()) return 0;
        try {
            return Integer.parseInt(val.trim());
        } catch (Exception e) {
            return 0;
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
