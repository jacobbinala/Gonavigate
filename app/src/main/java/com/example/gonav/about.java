package com.example.gonav;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass representing the About page.
 */
public class about extends Fragment {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView noteContent = view.findViewById(R.id.note_content);

        SpannableString bullet1 = createBulletSpan("   Dark mode is currently unsupported.\n\n");
        SpannableString bullet2 = createBulletSpan("   Google services is required.\n\n");
        SpannableString bullet3 = createBulletSpan("   indoor map (classroom map) is in development thus we require user feedback to improve this feature.\n\n");
        SpannableString bullet4 = createBulletSpan("   We kindly request your participation in the User Acceptance Testing for feedbacks. Thank you! ");

        SpannableString hyperlink = new SpannableString("https://forms.gle/XYdkhLfAoPChCtLd8");
        hyperlink.setSpan(new URLSpan("https://forms.gle/XYdkhLfAoPChCtLd8"), 0, hyperlink.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        noteContent.setText(TextUtils.concat(bullet1, bullet2, bullet3, bullet4, hyperlink));
        noteContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private SpannableString createBulletSpan(String text) {
        SpannableString spannableString = new SpannableString(text);
        Drawable bullet = ContextCompat.getDrawable(requireContext(), R.drawable.bullet_point);
        if (bullet != null) {
            bullet.setBounds(0, 0, bullet.getIntrinsicWidth(), bullet.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(bullet, DynamicDrawableSpan.ALIGN_BASELINE);
            spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }


    public about() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}
