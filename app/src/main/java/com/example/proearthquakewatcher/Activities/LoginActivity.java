package com.example.proearthquakewatcher.Activities;

///https://www.youtube.com/watch?v=wa8OrQ_e76M

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proearthquakewatcher.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    Button callSignup, login_tbn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        //Hooks
        callSignup = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logo_image);
        sloganText = findViewById(R.id.slogan_name);
        logoText = findViewById(R.id.logo_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_tbn = findViewById(R.id.login_btn);


        callSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(image, "logo_image_tran");
                pairs[1] = new Pair<View, String>(logoText, "logo_text_tran");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc_tran");
                pairs[3] = new Pair<View, String>(username, "username_tran");
                pairs[4] = new Pair<View, String>(password, "password_tran");
                pairs[5] = new Pair<View, String>(login_tbn, "button_tran");
                pairs[6] = new Pair<View, String>(callSignup, "login_signup_tran");

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });


    }
}