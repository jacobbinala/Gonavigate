package com.example.gonav;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.google.maps.android.PolyUtil;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollegeFragment extends Fragment implements GoogleMap.OnMarkerClickListener {
    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;

    private Polyline mPolyline;

    private ArrayList<Marker> CMarkers = new ArrayList<>();
    ArrayList<String> markerTitles = new ArrayList<>();

    private Map<String, Integer> titleToImageMap = new HashMap<>();


    private Button travelModeButton;
    private LatLng currentLocation;
    private LatLng markerPosition;

    private RadioGroup travelModeRadioGroup;
    private RadioButton radioButtonDriving ;
    private RadioButton radioButtonWalking ;

    private List<Polyline> mPolylines = new ArrayList<>();

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @SuppressLint("PotentialBehaviorOverride")
        @Override
        public void onMapReady(GoogleMap googleMap) {

            mMap = googleMap;

            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            // Set the user's current location as the map's camera position
            LatLng mmsu = new LatLng(18.057369044716616, 120.54805494447848);
            Marker mmsuMarker = mMap.addMarker(new MarkerOptions()
                    .position(mmsu)
                    .title("Mariano marcos state university"));
            CMarkers.add(mmsuMarker);


            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(mmsu) // Set the center of the map
                    .zoom(15) // Set the zoom level
                    .tilt(60) // Set the tilt angle
                    .build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            //////////// <markers //////////////////

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mmsu,18));


            ///Main Buildings / Colleges////////////

            LatLng cas = new LatLng(18.05946368694196, 120.54602490837974);
            Marker casMarker = mMap.addMarker(new MarkerOptions()
                    .position(cas)
                    .title("College of Arts and Sciences - CAS")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.casicon)));
            CMarkers.add(casMarker);


            LatLng cab = new LatLng(18.059618775238043, 120.54496472156747);
            Marker cabMarker = mMap.addMarker(new MarkerOptions()
                    .position(cab)
                    .title("Communication Arts Building - CAB")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.college_pin_svgrepo_com)));
            CMarkers.add(cabMarker);

            LatLng coe = new LatLng(18.059835443499704, 120.54396107250777);
            Marker coeMarker = mMap.addMarker(new MarkerOptions()
                    .position(coe)
                    .title("College of Engineering - COE")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.coeicon)));
            CMarkers.add(coeMarker);

            LatLng cafsd = new LatLng(18.05849410812075, 120.5508014932522);
            Marker cafsdMarker = mMap.addMarker(new MarkerOptions()
                    .position(cafsd)
                    .title("College of Agriculture, Food, and Sustainable Development - CAFSD")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.cafsdicon)));
            CMarkers.add(cafsdMarker);

            LatLng cbea = new LatLng(18.057309367289097, 120.55522418097772);
            Marker cbeaMarker = mMap.addMarker(new MarkerOptions()
                    .position(cbea)
                    .title("College of Business Economics and Accountancy - CBEA")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.cbeaicon)));
            CMarkers.add(cbeaMarker);

            LatLng chs = new LatLng(18.059370379500184, 120.55701354178376);
            Marker chsMarker = mMap.addMarker(new MarkerOptions()
                    .position(chs)
                    .title("College of Health and Sciences - CHS")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.chsicon)));
            CMarkers.add(chsMarker);

            LatLng com = new LatLng(18.05965338182237, 120.55730979543533);
            Marker comMarker = mMap.addMarker(new MarkerOptions()
                    .position(com)
                    .title("College of Medicine - COM")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.comicon)));
            CMarkers.add(comMarker);

            LatLng col = new LatLng(18.056434034515764, 120.55491768204973);
            Marker colMarker = mMap.addMarker(new MarkerOptions()
                    .position(col)
                    .title("College of Law - COL")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.colicon)));
            CMarkers.add(colMarker);

            ///////Others not batac/////

            LatLng cte = new LatLng(18.20700309772826, 120.59341727959091);
            Marker cteMarker = mMap.addMarker(new MarkerOptions()
                    .position(cte)
                    .title("College of Teacher Education - CTE")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.cteicon)));
            CMarkers.add(cteMarker);

            LatLng gs = new LatLng(18.2067995402149, 120.59335312250856);
            Marker gsMarker = mMap.addMarker(new MarkerOptions()
                    .position(gs)
                    .title("Graduate School")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.gsicon)));
            CMarkers.add(gsMarker);

            LatLng cit = new LatLng(18.203198790801117, 120.59346884207308);
            Marker citMarker = mMap.addMarker(new MarkerOptions()
                    .position(cit)
                    .title("College of Industrial Technology - CIT")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.citicon)));
            CMarkers.add(citMarker);

            LatLng casat = new LatLng(17.99143861246757, 120.49606343819279);
            Marker casatMarker = mMap.addMarker(new MarkerOptions()
                    .position(casat)
                    .title("College of Aquatic Sciences and Applied Technology - CIT")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.casaticon)));
            CMarkers.add(casatMarker);

            ///////////////////TWIN Gate



            LatLng tg1 = new LatLng(18.057127907526937, 120.54837117090977);
            Marker tg1Marker = mMap.addMarker(new MarkerOptions()
                    .position(tg1)
                    .title("MMSU Twin Gate")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.gate_sign_svgrepo_com)));
            CMarkers.add(tg1Marker);

            LatLng tg2 = new LatLng(18.05724617899407, 120.54769495407862);
            Marker tg2Marker = mMap.addMarker(new MarkerOptions()
                    .position(tg2)
                    .title("MMSU Twin Gate")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.gate_sign_svgrepo_com)));
            CMarkers.add(tg2Marker);


            // colleges
            markerTitles.add("Mariano marcos state university");
            markerTitles.add("College of Arts and Sciences - CAS");
            markerTitles.add("Communication Arts Building - CAB");
            markerTitles.add("College of Engineering - COE");
            markerTitles.add("College of Agriculture, Food, and Sustainable Development - CAFSD");
            markerTitles.add("College of Business Economics and Accountancy - CBEA");
            markerTitles.add("College of Health and Sciences - CHS");
            markerTitles.add("College of Law - COL");
            markerTitles.add("College of Medicine - COM");
            markerTitles.add("College of Teacher Education - CTE");
            markerTitles.add("Graduate School");
            markerTitles.add("College of Industrial Technology - CIT");
            markerTitles.add("College of Aquatic Sciences and Applied Technology - CASAT");



            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getContext()));



            ////button function passer of description
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(getActivity(), MarkerDetailsActivity.class);
                    String title = marker.getTitle();
                    String description = ""; // Set the description based on the title (customize as needed)

                    if (title.equals("Mariano marcos state university")) {
                        description = "The Mariano Marcos State University (MMSU) was established on January 6, " +
                                "1978 by virtue of Presidential Decree No. 1279 issued by the then President Ferdinand E. Marcos. " +
                                "The decree merged the then Mariano Marcos Memorial College of Science and Technology (MMMCST) " +
                                "in Batac and the Northern Luzon State College (NLSC) in Laoag City, and integrated the college " +
                                "departments of the Ilocos Norte Agricultural College (INAC) and the Ilocos Norte " +
                                "College of Arts and Trades (INCAT)";
                    } else if (title.equals("College of Arts and Sciences - CAS")) {
                        description = "The College of Arts and Sciences (CAS) is the service college of the Mariano Marcos State University (MMSU).  " +
                                "It was created soon after the University was established in 1978 to accomplish the institutional goal of providing " +
                                "quality liberal education to students, " +
                                "in addition to satisfying the need to offer degree programs in the arts and sciences." +
                                "\nDepartment of Computing and Information Sciences\n" +
                                "\nDepartment of Languages and Literature\n" +
                                "\nDepartment of Physical Science\n" +
                                "\nDepartment of Mathematics\n" +
                                "\nDepartment of Biology\n" +
                                "\nDepartment of Physical Education\n" +
                                "\nDepartment of Social Sciences";
                    } else if (title.equals("Communication Arts Building - CAB")) {
                        description = "CAB is a building that houses the Department of Computing and Information Sciences" +
                                ", consisting of two organizations, the Information Technology Society (ITSOC) and " +
                                "Computer Science Society (COMSOC). It serves as the primary location for faculty and " +
                                "students in the department to engage in research, teaching, and learning activities " +
                                "related to computing and information sciences. The building is equipped with state-of-" +
                                "the-art facilities and resources, including computer labs, classrooms, and study spaces," +
                                " to support the department's academic programs and research initiatives. Its mission is " +
                                "to provide a collaborative and dynamic environment that fosters innovation, creativity, " +
                                "and excellence in the field of computing and information sciences.";
                    } else if (title.equals("College of Engineering - COE")) {
                        description = "The College of Engineering (COE) was established in 1986, and it started with three-degree programs in civil, " +
                                "mechanical, and electrical engineering. It has since expanded to include other programs such as metallurgical, mining, " +
                                "computer, chemical, electronics, and communications engineering, as well as ceramics and agricultural engineering. The " +
                                "college has a reputation for providing relevant and quality engineering education, and its graduates consistently perform " +
                                "well in licensure exams, with several programs awarded top performers." +
                                "\n\nDepartment of Electronics Engineering (ECE)\n" +
                                "\nDepartment of Mechanical Engineering (ME)\n" +
                                "\nDepartment of Civil Engineering (CE)\n" +
                                "\nDepartment of Computer Engineering (CPE)\n" +
                                "\nDepartment of Chemical Engineering (ChemE)\n" +
                                "\nDepartment of Ceramics Engineering (CerE)\n" +
                                "\nDepartment of Electrical Engineering (EE)\n" +
                                "\nDepartment of Agricultural and Biosystems Engineering";
                    } else if (title.equals("College of Agriculture, Food, and Sustainable Development - CAFSD")) {
                        description = "The College of Agriculture, Food and Sustainable Development (CAFSD) is a premier college in Mariano " +
                                "Marcos State University with roots tracing back to the Batac Rural High School established in 1918. It was formally " +
                                "established in 1978 and was initially composed of three departments. The college was renamed CAFSD in 2010 to reflect " +
                                "the cohesion of program offerings and its status as a National University of Agriculture." +
                                "\n\nDepartment of Environmental Science\n" +
                                "\nDepartment of Agricultural Sciences\n" +
                                "\nDepartment of Development Communication\n" +
                                "\nDepartment of Forestry\n" +
                                "\nDepartment of Food Technology\n" +
                                "\nDepartment of Agribusiness";
                    } else if (title.equals("College of Business Economics and Accountancy - CBEA")) {
                        description = "The College of Business, Economics and Accountancy (CBEA) was established in 1987 and offers ten degree programs," +
                                " including BSBA, BS Economics, and BS Accounting Technology. The college is committed to providing quality management " +
                                "and business education, community-based research, extension and productivity-oriented programs. " +
                                "\n\nDepartment of Accountancy\n" +
                                "\nDepartment of Business Administration\n" +
                                "\nDepartment of Economics\n" +
                                "\nDepartment of Tourism and Hospitality Management\n";
                    } else if (title.equals("College of Health and Sciences - CHS")) {
                        description = "The College of Health Sciences at the university focuses on providing relevant education to meet the health needs " +
                                "of individuals and communities. Graduates perform well in licensure exams and the college produces globally competitive " +
                                "health professionals. The college has dedicated leaders who uphold excellence." +
                                "\n\nDepartment of Nursing\n" +
                                "\nDepartment of Physical Therapy\n" +
                                "\nDepartment of Pharmacy";
                    } else if (title.equals("College of Law - COL")) {
                        description = "The MMSU College of Law was established in response to the demand for a local law school from the government and " +
                                "private sectors of Ilocos Norte. The program was approved in 2008 and formally came into existence in 2009. Despite " +
                                "being modest, the school consistently produces graduates who perform well in the Bar Examinations and are known for " +
                                "their social awareness, competence, and love for God and country.";
                    } else if (title.equals("College of Medicine - COM")) {
                        description = "Mariano Marcos State University (MMSU) planned to establish a Doctor of Medicine program in the 1990s, but it was " +
                                "postponed when another state university opened a similar program. In 2012, MMSU decided to set up the MMSU College of " +
                                "Medicine to provide better health services to communities in Northern Luzon. After a few years of preparation, the " +
                                "Commission on Higher Education granted the Government Authority to operate the Doctor of Medicine program in 2015. " +
                                "The College graduated its first batch of 14 medical students in 2019, and it was granted a Certificate of Program " +
                                "Compliance by CHED." +
                                "\n\nDepartment of Medicine";
                    } else if (title.equals("College of Teacher Education - CTE")) {
                        description = "The College of Teacher Education (CTE) started as Ilocos Norte Normal School (INNS) in June 1917 offering a " +
                                "four-year curriculum on the secondary level until 1936. Upon the request of the Provincial Board of Ilocos Norte, " +
                                "it was reopened with the offering of the elementary education program.";
                    } else if (title.equals("Graduate School")) {
                        description = "The College of Teacher Education (CTE) started as Ilocos Norte Normal School (INNS) in June 1917 offering a " +
                                "four-year curriculum on the secondary level until 1936. Upon the request of the Provincial Board of Ilocos Norte, " +
                                "it was reopened with the offering of the elementary education program.";
                    } else if (title.equals("College of Industrial Technology - CIT")) {
                        description = "The College of Industrial Technology (CIT) is located in Laoag City, with an external campus in Paoay, " +
                                "Ilocos Norte. It was chosen in 1983 as one of the 21 public technological institutes in the Technical Vocational " +
                                "Education Project (TVEP) of the then Department of Education, Culture, and Sports (DECS) mandated to produce the " +
                                "needed manpower for industrial growth and development in the country.";
                    } else if (title.equals("College of Aquatic Sciences and Applied Technology - CASAT")) {
                        description = "The MMSU – College of Aquatic Sciences and Applied Technology (CASAT) is a satellite College in the Southern " +
                                "portion of Ilocos Norte. Situated at the front of Currimao Bay, the CASAT campus is suitable for the two offerings " +
                                "of the College, namely, Bachelor of Science in Fisheries and Bachelor of Science in Marine Biology. These programs " +
                                "conform to the standards set forth in their respective Policies, Standards, and Guidelines (PSG’s) with some " +
                                "enrichments as approved by the Board of Regents.";
                    } // Add more cases for each marker

                    intent.putExtra("title", title);
                    intent.putExtra("description", description);
                    startActivity(intent);
                }
            });




            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // Request permission to access the location if not already granted
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                return;
            }


            mMap.setMyLocationEnabled(true);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,18));
                            } else {
                                // Handle null location exception
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mmsu,18));
                                Toast.makeText(getContext(), "Unable to get location. Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            mMap.setOnMarkerClickListener(CollegeFragment.this);



            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    // Remove the polyline when the map is clicked
                    for (Polyline polyline : mPolylines) {
                        polyline.remove();
                    }
                    mPolylines.clear();

                    // Set currentLocation and/or markerPosition to null
                    currentLocation = null;
                    markerPosition = null;
                }
            });


        }


    };

    //////////////////
    @Override
    public void onResume() {
        super.onResume();

        // Retrieve the start and end points from SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("OfflinePolyline", Context.MODE_PRIVATE);
        float startLatitude = sharedPreferences.getFloat("startLatitude", 0);
        float startLongitude = sharedPreferences.getFloat("startLongitude", 0);
        float endLatitude = sharedPreferences.getFloat("endLatitude", 0);
        float endLongitude = sharedPreferences.getFloat("endLongitude", 0);

        if (startLatitude != 0 && startLongitude != 0 && endLatitude != 0 && endLongitude != 0) {
            LatLng startLatLng = new LatLng(startLatitude, startLongitude);
            LatLng endLatLng = new LatLng(endLatitude, endLongitude);

            // Draw the polyline using the retrieved start and end points
            drawPolyline(startLatLng, endLatLng,true);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("startLatitude");
            editor.remove("startLongitude");
            editor.remove("endLatitude");
            editor.remove("endLongitude");
            editor.apply();
        }

        if (mPolyline != null) {
            mPolyline.remove();
            mPolyline = null;
        }

    }

    /////////////////////////////////////

