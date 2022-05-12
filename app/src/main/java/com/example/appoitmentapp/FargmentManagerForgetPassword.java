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


public class FargmentManagerForgetPassword extends Fragment {
    private EditText emailEditText;
    ImageButton btnGoBackManager, managerResetPasswordBtn;
    private ProgressBar progressBar;

    FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_manager_forget_password, container, false);

        btnGoBackManager=v.findViewById(R.id.btnGoBackManager);
        emailEditText =v.findViewById(R.id.inputEmailForgetManager);
        managerResetPasswordBtn=v.findViewById(R.id.btnGmailLogoManager);
        progressBar=v.findViewById(R.id.progressBar2);

        auth=FirebaseAuth.getInstance();

        managerResetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        btnGoBackManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent backToManager= new Intent(getContext(), ManagerEntrance.class);
                startActivity(backToManager);
                getContext().startActivity(backToManager);
            }
        });
        return v;

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