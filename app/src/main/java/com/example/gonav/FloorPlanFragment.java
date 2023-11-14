package com.example.gonav;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import android.widget.AutoCompleteTextView;


public class FloorPlanFragment extends Fragment {

    private String imageName;

    private Spinner buildingSpinner;
    private TextView viewFloorPlanButton;
    private ImageView floorPlanImage;
    private TextView currentBuildingAndFloorLabel;
    private String currentBuilding = "COE";
    private int currentFloor = 1;

    private AutoCompleteTextView autoCompleteSearch;

    private Bitmap mutableBitmap;
    private Bitmap originalBitmap;
    private int currentSchoolFloor = -1;



    private Map<String, Map<Integer, List<Room>>> roomsByBuilding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when FloorPlanFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.mapsFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_floor_plan, container, false);

        buildingSpinner = view.findViewById(R.id.building_spinner);
        viewFloorPlanButton = view.findViewById(R.id.view_floor_plan_button);
        floorPlanImage = view.findViewById(R.id.floor_plan_image);
        currentBuildingAndFloorLabel = view.findViewById(R.id.current_building_and_floor_label);

        setupButton();
        setupRooms(); // Call setupRooms() before initializing the AutoCompleteTextView
        autoCompleteSearch = setupAutoCompleteTextView(view);

        // get the building name
        String building = getArguments().getString("building");

        // setup the spinner
        setupSpinner(building);



        return view;
    }



    private AutoCompleteTextView setupAutoCompleteTextView(View view) {
        AutoCompleteTextView autoCompleteSearch = view.findViewById(R.id.autoCompleteSearch);
        updateAutoCompleteAdapter(autoCompleteSearch, currentBuilding, currentFloor);

        autoCompleteSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String roomName = v.getText().toString();
                    List<String> roomNames = Arrays.asList(roomName);
                    searchRooms(roomNames);
                    return true;
                }
                return false;
            }
        });

        return autoCompleteSearch;
    }



    private List<String> getRoomNamesAndSynonyms(String building, int floor) {
        List<String> roomNamesAndSynonyms = new ArrayList<>();

        if (roomsByBuilding.containsKey(building) && roomsByBuilding.get(building).containsKey(floor)) {
            for (Room room : roomsByBuilding.get(building).get(floor)) {
                roomNamesAndSynonyms.add(room.getName());
                roomNamesAndSynonyms.addAll(room.getSynonyms());
            }
        }

        return roomNamesAndSynonyms;
    }