///////////for info window
    public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View mWindow;
        private Context mContext;

        public CustomInfoWindowAdapter(Context context) {
            mContext = context;
            mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
        }

        private void renderWindowText(Marker marker, View view) {
            String title = marker.getTitle();
            TextView titleTextView = view.findViewById(R.id.title);

            if (!title.equals("")) {
                titleTextView.setText(title);
            }
        }

        @Override
        public View getInfoWindow(Marker marker) {
            renderWindowText(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            renderWindowText(marker, mWindow);
            return mWindow;
        }
    }
////////////////////////////////////////////////////////////////////////for info window>


    ///////////////for search bar////////////
    private void setupAutoCompleteTextView(ArrayList<String> markerTitles) {

        if (getView() != null) {
            AutoCompleteTextView autoCompleteTextView = getView().findViewById(R.id.autoCompleteTextView);

            if(autoCompleteTextView != null) {
                if(getContext() != null && markerTitles != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, markerTitles);
                    autoCompleteTextView.setAdapter(adapter);
                    //rest of your code...
                } else {
                    Log.e("MapsFragment", "Context or markerTitles is null");
                }
            } else {
                Log.e("MapsFragment", "autoCompleteTextView is null");
            }
        } else {
            Log.e("MapsFragment", "View is null");
        }

        AutoCompleteTextView autoCompleteTextView = getView().findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, markerTitles);
        autoCompleteTextView.setAdapter(adapter);

        // Add clear button and search
        Drawable clearButton = ContextCompat.getDrawable(getContext(), R.drawable.clear);
        autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, clearButton, null);
        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP &&
                        event.getRawX() >= (autoCompleteTextView.getRight() - autoCompleteTextView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    autoCompleteTextView.setText("");
                    return true;
                }
                return false;
            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedMarkerTitle = (String) parent.getItemAtPosition(position);
                Marker selectedMarker = findMarkerByTitle(selectedMarkerTitle);
                if (selectedMarker != null) {
                    LatLng markerPosition = selectedMarker.getPosition();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 18));
                }
            }
        });
    }
