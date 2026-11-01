package com.example.database;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

public class RecordsFragment extends Fragment {

    private TextView tvTotalCount, tvRecordsStatus, tvResult;
    private LinearLayout containerRecords, layoutEmptyState;
    private MaterialButton btnGotoAdmission;

    private DatabaseHelper databaseHelper;

    public RecordsFragment() {
        // Required empty public constructor
    }

    public static RecordsFragment newInstance() {
        return new RecordsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_records, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof MainActivity) {
            databaseHelper = ((MainActivity) getActivity()).getDatabaseHelper();
        } else {
            databaseHelper = new DatabaseHelper(requireContext());
        }

        tvTotalCount = view.findViewById(R.id.tvTotalCount);
        tvRecordsStatus = view.findViewById(R.id.tvRecordsStatus);
        tvResult = view.findViewById(R.id.tvResult);
        containerRecords = view.findViewById(R.id.containerRecords);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        btnGotoAdmission = view.findViewById(R.id.btnGotoAdmission);

        btnGotoAdmission.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToAdmission(null);
            }
        });

        updateCountBadge();
        viewAllRecords();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCountBadge();
        viewAllRecords();
    }

    private void updateCountBadge() {
        if (databaseHelper != null && tvTotalCount != null) {
            int count = databaseHelper.getRecordsCount();
            tvTotalCount.setText(count + (count == 1 ? " Student" : " Students"));
        }
    }

    public void viewAllRecords() {
        if (databaseHelper == null || containerRecords == null) return;

        Cursor cursor = databaseHelper.getAllData();
        containerRecords.removeAllViews();

        if (cursor == null || cursor.getCount() == 0) {
            if (tvResult != null) tvResult.setText("No Records Found");
            layoutEmptyState.setVisibility(View.VISIBLE);
            containerRecords.setVisibility(View.GONE);
            tvRecordsStatus.setText("0 Records");
            if (cursor != null) {
                cursor.close();
            }
            return;
        }

        layoutEmptyState.setVisibility(View.GONE);
        containerRecords.setVisibility(View.VISIBLE);
        int total = cursor.getCount();
        tvRecordsStatus.setText(total + (total == 1 ? " Record" : " Records"));

        StringBuilder textData = new StringBuilder();
        LayoutInflater inflater = LayoutInflater.from(requireContext());

        while (cursor.moveToNext()) {
            final String id = cursor.getString(0);
            final String username = cursor.getString(1);
            final String email = cursor.getString(2);
            final String phone = cursor.getColumnCount() > 3 && cursor.getString(3) != null ? cursor.getString(3) : "";
            final String password = cursor.getColumnCount() > 4 && cursor.getString(4) != null ? cursor.getString(4) : "";
            final String gender = cursor.getColumnCount() > 5 && cursor.getString(5) != null ? cursor.getString(5) : "Male";
            final String course = cursor.getColumnCount() > 6 && cursor.getString(6) != null ? cursor.getString(6) : "BS Computer Science (BSCS)";
            final String facilities = cursor.getColumnCount() > 7 && cursor.getString(7) != null ? cursor.getString(7) : "General Tech";
            final String department = cursor.getColumnCount() > 8 && cursor.getString(8) != null ? cursor.getString(8) : "Department of IT";
            final String imageUriStr = cursor.getColumnCount() > 9 && cursor.getString(9) != null ? cursor.getString(9) : "";

            textData.append("ID: ").append(id).append("\n")
                    .append("Username: ").append(username).append("\n")
                    .append("Email: ").append(email).append("\n")
                    .append("Phone: ").append(phone).append("\n")
                    .append("Gender: ").append(gender).append("\n")
                    .append("Course: ").append(course).append("\n")
                    .append("Facilities: ").append(facilities).append("\n")
                    .append("-------------------------\n");

            View cardView = inflater.inflate(R.layout.item_student_card, containerRecords, false);

            TextView tvStudentName = cardView.findViewById(R.id.tvStudentName);
            TextView tvStudentIdBadge = cardView.findViewById(R.id.tvStudentIdBadge);
            TextView tvStudentEmail = cardView.findViewById(R.id.tvStudentEmail);
            TextView tvGenderBadge = cardView.findViewById(R.id.tvGenderBadge);
            TextView tvCourseBadge = cardView.findViewById(R.id.tvCourseBadge);
            TextView tvDepartmentBadge = cardView.findViewById(R.id.tvDepartmentBadge);
            TextView tvFacilitiesBadge = cardView.findViewById(R.id.tvFacilitiesBadge);
            TextView tvAvatarInitial = cardView.findViewById(R.id.tvAvatarInitial);
            ShapeableImageView ivStudentCardPhoto = cardView.findViewById(R.id.ivStudentCardPhoto);
            FrameLayout layoutAvatar = cardView.findViewById(R.id.layoutAvatar);
            ImageView btnQuickEdit = cardView.findViewById(R.id.btnQuickEdit);
            ImageView btnQuickDelete = cardView.findViewById(R.id.btnQuickDelete);

            tvStudentName.setText(username);
            tvStudentIdBadge.setText("#" + id);
            tvStudentEmail.setText(email);
            tvGenderBadge.setText(gender);
            tvCourseBadge.setText(course);
            tvDepartmentBadge.setText(department);
            tvFacilitiesBadge.setText(facilities);

            // Profile photo or initial
            if (imageUriStr != null && !imageUriStr.trim().isEmpty()) {
                try {
                    Uri photoUri = Uri.parse(imageUriStr);
                    ivStudentCardPhoto.setImageURI(photoUri);
                    ivStudentCardPhoto.setVisibility(View.VISIBLE);
                    tvAvatarInitial.setVisibility(View.GONE);
                } catch (Exception e) {
                    ivStudentCardPhoto.setVisibility(View.GONE);
                    tvAvatarInitial.setVisibility(View.VISIBLE);
                }
            } else {
                ivStudentCardPhoto.setVisibility(View.GONE);
                tvAvatarInitial.setVisibility(View.VISIBLE);
            }

            String initial = (username != null && !username.trim().isEmpty())
                    ? username.trim().substring(0, 1).toUpperCase()
                    : "S";
            tvAvatarInitial.setText(initial);

            // Gender styling
            if ("Female".equalsIgnoreCase(gender)) {
                layoutAvatar.setBackgroundResource(R.drawable.bg_avatar_female);
                tvAvatarInitial.setTextColor(requireContext().getColor(R.color.female_color));
                tvGenderBadge.setBackgroundResource(R.drawable.bg_pill_female);
                tvGenderBadge.setTextColor(requireContext().getColor(R.color.female_color));
            } else if ("Other".equalsIgnoreCase(gender)) {
                layoutAvatar.setBackgroundResource(R.drawable.bg_pill_course);
                tvAvatarInitial.setTextColor(requireContext().getColor(R.color.success_dark));
                tvGenderBadge.setBackgroundResource(R.drawable.bg_pill_course);
                tvGenderBadge.setTextColor(requireContext().getColor(R.color.success_dark));
            } else {
                layoutAvatar.setBackgroundResource(R.drawable.bg_avatar_male);
                tvAvatarInitial.setTextColor(requireContext().getColor(R.color.male_color));
                tvGenderBadge.setBackgroundResource(R.drawable.bg_pill_male);
                tvGenderBadge.setTextColor(requireContext().getColor(R.color.male_color));
            }

            // Quick Edit button opens Admission Form Fragment pre-filled
            View.OnClickListener populateFormAndEdit = v -> {
                Bundle studentBundle = new Bundle();
                studentBundle.putString("id", id);
                studentBundle.putString("username", username);
                studentBundle.putString("email", email);
                studentBundle.putString("phone", phone);
                studentBundle.putString("password", password);
                studentBundle.putString("course", course);
                studentBundle.putString("gender", gender);
                studentBundle.putString("facilities", facilities);
                studentBundle.putString("image_uri", imageUriStr);

                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).navigateToAdmission(studentBundle);
                }
            };

            cardView.setOnClickListener(populateFormAndEdit);
            btnQuickEdit.setOnClickListener(populateFormAndEdit);

            // Quick delete directly from card
            btnQuickDelete.setOnClickListener(v -> {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Delete Student")
                        .setMessage("Are you sure you want to delete " + username + " (ID #" + id + ")?")
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("Delete", (dialog, which) -> {
                            boolean deleted = databaseHelper.deleteData(id);
                            if (deleted) {
                                showSnackbar("🗑️ Student Record #" + id + " Deleted", true);
                                updateCountBadge();
                                viewAllRecords();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });

            containerRecords.addView(cardView);
        }

        cursor.close();
        if (tvResult != null) tvResult.setText(textData.toString());
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
