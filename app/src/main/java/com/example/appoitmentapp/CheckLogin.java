package com.example.appoitmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class CheckLogin extends AppCompatActivity {


    private int  SPLASH_TIME_OUT=1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_login);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(UsersEntrance.PREFS_NAME,0);
                boolean hasLoggedIn= sharedPreferences.getBoolean("hasLoggedIn",false);
                if(hasLoggedIn){
                    Intent checkLogToUserProfile =new Intent(CheckLogin.this, ActivityUserLoggedIn.class);
                    startActivity(checkLogToUserProfile);
                    finish();
                }else{
                    Intent checkLoginToUserLogIn =new Intent(CheckLogin.this,UsersEntrance.class);
                    startActivity(checkLoginToUserLogIn);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
    }
}