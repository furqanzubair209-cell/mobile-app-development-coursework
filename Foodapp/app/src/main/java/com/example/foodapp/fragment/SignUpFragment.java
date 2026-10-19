package com.example.foodapp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodapp.MainActivity;
import com.example.foodapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpFragment extends Fragment {

    private TextInputEditText etName, etEmail, etPassword, etConfirmPassword;
    private TextInputLayout tilName, tilEmail, tilPassword, tilConfirmPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etName = view.findViewById(R.id.etSignUpName);
        etEmail = view.findViewById(R.id.etSignUpEmail);
        etPassword = view.findViewById(R.id.etSignUpPassword);
        etConfirmPassword = view.findViewById(R.id.etSignUpConfirmPassword);

        tilName = view.findViewById(R.id.tilSignUpName);
        tilEmail = view.findViewById(R.id.tilSignUpEmail);
        tilPassword = view.findViewById(R.id.tilSignUpPassword);
        tilConfirmPassword = view.findViewById(R.id.tilSignUpConfirmPassword);

        Button btnSignUp = view.findViewById(R.id.btnSignUp);
        TextView tvGoToLogin = view.findViewById(R.id.tvGoToLogin);

        if (etConfirmPassword != null) {
            etConfirmPassword.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handleSignUp();
                    return true;
                }
                return false;
            });
        }

        if (btnSignUp != null) {
            btnSignUp.setOnClickListener(v -> handleSignUp());
        }

        if (tvGoToLogin != null) {
            tvGoToLogin.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).showLoginFragment();
                }
            });
        }
    }

    private void handleSignUp() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";
        String confirmPassword = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString().trim() : "";

        tilName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilConfirmPassword.setError(null);

        if (TextUtils.isEmpty(name)) {
            tilName.setError("Full Name is required");
            return;
        }

        if (TextUtils.isEmpty(email) || !email.contains("@")) {
            tilEmail.setError("Valid email is required");
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 4) {
            tilPassword.setError("Password must be at least 4 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            tilConfirmPassword.setError("Passwords do not match");
            return;
        }

        if (isAdded() && getContext() != null) {
            Toast.makeText(getContext(), "\uD83C\uDF89 Account created successfully!", Toast.LENGTH_SHORT).show();
        }

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).onAuthSuccess();
        }
    }
}
