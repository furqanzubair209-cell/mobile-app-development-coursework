package com.example.database;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AdmissionFragment extends Fragment {

    private NestedScrollView fragmentScrollView;

    // Student Form Inputs
    private EditText etUsername, etEmail, etPhone, etId, etPassword;
    private TextInputLayout tilId, tilUsername, tilEmail, tilPhone, tilPassword, tilCourse;
    private AutoCompleteTextView actvCourse;

    // Photo Selection
    private ShapeableImageView ivProfilePreview;
    private MaterialButton btnSelectImage;
    private TextView tvImageStatus;
    private String selectedImageUri = "";

    // 3 Radio Buttons
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale, rbOther;

    // Checkboxes
    private CheckBox cbWeb, cbAI, cbApp, cbCloud, cbTerms;

    // Action Buttons
    private Button btnInsert, btnUpdate, btnDelete, btnView;
    private MaterialButton btnClear;
    private TextView tvResult, tvTotalCount, tvRecordsStatus;
    private LinearLayout containerRecords, layoutEmptyState;

    private DatabaseHelper databaseHelper;
    private ActivityResultLauncher<String> photoPickerLauncher;

    public static final String[] COURSE_OPTIONS = {
            "BS Computer Science (BSCS)",
            "BS Software Engineering (BSSE)",
            "BS Artificial Intelligence (BSAI)",
            "BS Data Science (BSDS)",
            "BS Cyber Security (BSCS)",
            "BS Information Technology (BSIT)"
    };

    public AdmissionFragment() {
        // Required empty public constructor
    }

    public static AdmissionFragment newInstance() {
        return new AdmissionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admission, container, false);
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

        // Form Inputs
        etId = view.findViewById(R.id.etId);
        etUsername = view.findViewById(R.id.etUsername);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        etPassword = view.findViewById(R.id.etPassword);

        tilId = view.findViewById(R.id.tilId);
        tilUsername = view.findViewById(R.id.tilUsername);
        tilEmail = view.findViewById(R.id.tilEmail);
        tilPhone = view.findViewById(R.id.tilPhone);
        tilPassword = view.findViewById(R.id.tilPassword);

        // Dropdown
        tilCourse = view.findViewById(R.id.tilCourse);
        actvCourse = view.findViewById(R.id.actvCourse);

        // Photo Views
        ivProfilePreview = view.findViewById(R.id.ivProfilePreview);
        btnSelectImage = view.findViewById(R.id.btnSelectImage);
        tvImageStatus = view.findViewById(R.id.tvImageStatus);

        // 3 Radio Buttons
        rgGender = view.findViewById(R.id.rgGender);
        rbMale = view.findViewById(R.id.rbMale);
        rbFemale = view.findViewById(R.id.rbFemale);
        rbOther = view.findViewById(R.id.rbOther);

        // Checkboxes
        cbWeb = view.findViewById(R.id.cbWeb);
        cbAI = view.findViewById(R.id.cbAI);
        cbApp = view.findViewById(R.id.cbApp);
        cbCloud = view.findViewById(R.id.cbCloud);
        cbTerms = view.findViewById(R.id.cbTerms);

        // Action Buttons
        btnInsert = view.findViewById(R.id.btnInsert);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnView = view.findViewById(R.id.btnView);
        btnClear = view.findViewById(R.id.btnClear);

        // Containers
        tvResult = view.findViewById(R.id.tvResult);
        tvTotalCount = view.findViewById(R.id.tvTotalCount);
        tvRecordsStatus = view.findViewById(R.id.tvRecordsStatus);
        containerRecords = view.findViewById(R.id.containerRecords);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);

        // Setup Dropdown Adapter
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, COURSE_OPTIONS);
        actvCourse.setAdapter(courseAdapter);
        actvCourse.setText(COURSE_OPTIONS[0], false);

        // Register Photo Picker
        photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        try {
                            requireContext().getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        } catch (Exception ignored) {}
                        selectedImageUri = uri.toString();
                        try {
                            ivProfilePreview.setImageURI(uri);
                            tvImageStatus.setText("Custom photo attached");
                        } catch (Exception e) {
                            ivProfilePreview.setImageResource(R.drawable.ic_person);
                            tvImageStatus.setText("Default avatar selected");
                        }
                        showSnackbar("📷 Profile Picture Selected!", true);
                    }
                }
        );

        btnSelectImage.setOnClickListener(v -> {
            try {
                photoPickerLauncher.launch("image/*");
            } catch (Exception e) {
                showSnackbar("Unable to open photo picker", false);
            }
        });

        // Load records and update total count badge
        updateCountBadge();

        // Check if student data was passed for editing
        if (getArguments() != null) {
            populateFormFromArguments(getArguments());
        }

        // 1. ENROLL STUDENT (INSERT RECORD)
        btnInsert.setOnClickListener(v -> {
            clearErrors();

            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String course = actvCourse.getText().toString().trim();
            String gender = getGender();
            String facilities = getSelectedFacilities();

            if (username.isEmpty()) {
                tilUsername.setError("Please enter student full name");
                etUsername.requestFocus();
                return;
            }

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                tilEmail.setError("Please enter a valid email address");
                etEmail.requestFocus();
                return;
            }

            if (phone.isEmpty()) {
                tilPhone.setError("Please enter contact number");
                etPhone.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                tilPassword.setError("Please enter password");
                etPassword.requestFocus();
                return;
            }

            if (course.isEmpty()) {
                tilCourse.setError("Please select degree program");
                return;
            }

            if (!cbTerms.isChecked()) {
                showSnackbar("Please confirm information accuracy by checking the terms box", false);
                cbTerms.requestFocus();
                return;
            }

            long insertedId = databaseHelper.insertStudent(username, email, phone, password, gender, course, facilities, "Information Tech", selectedImageUri);

            if (insertedId != -1) {
                showSnackbar("🎉 Student Enrolled Successfully!", true);
                clearFields();
                updateCountBadge();

                // Switch to Database Records tab to show newly enrolled student
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToRecords();
                }
            } else {
                showSnackbar("❌ Enrollment Failed. Please try again.", false);
            }
        });

        // 2. VIEW ALL RECORDS (NAVIGATES TO TAB 2)
        btnView.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToRecords();
            }
        });

        // 3. UPDATE RECORD
        btnUpdate.setOnClickListener(v -> {
            clearErrors();

            String id = etId.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String course = actvCourse.getText().toString().trim();
            String gender = getGender();
            String facilities = getSelectedFacilities();

            if (id.isEmpty()) {
                tilId.setError("Enter Student ID to update");
                etId.requestFocus();
                return;
            }

            if (username.isEmpty()) {
                tilUsername.setError("Please enter student name");
                etUsername.requestFocus();
                return;
            }

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                tilEmail.setError("Please enter a valid email address");
                etEmail.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                tilPassword.setError("Please enter password");
                etPassword.requestFocus();
                return;
            }

            boolean updated = databaseHelper.updateData(id, username, email, phone, password, gender, course, facilities, "Information Tech", selectedImageUri);

            if (updated) {
                showSnackbar("✨ Student Record #" + id + " Updated Successfully", true);
                clearFields();
                updateCountBadge();

                // Switch to Database Records tab to view updated record
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToRecords();
                }
            } else {
                showSnackbar("⚠️ Update Failed: Student ID #" + id + " not found", false);
            }
        });

        // 4. DELETE RECORD
        btnDelete.setOnClickListener(v -> {
            clearErrors();

            String id = etId.getText().toString().trim();

            if (id.isEmpty()) {
                tilId.setError("Enter Student ID to delete");
                etId.requestFocus();
                return;
            }

            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Delete Student Record")
                    .setMessage("Are you sure you want to permanently delete Student ID #" + id + "?")
                    .setIcon(R.drawable.ic_delete)
                    .setPositiveButton("Delete", (dialog, which) -> {
                        boolean deleted = databaseHelper.deleteData(id);
                        if (deleted) {
                            showSnackbar("🗑️ Student Record #" + id + " Deleted", true);
                            clearFields();
                            updateCountBadge();
                            if (getActivity() instanceof MainActivity) {
                                ((MainActivity) getActivity()).navigateToRecords();
                            }
                        } else {
                            showSnackbar("❌ Delete Failed: Student ID #" + id + " not found", false);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // 5. RESET FORM
        btnClear.setOnClickListener(v -> {
            clearFields();
            showSnackbar("Form Cleared", true);
        });
    }

    public void populateFormFromArguments(Bundle args) {
        if (args == null || etId == null) return;

        String id = args.getString("id", "");
        String username = args.getString("username", "");
        String email = args.getString("email", "");
        String phone = args.getString("phone", "");
        String password = args.getString("password", "");
        String course = args.getString("course", "");
        String gender = args.getString("gender", "");
        String facilities = args.getString("facilities", "");
        String imageUriStr = args.getString("image_uri", "");

        etId.setText(id);
        etUsername.setText(username);
        etEmail.setText(email);
        etPhone.setText(phone);
        etPassword.setText(password);

        if (!course.isEmpty()) {
            actvCourse.setText(course, false);
        }

        if ("Female".equalsIgnoreCase(gender)) {
            rbFemale.setChecked(true);
        } else if ("Other".equalsIgnoreCase(gender)) {
            rbOther.setChecked(true);
        } else {
            rbMale.setChecked(true);
        }

        cbWeb.setChecked(facilities.contains("Web Dev"));
        cbAI.setChecked(facilities.contains("AI & ML"));
        cbApp.setChecked(facilities.contains("Mobile App"));
        cbCloud.setChecked(facilities.contains("Cyber & Cloud"));
        cbTerms.setChecked(true);

        selectedImageUri = imageUriStr;
        if (imageUriStr != null && !imageUriStr.trim().isEmpty()) {
            try {
                ivProfilePreview.setImageURI(Uri.parse(imageUriStr));
                tvImageStatus.setText("Attached photo loaded");
            } catch (Exception e) {
                ivProfilePreview.setImageResource(R.drawable.ic_person);
                tvImageStatus.setText("Default avatar selected");
            }
        } else {
            ivProfilePreview.setImageResource(R.drawable.ic_person);
            tvImageStatus.setText("Default avatar selected");
        }

        clearErrors();
        showSnackbar("Loaded Student Record #" + id + " into form for editing", true);
    }

    private String getGender() {
        if (rbMale.isChecked()) return "Male";
        if (rbFemale.isChecked()) return "Female";
        if (rbOther.isChecked()) return "Other";
        return "Male";
    }

    private String getSelectedFacilities() {
        ArrayList<String> list = new ArrayList<>();
        if (cbWeb.isChecked()) list.add("Web Dev");
        if (cbAI.isChecked()) list.add("AI & ML");
        if (cbApp.isChecked()) list.add("Mobile App");
        if (cbCloud.isChecked()) list.add("Cyber & Cloud");

        if (list.isEmpty()) return "General Tech";
        return String.join(", ", list);
    }

    private void updateCountBadge() {
        if (databaseHelper != null && tvTotalCount != null) {
            int count = databaseHelper.getRecordsCount();
            tvTotalCount.setText(count + (count == 1 ? " Student" : " Students"));
        }
    }

    private void clearFields() {
        etId.setText("");
        etUsername.setText("");
        etEmail.setText("");
        etPhone.setText("");
        etPassword.setText("");

        actvCourse.setText(COURSE_OPTIONS[0], false);
        selectedImageUri = "";
        ivProfilePreview.setImageResource(R.drawable.ic_person);
        tvImageStatus.setText("Default avatar selected");

        rbMale.setChecked(true);

        cbWeb.setChecked(false);
        cbAI.setChecked(false);
        cbApp.setChecked(false);
        cbCloud.setChecked(false);
        cbTerms.setChecked(false);

        clearErrors();
    }

    private void clearErrors() {
        if (tilId != null) tilId.setError(null);
        if (tilUsername != null) tilUsername.setError(null);
        if (tilEmail != null) tilEmail.setError(null);
        if (tilPhone != null) tilPhone.setError(null);
        if (tilPassword != null) tilPassword.setError(null);
        if (tilCourse != null) tilCourse.setError(null);
    }

    private void showSnackbar(String message, boolean isSuccess) {
        if (getView() == null) return;
        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
        if (!isSuccess) {
            snackbar.setBackgroundTint(requireContext().getColor(R.color.danger));
        } else {
            snackbar.setBackgroundTint(requireContext().getColor(R.color.colorPrimary));
        }
        snackbar.show();
    }
}
