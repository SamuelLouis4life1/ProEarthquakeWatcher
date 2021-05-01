package com.example.proearthquakewatcher.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.proearthquakewatcher.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    //private FirebaseAuth.AuthStateListener firebaseAuthAuthStateListener ;

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword, regConfirmPassword;
    Button regBtn, regToLoginBtn;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhoneNo = findViewById(R.id.phonenumber);
        regPassword = findViewById(R.id.password);
        regConfirmPassword = findViewById(R.id.confirmpassword);
        regBtn = findViewById(R.id.callSignup);
        regToLoginBtn = findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


    }
}