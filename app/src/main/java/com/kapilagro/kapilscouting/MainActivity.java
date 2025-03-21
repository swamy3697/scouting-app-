package com.kapilagro.kapilscouting;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.kapilagro.kapilscouting.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private boolean isUserLoggedIn;

    // Define your fragments
    private HomeFragment homeFragment = new HomeFragment();
    private ScoutFragment scoutFragment = new ScoutFragment();
    private AdviceFragment adviceFragment = new AdviceFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();

    private UsageFragment usageFragment =new UsageFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // Add this after setContentView in your MainActivity.java
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        }


        // Initialize bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setItemRippleColor(null); // Reset default
        bottomNavigationView.setItemRippleColor(ColorStateList.valueOf(Color.parseColor("#81F574")));

        MenuItem adviceItem=bottomNavigationView.getMenu().findItem(R.id.navigation_advice);
        if(adviceItem!=null){
            android.graphics.drawable.Drawable originalIcon = adviceItem.getIcon().mutate();
            float scaleFactor = 1.3f; // Adjust this value to control how much larger the icon should be

            int width = originalIcon.getIntrinsicWidth();
            int height = originalIcon.getIntrinsicHeight();

            // Create a layer drawable to scale the icon
            android.graphics.drawable.LayerDrawable layerDrawable = new android.graphics.drawable.LayerDrawable(
                    new android.graphics.drawable.Drawable[] { originalIcon });

            // Calculate the padding required to scale the icon
            int horizontalPadding = (int) (-width * (scaleFactor - 1) / 2);
            int verticalPadding = (int) (-height * (scaleFactor - 1) / 2);

            layerDrawable.setLayerInset(0, horizontalPadding, verticalPadding,
                    horizontalPadding, verticalPadding);

            adviceItem.setIcon(layerDrawable);
        }




       // bottomNavigationView.setItemRippleColor(null);


        int[][] states = new int[][] {
                new int[] { android.R.attr.state_checked }, // Selected
                new int[] { -android.R.attr.state_checked }  // Unselected
        };

        int[] colors = new int[] {
                ContextCompat.getColor(this, R.color.primary),
                ContextCompat.getColor(this, R.color.white)
        };
        ColorStateList colorStateList = new ColorStateList(states, colors);

        bottomNavigationView.setItemIconTintList(colorStateList);
        bottomNavigationView.setItemTextColor(colorStateList);





        // Set default fragment
        loadFragment(homeFragment);

        // Check login status
        SharedPreferences sharedPreferences = getSharedPreferences("loginpreference", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        isUserLoggedIn = isUserLoggedIn();
        editor.putBoolean("isloggedin", isUserLoggedIn);
        editor.apply();
    }

    private boolean isUserLoggedIn() {
        // Implement your login check logic here
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.navigation_home) {
            fragment = homeFragment;
        } else if (itemId == R.id.navigation_scout) {
            fragment = scoutFragment;
        } else if (itemId == R.id.navigation_advice) {
            fragment = adviceFragment;
        } else if (itemId == R.id.navigation_settings) {
            fragment = settingsFragment;
        }
        else if (itemId == R.id.navigation_usage) {
            fragment = usageFragment;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
            return true;
        }
        return false;
    }
}