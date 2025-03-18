package com.kapilagro.kapilscouting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen display time in milliseconds
    private static final int SPLASH_DISPLAY_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        // Handler to start the appropriate activity after the splash screen duration
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!isOnBoardingDone()) {
                // Start onboarding activity
                markOnBoardingCompleted();
                Intent onBoardingIntent = new Intent(SplashScreenActivity.this, OnBoardingActivity.class);
                startActivity(onBoardingIntent);
                finish();
                return; // Prevent further execution
            }

            if (userNotLoggedIn()) {
                // Start login activity
                Intent loginIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                return; // Prevent further execution
            }

            // If onboarding is done and user is logged in, start the main activity
            Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }, SPLASH_DISPLAY_TIME);
    }

    private boolean userNotLoggedIn() {
        // Replace with actual login check logic
        return true;
    }

    private boolean isOnBoardingDone() {
        SharedPreferences preferences = getSharedPreferences("onBordingPreference", MODE_PRIVATE);
        return preferences.getBoolean("isOnBoardingDone", false);
    }

    private void markOnBoardingCompleted() {
        SharedPreferences preferences = getSharedPreferences("onBordingPreference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isOnBoardingDone", true);
        editor.apply();
    }
}
