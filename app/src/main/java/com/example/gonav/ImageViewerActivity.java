package com.example.gonav;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

public class ImageViewerActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        Toolbar toolbar = findViewById(R.id.image_viewer_toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button in the toolbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        imageView = findViewById(R.id.image_viewer_image);
        int imageResourceId = getIntent().getIntExtra("imageResourceId", 0);
        if (imageResourceId != 0) {
            imageView.setImageResource(imageResourceId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle the back button in the toolbar
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
