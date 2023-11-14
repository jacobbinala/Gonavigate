package com.example.gonav;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapsFragment extends Fragment implements GoogleMap.OnMarkerClickListener {
    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;

    private Polyline mPolyline;




    private ArrayList<Marker> CMarkers = new ArrayList<>();
    ArrayList<String> markerTitles = new ArrayList<>();

    private Map<String, Integer> titleToImageMap = new HashMap<>();

    //private RadioButton travelModeButton;
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
            LatLng mmsu = new LatLng(18.057895101984894, 120.54811941700655);
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

          /////short cuts///////////////
            LatLng startPoint = new LatLng(18.05864090142201, 120.54803092554948);
            LatLng endPoint = new LatLng(18.05852309469541, 120.54864411795371);

            PolylineOptions polylineOptions = new PolylineOptions()
                    .add(startPoint, endPoint)
                    .color(Color.YELLOW)
                    .width(15);

            mMap.addPolyline(polylineOptions);
          /////////////////////////////////////////



            //////////// <markers //////////////////

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mmsu,18));



            ///Main Buildings / Colleges////////////
            LatLng admin = new LatLng(18.060192592600444, 120.548664064566);
            Marker mmsuAdminMarker = mMap.addMarker(new MarkerOptions()
                    .position(admin)
                    .title("MMSU Administration Building")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.mmsuicon)));
            CMarkers.add(mmsuAdminMarker);
            // Store the marker icon resource ID



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




            ///////marker parkings /////

            LatLng coeparking = new LatLng(18.059629633028564, 120.54378477835827);
            Marker coeParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(coeparking)
                    .title("COE Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(coeParkingAreaMarker);

            LatLng cabparking = new LatLng( 18.059450415958548, 120.54495300138551);
            Marker cabParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(cabparking)
                    .title("CAB Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(cabParkingAreaMarker);

            LatLng casparking = new LatLng( 18.059343395797175, 120.54612068747258);
            Marker casParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(casparking)
                    .title("CAS Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(casParkingAreaMarker);

            LatLng adminparking = new LatLng( 18.06026581800218, 120.54882732033447);
            Marker adminParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(adminparking)
                    .title("ADMIN Parking Area 1")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(adminParkingAreaMarker);

            LatLng admin2parking = new LatLng( 18.059774455104225, 120.54889085670548);
            Marker admin2ParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(admin2parking)
                    .title("ADMIN Parking Area 2")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(admin2ParkingAreaMarker);

            LatLng admin3parking = new LatLng( 18.059897972144014, 120.54828489041095);
            Marker admin3ParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(admin3parking)
                    .title("ADMIN Parking Area 3")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(admin3ParkingAreaMarker);

            LatLng cafsdparking = new LatLng(   18.05839711064046, 120.55045489168438);
            Marker cafsdParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(cafsdparking)
                    .title("CAFSD Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(cafsdParkingAreaMarker);


            LatLng colparking = new LatLng(   18.056560605106224, 120.55511556524478);
            Marker colParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(colparking)
                    .title("COL Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(colParkingAreaMarker);


            LatLng theat1parking = new LatLng(   18.0581532042498, 120.55377454201917);
            Marker theat1ParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(theat1parking)
                    .title("Theatro Ilocandia Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(theat1ParkingAreaMarker);


            LatLng theat2parking = new LatLng(   18.058520993925587, 120.55389988033843);
            Marker theat2ParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(theat2parking)
                    .title("Theatro Ilocandia Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(theat2ParkingAreaMarker);


            LatLng ovalparking = new LatLng(   18.05769353487202, 120.55405562174117);
            Marker ovalParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(ovalparking)
                    .title("OVAL Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(ovalParkingAreaMarker);


            LatLng cbeaparking = new LatLng(   18.05715760065966, 120.55598480582111);
            Marker cbeaParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(cbeaparking)
                    .title("CBEA Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(cbeaParkingAreaMarker);


            LatLng chsparking = new LatLng(   18.059195585391414, 120.55775085019107);
            Marker chsParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(chsparking)
                    .title("CHS Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(chsParkingAreaMarker);


            LatLng comparking = new LatLng(   18.059324188083863, 120.55696430401153);
            Marker comParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(comparking)
                    .title("COM Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(comParkingAreaMarker);

            LatLng mmsuhostelparking = new LatLng(   18.05824646139258, 120.55831558308589);
            Marker mmsuhostelParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(mmsuhostelparking)
                    .title("MMSU Hostel Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(chsParkingAreaMarker);


            LatLng lib1parking = new LatLng(    18.061999911810005, 120.54922494836767);
            Marker lib1ParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(lib1parking)
                    .title("MMSU Library Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(lib1ParkingAreaMarker);

            LatLng lib2parking = new LatLng(   18.06209715863711, 120.54885923652978);
            Marker lib2ParkingAreaMarker = mMap.addMarker(new MarkerOptions()
                    .position(lib2parking)
                    .title("MMSU Library Parking Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.parking)));
            CMarkers.add(lib2ParkingAreaMarker);
            ///////marker parkings>///////






            ///////New Facilities markerz///


            LatLng nberic = new LatLng(     18.05780614473803, 120.54453238531835);
            Marker nbericMarker = mMap.addMarker(new MarkerOptions()
                    .position(nberic)
                    .title("National Bioenergy Research and Innovation Center")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.cells_biology_svgrepo_com)));
            CMarkers.add(nbericMarker);

            LatLng coeds = new LatLng(     18.065598272524703, 120.54585835302551);
            Marker coedsMarker = mMap.addMarker(new MarkerOptions()
                    .position(coeds)
                    .title("MMSU COEDS Dormitory")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.bunk_hostel_svgrepo_com)));
            CMarkers.add(coedsMarker);


            LatLng lib = new LatLng(     18.062159155114664, 120.54906715060541);
            Marker libMarker = mMap.addMarker(new MarkerOptions()
                    .position(lib)
                    .title("MMSU Library")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.library_book_svgrepo_com)));
            CMarkers.add(libMarker);


            LatLng teatro = new LatLng(    18.05841282867744, 120.55361997275567);
            Marker teatroMarker = mMap.addMarker(new MarkerOptions()
                    .position(teatro)
                    .title("Teatro Ilocandia")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.performing_arts_svgrepo_com)));
            CMarkers.add(teatroMarker);

            LatLng fpic = new LatLng(     18.05714994551302, 120.55314730588319);
            Marker fpicMarker = mMap.addMarker(new MarkerOptions()
                    .position(fpic)
                    .title("Food Processing Innovation Center")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.mortar_svgrepo_com)));
            CMarkers.add(fpicMarker);

            LatLng tp = new LatLng(     18.0568276899166, 120.5528027983113);
            Marker tpMarker = mMap.addMarker(new MarkerOptions()
                    .position(tp)
                    .title("MMSU Technology Park")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.bulb_svgrepo_com)));
            CMarkers.add(tpMarker);

            LatLng fc = new LatLng(18.05755136597001, 120.55383749285919);
            Marker fcMarker = mMap.addMarker(new MarkerOptions()
                    .position(fc)
                    .title("MMSU Fitness Center")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.dumbbell_gym_svgrepo_com)));
            CMarkers.add(tpMarker);

            LatLng cc = new LatLng(  18.057543952691965, 120.55405406291302);
            Marker ccMarker = mMap.addMarker(new MarkerOptions()
                    .position(cc)
                    .title("MMSU Covered Court")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.court_playground_svgrepo_com)));
            CMarkers.add(ccMarker);

            LatLng clinic = new LatLng(  18.056516340681014, 120.55435706103991);
            Marker clinicMarker = mMap.addMarker(new MarkerOptions()
                    .position(clinic)
                    .title("MMSU Clinic/Infirmary")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.hospital_svgrepo_com)));
            CMarkers.add(clinicMarker);

            LatLng chap = new LatLng(  18.056868105044668, 120.55506076067911);
            Marker chapMarker = mMap.addMarker(new MarkerOptions()
                    .position(chap)
                    .title("MMSU Chapel")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.church_chapel_svgrepo_com__1_)));
            CMarkers.add(chapMarker);

            LatLng coop = new LatLng(  18.05751308247523, 120.55501442320886);
            Marker coopMarker = mMap.addMarker(new MarkerOptions()
                    .position(coop)
                    .title("MMSU Cooperative")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.teamwork_group_svgrepo_com)));
            CMarkers.add(coopMarker);

            LatLng sip = new LatLng(  18.056675903561406, 120.55200334574202);
            Marker sipMarker = mMap.addMarker(new MarkerOptions()
                    .position(sip)
                    .title("SIP Hub (Social Innovations and Partnership)")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.food_stall_stall_svgrepo_com)));
            CMarkers.add(sipMarker);

            LatLng swim = new LatLng(18.059038022897663, 120.55570945821727);
            Marker swimMarker = mMap.addMarker(new MarkerOptions()
                    .position(swim)
                    .title("MMSU Swimming Pool Area")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.pool_svgrepo_com)));
            CMarkers.add(swimMarker);

            LatLng oval = new LatLng( 18.05834867489638, 120.55479723147972);
            Marker ovalMarker = mMap.addMarker(new MarkerOptions()
                    .position(oval)
                    .title("MMSU Oval")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.sports_shoes_1_svgrepo_com)));
            CMarkers.add(ovalMarker);

            LatLng hos = new LatLng(    18.058097328845374, 120.55826341250838);
            Marker hosMarker = mMap.addMarker(new MarkerOptions()
                    .position(hos)
                    .title("MMSU Hostel and Function Hall")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.bunk_hostel_svgrepo_com)));
            CMarkers.add(hosMarker);

            LatLng man = new LatLng(    18.058004879488166, 120.55795264801125);
            Marker manMarker = mMap.addMarker(new MarkerOptions()
                    .position(man)
                    .title("MMSU Mansion")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.school)));
            CMarkers.add(manMarker);

            LatLng utcfood = new LatLng(18.058651473486584, 120.55803668836712);
            Marker utcfoodMarker = mMap.addMarker(new MarkerOptions()
                    .position(utcfood)
                    .title("UTC")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.book_svgrepo_com)));
            CMarkers.add(utcfoodMarker);




            ///////foood

            LatLng food = new LatLng(18.06051317109046, 120.54883853841596);
            Marker foodMarker = mMap.addMarker(new MarkerOptions()
                    .position(food)
                    .title("MMSU Admin Canteen")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.food_stall_stall_svgrepo_com)));
            CMarkers.add(foodMarker);

            LatLng casfood = new LatLng(18.060115499524866, 120.54618833407334);
            Marker casfoodMarker = mMap.addMarker(new MarkerOptions()
                    .position(casfood)
                    .title("CAS SC Canteen")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.food_stall_stall_svgrepo_com)));
            CMarkers.add(casfoodMarker);


            ///////Points of interest//////

            LatLng coed = new LatLng(18.05913865017037, 120.5551302966365);
            Marker coedMarker = mMap.addMarker(new MarkerOptions()
                    .position(coed)
                    .title("MMSU COED Garden")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.point_of_interest_svgrepo_com)));
            CMarkers.add(coedMarker);

            LatLng s1 = new LatLng(18.058744165150944, 120.55264398012632);
            Marker s1Marker = mMap.addMarker(new MarkerOptions()
                    .position(s1)
                    .title("Statue One")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.point_of_interest_svgrepo_com)));
            CMarkers.add(s1Marker);

            LatLng s2 = new LatLng(18.05830136161287, 120.55399707257851);
            Marker s2Marker = mMap.addMarker(new MarkerOptions()
                    .position(s2)
                    .title("Statue Two")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.point_of_interest_svgrepo_com)));
            CMarkers.add(s2Marker);

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


            LatLng nok = new LatLng(18.169107127881396, 120.52761131529317);
            Marker nokMarker = mMap.addMarker(new MarkerOptions()
                    .position(nok)
                    .title("Sphynx base")
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.sphynx)));
            CMarkers.add(nokMarker);
            ////////////////////////////////


            // colleges
            markerTitles.add("Mariano marcos state university");
            markerTitles.add("MMSU Administration Building");
            markerTitles.add("College of Arts and Sciences - CAS");
            markerTitles.add("Communication Arts Building - CAB");
            markerTitles.add("College of Engineering - COE");
            markerTitles.add("College of Agriculture, Food, and Sustainable Development - CAFSD");
            markerTitles.add("College of Business Economics and Accountancy - CBEA");
            markerTitles.add("College of Health and Sciences - CHS");
            markerTitles.add("College of Medicine");
            markerTitles.add("College of Law - COL");
            markerTitles.add("College of Medicine - COM");
            markerTitles.add("College of Teacher Education - CTE");
            markerTitles.add("Graduate School");
            markerTitles.add("College of Industrial Technology - CIT");
            markerTitles.add("College of Aquatic Sciences and Applied Technology - CASAT");
            // parking
            markerTitles.add("Mariano marcos state university");
            markerTitles.add("COE Parking Area");
            markerTitles.add("CAB Parking Area");
            markerTitles.add("CAS Parking Area");
            markerTitles.add("ADMIN Parking Area 1");
            markerTitles.add("ADMIN Parking Area 2");
            markerTitles.add("ADMIN Parking Area 3");
            markerTitles.add("CAFSD Parking Area");
            markerTitles.add("COL Parking Area");
            markerTitles.add("Theatro Ilocandia Parking Area");
            markerTitles.add("CBEA Parking Area");
            markerTitles.add("CHS Parking Area");
            markerTitles.add("COM Parking Area");
            markerTitles.add("MMSU Hostel Parking Area");
            markerTitles.add("MMSU Library Parking Area");
            //points of interest
            markerTitles.add("Mariano marcos state university");
            markerTitles.add("MMSU COED Garden");
            markerTitles.add("Don Mariano Marcos y Rubio Monument and Historical Marker");
            markerTitles.add("MMSU Rice Paddy Art");
            markerTitles.add("MMSU President's Farm");
            markerTitles.add("MMSU Twin Gate");
            //food
            markerTitles.add("Mariano marcos state university");
            markerTitles.add("National Bioenergy Research and Innovation Center");
            markerTitles.add("MMSU COEDS Dormitory");
            markerTitles.add("MMSU Library");
            markerTitles.add("Teatro Ilocandia");
            markerTitles.add("Food Processing Innovation Center");
            markerTitles.add("MMSU Technology Park");
            markerTitles.add("MMSU Fitness Center");
            markerTitles.add("MMSU Covered Court");
            markerTitles.add("MMSU Clinic/Infirmary");
            markerTitles.add("MMSU Chapel");
            markerTitles.add("MMSU Cooperative");
            markerTitles.add("SIP Hub (Social Innovations and Partnership)");
            markerTitles.add("MMSU Swimming Pool Area");
            markerTitles.add("MMSU Oval");
            markerTitles.add("MMSU Hostel and Function Hall");
            markerTitles.add("MMSU Plantation?? Dragonfruit farm? haha");
            markerTitles.add("MMSU Admin Canteen");
            markerTitles.add("CAS SC Canteen");
            markerTitles.add("UTC");
            markerTitles.add("Sphynx base");

            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getContext()));
            ////button function passer of description


            ////////



            ////////


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
                    } else if (title.equals("MMSU COED Garden")) {
                        description = "A campus garden is an amazing initiative that promotes sustainability, environmentalism, and " +
                                "provides a plethora of educational opportunities for students.Campus gardens serve as a gathering place for " +
                                "community events, outdoor classes, or simply as a peaceful retreat for students and staff to enjoy the beauty " +
                                "of nature on campus.";
                    }  else if (title.equals("Don Mariano Marcos y Rubio Monument and Historical Marker")) {
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
                    } // facilities

                    else if (title.equals("National Bioenergy Research and Innovation Center")) {
                        description = "The National Bioenergy Research and Innovation Center (NBERIC) envisions to become a national hub for " +
                                "bioenergy research, training, extension and technopreneurship. To date it has seven partner SUCs namely, " +
                                "Aklan State University, Cagayan State University, Central Luzon State University, Marinduque State College, " +
                                "Pangasinan State University, University of Antique and Western Philippines University. Other partners are the " +
                                "Department of Energy (DOE), Department of Environment and Natural Resources (DENR), Philippine Rice Institute " +
                                "(PhilRice), Department of Science and Technology (DOST), Department of Agriculture (DA), USAID-STRIDE, Ethanol " +
                                "Producers of the Philippines (EPAP), Sugar Regulatory Administration (SRA), and LGUs.";
                    } else if (title.equals("MMSU COEDS Dormitory")) {
                        description = "Indeed, home away from home.\n" +
                                "MMSU student-boarders can now enjoy their new beds, new wooden cabinets, new dining tables and chairs," +
                                " and a modern-looking comfort room – making their stay in the campus feel more like it is home. \n";

                    } else if (title.equals("MMSU Library")) {
                        description = "The establishment of the University Library System (ULS) in 1980 has become one of the most significant achievements of the university's development programs. Since then, the ULS continuous to serve as vital resource in the university.\n" +
                                "Guided by the \"information access\" and \"multimedia\" concepts, the ULS intends to deliver quality sevice to " +
                                "you in your use of the information needed in transforming and improving your life. Thus, it is manned by " +
                                "professionally qualified and competent staff, maintains appropriate and extensive facilities as well as " +
                                "collects and organizes relevant information resources in various formats.";
                    } else if (title.equals("Teatro Ilocandia")) {
                        description = "In addition to hosting performances, the university theater Teatro Ilocandia may also serve as a " +
                                "classroom or a rehearsal space for students studying theater or performing arts. Teatro Ilocandia is " +
                                "equipped with state-of-the-art technical equipment and offer opportunities for students to learn about " +
                                "various aspects of theater production, such as lighting, sound design, and stage management. " +
                                "Teatro Ilocandia can also provide a valuable cultural resource for the surrounding community, " +
                                "offering performances that are open to the public.";
                    } else if (title.equals("Food Processing Innovation Center")) {
                        description = "The FIC is intended to be a learning hub for research and development (R&D) on food production and the " +
                                "processing of various agricultural crops into high-value products which can be marketed locally and abroad.";
                    } else if (title.equals("MMSU Technology Park")) {
                        description = "The MMSU technology park aims to promote innovation and entrepreneurship by providing a supportive environment " +
                                "for technology-based startups and research activities. It is equipped with modern laboratory facilities, equipment, " +
                                "and resources that are available for use by both MMSU faculty and external stakeholders.";
                    } else if (title.equals("MMSU Fitness Center")) {
                        description = "The fitness center provides fitness and wellness services to the university's students, faculty, and " +
                                "staff, as well as the local community. The fitness center is equipped with modern exercise equipment such " +
                                "as treadmills, stationary bikes, weight machines, and free weights. It also has a cardio area and a group " +
                                "exercise room where fitness classes such as yoga, Zumba, and aerobics are held.";
                    } else if (title.equals("MMSU Covered Court")) {
                        description = "The court is equipped with standard equipment and facilities for different sports, such as basketball " +
                                "hoops, volleyball nets, and badminton posts. The MMSU Covered Court also hosts various sports events, " +
                                "tournaments, and competitions throughout the year, bringing together athletes and sports enthusiasts from " +
                                "different parts of the region. It is also used for events such as convocations, graduations, and other " +
                                "gatherings that require a large indoor space.";
                    } else if (title.equals("MMSU Clinic/Infirmary")) {
                        description = "The MMSU Clinic / Infirmary offers a range of healthcare services, including primary medical care, " +
                                "emergency treatment, laboratory tests, and medical consultations. It is staffed by licensed physicians, " +
                                "nurses, and healthcare professionals who are trained to provide quality medical care and assistance to " +
                                "patients.";
                    } else if (title.equals("MMSU Chapel")) {
                        description = "The chapel serves as a spiritual and religious center for the university's students, faculty, and " +
                                "staff, as well as the local community. The MMSU Chapel is a multi-faith facility that accommodates various " +
                                "religious beliefs and practices. It provides a peaceful and quiet environment for individuals to reflect, " +
                                "meditate, and offer prayers.";
                    } else if (title.equals("MMSU Cooperative")) {
                        description = "Welcome to our University Cooperative, where students come first. Our cooperative is a one-stop-shop " +
                                "for all your needs, from textbooks and school supplies to snacks and daily essentials. And with our focus " +
                                "on affordability and accessibility, you can rest assured that you're getting the best value for your money. " +
                                "But we're more than just a store - we're a community of students and staff working together to create a " +
                                "better university experience for everyone. So come and join us, and see for yourself why our cooperative " +
                                "is the go-to place for students who want to save money, make friends, and get the most out of their time " +
                                "at university.";
                    } else if (title.equals("SIP Hub (Social Innovations and Partnership)")) {
                        description = "The facility intends to showcase the MMSU’s culture of hospitality synergized in its four-fold " +
                                "functions – instruction, research, extension and production. In line with this, it will also feature the " +
                                "University’s patented food technologies such as black garlic, sandcooked-peanut, kamangeg flour and " +
                                "cheesecake, and dragon fruit pink noodles, among others.";
                    } else if (title.equals("MMSU Swimming Pool Area")) {
                        description = "Dive into our newly rehabilitated swimming pool, and experience the ultimate in aquatic adventure. " +
                                "With a massive size of 50 meters by 25 meters, and a depth that ranges from 1.8 meters to 2.3 meters " +
                                "(or 5.9 feet to 7.5 feet), this pool is a haven for both serious swimmers and those just looking to splash " +
                                "around. And with eight lanes, you can challenge yourself to a friendly race or simply soak up the sun on our " +
                                "pool deck. So whether you're a seasoned athlete or just looking for a refreshing way to beat the heat, our swimming pool is the place to be. Come on in, the water's fine!";
                    } else if (title.equals("MMSU Oval")) {
                        description = "Welcome to our university oval, the ultimate destination for athletes and sports enthusiasts alike." +
                                " With a massive area of 19,055.00 square meters, our facilities are equipped with everything you need to take " +
                                "your game to the next level. Whether you're into soccer, baseball, or just love watching from the stands, our " +
                                "soccer court, grandstand, and baseball court offer the perfect combination of space, comfort, and excitement. " +
                                "So why settle for anything less than the best? Come and join us at our sports complex, and experience the " +
                                "thrill of victory in a world-class setting.";
                    } else if (title.equals("MMSU Hostel and Function Hall")) {
                        description = "Welcome to our University Hostel and function hall, the perfect place to call home away from home. " +
                                "Our comfortable and spacious rooms offer all the amenities you need for a restful stay, while our function " +
                                "hall provides a versatile space for meetings, conferences, and events. Whether you're a student attending " +
                                "classes, a professor here for a conference, or a group looking for a venue, we've got you covered. And with " +
                                "our friendly staff always on hand to assist you, you'll feel right at home in no time. So book your stay " +
                                "with us today, and experience the best of both worlds - a comfortable and convenient accommodation, and a " +
                                "versatile and well-equipped function hall all in one place.";
                    } else if (title.equals("MMSU Admin Canteen")) {
                        description = "Welcome to our canteen, we believe that good food fuels great minds. That's why we offer a variety of " +
                                "delicious and affordable meals and snacks to keep you going throughout the day. Whether you're in the mood " +
                                "for something savory or sweet, our menu has something for everyone. From hearty sandwiches and fresh salads " +
                                "to tasty pastries and refreshing drinks, we've got you covered. So come and grab a bite with us today, and " +
                                "let our food energize you for whatever challenges lie ahead!";
                    } else if (title.equals("CAS SC Canteen")) {
                        description = "Welcome to our canteen, we believe that good food fuels great minds. That's why we offer a variety of " +
                                "delicious and affordable meals and snacks to keep you going throughout the day. Whether you're in the mood " +
                                "for something savory or sweet, our menu has something for everyone. From hearty sandwiches and fresh salads " +
                                "to tasty pastries and refreshing drinks, we've got you covered. So come and grab a bite with us today, and " +
                                "let our food energize you for whatever challenges lie ahead!";
                    } else if (title.equals("MMSU Administration Building")) {
                        description = "The Mariano Marcos State University Administration Building is a testament to the university's commitment " +
                                "to providing a modern and functional space for its administrative operations. It reflects the university's " +
                                "focus on excellence, innovation, and sustainability, and is an essential part of the campus's vibrant and " +
                                "dynamic atmosphere.";
                    } else if (title.equals("MMSU Mansion")) {
                        description = "Located within the beautiful campus premises, the MMSU Mansion can be a great " +
                                "option for those looking for a place to stay, whether it be for leisure or work " +
                                "purposes. Additionally, with its spacious and elegant halls, the mansion can also " +
                                "serve as an excellent venue for various events, such as conferences, birthday parties, " +
                                "and other special occasions.";
                    } else if (title.equals("UTC")) {
                        description = "MMSU UTC, or the Mariano Marcos State University University Training Center, is a modern and spacious facility located in Batac City, Ilocos Norte, Philippines. The center offers a wide range of amenities and services for various activities and events, including conferences, seminars, and workshops.";
                    } else if (title.equals("Sphynx base")) {
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

            mMap.setOnMarkerClickListener(MapsFragment.this);



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

    @SuppressLint("ClickableViewAccessibility")
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
                            drawPolyline(currentLocation, markerPosition, false);
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

