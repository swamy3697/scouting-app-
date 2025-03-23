package com.kapilagro.kapilscouting

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        Log.d(TAG, "SplashScreen started")

        // Handler to start the appropriate activity after the splash screen duration
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            if (!this.isOnBoardingDone) {
                // Start onboarding activity
                Log.d(TAG, "Onboarding not done, starting OnBoardingActivity")
                val onBoardingIntent =
                    Intent(this@SplashScreenActivity, OnBoardingActivity::class.java)
                startActivity(onBoardingIntent)
                finish()
            } else if (!this.isUserLoggedIn) {
                // Start login activity
                Log.d(TAG, "Onboarding done, user not logged in, starting LoginActivity")
                val loginIntent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            } else {
                // If onboarding is done and user is logged in, start the main activity
                Log.d(TAG, "Onboarding done, user logged in, starting MainActivity")
                val mainIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        }, SPLASH_DISPLAY_TIME.toLong())
    }

    private val isUserLoggedIn: Boolean
        get() {
            val sharedPreferences =
                getSharedPreferences("loginpreference", MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("isUserLoggedIn", false)
            Log.d(TAG, "isUserLoggedIn: " + isLoggedIn)
            return isLoggedIn
        }

    private val isOnBoardingDone: Boolean
        get() {
            val preferences =
                getSharedPreferences("onBoardingPreference", MODE_PRIVATE)
            val isDone = preferences.getBoolean("isOnBoardingDone", false)
            Log.d(
                TAG,
                "isOnBoardingDone check in SplashScreen: " + isDone
            )
            return isDone
        }

    companion object {
        // Splash screen display time in milliseconds
        private const val SPLASH_DISPLAY_TIME = 1000
        private const val TAG = "SplashScreenActivity"
    }
}