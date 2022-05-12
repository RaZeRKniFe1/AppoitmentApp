package com.example.appoitmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UserSignUp extends AppCompatActivity {

    private EditText fullName, passwordInput,emailInput,phoneInput;
    private TextView termsOfUse;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private CheckBox checkBox;
    private Button signUpNewUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        ConstraintLayout constrainRemoval=findViewById(R.id.constRemove);
        ImageButton ImageBtnGoBack=findViewById(R.id.btnGoToMainUser);
        checkBox=findViewById(R.id.termsOfUseCheckBox);
        termsOfUse=findViewById(R.id.termsOfUseTxtBtn);
        fullName=findViewById(R.id.txtInputUserFullname);
        passwordInput =findViewById(R.id.txtInputUserPassword);
        emailInput=findViewById(R.id.txtInputUserEmail);
        phoneInput=findViewById(R.id.txtInputUserPhone);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        mAuth = FirebaseAuth.getInstance();
        signUpNewUser=findViewById(R.id.btnUserSignUpFinish);

        termsOfUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager goToTermsPage = getSupportFragmentManager();
                goToTermsPage.beginTransaction().replace(R.id.fragmentContainerView4,FargmentTermsOfUse.class,null).setReorderingAllowed(true).addToBackStack(null).commit();
                constrainRemoval.setVisibility(View.GONE);
                signUpNewUser.setVisibility(View.GONE);
            }
        });

        signUpNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            isOkParameters();
            }
        });

        ImageBtnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userEntrance = new Intent(getApplicationContext(),UsersEntrance.class);
                startActivity(userEntrance);
                finish();
            }
        });

    }

    private void RegisterUser() {
        String name=fullName.getText().toString();
        String password= passwordInput.getText().toString();
        String email=emailInput.getText().toString();
        String phone=phoneInput.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    NewUser newUser = new NewUser(name, password, email, phone);
                    FirebaseDatabase.getInstance().getReference("AuthUsers").
                            child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).
                            setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mAuth.getCurrentUser().sendEmailVerification();
                                Toast.makeText(getApplicationContext(), "User added Successfully!\nWelcome " + name+"\n please Verifed your email", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            } else
                                Toast.makeText(getApplicationContext(), " The Email  already used!!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "Not Good " + name + " \nThe Email "+email+ "\nalready used!!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

        });
    }

    public  void isOkParameters(){
        if (TextUtils.isEmpty(fullName.getText().toString().trim())) {
            fullName.setError("full Name cant be empty");
            fullName.requestFocus();
            return;
        } else if(!FullNameWithoutNumbers(fullName.getText().toString().trim())){
            fullName.setError("please enter your full Name! it must be only in letters!");
            fullName.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(passwordInput.getText().toString().trim())) {
            passwordInput.setError("password cant be empty");
            passwordInput.requestFocus();
            return;
        }else if(!passwordStrong(passwordInput.getText().toString().trim())){
            passwordInput.setError("password must be with letters and numbers");
            passwordInput.requestFocus();
            return;
        }else if (passwordInput.getText().toString().length() <6){
            passwordInput.setError("min password length should be 6 Characters!");
            passwordInput.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(emailInput.getText().toString().trim())) {
            emailInput.setError("Email cannot be empty");
            emailInput.requestFocus();
            return;
        } else if(!emailValid(emailInput.getText().toString().trim())){
            emailInput.setError("email need to be a valid");
            emailInput.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(phoneInput.getText().toString().trim())) {
            phoneInput.setError("phone cant be empty ");
            phoneInput.requestFocus();
            return;
        }else if(phoneInput.getText().toString().length() != 10){
            phoneInput.setError("You need to put 10 digit number");
            phoneInput.requestFocus();
            return;
        }else if(!checkBox.isChecked()){
            checkBox.setError("You must to agree \n the terms and conditions");
            Toast.makeText(UserSignUp.this,"You must to agree \n the terms & conditions",Toast.LENGTH_LONG).show();
            return;
        }

        else{
            progressBar.setVisibility(View.VISIBLE);
            RegisterUser();
        }

    }
    public boolean FullNameWithoutNumbers(String name) {
        return name.matches("[a-zA-Z]+[ ][a-zA-Z]+");
    }
    public boolean passwordStrong(String name) {
        return name.matches("[a-zA-Z0-9]+");
    }

    public boolean emailValid(String name) {

        return name.matches("[a-zA-Z0-9]+[@]+[a-zA-Z]+[.]+[a-z]+");
    }
}
