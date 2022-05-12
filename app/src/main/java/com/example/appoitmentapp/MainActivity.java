package com.example.appoitmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnManagerEntrance = findViewById(R.id.BtnManagersEntrance);
        Button btnUserEntrance = findViewById(R.id.BtnUsersEntrance);
        btnManagerEntrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnManagerEntrance.setBackgroundColor(Color.RED);
                btnManagerEntrance.setTextColor(Color.BLACK);
                Intent managerEntrance = new Intent(getApplicationContext(),ManagerEntrance.class);
                startActivity(managerEntrance);
                finish();
            }
        });
        btnUserEntrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUserEntrance.setTextColor(Color.BLACK);
                btnUserEntrance.setBackgroundColor(Color.RED);
                Intent userEntrance = new Intent(getApplicationContext(),UsersEntrance.class);
                startActivity(userEntrance);
                finish();
            }
        });


    }


}
