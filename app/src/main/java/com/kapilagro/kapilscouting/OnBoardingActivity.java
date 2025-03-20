package com.kapilagro.kapilscouting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_on_boarding);
        SharedPreferences preferences=getSharedPreferences("onBordingPreference",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("isOnBoardingDone",true);
        editor.apply();

       new Handler(Looper.getMainLooper()).postDelayed(()->{
           if (!isUserLoggedIn()) {

               // Start login activity

               Intent loginIntent = new Intent(OnBoardingActivity.this, LoginActivity.class);
               startActivity(loginIntent);
               finish();
               return; // Prevent further execution
           }
           else{
               Intent mainIntent=new Intent(OnBoardingActivity.this,MainActivity.class);
               startActivity(mainIntent);
               finish();

           }

       },2000);
    }

    private boolean isUserLoggedIn() {

        SharedPreferences sharedPreferences = getSharedPreferences("loginpreference", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isUserLoggedIn", false);


    }
}