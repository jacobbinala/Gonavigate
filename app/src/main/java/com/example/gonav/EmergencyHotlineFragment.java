package com.example.gonav;import android.os.Bundle;import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class EmergencyHotlineFragment extends Fragment {

    private LinearLayout linearLayout;
    private List<EmergencyContact> contacts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_emergency_hotline, container, false);
        linearLayout = rootView.findViewById(R.id.linearLayout);

        initializeContacts();
        populateContactList();

        return rootView;
    }

    private void initializeContacts() {
        contacts = new ArrayList<>();

        // General Hotline
        contacts.add(new EmergencyContact("General Hotline", "PNP - Batac City(Smart)", "09294747860"));
        contacts.add(new EmergencyContact("General Hotline", "PNP - Batac City(Globe)", "09162406552"));
        contacts.add(new EmergencyContact("General Hotline", "Mariano Marcos Memorial Hospital and Medical Center (MMMH & MC)", "6008000"));
        contacts.add(new EmergencyContact("General Hotline", "Bureau of Fire Protection - Batac City", "7922115"));
        contacts.add(new EmergencyContact("General Hotline", "Bureau of Fire Protection - Batac City (Globe)", "09065836071"));
        contacts.add(new EmergencyContact("General Hotline", "Ilocos Norte - Provincial Police Office (Globe)", "09663583852"));
        contacts.add(new EmergencyContact("General Hotline", "Ilocos Norte - Provincial Police Office (Smart)", "09393207455"));

        // Guidance Hotline
        contacts.add(new EmergencyContact("Guidance Hotline", "GERALDEEN B. PASCUAL, RGC-CBEA", "+639053107169"));
        contacts.add(new EmergencyContact("Guidance Hotline", "IRENE DLC. FLORES, RGC-CAFSD", "+639175022060"));
        contacts.add(new EmergencyContact("Guidance Hotline", "MADELYN Q. QUITORIANO-CASAT", "+639158307657"));
        contacts.add(new EmergencyContact("Guidance Hotline", "RODEL B. REYES-CAS", "+639513507757"));
        contacts.add(new EmergencyContact("Guidance Hotline", "SHAREID AGUILAR-CHS", "+639771560668"));
        contacts.add(new EmergencyContact("Guidance Hotline", "ROMELYN N. PABRO-COE", "+639513507752"));
        contacts.add(new EmergencyContact("Guidance Hotline", "DIAN ANTONETTE N. REANTILLO-CIT", "+639517226639"));
        contacts.add(new EmergencyContact("Guidance Hotline", "ROSELIA A. BORROMEO-CTE", "+639662346453"));
        contacts.add(new EmergencyContact("Guidance Hotline", "GINA SYLVIA S. GAOAT-CTE", "+639338694356"));
        contacts.add(new EmergencyContact("Guidance Hotline", "ALBERT P. LAYUS-LES Laoag", "+639302353035"));
        contacts.add(new EmergencyContact("Guidance Hotline", "ELSIE R. MARCELINO-LHS Laoag", "+639283695177"));
        contacts.add(new EmergencyContact("Guidance Hotline", "FELMA A. MAGNO-LES Batac", "+639269956164"));
        contacts.add(new EmergencyContact("Guidance Hotline", "MIGNOS CECILIA S. DIEGO-LHS Batac", "+639175154008"));
    }


    private void populateContactList() {
        String currentCategory = "";
        for (EmergencyContact contact : contacts) {
            if (!currentCategory.equals(contact.getCategory())) {
                currentCategory = contact.getCategory();
                linearLayout.addView(createCategoryTitleView(currentCategory));
            }
            linearLayout.addView(createContactView(contact));
        }
    }

    private TextView createCategoryTitleView(String category) {
        TextView categoryName = new TextView(getContext());
        categoryName.setText(category);
        categoryName.setTextAppearance(android.R.style.TextAppearance_Large);
        categoryName.setPadding(0, 16, 0, 8);
        return categoryName;
    }

    private View createContactView(EmergencyContact contact) {
        View view = getLayoutInflater().inflate(R.layout.contact_item, null, false);

        TextView contactName = view.findViewById(R.id.contactName);
        ImageView quickDial = view.findViewById(R.id.quickDial);

        contactName.setText(contact.getName());
        quickDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialConfirmationDialog(contact.getPhoneNumber());
            }
        });

        return view;
    }

    private void showDialConfirmationDialog(String phoneNumber) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
        builder.setTitle("Dial Emergency Number");
        builder.setMessage("Do you want to call " + phoneNumber + "?");
        builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialPhoneNumber(phoneNumber);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            startActivity(intent);
        }
    }

    static class EmergencyContact {
        private String category;
        private String name;
        private String phoneNumber;

        public EmergencyContact(String category, String name, String phoneNumber) {
            this.category = category;
            this.name = name;
            this.phoneNumber = phoneNumber;
        }

        public String getCategory() {
            return category;
        }

        public String getName() {
            return name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }
    }
}

