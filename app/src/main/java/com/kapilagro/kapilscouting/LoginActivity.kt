package com.kapilagro.kapilscouting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private ViewFlipper viewFlipper;

    // Welcome Screen Views
    private Button btnLogin;
    private Button btnSignup;

    // Login Form Views
    private ImageButton btnBackLogin;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private TextView tvForgotPassword;
    private Button btnLoginSubmit;
    private TextView tvGoToSignup;

    // Sign Up Form Views
    private ImageButton btnBackSignup;
    private TextInputEditText etName;
    private TextInputEditText etEmailSignup;
    private TextInputEditText etPasswordSignup;
    private TextInputEditText etConfirmPassword;
    private Button btnSignupSubmit;
    private TextView tvGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "LoginActivity started");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize ViewFlipper
        viewFlipper = findViewById(R.id.view_flipper);

        // Initialize welcome screen views
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.btn_signup);

        // Set click listeners for welcome screen
        btnLogin.setOnClickListener(v -> showLoginForm());
        btnSignup.setOnClickListener(v -> showSignupForm());
    }

    private void showLoginForm() {
        if (viewFlipper.getChildCount() < 2) {
            View loginView = getLayoutInflater().inflate(R.layout.activity_login_form, null);
            viewFlipper.addView(loginView);

            // Initialize login form views
            btnBackLogin = loginView.findViewById(R.id.btn_back_login);
            etEmail = loginView.findViewById(R.id.et_email);
            etPassword = loginView.findViewById(R.id.et_password);
            tvForgotPassword = loginView.findViewById(R.id.tv_forgot_password);
            btnLoginSubmit = loginView.findViewById(R.id.btn_login_submit);
            tvGoToSignup = loginView.findViewById(R.id.tv_go_to_signup);

            // Set login form click listeners
            btnBackLogin.setOnClickListener(v -> showWelcomeScreen());
            tvForgotPassword.setOnClickListener(v -> handleForgotPassword());
            btnLoginSubmit.setOnClickListener(v -> handleLogin());
            tvGoToSignup.setOnClickListener(v -> showSignupForm());
        }

        viewFlipper.setDisplayedChild(1);
    }

    private void showSignupForm() {
        if (viewFlipper.getChildCount() < 3) {
            View signupView = getLayoutInflater().inflate(R.layout.activity_signup_form, null);
            viewFlipper.addView(signupView);

            // Initialize signup form views
            btnBackSignup = signupView.findViewById(R.id.btn_back_signup);
            etName = signupView.findViewById(R.id.et_name);
            etEmailSignup = signupView.findViewById(R.id.et_email_signup);
            etPasswordSignup = signupView.findViewById(R.id.et_password_signup);
            etConfirmPassword = signupView.findViewById(R.id.et_confirm_password);
            btnSignupSubmit = signupView.findViewById(R.id.btn_signup_submit);
            tvGoToLogin = signupView.findViewById(R.id.tv_go_to_login);

            // Set signup form click listeners
            btnBackSignup.setOnClickListener(v -> showWelcomeScreen());
            btnSignupSubmit.setOnClickListener(v -> handleSignup());
            tvGoToLogin.setOnClickListener(v -> showLoginForm());
        }

        viewFlipper.setDisplayedChild(2);
    }

    private void showWelcomeScreen() {
        viewFlipper.setDisplayedChild(0);
    }

    private void handleLogin() {
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate credentials (simplified for testing)
        if (validateCredentials(email, password)) {
            Log.d(TAG, "Login successful - marking user as logged in");

            // Save login status
            SharedPreferences sharedPreferences = getSharedPreferences("loginpreference", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isUserLoggedIn", true);
            editor.apply();

            // Navigate to MainActivity
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }

    // For testing, we'll accept any non-empty credentials
    private boolean validateCredentials(String email, String password) {
        // Replace with actual validation logic in production
        return !email.isEmpty() && !password.isEmpty();
    }

    private void handleSignup() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String email = etEmailSignup.getText() != null ? etEmailSignup.getText().toString().trim() : "";
        String password = etPasswordSignup.getText() != null ? etPasswordSignup.getText().toString().trim() : "";
        String confirmPassword = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString().trim() : "";

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simulate successful signup
        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show();
        showLoginForm();
    }

    private void handleForgotPassword() {
        Toast.makeText(this, "Forgot password functionality", Toast.LENGTH_SHORT).show();
    }
}