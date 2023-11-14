package com.example.gonav;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.widget.TextView;


public class TutorialActivity extends AppCompatActivity {

    private Button gotItButton;
    private TextView swipeLeftText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        gotItButton = findViewById(R.id.got_it_button);

        int[] tutorialImages = new int[]{
                R.drawable.tutorial_image_1,
                R.drawable.tutorial_image_2,
                R.drawable.tutorial_image_3,
                R.drawable.tutorial_image_4,
                R.drawable.tutorial_image_5,
                R.drawable.tutorial_image_6

                // Add more images as needed
        };

        TutorialAdapter adapter = new TutorialAdapter(this, tutorialImages);
        viewPager.setAdapter(adapter);

        swipeLeftText = findViewById(R.id.swipe_left_text);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == tutorialImages.length - 1) {
                    gotItButton.setVisibility(View.VISIBLE);
                    swipeLeftText.setVisibility(View.INVISIBLE);
                } else {
                    gotItButton.setVisibility(View.INVISIBLE);
                    swipeLeftText.setVisibility(View.VISIBLE);
                }
            }
        });

        gotItButton.setOnClickListener(view -> {
            // Set the tutorial_shown flag in SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("tutorial_shown", true);
            editor.apply();

            // Start MainActivity
            Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}