package com.example.gonav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class LegendsFragment extends Fragment {

    private static final Item[] ITEMS = new Item[] {

            new Item("MMSU Admin", R.drawable.mmsuicon),
            new Item("College of Engineering ", R.drawable.coeicon),
            new Item("Communication Arts Building", R.drawable.college_pin_svgrepo_com),
            new Item("College of Art and Sciences", R.drawable.casicon),
            new Item("College of Agriculture, Food, and Sustainable development", R.drawable.cafsdicon),
            new Item("College of Medicine ", R.drawable.comicon),
            new Item("College of Health and Sciences ", R.drawable.chsicon),
            new Item("Graduate School ", R.drawable.gsicon),
            new Item("College of Teacher Education", R.drawable.cteicon),
            new Item("College of Industrial Technology", R.drawable.citicon),
            new Item("College of Business Economics and Accountancy", R.drawable.cbeaicon),
            new Item("MMSU library", R.drawable.library_book_svgrepo_com),
            new Item("MMSU Parking areas", R.drawable.parking),

            new Item("MMSU Technology Park", R.drawable.bulb_svgrepo_com),
            new Item("Teatro Ilocandia", R.drawable.performing_arts_svgrepo_com),
            new Item("Food Processing Innovation center", R.drawable.mortar_svgrepo_com),
            new Item("MMSU Fitness Center", R.drawable.dumbbell_gym_svgrepo_com),
            new Item("MMSU Covered Court", R.drawable.court_playground_svgrepo_com),
            new Item("MMSU Cooperative", R.drawable.teamwork_group_svgrepo_com),
            new Item("MMSU Chapel", R.drawable.church_chapel_svgrepo_com__1_),
            new Item("National Bioenergy Research and Innovation center", R.drawable.cells_biology_svgrepo_com),
            new Item("MMSU CLinic", R.drawable.hospital_svgrepo_com),
            new Item("MMSU Dormitories", R.drawable.bunk_hostel_svgrepo_com),
            new Item("MMSU Mansion", R.drawable.school),
            new Item("UTC", R.drawable.book_svgrepo_com),


            new Item("Points of Interest", R.drawable.point_of_interest_svgrepo_com),
            new Item("Canteen/food area", R.drawable.food_stall_stall_svgrepo_com),
            new Item("Gates", R.drawable.gate_sign_svgrepo_com),
            new Item("Pool", R.drawable.pool_svgrepo_com),


    };

    public LegendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_legends, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ItemAdapter(ITEMS));

        return rootView;
    }

    private static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

        private final Item[] mItems;

        public ItemAdapter(Item[] items) {
            mItems = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(mItems[position]);
        }

        @Override
        public int getItemCount() {
            return mItems.length;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            private final ImageView mIconView;
            private final TextView mLabelView;

            public ViewHolder(View itemView) {
                super(itemView);
                mIconView = itemView.findViewById(R.id.item_icon);
                mLabelView = itemView.findViewById(R.id.item_label);
            }

            public void bind(Item item) {
                mIconView.setImageResource(item.getIconResourceId());
                mLabelView.setText(item.getLabel());
            }

        }

    }

    private static class Item {

        private final String mLabel;
        private final int mIconResourceId;

        public Item(String label, int iconResourceId) {
            mLabel = label;
            mIconResourceId = iconResourceId;
        }

        public String getLabel() {
            return mLabel;
        }

        public int getIconResourceId() {
            return mIconResourceId;
        }

    }

}
