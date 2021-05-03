package com.example.proearthquakewatcher.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class SignIn extends AppCompatActivity {

    //private CallbackManager callbackManager;
    //private LoginButton loginButton;
    private FirebaseAuth firebaseAuth;
    private Userhelper modelsContact;
    private FirebaseAuth.AuthStateListener firebaseAuthAuthStateListener ;

    ProgressBar progressBar;
    AlertDialog alert;
    TextInputLayout emailView, passwordView;
    TextView recoverPassword, logoText, sloganText;
    ImageView imageView;
    Button callSignup, login_tbn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        imageView = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        callSignup = findViewById(R.id.signup_screen);
        login_tbn = findViewById(R.id.login_btn);

        emailView = findViewById(R.id.email_login);
        passwordView = findViewById(R.id.passord_login);
        recoverPassword = findViewById(R.id.forget_password);
        firebaseAuth = FirebaseAuth.getInstance();
        //loginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        EditText emailView = new EditText(SignIn.this);
        emailView.setHint("exemple@exemple.com");


        callSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, RegisterActivity.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(imageView, "logo_image_tran");
                pairs[1] = new Pair<View, String>(logoText, "logo_text_tran");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc_tran");
                pairs[3] = new Pair<View, String>(emailView, "email_tran");
                pairs[4] = new Pair<View, String>(passwordView, "password_tran");
                pairs[5] = new Pair<View, String>(login_tbn, "log_reg_button_tran");
                pairs[6] = new Pair<View, String>(callSignup, "call_signUp_tran");

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignIn.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });




        recoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SignIn.this);
                builder.setCancelable(false);
                builder.setTitle("Password recovery");
                builder.setMessage("Enter your email:");
                builder.setView(emailView);

                if (!emailView.getText().equals("")) {
                    builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, final int i) {
                            firebaseAuth = FirebaseAuth.getInstance();
                            String emailRecuperar = emailView.getText().toString();
                            firebaseAuth.sendPasswordResetEmail(emailRecuperar).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignIn.this, "In a moment you will receive an e-mail!", Toast.LENGTH_LONG).show();
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignIn.this, "Failed to send email!", Toast.LENGTH_LONG).show();
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    });


                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });

                } else {
                    Toast.makeText(SignIn.this, "Please fill in your e-mail!", Toast.LENGTH_LONG).show();
                }
                alert = builder.create();
                alert.show();

            }
        });

    }

    public void Login(View view) {
        ValidateLogin();
        //progressBar.setVisibility(View.VISIBLE);
    }

    private void ValidateLogin() {

        final String email = emailView.getEditText().getText().toString();
        final String password = passwordView.getEditText().getText().toString();

        if(TextUtils.isEmpty(email)){
            emailView.setError("E-mail field is required");
            emailView.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailView.setError("Please, enter a valid email");
            emailView.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            passwordView.setError("Password field is required");
            passwordView.requestFocus();
            return;
        }


        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    Intent intent = new Intent(SignIn.this, MapsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    Preference preference = new Preference(LoginActivity.this);
//                    preference.savedPreferenceUser(modelsContact.getEmail(), modelsContact.getPassword());

                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, MapsActivity.class));
        }
    }


    public void GoToRegister(View view) {
        Intent intent = new Intent(SignIn.this, RegisterActivity.class);
        startActivity(intent);
    }
     **/
}