//////////////////////////////////

    //// SEARCH ENGINE
    private Marker findMarkerByTitle(String title) {
        for (Marker marker :CMarkers) {
            if (marker.getTitle().equals(title)) {
                return marker;
            }
        }
        return null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // Get the user's current location
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            markerPosition = marker.getPosition();

                            // Draw a polyline between the current location and the clicked marker
                            drawPolyline(currentLocation, markerPosition,false);
                        } else {
                            Toast.makeText(getContext(), "Unable to get location. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return false;
    }

    private TravelMode travelMode = TravelMode.DRIVING; // Set a default value



    // Method to draw a polyline between two LatLng points
    private void drawPolyline(LatLng start, LatLng end, boolean moveCamera) {

        // Remove the previous polyline if it exists
        for (Polyline polyline : mPolylines) {
            polyline.remove();
        }
        mPolylines.clear();


        // GeoApiContext with API key
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyB5aTpq_e4i5nu_tAkLhfeAmDvBBSpuq5E") // Replace with your API key
                .build();

        // Request directions between the start and end points
        DirectionsApiRequest directionsApiRequest = DirectionsApi.newRequest(geoApiContext)
                .origin(new com.google.maps.model.LatLng(start.latitude, start.longitude))
                .destination(new com.google.maps.model.LatLng(end.latitude, end.longitude))
                .mode(travelMode); // You can change the travel mode as needed // remember change to travelMode

        // Execute the request asynchronously and handle the result
        directionsApiRequest.setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                if (result != null && result.routes.length > 0) {
                    // Get the encoded polyline from the result
                    String encodedPolyline = result.routes[0].overviewPolyline.getEncodedPath();

                    // Decode the encoded polyline to a list of LatLng points
                    List<LatLng> decodedPath = PolyUtil.decode(encodedPolyline);

                    // Draw the polyline on the map
                    getActivity().runOnUiThread(() -> {
                        PolylineOptions polylineOptions = new PolylineOptions()
                                .addAll(decodedPath)
                                .color(travelMode == TravelMode.WALKING ? Color.YELLOW : Color.BLUE)
                                .width(10);

                        mPolyline = mMap.addPolyline(polylineOptions);
                        mPolylines.add(mPolyline);

                        // Move the camera to the starting point only if moveCamera is true
                        if (moveCamera) {
                            moveCameraToLatLng(start, 20.0f);
                        }
                    });
                }
            }


            @Override
            public void onFailure(Throwable e) {
                // Handle the error
                e.printStackTrace();
            }
        });
    }

    private void moveCameraToLatLng(LatLng latLng, float zoomLevel) {
        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        }
    }






    // vector to bitmap convertor////////////////////////
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){
        Drawable vectorDrawable  = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    /////////////////////////////////////////////////////////

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        travelModeRadioGroup = view.findViewById(R.id.travelModeRadioGroup);
        radioButtonDriving = view.findViewById(R.id.radioButtonDriving);
        radioButtonWalking = view.findViewById(R.id.radioButtonWalking);

        travelModeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonWalking) {
                travelMode = TravelMode.WALKING;
            } else {
                travelMode = TravelMode.DRIVING;
            }

            // Check if both currentLocation and markerPosition are not null
            if (currentLocation != null && markerPosition != null) {
                // Redraw the polyline with the new travel mode
                drawPolyline(currentLocation, markerPosition, false);
            } else {
                // Show a message to the user indicating that the location is unavailable
                Toast.makeText(getActivity(), "Location unavailable. Please target a location and try again.", Toast.LENGTH_LONG).show();
            }
        });

        /////

        Button openOfflinePolylineActivityButton = view.findViewById(R.id.openOfflinePolylineActivityButton);
        openOfflinePolylineActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OfflinePolylineActivity.class);

            ArrayList<String> markerNames = new ArrayList<>();
            ArrayList<LatLng> markerLatLngs = new ArrayList<>();

            for (Marker marker : CMarkers) {
                markerNames.add(marker.getTitle());
                markerLatLngs.add(marker.getPosition());
            }

            intent.putStringArrayListExtra("markerNames", markerNames);
            intent.putParcelableArrayListExtra("markerLatLngs", markerLatLngs);

            startActivity(intent);
        });

        setupAutoCompleteTextView(markerTitles);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mMap != null) {
                    callback.onMapReady(mMap);
                }
            } else {
                Toast.makeText(getContext(), "Permission denied. Please grant location permission to use this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }



}