//updateAutoCompleteAdapter to update the adapter based on the selected building and floor:
    private void updateAutoCompleteAdapter(AutoCompleteTextView autoCompleteSearch, String building, int floor) {
        List<String> roomNames = getRoomNamesAndSynonyms(building, floor);
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, roomNames);
        autoCompleteSearch.setAdapter(autoCompleteAdapter);
    }



    private void setupRooms() {
        roomsByBuilding = new HashMap<>();

        // "COE"
        Map<Integer, List<Room>> cafsdRoomsByFloor = new HashMap<>();
        cafsdRoomsByFloor.put(1, Arrays.asList(
                new Room("Student Center Office", Arrays.asList(),104,3,33,31),
                new Room("Student Center Office", Arrays.asList(),829,3,24,35),
                new Room("Room 113", Arrays.asList(),11,81,128,79),
                new Room("Room 112", Arrays.asList(),10,159,130,80),
                new Room("Crop Processing Room", Arrays.asList(),10,239,130,77),
                new Room("Meat Processing Room", Arrays.asList(),10,316,130,74),
                new Room("Kitchen", Arrays.asList(),10,391,68,84),
                new Room("Storage Room", Arrays.asList(),78,391,62,85),
                new Room("Storage Room", Arrays.asList(),10,631,39,36),
                new Room("Storage Room", Arrays.asList(),50,632,90,16),
                new Room("Storage Room", Arrays.asList(),880,613,71,43),
                new Room("Storage Room", Arrays.asList(),115,660,25,31),
                new Room("Storage", Arrays.asList(),725,102,33,15),
                new Room("Storage", Arrays.asList(),185,102,33,15),
                new Room("Storage Area", Arrays.asList(),539,753,78,26),
                new Room("Room 110", Arrays.asList(),78,478,62,44),
                new Room("Forestry and ENVICSI Lab Room", Arrays.asList(),10,523,130,108),
                new Room("Reading Center", Arrays.asList(),140,660,116,119),
                new Room("Reading Center", Arrays.asList(),255,660,169,119),
                new Room("Secretariat Office", Arrays.asList(),616,657,78,122),
                new Room("Secretary Office", Arrays.asList(),693,657,79,122),
                new Room("Dean's office", Arrays.asList(),771,657,52,122),
                new Room("Stock Room", Arrays.asList(),821,657,24,25),
                new Room("Broadcast Room", Arrays.asList(),892,578,58,35),
                new Room("Development Communication Laboratory Room", Arrays.asList(),821,478,130,135),
                new Room("Instrument Room", Arrays.asList(),887,436,62,41),
                new Room("AREC Office", Arrays.asList(),821,354,66,82),
                new Room("Chemical Room Stock Room", Arrays.asList(),887,355,64,79),
                new Room("Room 104", Arrays.asList("Soil Science Laboratory"),820,229,131,126),
                new Room("Room 105", Arrays.asList(),820,150,130,79),
                new Room("Room 106", Arrays.asList(),821,81,130,68),
                new Room("Classroom", Arrays.asList(),234,3,476,114),
                new Room("CR", Arrays.asList(),9,692,131,83),
                new Room("CR", Arrays.asList(),823,682,128,94),
                new Room("CR", Arrays.asList(),710,3,63,111),
                new Room("CR", Arrays.asList(),171,3,68,111)
        ));
        cafsdRoomsByFloor.put(2, Arrays.asList(

                new Room("Room 212", Arrays.asList(), 7, 84, 130, 90),
                new Room("Room 211", Arrays.asList(), 7, 174, 129, 77),
                new Room("Room 210", Arrays.asList("Conference and Audio Visual Room"), 8,253, 128, 229),
                new Room("Room 209", Arrays.asList("Animal Science Laboratory"), 7, 481, 129, 147),
                new Room("Research Extension Office", Arrays.asList(), 6, 628, 41, 37),
                new Room("Storage Room", Arrays.asList(), 47, 629, 88, 19),
                new Room("Storage Room", Arrays.asList(), 111, 658, 255, 30),
                new Room("Storage Room", Arrays.asList(), 898, 613, 42, 42),
                new Room("Storage Room", Arrays.asList(), 717, 105, 32, 15),
                new Room("Storage Room", Arrays.asList(), 182, 105, 30, 14),
                new Room("Storage Area", Arrays.asList(), 532, 751, 76, 25),
                new Room("Computer Room", Arrays.asList(), 137, 658, 77, 83),
                new Room("DEVCOM RURAL DEVT. & Forestry Department", Arrays.asList(), 137, 658, 258, 116),
                new Room("Faculty Lounge", Arrays.asList(), 395, 719, 95, 55),
                new Room("Guidance Office", Arrays.asList(), 396, 659, 46, 60),
                new Room("Counseling", Arrays.asList(), 442, 658, 48, 61),
                new Room("Department of Agricultural Sciences ", Arrays.asList(), 609, 655, 203, 121),
                new Room("Microrial Isolation Room", Arrays.asList(), 811, 612, 86, 19),
                new Room("Room 202", Arrays.asList("Crop Protection Laboratory Room"),811, 482, 130, 129),
                new Room("Weed & Feed Tech Room", Arrays.asList(),907, 404, 33, 77),
                new Room("Room 203", Arrays.asList(),811, 403, 96, 79),
                new Room("Room 204", Arrays.asList(),811, 324, 139, 80),
                new Room("Room 205", Arrays.asList(),811, 245, 130, 80),
                new Room("Room 206", Arrays.asList(),811, 166, 131, 79),
                new Room("Room 207", Arrays.asList(),811, 83, 128, 80),
                new Room("Environmental Science Office", Arrays.asList(),850, 44, 89, 39),
                new Room("Student Center Office", Arrays.asList(),818,6,26,35),
                new Room("Food Science & Technology Office", Arrays.asList(),7,43,91,41),
                new Room("Classroom", Arrays.asList(),229,6,472,115),
                new Room("Stock Room", Arrays.asList(), 813, 655, 22, 24),
                new Room("CR", Arrays.asList(),167,6,62,114),
                new Room("CR", Arrays.asList(),702,6,64,114),
                new Room("CR", Arrays.asList(),812,669,128,105),
                new Room("CR", Arrays.asList(),7,689,128,87),
                new Room("CR", Arrays.asList(),672,757,64,18)
        ));
        roomsByBuilding.put("CAFSD", cafsdRoomsByFloor);

        // "COE"
        Map<Integer, List<Room>> coeRoomsByFloor = new HashMap<>();
        coeRoomsByFloor.put(1, Arrays.asList(
                new Room("Comfort Room", Arrays.asList("CR"), 167, 1, 81, 118),
                new Room("Stock Room", Arrays.asList(), 249, 1, 156, 115),
                new Room("Audio Visual Room", Arrays.asList("AVR"), 167, 1, 81, 118),
                new Room("Smart Wireless Lab", Arrays.asList(), 403, 5, 74, 114),
                new Room("ABE Laboratory Room", Arrays.asList("ABE"), 473, 4, 80, 113),
                new Room("ABE Laboratory Room", Arrays.asList("ABE"), 552, 4, 81, 115),
                new Room("Room 216", Arrays.asList("216"), 632, 1, 72, 116),
                new Room("ABE Faculty Room", Arrays.asList("ABE"), 701, 3, 74, 114),
                new Room("ME Laboratory Room", Arrays.asList("ME"), 821, 83, 128, 134),
                new Room("ME Laboratory Room", Arrays.asList("ME"), 820, 215, 113, 66),
                new Room("ME Laboratory Room", Arrays.asList("ME"), 820, 277, 131, 68),
                new Room("ME Faculty Room", Arrays.asList("ME"), 821, 344, 132, 69),
                new Room("ME Faculty Room", Arrays.asList("ME"), 820, 408, 131, 69),
                new Room("ME Laboratory Room", Arrays.asList("ME"), 816, 475, 133, 141),
                new Room("Comfort Room", Arrays.asList("CR"), 868, 692, 83, 85),
                new Room("Stock Room", Arrays.asList(), 863, 656, 38, 35),
                new Room("ABE Laboratory Room", Arrays.asList("ABE"), 767, 656, 97, 124),
                new Room("CE Laboratory Room", Arrays.asList("CE"), 625, 655, 143, 124),
                new Room("Guidance Counselor Office", Arrays.asList(""), 537, 655, 90, 128),
                new Room("Lobby", Arrays.asList(), 421, 660, 119, 121),
                new Room("Secretary Office", Arrays.asList(), 344, 660, 84, 119),
                new Room("Dean's Office", Arrays.asList(), 384, 720, 40, 56),
                new Room("AACOUP Room", Arrays.asList(), 268, 660, 80, 119),
                new Room("IHUB", Arrays.asList(), 191, 657, 80, 122),
                new Room("Reading Center", Arrays.asList(""), 108, 659, 83, 121),
                new Room("Comfort Room", Arrays.asList("CR"), 15, 705, 96, 75),
                new Room("CPE Dept Laboratory Room", Arrays.asList("CPE"), 9, 557, 135, 75),
                new Room("CPE Dept Faculty Room", Arrays.asList("CPE"), 11, 456, 129, 103),
                new Room("CPE Laboratory Room", Arrays.asList("CPE"), 9, 400, 131, 56),
                new Room("CHE Dept Faculty Room", Arrays.asList("CHE"), 9, 313, 132, 83),
                new Room("CHE Dept Laboratory Room", Arrays.asList("CHE"), 12, 233, 129, 83),
                new Room("CERE Dept Faculty Room", Arrays.asList("CERE"), 11, 155, 129, 80),
                new Room("CERE Dept Laboratory Room", Arrays.asList("CERE"), 11, 81, 129, 76)
        ));
        coeRoomsByFloor.put(2, Arrays.asList(
                new Room("Comfort Room", Arrays.asList("CR"), 169, 7, 82, 112),
                new Room("Stock Room", Arrays.asList(), 208, 100, 39, 19),
                new Room("Room 222", Arrays.asList("222"), 244, 3, 79, 117),
                new Room("Room 223", Arrays.asList("223"), 321, 5, 72, 115),
                new Room("Room 224", Arrays.asList("224"), 391, 8, 74, 113),
                new Room("Room 225", Arrays.asList("225"), 460, 4, 75, 116),
                new Room("Room 222", Arrays.asList("222"), 244, 3, 79, 117),
                new Room("Mechatronics Center", Arrays.asList(), 532, 5, 160, 112),
                new Room("Electronics Engineering Laboratory", Arrays.asList("EE"), 691, 7, 77, 113),
                new Room("EE Faculty Room", Arrays.asList("EE"), 852, 41, 93, 54),
                new Room("EE Dept Faculty Room", Arrays.asList("EE"), 812, 95, 129, 76),
                new Room("Room 202", Arrays.asList("202"), 809, 171, 138, 77),
                new Room("Room 203", Arrays.asList("203"), 812, 245, 133, 79),
                new Room("Room 204", Arrays.asList("204"), 813, 324, 130, 76),
                new Room("Room 205", Arrays.asList("205"), 817, 400, 128, 79),
                new Room("Room 206", Arrays.asList("206"), 812, 476, 132, 75),
                new Room("Room 207", Arrays.asList("207"), 815, 549, 128, 68),
                new Room("Stock Room", Arrays.asList(""), 857, 724, 86, 55),
                new Room("CE Dept Faculty Room", Arrays.asList("208", "Room 208"), 815, 549, 128, 68),
                new Room("Room 208", Arrays.asList("208","210A"), 632, 656, 117, 121),
                new Room("Room 210B", Arrays.asList("210B"), 554, 656, 86, 128),
                new Room("Room 211", Arrays.asList("211"), 410, 658, 146, 120),
                new Room("Room 212", Arrays.asList("212"), 264, 656, 148, 124),
                new Room("Room 213", Arrays.asList("213"), 186, 658, 82, 120),
                new Room("Room 212", Arrays.asList("212"), 106, 658, 80, 118),
                new Room("Office", Arrays.asList(""), 4, 726, 102, 52),
                new Room("Room 215", Arrays.asList("215"), 6, 558, 134, 76),
                new Room("Room 216", Arrays.asList("216"), 8, 478, 130, 82),
                new Room("Room 217", Arrays.asList("217"), 4, 404, 134, 78),
                new Room("Room 218", Arrays.asList("218"), 6, 324, 128, 82),
                new Room("Room 219", Arrays.asList("219"), 8, 250, 130, 74),
                new Room("Room 220", Arrays.asList("220"), 6, 174, 128, 76),
                new Room("BCE Faculty Room", Arrays.asList("BCE"), 10, 96, 128, 80),
                new Room("BCE Faculty Room", Arrays.asList("BCE"), 9, 42, 94, 60)


                ));
        roomsByBuilding.put("COE", coeRoomsByFloor);

         //Admin
        Map<Integer, List<Room>> adminRoomsByFloor = new HashMap<>();
        adminRoomsByFloor.put(1, Arrays.asList(
                new Room("Storage", Arrays.asList(), 15, 557, 36, 31),
                new Room("Storage", Arrays.asList(), 1254, 530, 36, 32),
                new Room("Supply Storage", Arrays.asList(), 97, 578, 101, 42),
                new Room("Supply Storage", Arrays.asList(), 99, 532, 92, 30),
                new Room("BAC Office", Arrays.asList(), 113, 491, 78, 28),
                new Room("BAC Office", Arrays.asList(), 29, 481, 32, 30),
                new Room("Accounting Office", Arrays.asList(), 135, 438, 109, 40),
                new Room("Storage", Arrays.asList(), 218, 404, 44, 21),
                new Room("Directors Office", Arrays.asList(), 70, 360, 38, 27),
                new Room("Auditors Office", Arrays.asList(), 155, 365, 38, 27),
                new Room("GOA Office", Arrays.asList(), 216, 354, 31, 24),
                new Room("Security Office", Arrays.asList(), 338, 190, 38, 27),
                new Room("Conference Room", Arrays.asList(), 257, 110, 76, 45),
                new Room("Budget Office", Arrays.asList(), 380, 171, 34, 30),
                new Room("Holding Room", Arrays.asList(), 375, 63, 36, 21),
                new Room("Holding Room", Arrays.asList(), 861, 40, 34, 28),
                new Room("Cashier Office", Arrays.asList(), 421, 146, 71, 48),
                new Room("Registrar Office", Arrays.asList(), 780, 127, 79, 43),
                new Room("Registrar Storage", Arrays.asList(), 841, 217, 50, 34),
                new Room("Records Storage", Arrays.asList(), 1045, 328, 38, 26),
                new Room("Records Office", Arrays.asList(), 1060, 366, 67, 45),
                new Room("Pantry", Arrays.asList(), 1191, 348, 35, 20),
                new Room("Strat Com", Arrays.asList(), 1102, 432, 48, 20),
                new Room("Printing Office", Arrays.asList(), 1115, 474, 42, 25),
                new Room("Alumni Office", Arrays.asList(), 1218, 451, 49, 45),
                new Room("ANTAP Office", Arrays.asList(), 1117, 511, 61, 27),
                new Room("Student Service Development", Arrays.asList(), 1082, 558, 138, 61)
        ));
        adminRoomsByFloor.put(2, Arrays.asList(
                new Room("ETEEAP Office", Arrays.asList("Internationalization Linkages, & Partnership & Public-Private Partnership"), 11, 521, 134, 65),
                new Room("PPDG office", Arrays.asList(), 46, 455, 85, 60),
                new Room("Accessors Office", Arrays.asList(), 79, 397, 53, 33),
                new Room("PPDO Directors Office", Arrays.asList(), 122, 423, 55, 32),
                new Room("HRM", Arrays.asList("Human Resource Management"), 90, 329, 97, 48),
                new Room("Quality Assurance Office", Arrays.asList(), 258, 147, 84, 50),
                new Room("ELP and Planning Office", Arrays.asList(), 333, 113, 77, 60),
                new Room("Admin Services Office", Arrays.asList(), 668, 97, 46, 38),
                new Room("Legal Office", Arrays.asList(), 716, 110, 35, 37),
                new Room("Conference Room", Arrays.asList(), 740, 142, 82, 47),
                new Room("O.P Staff Office", Arrays.asList(), 859, 261, 53, 38),
                new Room("Office Of The President", Arrays.asList(), 900, 213, 72, 42),
                new Room("Board Sec. Office", Arrays.asList(), 898, 318, 64, 43),
                new Room("Holding Room", Arrays.asList(), 961, 280, 54, 39),
                new Room("VP For Admin", Arrays.asList(), 930, 383, 71, 36),
                new Room("Board Room", Arrays.asList(), 1019, 392, 55, 47),
                new Room("VP For Academic Affairs", Arrays.asList(), 957, 452, 67, 44),
                new Room("VP For Planning And Development", Arrays.asList(), 967, 543, 66, 41),
                new Room("VP For Research and Extension", Arrays.asList(), 1040, 532, 64, 45)


        ));
        roomsByBuilding.put("ADMIN", adminRoomsByFloor);

           //CBEA
        Map<Integer, List<Room>> cbeaRoomsByFloor = new HashMap<>();
        cbeaRoomsByFloor.put(1, Arrays.asList(
                new Room("Kitchen 2", Arrays.asList("Kitchen"), 344, 122, 88, 85),
                new Room("Kitchen 1", Arrays.asList("Kitchen"), 515, 123, 89, 85),
                new Room("Cafeteria", Arrays.asList(""), 61, 289, 235, 136),
                new Room("Terminal 1", Arrays.asList("Terminal"), 378, 290, 67, 134),
                new Room("Terminal 2", Arrays.asList("Terminal"), 451, 289, 63, 136),
                new Room("CR", Arrays.asList("Comfort Room, Bathroom"), 56, 626, 72, 90),
                new Room("Classroom", Arrays.asList(""), 127, 626, 82, 88),
                new Room("Classroom", Arrays.asList(""), 211, 626, 83, 90),
                new Room("Lobby", Arrays.asList(""), 294, 626, 87, 91),
                new Room("Computer Laboratory", Arrays.asList("Com lab"), 378, 627, 87, 90),
                new Room("Computer Laboratory", Arrays.asList("Com lab"), 464, 628, 85, 89),
                new Room("Female CR", Arrays.asList("Comfort Room, Bathroom, CR"), 548, 627, 52, 88),
                new Room("Accreditation Room", Arrays.asList(""), 596, 628, 88, 84),
                new Room("Admin Office", Arrays.asList("Admin"), 676, 624, 56, 90),
                new Room("Dean Office", Arrays.asList(" "), 734, 626, 32, 90),
                new Room("Room 101", Arrays.asList("101"), 595, 36, 88, 88),
                new Room("Accountancy Department", Arrays.asList(""), 679, 32, 92, 88),
                new Room("Room 103", Arrays.asList("103"), 763, 36, 90, 88),
                new Room("Male CR", Arrays.asList("Comfort Room, Bathroom, CR"), 847, 34, 92, 88),
                new Room("Room 104", Arrays.asList("104"), 933, 36, 88, 86),
                new Room("Room 105", Arrays.asList("105"), 1017, 32, 90, 86),
                new Room("Audio Visual Room", Arrays.asList("AVR"), 819, 372, 120, 86),
                new Room("Faculty Room", Arrays.asList(""), 933, 368, 58, 88),
                new Room("Department of Tourism Management and Hospitality Management", Arrays.asList(""), 985, 374, 86, 86),
                new Room("Entrep Lab", Arrays.asList(""), 1069, 372, 84, 86),
                new Room("Department", Arrays.asList(""), 1187, 118, 100, 106),
                new Room("Department", Arrays.asList(""), 1185, 240, 106, 118),
                new Room("Lobby", Arrays.asList(""), 767, 622, 82, 92),
                new Room("Business Admin Department", Arrays.asList("Admin"), 851, 626, 82, 86),
                new Room("Extension and Community Involvement", Arrays.asList("Research and Dev't Office"), 937, 628, 81, 87),
                new Room("Male CR", Arrays.asList("Comfort Room, Bathroom, CR"), 1020, 627, 46, 87),
                new Room("Food and Beverages Services Mock Room", Arrays.asList("Front Office Operation Mock Room", "Mock Room"), 1037, 509, 255, 85)

                ));
        cbeaRoomsByFloor.put(2, Arrays.asList(
            // none yet
        ));
        roomsByBuilding.put("CBEA", cbeaRoomsByFloor);

         //CHS
        Map<Integer, List<Room>> chsRoomsByFloor = new HashMap<>();
        chsRoomsByFloor.put(1, Arrays.asList(
                new Room("Comfort Room", Arrays.asList("CR"), 21, 10, 70, 90),
                new Room("Classroom", Arrays.asList(""), 88, 10, 133, 130),
                new Room("Classroom", Arrays.asList(""), 220, 10, 133, 132),
                new Room("Micro Lab", Arrays.asList(""), 351, 12, 134, 129),
                new Room("Anatomy Room", Arrays.asList(""), 483, 10, 133, 135),
                new Room("Stock Room", Arrays.asList(""), 21, 193, 126, 64),
                new Room("Nurses Accreditation Room", Arrays.asList(""), 615, 13, 129, 130),
                new Room("Administrative Office", Arrays.asList(""), 745, 9, 133, 134),
                new Room("Dean Office", Arrays.asList(""), 879, 11, 68, 130),
                new Room("Nursing Faculty Room", Arrays.asList("Faculty Room"), 945, 10, 266, 130),
                new Room("Nursing Lounge", Arrays.asList(""), 1141, 12, 64, 60),
                new Room("Comfort Room", Arrays.asList("CR"), 1205, 12, 110, 102),
                new Room("Audio Visual Room", Arrays.asList("AVR"), 1143, 254, 134, 276),
                new Room("Stock Room", Arrays.asList(""), 1141, 564, 28, 39),
                new Room("Comfort Room", Arrays.asList("CR"), 1140, 566, 55, 61),
                new Room("Pharmacy Faculty Room", Arrays.asList("Faculty"), 1194, 562, 147, 66),
                new Room("Classroom", Arrays.asList(""), 1339, 564, 90, 63),
                new Room("Pharmacy Accreditation Room", Arrays.asList(""), 1427, 563, 57, 64),
                new Room("Simulated Pharmacy", Arrays.asList(""), 1482, 561, 51, 66),
                new Room("Stock Room", Arrays.asList(""), 1452, 484, 83, 50)
        ));
        chsRoomsByFloor.put(2, Arrays.asList(
                new Room("Comfort Room", Arrays.asList("CR"), 25, 11, 66, 129),
                new Room("Stock Room", Arrays.asList(""), 21, 95, 70, 48),
                new Room("Stock Room", Arrays.asList(""), 23, 192, 124, 67),
                new Room("Conference Room", Arrays.asList(""), 87, 9, 266, 134),
                new Room("Classroom", Arrays.asList(""), 352, 9, 131, 131),
                new Room("Classroom", Arrays.asList(""), 479, 11, 137, 130),
                new Room("Classroom", Arrays.asList(""), 612, 9, 132, 136),
                new Room("Classroom", Arrays.asList(""), 740, 10, 138, 132),
                new Room("Classroom", Arrays.asList(""), 872, 8, 132, 134),
                new Room("Research and Extension Room", Arrays.asList(""), 1002, 12, 202, 132),
                new Room("Comfort Room", Arrays.asList("CR"), 1202, 10, 102, 102),
                new Room("Stock Room", Arrays.asList(""), 1156, 188, 112, 66),
                new Room("Laboratory 1", Arrays.asList(""), 1134, 252, 134, 278),
                new Room("Stock Room", Arrays.asList(""), 1134, 560, 30, 40),
                new Room("Comfort Room", Arrays.asList("CR"), 1134, 562, 56, 60),
                new Room("Laboratory 2", Arrays.asList(""), 1188, 554, 150, 66),
                new Room("Laboratory 3", Arrays.asList(""), 1132, 560, 198, 64)
        ));
        roomsByBuilding.put("CHS", chsRoomsByFloor);

        //cas
        Map<Integer, List<Room>> casRoomsByFloor = new HashMap<>();
        casRoomsByFloor.put(1, Arrays.asList(
                new Room("114", Arrays.asList("Bio Instrument Room"), 6, 83, 134, 82),
                new Room("115", Arrays.asList("Biology Department"), 5, 161, 136, 83),
                new Room("116", Arrays.asList("Zoology Laboratory Room"), 6, 240, 134, 155),
                new Room("chemical room", Arrays.asList(), 7, 393, 71, 86),
                new Room("stock room", Arrays.asList(), 76, 394, 62, 85),
                new Room("stock room", Arrays.asList(), 157, 660, 38, 33),
                new Room("stock room", Arrays.asList(), 818, 394, 64, 84),
                new Room("117A", Arrays.asList(), 7, 478, 133, 74),
                new Room("117B", Arrays.asList(), 7, 553, 132, 80),
                new Room("Botany Laboratory Room", Arrays.asList(), 6, 477, 134, 158),
                new Room("student affairs coordinator", Arrays.asList(), 7, 633, 91, 37),
                new Room("reading center", Arrays.asList("119"), 196, 660, 114, 123),
                new Room("Audio Visual Room", Arrays.asList("AVR","120"), 308, 659, 115, 125),
                new Room("Accreditation room", Arrays.asList(), 613, 693, 79, 87),
                new Room("Secretary's room", Arrays.asList(), 690, 657, 112, 39),
                new Room("Dean's Office", Arrays.asList(), 690, 693, 113, 87),
                new Room("Supply and Printing Section", Arrays.asList(), 876, 614, 75, 46),
                new Room("104", Arrays.asList("Physics laboratory room"), 816, 478, 135, 140),
                new Room("105B", Arrays.asList("Physics Room"), 879, 392, 70, 89),
                new Room("105A", Arrays.asList("Physics laboratory room"), 816, 240, 132, 156),
                new Room("106", Arrays.asList("Physics laboratory room"), 816, 161, 133, 83),
                new Room("Guidance and Counseling Unit", Arrays.asList(), 816, 117, 134, 47),
                new Room("107", Arrays.asList("consultation room"), 815, 82, 135, 40),
                new Room("108", Arrays.asList("laboratory"), 642, 3, 131, 116),
                new Room("109", Arrays.asList("Speech laboratory"), 562, 2, 84, 116),
                new Room("110", Arrays.asList(), 484, 3, 81, 116),
                new Room("111A", Arrays.asList(), 404, 55, 81, 65),
                new Room("111B", Arrays.asList(), 405, 2, 82, 58),
                new Room("Molecular Laboratory", Arrays.asList(), 404, 3, 160, 56),
                new Room("Biotechnology Laboratory", Arrays.asList(), 405, 55, 60, 64),
                new Room("Microbiology laboratory", Arrays.asList(), 246, 3, 162, 117),
                new Room("112A", Arrays.asList(), 330, 7, 76, 112),
                new Room("112B", Arrays.asList(), 246, 4, 85, 118)

        ));
        casRoomsByFloor.put(2, Arrays.asList(
                new Room("215", Arrays.asList(), 4, 78, 136, 87),
                new Room("216", Arrays.asList("Chemistry Laboratory"), 5, 159, 135, 165),
                new Room("214", Arrays.asList("Chemistry Laboratory"), 168, 1, 143, 118),
                new Room("213", Arrays.asList("Chemistry Laboratory"), 311, 1, 144, 116),
                new Room("212", Arrays.asList("Chemistry Laboratory"), 454, 1, 96, 119),
                new Room("217", Arrays.asList("Chemistry Instrument room"), 5, 322, 134, 84),
                new Room("218", Arrays.asList("Chemistry stock room"), 6, 402, 132, 82),
                new Room("219", Arrays.asList("Chemistry Laboratory"), 5, 482, 136, 150),
                new Room("Research and Extension Office", Arrays.asList(), 5, 631, 94, 39),
                new Room("221", Arrays.asList("Stock room"), 176, 661, 20, 22),
                new Room("222", Arrays.asList("Chemistry Instrument room"), 194, 660, 104, 122),
                new Room("223", Arrays.asList("English department"), 295, 660, 128, 124),
                new Room("224A", Arrays.asList("Math Room"), 421, 660, 43, 123),
                new Room("224B", Arrays.asList("Reading Center"), 462, 660, 41, 123),
                new Room("Internet Station", Arrays.asList(), 536, 735, 78, 35),
                new Room("201", Arrays.asList("Mathematics Department"), 613, 656, 81, 127),
                new Room("202", Arrays.asList("Social Science Department"), 693, 656, 114, 128),
                new Room("203", Arrays.asList("Stock Room"), 804, 656, 18, 21),
                new Room("SC office", Arrays.asList("Student Council"), 868, 616, 83, 42),
                new Room("204", Arrays.asList(), 820, 543, 132, 73),
                new Room("205", Arrays.asList("Conference Room"), 819, 482, 133, 63),
                new Room("206", Arrays.asList(), 819, 402, 133, 83),
                new Room("207", Arrays.asList(), 818, 321, 135, 85),
                new Room("208", Arrays.asList(), 818, 240, 133, 83),
                new Room("209", Arrays.asList(), 819, 159, 133, 83),
                new Room("210", Arrays.asList(), 819, 77, 133, 83),
                new Room("211A", Arrays.asList("Computer Lab"), 638, 1, 83, 116),
                new Room("211B", Arrays.asList("Faculty Room"), 549, 1, 91, 117)


        ));
        roomsByBuilding.put("CAS", casRoomsByFloor);

        //cab
        Map<Integer, List<Room>> cabRoomsByFloor = new HashMap<>();
        cabRoomsByFloor.put(1, Arrays.asList(
                new Room("1", Arrays.asList("room 1", "lecture room 1"), 12, 258, 128, 81),
                new Room("1", Arrays.asList("room 1", "lecture room 1"), 11, 430, 130, 91),
                new Room("2", Arrays.asList("room 2", "lecture room 2"), 12, 169, 128, 88),
                new Room("2", Arrays.asList("room 2", "lecture room 2"), 11, 522, 130, 91),
                new Room("3", Arrays.asList("room 3", "lecture room 3"), 12, 8, 128, 84),
                new Room("3", Arrays.asList("room 3", "lecture room 3"), 192, 657, 109, 118),
                new Room("4", Arrays.asList("room 4", "lecture room 4"), 658, 696, 103, 76),
                new Room("4", Arrays.asList("room 4", "lecture room 4"), 171, 6, 75, 114),
                new Room("5", Arrays.asList("room 5", "lecture room 5"), 813, 521, 127, 91),
                new Room("5", Arrays.asList("room 5", "lecture room 5"), 246, 8, 76, 111),
                new Room("6", Arrays.asList("room 6", "lecture room 6"), 813, 430, 128, 90),
                new Room("6", Arrays.asList("room 6", "lecture room 6"), 321, 6, 76, 114),
                new Room("7", Arrays.asList("room 7", "lecture room 7"), 396, 7, 75, 112),
                new Room("8", Arrays.asList("room 8", "lecture room 8"), 472, 7, 75, 111),
                new Room("9", Arrays.asList("room 9", "lecture room 9"), 547, 6, 75, 113),
                new Room("10", Arrays.asList("room 10", "lecture room 10"), 622, 7, 75, 113),
                new Room("11", Arrays.asList("room 11", "lecture room 11"), 698, 6, 67, 112),
                new Room("12", Arrays.asList("room 12", "lecture room 12"), 813, 172, 127, 87),
                new Room("13", Arrays.asList("room 13", "lecture room 13"), 812, 259, 130, 81),
                new Room("faculty room", Arrays.asList("faculty"), 105, 656, 43, 41),
                new Room("faculty room", Arrays.asList("faculty"), 802, 652, 60, 42),
                new Room("deans secretary", Arrays.asList("secretary"),301 , 655, 120, 49),
                new Room("deans office", Arrays.asList("deans"),303 , 721, 60, 51),
                new Room("deans office", Arrays.asList("deans"),303 , 721, 60, 51),
                new Room("deans office", Arrays.asList("deans"),626 , 696, 33, 76),
                new Room("faculty quarters", Arrays.asList("faculty"),534 , 653, 91, 20)
        ));
        cabRoomsByFloor.put(2, Arrays.asList(
                new Room("room 7", Arrays.asList("7", "lecture room 7"), 42, 414, 124, 84),
                new Room("room 8", Arrays.asList("8", "lecture room 8"), 41, 500, 124, 87),
                new Room("room 9", Arrays.asList("9", "lecture room 9"), 215, 626, 105, 114),
                new Room("room 10", Arrays.asList("10", "lecture room 10"), 321, 625, 104, 114),
                new Room("room 11", Arrays.asList("11", "lecture room 11"), 426, 626, 60, 115),
                new Room("room 12", Arrays.asList("12", "lecture room 12"), 488, 627, 67, 113),
                new Room("room 13", Arrays.asList("13", "lecture room 13"), 556, 626, 103, 114),
                new Room("room 13", Arrays.asList("13", "lecture room 13"), 41, 157, 124, 82),
                new Room("room 14", Arrays.asList("14", "lecture room 14"), 658, 625, 103, 117),
                new Room("room 14", Arrays.asList("14", "lecture room 14"), 42, 241, 122, 83),
                new Room("room 15", Arrays.asList("15", "lecture room 15"), 806, 499, 127, 86),
                new Room("room 16", Arrays.asList("16", "lecture room 16"), 812, 414, 120, 82),
                new Room("room 16", Arrays.asList("16", "lecture room 16"), 194, 5, 72, 108),

                new Room("room 17", Arrays.asList("17", "lecture room 17"), 267, 5, 72, 107),
                new Room("room 18", Arrays.asList("18", "lecture room 18"), 339, 5, 72, 107),
                new Room("room 19", Arrays.asList("19", "lecture room 19"), 410, 5, 72, 107),
                new Room("room 20", Arrays.asList("20", "lecture room 20"), 483, 5, 72, 107),
                new Room("room 21", Arrays.asList("21", "lecture room 21"), 555, 5, 72, 107),
                new Room("room 22", Arrays.asList("22", "lecture room 22"), 627, 5, 72, 107),
                new Room("room 23", Arrays.asList("23", "lecture room 23"), 699, 5, 66, 107),
                new Room("room 24", Arrays.asList("24", "lecture room 24"), 810, 158, 123, 83),
                new Room("room 25", Arrays.asList("25", "lecture room 25"), 810, 241, 125, 86)


        ));
        roomsByBuilding.put("CAB", cabRoomsByFloor);
        // Add more buildings and rooms


    }

    private final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            List<String> roomNames = Arrays.asList(query.split(","));
            searchRooms(roomNames);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return true;
        }
    };

    private void searchRooms(List<String> roomNames) {
        boolean roomsNotFound = true;
        clearHighlights(); // Call the method 1to clear the previous highlights

        Map<Integer, List<Room>> roomsByFloor = roomsByBuilding.get(currentBuilding);
        if (roomsByFloor != null) {
            List<Room> currentFloorRooms = roomsByFloor.get(currentFloor);
            if (currentFloorRooms != null) {
                for (Room room : currentFloorRooms) {
                    String roomNameLowerCase = room.getName().toLowerCase();
                    List<String> roomSynonymsLowerCase = room.getSynonyms().stream().map(String::toLowerCase).collect(Collectors.toList());

                    for (String roomName : roomNames) {
                        if (roomNameLowerCase.equals(roomName.toLowerCase()) || roomSynonymsLowerCase.contains(roomName.toLowerCase())) {
                            highlightRoom(room.getX(), room.getY(), room.getWidth(), room.getHeight());
                            roomsNotFound = false;
                            break;
                        }
                    }
                }
            }
        }
        if (roomsNotFound) {
            Toast.makeText(requireContext(), "Rooms not found", Toast.LENGTH_SHORT).show();
        }
    }





    private void highlightRoom(int roomX, int roomY, int roomWidth, int roomHeight) {
        if (mutableBitmap != null) {
            Canvas canvas = new Canvas(mutableBitmap);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            canvas.drawRect(roomX, roomY, roomX + roomWidth, roomY + roomHeight, paint);
            floorPlanImage.setImageBitmap(mutableBitmap);
        } else {
            Toast.makeText(requireContext(), "Cannot load floor plan image as bitmap", Toast.LENGTH_SHORT).show();
        }
    }


