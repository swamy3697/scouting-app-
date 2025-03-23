package com.kapilagro.kapilscouting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen display time in milliseconds
    private static final int SPLASH_DISPLAY_TIME = 1000;
    private static final String TAG = "SplashScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        Log.d(TAG, "SplashScreen started");

        // Handler to start the appropriate activity after the splash screen duration
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!isOnBoardingDone()) {
                // Start onboarding activity
                Log.d(TAG, "Onboarding not done, starting OnBoardingActivity");
                Intent onBoardingIntent = new Intent(SplashScreenActivity.this, OnBoardingActivity.class);
                startActivity(onBoardingIntent);
                finish();
            } else if (!isUserLoggedIn()) {
                // Start login activity
                Log.d(TAG, "Onboarding done, user not logged in, starting LoginActivity");
                Intent loginIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            } else {
                // If onboarding is done and user is logged in, start the main activity
                Log.d(TAG, "Onboarding done, user logged in, starting MainActivity");
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_TIME);
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginpreference", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isUserLoggedIn", false);
        Log.d(TAG, "isUserLoggedIn: " + isLoggedIn);
        return isLoggedIn;
    }

    private boolean isOnBoardingDone() {
        SharedPreferences preferences = getSharedPreferences("onBoardingPreference", MODE_PRIVATE);
        boolean isDone = preferences.getBoolean("isOnBoardingDone", false);
        Log.d(TAG, "isOnBoardingDone check in SplashScreen: " + isDone);
        return isDone;
    }
}