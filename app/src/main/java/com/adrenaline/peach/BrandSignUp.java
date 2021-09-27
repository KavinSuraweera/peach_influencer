package com.adrenaline.peach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class BrandSignUp extends AppCompatActivity {

    public static final String TAG = "TAG";
    private View decorView;
    EditText type,name,contactNo,userName,eMail,password,rPassword;
    Button mRegisterbtn;
    private ImageButton back_btn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String brandID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_sign_up);

        decorView = getWindow().getDecorView();
        back_btn = findViewById(R.id.btn_back_arrow);

        type = findViewById(R.id.txt_type);
        name =findViewById(R.id.txt_name);
        contactNo =findViewById(R.id.txt_contact);
        userName =findViewById(R.id.txt_username);
        eMail =findViewById(R.id.txt_email);
        password =findViewById(R.id.txt_pass);
        rPassword =findViewById(R.id.txt_rpass);
        mRegisterbtn =findViewById(R.id.create_button);

        fAuth =  FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),brandDashboard.class));
//            finish();
//        }

        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eMail.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String rPass = rPassword.getText().toString().trim();
                String btype = type.getText().toString();
                String bname = name.getText().toString();
                String contact = contactNo.getText().toString();
                String usrName = userName.getText().toString();

                if(fAuth.getCurrentUser() != null){
                    Toast.makeText(BrandSignUp.this, "User created", Toast.LENGTH_SHORT).show();
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
                fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(BrandSignUp.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(BrandSignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                            brandID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("brands").document(brandID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("type",btype);
                            user.put("name",bname);
                            user.put("contactNo",contact);
                            user.put("userName", usrName);
                            user.put("email", email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: User profile is created for "+ brandID);
                                }
                            });
//                            startActivity(new Intent(getApplicationContext(),creatorLogin.class));



                        }else{
                            Toast.makeText(BrandSignUp.this, "Error !"+task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
    }

    public void openLogin(){
        Intent intent = new Intent(this,brand_login.class);
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