////// Building name spinner
    private void setupSpinner(String building) {
        List<String> buildings = new ArrayList<>(Arrays.asList("CAB", "COE", "CAS", "CAFSD", "CBEA","CHS", "ADMIN"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, buildings) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setText(getItem(position));
                return view;
            }


        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(adapter);

        int position = adapter.getPosition(building);
        buildingSpinner.setSelection(position);

        buildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentBuilding = parent.getItemAtPosition(position).toString();
                updateBuildingAndFloorLabel();
                updateAutoCompleteAdapter(autoCompleteSearch, currentBuilding, currentFloor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

    }

    private void setupButton() {
        viewFloorPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a dialog to choose the floor
                String[] floors = new String[]{"1st Floor", "2nd Floor"};
                new AlertDialog.Builder(requireContext())
                        .setTitle("Select Floor")
                        .setSingleChoiceItems(floors, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                currentFloor = which + 1;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateBuildingAndFloorLabel();
                                loadFloorPlanImage();
                                updateAutoCompleteAdapter(autoCompleteSearch, currentBuilding, currentFloor);
                                dialog.dismiss();
                            }
                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    private void updateBuildingAndFloorLabel() {
        String floorText = currentFloor == 1 ? "1st Floor" : "2nd Floor";
        currentBuildingAndFloorLabel.setText(String.format(Locale.ROOT, "%s, %s", currentBuilding, floorText));
    }


    private void loadFloorPlanImage() {
        imageName = String.format(Locale.ROOT, "%s_floor_%d", currentBuilding.toLowerCase(Locale.ROOT), currentFloor);
        int resourceId = getResources().getIdentifier(imageName, "drawable", requireContext().getPackageName());
        if (resourceId != 0) {
            Drawable drawable = ContextCompat.getDrawable(requireContext(), resourceId);

            if (drawable instanceof BitmapDrawable) {
                originalBitmap = ((BitmapDrawable) drawable).getBitmap();
                mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
                floorPlanImage.setImageBitmap(mutableBitmap);
            } else {
                Toast.makeText(requireContext(), "Cannot load floor plan image as bitmap", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "Floor plan not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearHighlights() {
        if (originalBitmap != null) {
            mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
            floorPlanImage.setImageBitmap(mutableBitmap);
        } else {
            Toast.makeText(requireContext(), "Cannot clear highlights", Toast.LENGTH_SHORT).show();
        }
    }




    public static class Room {
        private String name;
        private List<String> synonyms;
        private int x;
        private int y;
        private int width;
        private int height;

        public Room(String name, List<String> synonyms, int x, int y, int width, int height) {
            this.name = name;
            this.synonyms = synonyms;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        public String getName() {
            return name;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
        public List<String> getSynonyms() {
            return synonyms;
        }

        public void setSynonyms(List<String> synonyms) {
            this.synonyms = synonyms;
        }
    }

}

