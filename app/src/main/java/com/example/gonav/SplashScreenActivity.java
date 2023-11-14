package com.example.gonav;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int splashScreenDuration = 2000;

    private ImageView logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        logoImage = findViewById(R.id.logo_image);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        logoImage.startAnimation(animation);


        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE);
            boolean tutorialShown = sharedPreferences.getBoolean("tutorial_shown", false);

            Intent intent;

            if (!tutorialShown) {
                intent = new Intent(SplashScreenActivity.this, TutorialActivity.class);
            } else {
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            }

            startActivity(intent);
            finish();
        }, splashScreenDuration);
    }
}
