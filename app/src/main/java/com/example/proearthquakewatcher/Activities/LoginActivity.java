package com.example.proearthquakewatcher.Activities;

///https://www.youtube.com/watch?v=wa8OrQ_e76M
// https://www.youtube.com/watch?v=eGWu0-0TWFI&list=RDCMUCnKhcV7frITmrYbIU5MrMZw&start_radio=1&t=1127
// https://www.youtube.com/watch?v=9QOg8R8ol1w&list=RDCMUCnKhcV7frITmrYbIU5MrMZw&index=9
// https://www.youtube.com/watch?v=eGWu0-0TWFI&t=100s
// https://www.youtube.com/watch?v=wa8OrQ_e76M&t=212s
//

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proearthquakewatcher.MapsActivity;
import com.example.proearthquakewatcher.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase; // rootNode
    private DatabaseReference databaseReference; // reference

    Button callSignup, login_tbn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        //Hooks
        callSignup = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logo_image);
        sloganText = findViewById(R.id.slogan_name);
        logoText = findViewById(R.id.logo_name);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login_tbn = findViewById(R.id.login_btn);
        final EditText emailView = new EditText(LoginActivity.this);
        emailView.setHint("exemple@exemple.com");


        callSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(image, "logo_image_tran");
                pairs[1] = new Pair<View, String>(logoText, "logo_text_tran");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc_tran");
                pairs[3] = new Pair<View, String>(email, "email_tran");
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

    public void Login(View view) {
        validateLogin();
        IfExistUser();
    }

    private void IfExistUser() {

        final String userEnteredUserEmail = email.getEditText().getText().toString().trim();
        final String userEnteredUserPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("userEmail").equalTo(userEnteredUserEmail);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    email.setError(null);
                    email.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredUserEmail).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredUserPassword)){

                        email.setError(null);
                        email.setErrorEnabled(false);

                        String nameFromDB = snapshot.child(userEnteredUserEmail).child("name").getValue(String.class);
                        String usernameFromDB = snapshot.child(userEnteredUserEmail).child("username").getValue(String.class);
                        String phoneNoFromDB = snapshot.child(userEnteredUserEmail).child("phoneNo").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredUserEmail).child("email").getValue(String.class);
                        String confirmPasswordFromDB = snapshot.child(userEnteredUserEmail).child("confirmPassword").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNo", phoneNoFromDB);
                        intent.putExtra("password", passwordFromDB);
                        intent.putExtra("confirmPassword", confirmPasswordFromDB);

                        startActivity(intent);
                    }
                    else {
                        password.setError("Wrong password");
                        password.requestFocus();
                    }
                }
                else {
                    email.setError("No such user exist");
                    email.requestFocus();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void validateLogin() {

        final String emailField = email.getEditText().getText().toString();
        final String passwordField = password.getEditText().getText().toString();

        if (TextUtils.isEmpty(emailField)) {
            email.setError("E-mail field is required");
            email.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailField).matches()) {
            email.setError("Please, enter a valid email");
            email.requestFocus();
            return;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(passwordField)) {
            password.setError("Password field is required");
            password.requestFocus();
            return;
        }
    }

}