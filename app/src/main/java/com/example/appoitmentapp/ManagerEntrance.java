package com.example.appoitmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ManagerEntrance extends AppCompatActivity {
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_entrance);

        Button btnManagerForget = findViewById(R.id.btnManagerForgetPassword);
        Button btnManagerLogIn =findViewById(R.id.btnManagerLogin);
        ImageButton btnGoToMain =findViewById(R.id.btnGoToMain2);

        btnManagerLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       btnManagerForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView5,FargmentManagerForgetPassword.class,null).setReorderingAllowed(true).addToBackStack(null).commit();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerView2,FargmentManagerForgetPassword.class,null).setReorderingAllowed(true).addToBackStack(null).commit();
            }
       });
       btnGoToMain.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent MainActivity = new Intent(getApplicationContext(), com.example.appoitmentapp.MainActivity.class);
               startActivity(MainActivity);
               finish();
           }
       });


    }
    }