package com.adrenaline.peach;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class editCreator extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView fName,lName,contactNo,userName,eMail;
    Button confirm;
    private View decorView;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private String creatorId;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_creator);

        confirm = findViewById(R.id.confirm_button);

        fName = findViewById(R.id.txt_fname);
        lName = findViewById(R.id.txt_lname);
        contactNo =findViewById(R.id.txt_contact);
        userName =findViewById(R.id.txt_username);
        eMail =findViewById(R.id.txt_email);






        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        user = fAuth.getCurrentUser();
        creatorId = fAuth.getCurrentUser().getUid();


        DocumentReference documentReference = fStore.collection("creators").document(creatorId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fName.setText(documentSnapshot.getString("fName"));
                lName.setText(documentSnapshot.getString("lName"));
                userName.setText(documentSnapshot.getString("userName"));
                contactNo.setText(documentSnapshot.getString("contactNo"));
                eMail.setText(documentSnapshot.getString("email"));




            }
        });



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = eMail.getText().toString().trim();
                String fname = fName.getText().toString();
                String lname = lName.getText().toString();
                String contact = contactNo.getText().toString();
                String usrName = userName.getText().toString();


                creatorId = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.getInstance().collection("creators").document(creatorId);
                Map<String,Object> user = new HashMap<>();
                user.put("fName",fname);
                user.put("lName",lname);
                user.put("contactNo",contact);
                user.put("userName",usrName);
                user.put("email",email);

                documentReference.update(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "onSuccess: User Updated");
                                Intent intent =new Intent(editCreator.this,creatorProfile.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: err ",e);
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
    }

    public void update(View view){

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

}