package com.adrenaline.peach;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BrandProfile extends AppCompatActivity {
    private View decorView;

    TextView name,type,contactNo,userName,eMail,uname;
    Button edit;
    ImageView changeProfileImage;
    private Button btn_logout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private String brandId;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_profile);

        btn_logout = findViewById(R.id.btn_logout);
        decorView = getWindow().getDecorView();
        edit = findViewById(R.id.btn_edit);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        user = fAuth.getCurrentUser();
        brandId = fAuth.getCurrentUser().getUid();

        name = findViewById(R.id.usr_fname);
        uname = findViewById(R.id.usr_uname);
        userName = findViewById(R.id.uname);
        type = findViewById(R.id.fname);
        eMail = findViewById(R.id.uemail);
        contactNo = findViewById(R.id.ucontact);


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();//logout
                Intent intent = new Intent(BrandProfile.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        DocumentReference documentReference = fStore.collection("brands").document(brandId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name.setText(documentSnapshot.getString("name"));
                type.setText(documentSnapshot.getString("type"));
                userName.setText(documentSnapshot.getString("userName"));
                uname.setText(documentSnapshot.getString("userName"));
                contactNo.setText(documentSnapshot.getString("contactNo"));
                eMail.setText(documentSnapshot.getString("email"));




            }
        });
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