package com.example.database;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private TabLayout tabLayout;

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
        tabLayout = findViewById(R.id.tabLayout);

        // Tab selection listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    loadFragment(AdmissionFragment.newInstance());
                } else if (tab.getPosition() == 1) {
                    loadFragment(RecordsFragment.newInstance());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Load Admission Form by default
        if (savedInstanceState == null) {
            loadFragment(AdmissionFragment.newInstance());
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(this);
        }
        return databaseHelper;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    /**
     * Switch to Database Records Fragment Tab
     */
    public void navigateToRecords() {
        if (tabLayout != null && tabLayout.getTabAt(1) != null) {
            tabLayout.getTabAt(1).select();
        } else {
            loadFragment(RecordsFragment.newInstance());
        }
    }

    /**
     * Switch to Admission Form Fragment Tab (with optional bundle data for editing)
     */
    public void navigateToAdmission(Bundle studentData) {
        AdmissionFragment fragment = AdmissionFragment.newInstance();
        if (studentData != null) {
            fragment.setArguments(studentData);
        }
        loadFragment(fragment);
        if (tabLayout != null && tabLayout.getTabAt(0) != null) {
            tabLayout.getTabAt(0).select();
        }
    }
}
