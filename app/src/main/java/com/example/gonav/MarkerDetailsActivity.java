package com.example.gonav;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.gonav.databinding.ActivityMarkerDetailsBinding;
import com.google.android.material.snackbar.Snackbar;

public class MarkerDetailsActivity extends AppCompatActivity {

    private String title;
    private String description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_details);

        ImageView imageView = findViewById(R.id.image);
        TextView descriptionView = findViewById(R.id.description);


        ///
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                finish();
            }
        });

        /////////////
        // Get the marker's title and description from the intent extras
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");


        // Set the image and description based on the title (customize as needed)
        // college markers
        if (title.equals("Mariano marcos state university")) {
            imageView.setImageResource(R.drawable.mmsu_rice_paddy);
            descriptionView.setText(description);
        } else if (title.equals("College of Arts and Sciences - CAS")) {
            imageView.setImageResource(R.drawable.cas_1);
            descriptionView.setText(description);
        } else if (title.equals("Communication Arts Building - CAB")) {
            imageView.setImageResource(R.drawable.cab_pano1);
            descriptionView.setText(description);
        } else if (title.equals("College of Engineering - COE")) {
            imageView.setImageResource(R.drawable.coe01_1);
            descriptionView.setText(description);

        } else if (title.equals("College of Agriculture, Food, and Sustainable Development - CAFSD")) {
            imageView.setImageResource(R.drawable.cafsd_pano);
            descriptionView.setText(description);
        } else if (title.equals("College of Business Economics and Accountancy - CBEA")) {
            imageView.setImageResource(R.drawable.cbeapano);
            descriptionView.setText(description);
        } else if (title.equals("College of Health and Sciences - CHS")) {
            imageView.setImageResource(R.drawable.chs_1);
            descriptionView.setText(description);
        } else if (title.equals("College of Law - COL")) {
            imageView.setImageResource(R.drawable.col1);
            descriptionView.setText(description);
        } else if (title.equals("College of Medicine - COM")) {
            imageView.setImageResource(R.drawable.com_pano);
            descriptionView.setText(description);
        } else if (title.equals("College of Teacher Education - CTE")) {
            imageView.setImageResource(R.drawable.ctepic);
            descriptionView.setText(description);
        } else if (title.equals("Graduate School")) {
            imageView.setImageResource(R.drawable.gs1);
            descriptionView.setText(description);
        } else if (title.equals("College of Industrial Technology - CIT")) {
            imageView.setImageResource(R.drawable.gs1);
            descriptionView.setText(description);
        } else if (title.equals("College of Aquatic Sciences and Applied Technology - CASAT")) {
            imageView.setImageResource(R.drawable.casatp1);
            descriptionView.setText(description);
        } else if (title.equals("MMSU COED Garden")) {
            imageView.setImageResource(R.drawable.garden1_1);
            descriptionView.setText(description);
        } else if (title.equals("Don Mariano Marcos y Rubio Monument and Historical Marker")) {
            imageView.setImageResource(R.drawable.statue_admin);
            descriptionView.setText(description);
        } else if (title.equals("MMSU President's Farm")) {
            imageView.setImageResource(R.drawable.president_farm);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Twin Gate")) {
            imageView.setImageResource(R.drawable.twingate);
            descriptionView.setText(description);
        } else if (title.equals("Sunken Garden")) {
            imageView.setImageResource(R.drawable.sunken_garden);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Rice Paddy Art")) {
            imageView.setImageResource(R.drawable.riceart);
            descriptionView.setText(description);
        } else if (title.equals("COE Parking Area")) {
            imageView.setImageResource(R.drawable.coe_park2);
            descriptionView.setText(description);
        } else if (title.equals("CAB Parking Area")) {
            imageView.setImageResource(R.drawable.coe_park2);
            descriptionView.setText(description);
        } else if (title.equals("CAS Parking Area")) {
            imageView.setImageResource(R.drawable.cas_parking);
            descriptionView.setText(description);
        } else if (title.equals("ADMIN Parking Area 1")) {
            imageView.setImageResource(R.drawable.admin_parking1);
            descriptionView.setText(description);
        } else if (title.equals("ADMIN Parking Area 2")) {
            imageView.setImageResource(R.drawable.admin_parking_2);
            descriptionView.setText(description);
        } else if (title.equals("ADMIN Parking Area 3")) {
            imageView.setImageResource(R.drawable.admin_parking3);
            descriptionView.setText(description);
        } else if (title.equals("CAFSD Parking Area")) {
            imageView.setImageResource(R.drawable.cafsd_parking);
            descriptionView.setText(description);
        } else if (title.equals("COL Parking Area")) {
            imageView.setImageResource(R.drawable.col_park);
            descriptionView.setText(description);
        } else if (title.equals("Teatro Ilocandia Parking Area 1")) {
            imageView.setImageResource(R.drawable.teatro_park_1);
            descriptionView.setText(description);
        } else if (title.equals("Teatro Ilocandia Parking Area 2")) {
            imageView.setImageResource(R.drawable.teatro_park2);
            descriptionView.setText(description);
        } else if (title.equals("CBEA Parking Area")) {
            imageView.setImageResource(R.drawable.cbea_park_side);
            descriptionView.setText(description);
        } else if (title.equals("CHS Parking Area")) {
            imageView.setImageResource(R.drawable.chs_park1);
            descriptionView.setText(description);
        } else if (title.equals("COM Parking Area")) {
            imageView.setImageResource(R.drawable.com_park);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Hostel Parking Area")) {
            imageView.setImageResource(R.drawable.hostel_parking);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Library Parking Area")) {
            imageView.setImageResource(R.drawable.library_park_1);
            descriptionView.setText(description);
        } else if (title.equals("MMSU COEDS Dormitory")) {
            imageView.setImageResource(R.drawable.coeds_dorm_park);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Library")) {
            imageView.setImageResource(R.drawable.library_1);
            descriptionView.setText(description);
        } else if (title.equals("Teatro Ilocandia")) {
            imageView.setImageResource(R.drawable.teatroilocandia_1);
            descriptionView.setText(description);
        } else if (title.equals("Food Processing Innovation Center")) {
            imageView.setImageResource(R.drawable.foods_fpic);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Technology Park")) {
            imageView.setImageResource(R.drawable.foods_fpic);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Fitness Center")) {
            imageView.setImageResource(R.drawable.fitnesscenter_1);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Covered Court")) {
            imageView.setImageResource(R.drawable.cc_1);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Clinic/Infirmary")) {
            imageView.setImageResource(R.drawable.infirmary_2);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Chapel")) {
            imageView.setImageResource(R.drawable.cbea_chapel_pano);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Cooperative")) {
            imageView.setImageResource(R.drawable.mmsucoopic);
            descriptionView.setText(description);
        } else if (title.equals("SIP Hub (Social Innovations and Partnership)")) {
            imageView.setImageResource(R.drawable.siphubpic);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Swimming Pool Area")) {
            imageView.setImageResource(R.drawable.poolpic);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Oval")) {
            imageView.setImageResource(R.drawable.oval_1);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Hostel and Function Hall")) {
            imageView.setImageResource(R.drawable.hostel_2);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Admin Canteen")) {
            imageView.setImageResource(R.drawable.canteen_1);
            descriptionView.setText(description);
        } else if (title.equals("CAS SC Canteen")) {
            imageView.setImageResource(R.drawable.canteen_1);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Administration Building")) {
            imageView.setImageResource(R.drawable.adminpic);
            descriptionView.setText(description);
        } else if (title.equals("MMSU Mansion")) {
            imageView.setImageResource(R.drawable.mansion_1);
            descriptionView.setText(description);
        } else if (title.equals("UTC")) {
            imageView.setImageResource(R.drawable.board_examiner_viewer);
            descriptionView.setText(description);
        } else if (title.equals("Sphynx base")) {
            imageView.setImageResource(R.drawable.sphynxpic);
            descriptionView.setText(description);
        }

        ///////////



        //pass
        Button floorplansButton = findViewById(R.id.floorplans_button);
        floorplansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String building = ""; // Set the building based on the title (customize as needed)
                if (title != null) {
                    if (title.equals("Mariano marcos state university")) {
                        building = "MMSU";
                    } else if (title.equals("College of Arts and Sciences - CAS")) {
                        building = "CAS";
                    } else if (title.equals("Communication Arts Building - CAB")) {
                        building = "CAB";
                    }else if (title.equals("College of Engineering - COE")) {
                        building = "COE";

                    } else if (title.equals("College of Agriculture, Food, and Sustainable Development - CAFSD")) {
                        building = "CAFSD";
                    } else if (title.equals("College of Business Economics and Accountancy - CBEA")) {
                        building = "CBEA";
                    } else if (title.equals("College of Health and Sciences - CHS")) {
                        building = "CHS";
                    } else if (title.equals("MMSU Administration Building")) {
                        building = "ADMIN";
                    }
                    // add more else if statements for other buildings

                    // Create a bundle and put the building name as an argument
                    Bundle args = new Bundle();
                    args.putString("building", building);

                    Intent intent = new Intent(MarkerDetailsActivity.this, MainActivity.class);
                    intent.putExtra("building", building);
                    startActivity(intent);
                }}
        });

    }

}


