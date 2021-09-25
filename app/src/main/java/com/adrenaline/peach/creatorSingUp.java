package com.adrenaline.peach;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class creatorSingUp extends AppCompatActivity {

    public static final String TAG = "TAG";
    private View decorView;
    private ImageButton back_btn;
    EditText fName,lName,contactNo,userName,eMail,password,rPassword;
    ImageButton mRegisterbtn,changeProfileImage;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String createrID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_sing_up);

         fName = findViewById(R.id.txt_fname);
         lName =findViewById(R.id.txt_lname);
         contactNo =findViewById(R.id.txt_contact);
         userName =findViewById(R.id.txt_username);
         eMail =findViewById(R.id.txt_email);
         password =findViewById(R.id.txt_pass);
         rPassword =findViewById(R.id.txt_rpass);
         mRegisterbtn = findViewById(R.id.btn_reg);

         changeProfileImage = findViewById(R.id.upload_pic);

         changeProfileImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ////open gallery
                 Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 startActivityForResult(openGalleryIntent,1000);
             }
         });


        fAuth =  FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

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
                String fname = fName.getText().toString();
                String lname = lName.getText().toString();
                String contact = contactNo.getText().toString();
                String usrName = userName.getText().toString();

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
                            Toast.makeText(creatorSingUp.this, "User Created", Toast.LENGTH_SHORT).show();
                            createrID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("creators").document(createrID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fname);
                            user.put("lName",lname);
                            user.put("contactNo",contact);
                            user.put("userName", usrName);
                            user.put("email", email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: User profile is created for "+createrID);
                                }
                            });
//                            startActivity(new Intent(getApplicationContext(),creatorLogin.class));



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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(requestCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                //changeProfileImage.setImageUri(imageUri)
            }
        }
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