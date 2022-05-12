package com.example.appoitmentapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appoitmentapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.net.URI;

public class NavUserCommunicate extends Fragment {


    Button buttonCall ,businessLocation;
    GoogleMap map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_nav_user_communicate, container, false);


        businessLocation=v.findViewById(R.id.btnBusinessLocation);
        buttonCall=v.findViewById(R.id.btnCallAdmin);

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCall.setBackgroundColor(Color.GREEN);
                Intent dialToAdmin =new Intent(Intent.ACTION_DIAL);
                dialToAdmin.setData(Uri.parse("tel:0523338769")); // set the phone of the admin
                startActivity(dialToAdmin);
                buttonCall.setBackgroundColor(Color.CYAN);

            }
        });
        businessLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent BusinessLocation = new Intent(getContext(), BusinessLocation.class);
                startActivity(BusinessLocation);


            }
        });

        return  v;
    }

}