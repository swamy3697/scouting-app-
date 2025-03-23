package com.kapilagro.kapilscouting

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class OnBoardingActivity : AppCompatActivity() {
    private var countDownTimer: CountDownTimer? = null
    private var navigationStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_on_boarding)

        Log.d(TAG, "OnBoarding activity started - Starting 5-second countdown")

        // Use CountDownTimer instead of Handler for more reliable timing
        countDownTimer = object : CountDownTimer(5000, 5000) {
            override fun onTick(millisUntilFinished: Long) {
                // Not used, but will be called once at the beginning
                Log.d(TAG, "Timer tick: " + millisUntilFinished + "ms remaining")
            }

            override fun onFinish() {
                Log.d(TAG, "5-second countdown finished")

                // Add a flag to prevent double navigation
                if (navigationStarted) {
                    Log.d(TAG, "Navigation already started, ignoring")
                    return
                }

                navigationStarted = true

                // Mark onboarding as completed after the timer finishes
                val preferences = getSharedPreferences("onBoardingPreference", MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putBoolean("isOnBoardingDone", true)
                // Use commit() instead of apply() for immediate write
                editor.commit()

                Log.d(TAG, "Marked onboarding as done, now checking login status")

                if (!isUserLoggedIn) {
                    // Navigate to LoginActivity if user is not logged in
                    Log.d(TAG, "User not logged in, navigating to LoginActivity")
                    val loginIntent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(loginIntent)
                    finish()
                } else {
                    // Navigate to MainActivity if user is logged in
                    Log.d(TAG, "User logged in, navigating to MainActivity")
                    val mainIntent = Intent(this@OnBoardingActivity, MainActivity::class.java)
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(mainIntent)
                    finish()
                }
            }
        }

        // Start the timer
        countDownTimer!!.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel the timer to avoid memory leaks
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
    }

    private val isUserLoggedIn: Boolean
        get() {
            val sharedPreferences =
                getSharedPreferences("loginpreference", MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("isUserLoggedIn", false)
            Log.d(TAG, "isUserLoggedIn: " + isLoggedIn)
            return isLoggedIn
        }

    companion object {
        private const val TAG = "OnBoardingActivity"
    }
}