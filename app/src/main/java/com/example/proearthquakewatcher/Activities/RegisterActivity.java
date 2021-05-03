package com.example.proearthquakewatcher.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proearthquakewatcher.MapsActivity;
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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener firebaseAuthAuthStateListener ;
    private Userhelper modelsContact;

    TextInputLayout lastNameEdt, firstNameEdt, phoneEdt, confirmPasswordRegister, passwordRegister,  emailRegister;
    RadioButton rbMale, rbFemale;
    RadioGroup mradioGroup;
    ProgressBar progressBar;
    Button btnRegister, login_tbn, callSignup;
    TextView logoText, sloganText;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageView = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        callSignup = findViewById(R.id.signIn);
        login_tbn = findViewById(R.id.signIn);

        lastNameEdt = findViewById(R.id.lastname_register);
        firstNameEdt = findViewById(R.id.firstname_register);
        phoneEdt = findViewById(R.id.phone_register);
        emailRegister = findViewById(R.id.email_register);
        passwordRegister = findViewById(R.id.passord_edt);
        confirmPasswordRegister = findViewById(R.id.comfirm_password_edt);
        mradioGroup = findViewById(R.id.radioGroup);
        rbMale = findViewById(R.id.RbMale);
        rbFemale = findViewById(R.id.RbFemale);
        firebaseAuth = FirebaseAuth.getInstance();
        btnRegister = findViewById(R.id.register_user);

        callSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, SignIn.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(imageView, "logo_image_tran");
                pairs[1] = new Pair<View, String>(logoText, "logo_text_tran");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc_tran");
                pairs[3] = new Pair<View, String>(emailRegister, "email_tran");
                pairs[4] = new Pair<View, String>(passwordRegister, "password_tran");
                pairs[5] = new Pair<View, String>(login_tbn, "login_signup_tran");
                pairs[6] = new Pair<View, String>(callSignup, "call_signUp_tran");

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });





        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //progressBar.setVisibility(View.VISIBLE);

                if (passwordRegister.getEditText().getText().toString().equals(confirmPasswordRegister.getEditText().getText().toString())) {
                    modelsContact = new Userhelper();

                    modelsContact.setName(lastNameEdt.getEditText().getText().toString());
                    modelsContact.setUsername(firstNameEdt.getEditText().getText().toString());
                    modelsContact.setPhoneNo(phoneEdt.getEditText().getText().toString());
                    modelsContact.setEmail(emailRegister.getEditText().getText().toString());
                    modelsContact.setPassword(passwordRegister.getEditText().getText().toString());
                    modelsContact.setConfirmPassword(confirmPasswordRegister.getEditText().getText().toString());

                    if (rbMale.isChecked()) {
                        modelsContact.setSex("Male");
                    } else if (rbFemale.isChecked()) {
                        modelsContact.setSex("Female");
                    }

                    registerUser();
                    Toast.makeText(RegisterActivity.this, "All success !", Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(RegisterActivity.this, "Password and confirm password must be matched !", Toast.LENGTH_LONG).show();

                }
                Intent intent = new Intent(RegisterActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {

        firebaseAuth = FirebaseConfiguration.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(
                modelsContact.getEmail(),
                modelsContact.getPassword()
        ).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Intent intent = new Intent(RegisterActivity.this, MapsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    insertUser(modelsContact);
                    //Toast.makeText(RegisterActivity.this, "User Registered !", Toast.LENGTH_LONG).show();

                } else {

                    String erroExcecao = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Enter a stronger password, containing at least 6 characters and containing letters and numbers!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "The email you entered is invalid, please enter a new email";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "This email is already registered!";
                    } catch (Exception e) {
                        erroExcecao = "Error when registering!";
                        e.printStackTrace();
                    }

                    Toast.makeText(RegisterActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private boolean insertUser(Userhelper modelsContact) {
        try {
            databaseReference = FirebaseConfiguration.getFirebase().child("User");
            String key = databaseReference.push().getKey();
            modelsContact.getGetKeyUser();
            databaseReference.child(key).setValue(modelsContact);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthAuthStateListener);
    }

}