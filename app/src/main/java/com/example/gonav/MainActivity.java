package com.example.gonav;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


import com.example.gonav.databinding.ActivityMainBinding;

import org.jetbrains.annotations.Nullable;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

            setSupportActionBar(binding.appBarMain.toolbar);
            /**
            binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
             */


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.mapsFragment, R.id.nav_home, R.id.ParkingFragment, R.id.CollegeFragment, R.id.InterestFragment,
                R.id.foodFragment,R.id.FloorPlanFragment,R.id.EmergencyHotlineFragment, R.id.about, R.id.LegendsFragment)
                .setOpenableLayout(drawer)
                .build();
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationView, navController);

        // Get the building name from the intent extras
        String building = getIntent().getStringExtra("building");
        if (building != null) {
            Bundle args = new Bundle();
            args.putString("building", building);
            navController.navigate(R.id.FloorPlanFragment, args);
        }


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}