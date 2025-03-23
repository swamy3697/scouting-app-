package com.kapilagro.kapilscouting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"
    private lateinit var viewFlipper: ViewFlipper

    // Welcome Screen Views
    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button

    // Login Form Views
    private lateinit var btnBackLogin: ImageButton
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var tvForgotPassword: TextView
    private lateinit var btnLoginSubmit: Button
    private lateinit var tvGoToSignup: TextView

    // Sign Up Form Views
    private lateinit var btnBackSignup: ImageButton
    private lateinit var etName: TextInputEditText
    private lateinit var etEmailSignup: TextInputEditText
    private lateinit var etPasswordSignup: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var btnSignupSubmit: Button
    private lateinit var tvGoToLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        Log.d(TAG, "LoginActivity started")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize ViewFlipper
        viewFlipper = findViewById(R.id.view_flipper)

        // Initialize welcome screen views
        btnLogin = findViewById(R.id.btn_login)
        btnSignup = findViewById(R.id.btn_signup)

        // Set click listeners for welcome screen
        btnLogin.setOnClickListener { showLoginForm() }
        btnSignup.setOnClickListener { showSignupForm() }
    }

    private fun showLoginForm() {
        if (viewFlipper.childCount < 2) {
            val loginView = layoutInflater.inflate(R.layout.activity_login_form, null)
            viewFlipper.addView(loginView)

            // Initialize login form views
            btnBackLogin = loginView.findViewById(R.id.btn_back_login)
            etEmail = loginView.findViewById(R.id.et_email)
            etPassword = loginView.findViewById(R.id.et_password)
            tvForgotPassword = loginView.findViewById(R.id.tv_forgot_password)
            btnLoginSubmit = loginView.findViewById(R.id.btn_login_submit)
            tvGoToSignup = loginView.findViewById(R.id.tv_go_to_signup)

            // Set login form click listeners
            btnBackLogin.setOnClickListener { showWelcomeScreen() }
            tvForgotPassword.setOnClickListener { handleForgotPassword() }
            btnLoginSubmit.setOnClickListener { handleLogin() }
            tvGoToSignup.setOnClickListener { showSignupForm() }
        }

        viewFlipper.displayedChild = 1
    }

    private fun showSignupForm() {
        if (viewFlipper.childCount < 3) {
            val signupView = layoutInflater.inflate(R.layout.activity_signup_form, null)
            viewFlipper.addView(signupView)

            // Initialize signup form views
            btnBackSignup = signupView.findViewById(R.id.btn_back_signup)
            etName = signupView.findViewById(R.id.et_name)
            etEmailSignup = signupView.findViewById(R.id.et_email_signup)
            etPasswordSignup = signupView.findViewById(R.id.et_password_signup)
            etConfirmPassword = signupView.findViewById(R.id.et_confirm_password)
            btnSignupSubmit = signupView.findViewById(R.id.btn_signup_submit)
            tvGoToLogin = signupView.findViewById(R.id.tv_go_to_login)

            // Set signup form click listeners
            btnBackSignup.setOnClickListener { showWelcomeScreen() }
            btnSignupSubmit.setOnClickListener { handleSignup() }
            tvGoToLogin.setOnClickListener { showLoginForm() }
        }

        viewFlipper.displayedChild = 2
    }

    private fun showWelcomeScreen() {
        viewFlipper.displayedChild = 0
    }

    private fun handleLogin() {
        val email = etEmail.text?.toString()?.trim() ?: ""
        val password = etPassword.text?.toString()?.trim() ?: ""

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Validate credentials (simplified for testing)
        if (validateCredentials(email, password)) {
            Log.d(TAG, "Login successful - marking user as logged in")

            // Save login status
            val sharedPreferences = getSharedPreferences("loginpreference", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isUserLoggedIn", true)
            editor.apply()

            // Navigate to MainActivity
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
        }
    }

    // For testing, we'll accept any non-empty credentials
    private fun validateCredentials(email: String, password: String): Boolean {
        // Replace with actual validation logic in production
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun handleSignup() {
        val name = etName.text?.toString()?.trim() ?: ""
        val email = etEmailSignup.text?.toString()?.trim() ?: ""
        val password = etPasswordSignup.text?.toString()?.trim() ?: ""
        val confirmPassword = etConfirmPassword.text?.toString()?.trim() ?: ""

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Simulate successful signup
        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
        showLoginForm()
    }

    private fun handleForgotPassword() {
        Toast.makeText(this, "Forgot password functionality", Toast.LENGTH_SHORT).show()
    }
}