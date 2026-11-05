package com.example.database;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private MaterialButton btnNavProductForm, btnNavDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);

        btnNavProductForm = findViewById(R.id.btnNavProductForm);
        btnNavDashboard = findViewById(R.id.btnNavDashboard);

        // Top Bar Switcher Button Listeners
        btnNavProductForm.setOnClickListener(v -> selectProductFormTab(null));
        btnNavDashboard.setOnClickListener(v -> selectDashboardTab());

        // Load Product Form by default
        if (savedInstanceState == null) {
            selectProductFormTab(null);
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(this);
        }
        return databaseHelper;
    }

    public void selectProductFormTab(Bundle args) {
        ProductFormFragment fragment = ProductFormFragment.newInstance();
        if (args != null) {
            fragment.setArguments(args);
        }
        loadFragment(fragment);

        if (btnNavProductForm != null && btnNavDashboard != null) {
            btnNavProductForm.setBackgroundTintList(getColorStateList(R.color.white));
            btnNavProductForm.setTextColor(getColor(R.color.primary_navy));
            btnNavProductForm.setIconTint(getColorStateList(R.color.primary_navy));

            btnNavDashboard.setBackgroundTintList(null);
            btnNavDashboard.setTextColor(getColor(R.color.white));
            btnNavDashboard.setIconTint(getColorStateList(R.color.white));
        }
    }

    public void selectDashboardTab() {
        loadFragment(ProductCatalogFragment.newInstance());

        if (btnNavProductForm != null && btnNavDashboard != null) {
            btnNavDashboard.setBackgroundTintList(getColorStateList(R.color.white));
            btnNavDashboard.setTextColor(getColor(R.color.primary_navy));
            btnNavDashboard.setIconTint(getColorStateList(R.color.primary_navy));

            btnNavProductForm.setBackgroundTintList(null);
            btnNavProductForm.setTextColor(getColor(R.color.white));
            btnNavProductForm.setIconTint(getColorStateList(R.color.white));
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    /**
     * Switch to Product Catalog / Dashboard Fragment
     */
    public void navigateToRecords() {
        selectDashboardTab();
    }

    /**
     * Switch to Product Form Fragment (with optional pre-filled product bundle data for editing)
     */
    public void navigateToAdmission(Bundle productData) {
        selectProductFormTab(productData);
    }
}
