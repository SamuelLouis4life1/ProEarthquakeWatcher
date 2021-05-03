package com.example.proearthquakewatcher.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.proearthquakewatcher.Model.Userhelper;
import com.example.proearthquakewatcher.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {


    private Userhelper userhelper; // modelsContact
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase; // rootNode
    private DatabaseReference databaseReference; // reference
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
        //progressBar = findViewById(R.id.progressBar);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regPassword.getEditText().getText().toString().equals(regConfirmPassword.getEditText().getText().toString())) {
                    userhelper = new Userhelper();

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

                    // Get all the values from text fields
                    String name = regName.getEditText().getText().toString();
                    String username = regUsername.getEditText().getText().toString();
                    String email = regEmail.getEditText().getText().toString();
                    String phoneNo = regPhoneNo.getEditText().getText().toString();
                    String password = regPassword.getEditText().getText().toString();
                    String confirmPassword = regConfirmPassword.getEditText().getText().toString();

                    Userhelper userhelper = new Userhelper(name, username, email, phoneNo, password, confirmPassword );

                    databaseReference.child(phoneNo).setValue(userhelper);

                    Intent intent = new Intent(SignUp.this, LoginActivity.class);
                    startActivity(intent);

                    //RegisterUser();
                }else {
                    Toast.makeText(SignUp.this, "Password and confirm password must be matched !", Toast.LENGTH_LONG).show();

                }

            }
        });

    }


    public void BackToLogIn(View view) {
        Intent intent = new Intent(SignUp.this, LoginActivity.class);
        startActivity(intent);
    }
}