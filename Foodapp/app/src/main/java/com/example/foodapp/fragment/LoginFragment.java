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

public class LoginFragment extends Fragment {

    private TextInputEditText etEmail, etPassword;
    private TextInputLayout tilEmail, tilPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = view.findViewById(R.id.etLoginEmail);
        etPassword = view.findViewById(R.id.etLoginPassword);
        tilEmail = view.findViewById(R.id.tilLoginEmail);
        tilPassword = view.findViewById(R.id.tilLoginPassword);

        Button btnLogin = view.findViewById(R.id.btnLogin);
        TextView tvGoToSignUp = view.findViewById(R.id.tvGoToSignUp);

        if (etPassword != null) {
            etPassword.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handleLogin();
                    return true;
                }
                return false;
            });
        }

        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> handleLogin());
        }

        if (tvGoToSignUp != null) {
            tvGoToSignUp.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).showSignUpFragment();
                }
            });
        }
    }

    private void handleLogin() {
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        tilEmail.setError(null);
        tilPassword.setError(null);

        if (TextUtils.isEmpty(email)) {
            tilEmail.setError("Email is required");
            return;
        }

        if (!email.contains("@")) {
            tilEmail.setError("Please enter a valid email address");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            tilPassword.setError("Password is required");
            return;
        }

        if (password.length() < 4) {
            tilPassword.setError("Password must be at least 4 characters");
            return;
        }

        if (isAdded() && getContext() != null) {
            Toast.makeText(getContext(), "\uD83C\uDF89 Welcome back to Foodie!", Toast.LENGTH_SHORT).show();
        }

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).onAuthSuccess();
        }
    }
}
