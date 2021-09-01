package com.adrenaline.peach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class creatorSingUp extends AppCompatActivity {

    private View decorView;
    private ImageButton back_btn;
    EditText fName,lName,contactNo,userName,eMail,password,rPassword;
    ImageButton mRegisterbtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_sing_up);

        fName =findViewById(R.id.txt_fname);
        lName =findViewById(R.id.txt_lname);
        contactNo =findViewById(R.id.txt_contact);
        userName =findViewById(R.id.txt_username);
        eMail =findViewById(R.id.txt_email);
        password =findViewById(R.id.txt_pass);
        rPassword =findViewById(R.id.txt_rpass);
        mRegisterbtn = findViewById(R.id.btn_reg);

        fAuth =  FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),creator_dashboard.class));
            finish();
        }


        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eMail.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String rPass = rPassword.getText().toString().trim();

                if(fAuth.getCurrentUser() != null){
                    Toast.makeText(creatorSingUp.this, "User created", Toast.LENGTH_SHORT).show();
                    finish();
                }

                if(TextUtils.isEmpty(email)){
                    eMail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    password.setError("Password is required");
                    return;
                }

                if(pass.length() < 6){
                    password.setError("Password must be 6 or more characters long ");
                    return;
                }


                //register the user in firebase
                fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(creatorSingUp.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(creatorSingUp.this, "User created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),creator_dashboard.class));
                        }else{
                            Toast.makeText(creatorSingUp.this, "Error !"+task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        back_btn = findViewById(R.id.btn_back_arrow);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }


    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    };



    public void openLogin(){
        Intent intent = new Intent(this,creatorLogin.class);
        startActivity(intent);

    }
}