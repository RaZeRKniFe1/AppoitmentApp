package com.example.appoitmentapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FargmentUserForgetPassword extends Fragment {
    private EditText emailEditText;
    private ImageButton btnBackToUserEntrance,userResetPasswordBtn;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_user_forget_password, container, false);

        btnBackToUserEntrance=v.findViewById(R.id.btnBack);
         userResetPasswordBtn= v.findViewById(R.id.btnGmailLogoManager);
        emailEditText =v.findViewById(R.id.inputEmailForgetManager);
        progressBar=v.findViewById(R.id.progressBar2);

        auth = FirebaseAuth.getInstance();

        userResetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });


        btnBackToUserEntrance.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                Intent backToUser= new Intent(getContext(), UsersEntrance.class);
               startActivity(backToUser);
               getContext().startActivity(backToUser);

            }
       });

        return  v;
    }
    private void resetPassword() {
        progressBar.setVisibility(View.VISIBLE);
        String email = emailEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("email is require!!");
            emailEditText.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please provide a valid Email !");
            emailEditText.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(getActivity(),"check your email to reset your password!!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(),"Try again! \n something wrong happened",Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }


}