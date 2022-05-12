package com.example.appoitmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityUserLoggedIn extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FireBase
    FirebaseUser currentUser;
    DatabaseReference referenceAuthUsers;
    FirebaseAuth mAuth;
    String userID; //Unique User ID from FireBase.

    //Views from XML
    TextView userEmail ,userFullName,userPhone;
    ImageView avatarIv;
    FloatingActionButton fab;

    //Navigator
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    FragmentManager fragmentManager;

    //Fab buttons
    FloatingActionButton fMain,changeNameFab,changePasswordFab,uploadPhotoFab;
    Float translationYaxis=100f;
    Boolean menuOpen =false;
    OvershootInterpolator interpolator =new OvershootInterpolator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged_in);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);
        toggle=new ActionBarDrawerToggle(this, drawer ,toolbar ,R.string.drawer_open,R.string.drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        userEmail=headerView.findViewById(R.id.nav_user_email_view);
        userFullName=headerView.findViewById(R.id.nav_user_name_view);
        userPhone=headerView.findViewById(R.id.nav_user_phone_view);
        avatarIv=findViewById(R.id.avatarIv);
        fab=findViewById(R.id.fab_main);

        mAuth =FirebaseAuth.getInstance();
        currentUser =mAuth.getCurrentUser();
        fragmentManager = getSupportFragmentManager();
        userID=mAuth.getCurrentUser().getUid().toString();
        referenceAuthUsers = FirebaseDatabase.getInstance().getReference().child("AuthUsers");
        nav_view=findViewById(R.id.nav_view);

        NotCurrentUser();
        ShowMenu();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
        super.onBackPressed();
    }}
    public void NotCurrentUser(){
        if (currentUser != null) {
            updateHeader();
        }else{
            Logout();
        }
    }
    private void Logout() {
        SharedPreferences sharedPreferences =getSharedPreferences(UsersEntrance.PREFS_NAME,0);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean("hasLoggedIn",false);
        editor.commit();
        Intent checkLoginToUserLogIn =new Intent(ActivityUserLoggedIn.this,UsersEntrance.class);
        startActivity(checkLoginToUserLogIn);
        Toast.makeText(getApplicationContext(), "You logout successfully ! \n hope we see you again!", Toast.LENGTH_LONG).show();
        finish();
    }
    public void updateHeader (){ //method that Show the users details from the firebase
        referenceAuthUsers.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fullName=snapshot.child("displayName").getValue().toString();
                String phone=snapshot.child("phoneNumber").getValue().toString();
                String email=snapshot.child("email").getValue().toString();
                userEmail.setText(email);
                userFullName.setText(fullName);
                userPhone.setText(phone);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Not Good!!", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        return super.onCreateOptionsMenu(menu);
    }
    private void ShowMenu() {
        fMain=findViewById(R.id.fab_main);
        changeNameFab =findViewById(R.id.fab_changeName);
       changePasswordFab=findViewById(R.id.fab_changePassword);
        uploadPhotoFab=findViewById(R.id.fab_uploadPhoto);

        changeNameFab.setAlpha(0f);
        changePasswordFab.setAlpha(0f);
        uploadPhotoFab.setAlpha(0f);

        uploadPhotoFab.setTranslationY(translationYaxis);
        changeNameFab.setTranslationY(translationYaxis);
        uploadPhotoFab.setTranslationY(translationYaxis);

        fMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuOpen){
                    CloseMenu();
                }else{
                    OpenMenu();
                }
            }
        });
        uploadPhotoFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityUserLoggedIn.this,"camera is clicked",Toast.LENGTH_SHORT).show();


            }
        });
        changeNameFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityUserLoggedIn.this,"changeName is clicked",Toast.LENGTH_SHORT).show();
                final EditText changeName = new EditText(v.getContext());

                final AlertDialog.Builder changeNameDialog = new AlertDialog.Builder(v.getContext());
                changeNameDialog.setTitle("Changing your Name?");
                changeNameDialog.setMessage("Enter Your full Name");
                changeNameDialog.setView(changeName);

                changeNameDialog.setPositiveButton("confirms", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newUserName = changeName.getText().toString().trim();
                        if(newUserName.isEmpty()){
                            Toast.makeText(ActivityUserLoggedIn.this, "Full Name can't be empty !", Toast.LENGTH_SHORT).show();
                            return;
                        }else if(!FullNameWithoutNumbers(changeName.getText().toString().trim())){
                            Toast.makeText(ActivityUserLoggedIn.this, "please enter your full Name! it must be only in letters!", Toast.LENGTH_SHORT).show();
                            return;
                        }else{

                             FirebaseDatabase.getInstance().getReference().child("AuthUsers").child(userID).child("displayName").setValue(newUserName);
                            Toast.makeText(ActivityUserLoggedIn.this, "Changed successfully!", Toast.LENGTH_SHORT).show();
                            updateHeader();
                        }
                    }
                });
                changeNameDialog.setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close
                    }
                });

                changeNameDialog.create().show();
            }
        });
        changePasswordFab.setOnClickListener(new View.OnClickListener() { //Button of fab when he clickes on it
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityUserLoggedIn.this,"changePassword is clicked",Toast.LENGTH_SHORT).show();
                final EditText resetPassword = new EditText(v.getContext());

                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter New password >6 Chrachter long");
                passwordResetDialog.setView(resetPassword);
                passwordResetDialog.setPositiveButton("confirms", new DialogInterface.OnClickListener() { //creates a new button of yes
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        {
                            String newPasword = resetPassword.getText().toString().trim();
                            if (newPasword.isEmpty()) {
                                Toast.makeText(ActivityUserLoggedIn.this, "Password is require", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (newPasword.length() < 6) {
                                Toast.makeText(ActivityUserLoggedIn.this, "Password need to be more than 6 charcters", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                currentUser.updatePassword(newPasword).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(ActivityUserLoggedIn.this, "Password has change succesfully", Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ActivityUserLoggedIn.this, "Password has not changed!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            };
                        }
                    }
                });
                passwordResetDialog.setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    //close
                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }
    private void OpenMenu() { //Fab Close the menu of buttuns
        menuOpen=! menuOpen;
        fMain.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down);
        uploadPhotoFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        changePasswordFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        changeNameFab.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();

    }
    private void CloseMenu() {  //Fab open the menu of buttuns
        menuOpen=! menuOpen;
        fMain.setImageResource(R.drawable.ic_settings);
        uploadPhotoFab.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        changePasswordFab.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        changeNameFab.animate().translationY(translationYaxis).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {  //Navifgations buttons
        switch (item.getItemId()){
            case (R.id.nav_logout):
                Logout();
               break;
            case (R.id.nav_communicate):
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, NavUserCommunicate.class,null).setReorderingAllowed(true).addToBackStack(null).commit();
                onBackPressed();
                break;
            case (R.id.nav_appointment):
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, NavUserNewQueue.class,null).setReorderingAllowed(true).addToBackStack(null).commit();
                onBackPressed();
                break;
            case (R.id.nav_delete):
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, NavDeleteQueueUser.class,null).setReorderingAllowed(true).addToBackStack(null).commit();
                onBackPressed();
                break;
        }
        onBackPressed();
        return true;
    }
    public boolean FullNameWithoutNumbers(String name) {
        return name.matches("[a-zA-Z]+[ ][a-zA-Z]+");
    }
}