package com.example.gonav.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gonav.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {



    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        WebView mWebView = binding.webviewHome;

        mWebView.setWebViewClient(new WebViewClient()); // Enable navigation within the WebView


        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String url = "https://www.mmsu.edu.ph/about/directory";
        mWebView.loadUrl(url);


        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
