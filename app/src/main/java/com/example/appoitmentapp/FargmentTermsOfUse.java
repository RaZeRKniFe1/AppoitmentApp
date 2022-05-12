package com.example.appoitmentapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

import org.sufficientlysecure.htmltextview.HtmlAssetsImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

public class FargmentTermsOfUse extends Fragment {

    WebView webView; //declare of the Html melel
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View v=inflater.inflate(R.layout.fragment_terms_of_use, container, false);

            webView = v.findViewById(R.id.webView);
             WebSettings webSettings =webView.getSettings();
             webSettings.setJavaScriptEnabled(true); // lets the file run on JavaScript and HTML
            webView.loadUrl("file:///android_asset/TermsAndUse.html"); // where the file is found

            webView.setWebViewClient(new WebViewClient());

            ImageButton btnGoBackUserSignUP =v.findViewById(R.id.btnGoBackUserSignUp);
            btnGoBackUserSignUP.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent backToUserSignUp= new Intent(getContext(), UserSignUp.class);
                    startActivity(backToUserSignUp);
                    getContext().startActivity(backToUserSignUp);

                }
            });
            return v;
    }
}