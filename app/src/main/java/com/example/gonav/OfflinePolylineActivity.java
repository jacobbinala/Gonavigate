package com.example.gonav;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

public class OfflinePolylineActivity extends AppCompatActivity {

    private AutoCompleteTextView startPointAutoComplete;
    private AutoCompleteTextView endPointAutoComplete;
    private LatLng selectedStartPoint;
    private LatLng selectedEndPoint;
    private ArrayList<LatLng> markerLatLngs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_polyline);

        startPointAutoComplete = findViewById(R.id.startPointAutoComplete);
        endPointAutoComplete = findViewById(R.id.endPointAutoComplete);

        // Get the list of marker names and LatLng from the intent
        ArrayList<String> markerNames = getIntent().getStringArrayListExtra("markerNames");
        markerLatLngs = getIntent().getParcelableArrayListExtra("markerLatLngs");

        // Create an ArrayAdapter using the marker names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, markerNames);

        // Apply the adapter to the AutoCompleteTextViews
        startPointAutoComplete.setAdapter(adapter);
        endPointAutoComplete.setAdapter(adapter);

        // Set onItemClickListener for the AutoCompleteTextViews
        startPointAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = parent.getItemAtPosition(position).toString();
            int selectedIndex = markerNames.indexOf(selectedItem);
            selectedStartPoint = markerLatLngs.get(selectedIndex);
        });

        endPointAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = parent.getItemAtPosition(position).toString();
            int selectedIndex = markerNames.indexOf(selectedItem);
            selectedEndPoint = markerLatLngs.get(selectedIndex);
        });



        // Add the "Go" button and its OnClickListener
        Button goButton = findViewById(R.id.offlinePolylineButton);
        goButton.setOnClickListener(v -> {
            if (selectedStartPoint != null && selectedEndPoint != null) {
                // Save the selected start and end points to SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("OfflinePolyline", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putFloat("startLatitude", (float) selectedStartPoint.latitude);
                editor.putFloat("startLongitude", (float) selectedStartPoint.longitude);
                editor.putFloat("endLatitude", (float) selectedEndPoint.latitude);
                editor.putFloat("endLongitude", (float) selectedEndPoint.longitude);

                editor.apply();

                // Finish the activity to return to the map
                finish();
            } else {
                Toast.makeText(OfflinePolylineActivity.this, "Please select both a start and end point.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
