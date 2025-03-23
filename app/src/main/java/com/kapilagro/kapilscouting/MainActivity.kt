package com.kapilagro.kapilscouting

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var bottomNavigationView: BottomNavigationView
    private var isUserLoggedIn: Boolean = false

    // Define your fragments
    private val homeFragment = HomeFragment()
    private val scoutFragment = ScoutFragment()
    private val adviceFragment = AdviceFragment()
    private val settingsFragment = SettingsFragment()
    private val usageFragment = UsageFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Add this after setContentView in your MainActivity.kt
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        }

        // Initialize bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener(this)
        bottomNavigationView.itemRippleColor = null // Reset default
        bottomNavigationView.itemRippleColor = ColorStateList.valueOf(Color.parseColor("#81F574"))

        val adviceItem = bottomNavigationView.menu.findItem(R.id.navigation_advice)
        adviceItem?.let {
            val originalIcon = it.icon?.mutate()
            val scaleFactor = 1.3f // Adjust this value to control how much larger the icon should be

            val width = originalIcon?.intrinsicWidth
            val height = originalIcon?.intrinsicHeight

            // Create a layer drawable to scale the icon
            val layerDrawable = android.graphics.drawable.LayerDrawable(
                arrayOf(originalIcon)
            )

            // Calculate the padding required to scale the icon
            val horizontalPadding = width.let { it1 -> (-it1!! * (scaleFactor - 1) / 2).toInt() }
            val verticalPadding = height.let { it1 -> (-it1!! * (scaleFactor - 1) / 2).toInt() }

            layerDrawable.setLayerInset(
                0, horizontalPadding, verticalPadding,
                horizontalPadding, verticalPadding
            )

            it.icon = layerDrawable
        }

        val states = arrayOf(
            intArrayOf(android.R.attr.state_checked), // Selected
            intArrayOf(-android.R.attr.state_checked)  // Unselected
        )

        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.primary),
            ContextCompat.getColor(this, R.color.white)
        )
        val colorStateList = ColorStateList(states, colors)

        bottomNavigationView.itemIconTintList = colorStateList
        bottomNavigationView.itemTextColor = colorStateList

        // Set default fragment
        loadFragment(homeFragment)

        // Check login status
        val sharedPreferences = getSharedPreferences("loginpreference", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        isUserLoggedIn = isUserLoggedIn()
        editor.putBoolean("isloggedin", isUserLoggedIn)
        editor.apply()
    }

    private fun isUserLoggedIn(): Boolean {
        // Implement your login check logic here
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment? = when (item.itemId) {
            R.id.navigation_home -> homeFragment
            R.id.navigation_scout -> scoutFragment
            R.id.navigation_advice -> adviceFragment
            R.id.navigation_settings -> settingsFragment
            R.id.navigation_usage -> usageFragment
            else -> null
        }

        return loadFragment(fragment)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            return true
        }
        return false
    }
}