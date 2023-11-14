package com.example.gonav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.TutorialViewHolder> {

    private int[] tutorialImages;
    private Context context;

    public TutorialAdapter(Context context, int[] tutorialImages) {
        this.context = context;
        this.tutorialImages = tutorialImages;
    }

    @NonNull
    @Override
    public TutorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutorial_page, parent, false);
        return new TutorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialViewHolder holder, int position) {
        holder.tutorialImage.setImageResource(tutorialImages[position]);
    }

    @Override
    public int getItemCount() {
        return tutorialImages.length;
    }

    public static class TutorialViewHolder extends RecyclerView.ViewHolder {

        ImageView tutorialImage;

        public TutorialViewHolder(@NonNull View itemView) {
            super(itemView);
            tutorialImage = itemView.findViewById(R.id.tutorial_image);
        }
    }
}
