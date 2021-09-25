package com.adrenaline.peach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class brand_login extends AppCompatActivity {
    EditText pass,email;
    private View decorView;
    private TextView link_signUp;
    Button btn_login;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_login);

        decorView = getWindow().getDecorView();
        email = findViewById(R.id.txt_username);
        pass = findViewById(R.id.txt_pw);
        progressBar = findViewById(R.id.progressBar);
        btn_login = findViewById(R.id.btn_login);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),brandDashboard.class));
            finish();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String eMail = email.getText().toString().trim();
                String Password = pass.getText().toString().trim();

                if(TextUtils.isEmpty(eMail)){
                    email.setError("Email required");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    pass.setError("password required");
                    return;
                }

                if(Password.length() < 6){
                    pass.setError("Password is too short");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //authenticate the user

                fAuth.signInWithEmailAndPassword(eMail,Password).addOnCompleteListener(brand_login.this,new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(brand_login.this,BrandSignUp.class));
                        }else{
                            Toast.makeText(brand_login.this, "Failed to login! please check your credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        link_signUp = getWindow().findViewById(R.id.link_signup);
        link_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreatecreator();
            }
        });
    }

    private void openCreatecreator() {
        Intent intent = new Intent(this,BrandSignUp.class);
        startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        decorView.setSystemUiVisibility(hideSystemBars());

    }



    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    };
}