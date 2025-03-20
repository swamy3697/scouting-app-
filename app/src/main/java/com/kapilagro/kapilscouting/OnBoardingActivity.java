package com.kapilagro.kapilscouting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class OnBoardingActivity extends AppCompatActivity {

    private static final String TAG = "OnBoardingActivity";
    private CountDownTimer countDownTimer;
    private boolean navigationStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_on_boarding);

        Log.d(TAG, "OnBoarding activity started - Starting 5-second countdown");

        // Use CountDownTimer instead of Handler for more reliable timing
        countDownTimer = new CountDownTimer(5000, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Not used, but will be called once at the beginning
                Log.d(TAG, "Timer tick: " + millisUntilFinished + "ms remaining");
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "5-second countdown finished");

                // Add a flag to prevent double navigation
                if (navigationStarted) {
                    Log.d(TAG, "Navigation already started, ignoring");
                    return;
                }

                navigationStarted = true;

                // Mark onboarding as completed after the timer finishes
                SharedPreferences preferences = getSharedPreferences("onBoardingPreference", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isOnBoardingDone", true);
                // Use commit() instead of apply() for immediate write
                editor.commit();

                Log.d(TAG, "Marked onboarding as done, now checking login status");

                if (!isUserLoggedIn()) {
                    // Navigate to LoginActivity if user is not logged in
                    Log.d(TAG, "User not logged in, navigating to LoginActivity");
                    Intent loginIntent = new Intent(OnBoardingActivity.this, LoginActivity.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                    finish();
                } else {
                    // Navigate to MainActivity if user is logged in
                    Log.d(TAG, "User logged in, navigating to MainActivity");
                    Intent mainIntent = new Intent(OnBoardingActivity.this, MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
            }
        };

        // Start the timer
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the timer to avoid memory leaks
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginpreference", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isUserLoggedIn", false);
        Log.d(TAG, "isUserLoggedIn: " + isLoggedIn);
        return isLoggedIn;
    }
}