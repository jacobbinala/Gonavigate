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

public class InterestFragment extends Fragment implements GoogleMap.OnMarkerClickListener {
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

            ///////marker interest here v/////




            ///////Points of interest//////

            LatLng coed = new LatLng(18.05913865017037, 120.5551302966365);
            Marker coedMarker = mMap.addMarker(new MarkerOptions()
                    .position(coed)
                    .title("MMSU COED Garden")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.point_of_interest_svgrepo_com)));
            CMarkers.add(coedMarker);


            LatLng s3 = new LatLng(18.05986945072962, 120.54859622318888);
            Marker s3Marker = mMap.addMarker(new MarkerOptions()
                    .position(s3)
                    .title("Don Mariano Marcos y Rubio Monument and Historical Marker")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.point_of_interest_svgrepo_com)));
            CMarkers.add(s3Marker);

            LatLng rp = new LatLng(18.057373201499264, 120.5480950611824);
            Marker rpMarker = mMap.addMarker(new MarkerOptions()
                    .position(rp)
                    .title("MMSU Rice Paddy Art")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.point_of_interest_svgrepo_com)));
            CMarkers.add(rpMarker);

            LatLng pf = new LatLng(18.057527166281524, 120.55051887273603);
            Marker pfMarker = mMap.addMarker(new MarkerOptions()
                    .position(pf)
                    .title("MMSU President's Farm")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.point_of_interest_svgrepo_com)));
            CMarkers.add(pfMarker);

            LatLng sg = new LatLng(18.06139685914412, 120.5489116252973);
            Marker sgMarker = mMap.addMarker(new MarkerOptions()
                    .position(sg)
                    .title("Sunken Garden")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.point_of_interest_svgrepo_com)));
            CMarkers.add(sgMarker);



            ///////////////////TWIN Gate



            /*LatLng tg1 = new LatLng(18.057127907526937, 120.54837117090977);
            Marker tg1Marker = mMap.addMarker(new MarkerOptions()
                    .position(tg1)
                    .title("MMSU Twin Gate")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.gate_10_svgrepo_com)));
            CMarkers.add(tg1Marker);

            LatLng tg2 = new LatLng(18.05724617899407, 120.54769495407862);
            Marker tg2Marker = mMap.addMarker(new MarkerOptions()
                    .position(tg2)
                    .title("MMSU Twin Gate")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.gate_10_svgrepo_com)));
            CMarkers.add(tg2Marker);*/

            LatLng nok = new LatLng(18.169107127881396, 120.52761131529317);
            Marker nokMarker = mMap.addMarker(new MarkerOptions()
                    .position(nok)
                    .title("Sphynx base")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.sphynx)));
            CMarkers.add(nokMarker);
            ////////////////////////////////


            // points of interest
            markerTitles.add("Mariano marcos state university");
            markerTitles.add("MMSU COED Garden");
            markerTitles.add("Don Mariano Marcos y Rubio Monument and Historical Marker");
            markerTitles.add("MMSU Rice Paddy Art");
            markerTitles.add("MMSU President's Farm");
            markerTitles.add("MMSU Twin Gate");
            markerTitles.add("Sunken Garden");
            markerTitles.add("Sphynx base");


            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getContext()));

            //////button function passer of description
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
                    } else if (title.equals("MMSU COED Garden")) {
                        description = "A campus garden is an amazing initiative that promotes sustainability, environmentalism, and " +
                                "provides a plethora of educational opportunities for students.Campus gardens serve as a gathering place for " +
                                "community events, outdoor classes, or simply as a peaceful retreat for students and staff to enjoy the beauty " +
                                "of nature on campus.";
                    } else if (title.equals("Don Mariano Marcos y Rubio Monument and Historical Marker")) {
                        description = "Born in Batac, Ilocos Norte, 21 April 1897, to Don Fabian Marcos and Doña Cresencia Rubio. His father was " +
                                "the Justice of the Peace, school teacher and gobernadorcillo of Batac after the Revolution.The monument serves " +
                                "as a physical reminder of history and is meant to inspire reflection, contemplation, and reverence for the late " +
                                "figure of justice. in addition to satisfying the need to offer degree programs in the arts and sciences.";
                    } else if (title.equals("MMSU Rice Paddy Art")) {
                        description = "Reaffirming their strong resolve to champion farm tourism in the province, the Mariano Marcos State " +
                                "University and the Philippine Rice Research Institute- Batac have started to install a rice paddy art.";
                    } else if (title.equals("MMSU President's Farm")) {
                        description = "The MMSU’s President’s Farm, located at the southwestern portion of the College of Agriculture, " +
                                "Food, and Sustainable Development (CAFSD) now showcases thirteen new and high-yielding inbred and hybrid " +
                                "rice varieties.";
                    } else if (title.equals("MMSU Twin Gate")) {
                        description = "The MMSU twin gate serves as an entrance and exit form for the campus.";
                    } else if (title.equals("Sunken Garden")) {
                        description = "The Sunken Garden is surrounded by a lush green landscape with different types of trees, plants, and " +
                                "flowers. It features a sunken amphitheater that serves as a venue for various events such as concerts, " +
                                "cultural shows, and other outdoor activities. The Sunken Garden is also an ideal location for outdoor " +
                                "recreational activities such as jogging, walking, and exercising. It provides a serene and peaceful " +
                                "environment for individuals to unwind and enjoy nature.";
                    }else if (title.equals("Sphynx base")) {
                        description = " the sphynx base is the balay of nookie where we do our group activities. if you see this you earn the right to contact us and we'll have something for you.\n facebook: https://www.facebook.com/chris.jaycob.ramos \n say the magic word 'Ni man mang NOk'";
                    }
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
            mMap.setOnMarkerClickListener(InterestFragment.this);
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
            drawPolyline(startLatLng, endLatLng, true);

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
                drawPolyline(currentLocation, markerPosition,false);
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

