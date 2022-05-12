package com.example.appoitmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsersEntrance extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    private EditText userInputPassword,userInputEmail;
    private Button btnUserSignUp ,btnUserLogin,btnUserForget;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_entrance);
        userInputEmail = findViewById(R.id.userEmailLoginInput);
        userInputPassword = findViewById(R.id.userPasswordLoginInput);


        btnUserLogin = findViewById(R.id.btnUserLogIn);
        btnUserForget=findViewById(R.id.btnUserForgetPassword);
        btnUserSignUp=findViewById(R.id.btnUserSignUp);
        ImageButton btnGoToMain =findViewById(R.id.btnGoToMain3);
        progressBar= findViewById(R.id.progressBar);

        mAuth =FirebaseAuth.getInstance();

       btnUserLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            userLogin();

           }
       });

        btnUserSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUserSignUp.setBackgroundColor(Color.RED);
                btnUserSignUp.setTextColor(Color.WHITE);
                Intent userSignUp = new Intent(getApplicationContext(),UserSignUp.class);
                startActivity(userSignUp);
                finish();

            }
        });
        btnUserForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerView3,FargmentUserForgetPassword.class,null).setReorderingAllowed(true).addToBackStack(null).commit();
                btnUserSignUp.setVisibility(View.GONE);

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

    private void userLogin() {
        String textPassword=userInputPassword.getText().toString().trim();
        String userEmailInput=userInputEmail.getText().toString().trim();

        if (userEmailInput.isEmpty()){
            userInputEmail.setError("Email is required!");
            userInputEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmailInput).matches()){
            userInputEmail.setError("Please provides a valid email !");
            userInputEmail.requestFocus();
            return;
        }
        if(textPassword.isEmpty()){
            userInputPassword.setError("password is required!");
            userInputPassword.requestFocus();
            return;
        }
        if(textPassword.length()< 6){
            userInputPassword.setError("Min password length is 6 charchters!");
            userInputPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(userEmailInput,textPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //redirect to user profile
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        SharedPreferences sharedPreferences =getSharedPreferences(UsersEntrance.PREFS_NAME,0);
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putBoolean("hasLoggedIn",true);
                        editor.commit();

                        Toast.makeText(UsersEntrance.this,"login Succseeded!",Toast.LENGTH_LONG).show();
                        Intent UserLogin = new Intent(getApplicationContext(), ActivityUserLoggedIn.class);
                        startActivity(UserLogin);
                        finish();

                    } else{
                        user.sendEmailVerification();
                        Toast.makeText(UsersEntrance.this,"Check your email to verify your account! ",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                } else{
                    Toast.makeText(UsersEntrance.this,"Failed to login! \n please check your credentials",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        btnUserSignUp.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }
}