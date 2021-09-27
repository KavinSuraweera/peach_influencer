package com.adrenaline.peach;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class creator_dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button btn_logout,profile;
    private View decorView;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    TextView view_fname,view_lname,view_email;
    View hView;
    TextView fName,lName,contactNo,userName,eMail;

    private FirebaseUser user;

    private String creatorId;
    private StorageReference storageReference;

    //variables for drawer layout
    ArrayList<Brand> BrandArrayList;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    bAdapter bAdapter;
    RecyclerView recyclerView;
    bAdapter adapter;
    TextView uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_dashboard);
        btn_logout = findViewById(R.id.btn_logout);
        decorView = getWindow().getDecorView();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();



        user = fAuth.getCurrentUser();
        creatorId = fAuth.getCurrentUser().getUid();

//        recyclerView =(RecyclerView)findViewById(R.id.rv);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        userName = findViewById(R.id.usrname);
        btn_logout = findViewById(R.id.btn_logout);
        profile = findViewById(R.id.profile);


       // lName = findViewById(R.id.view_lname);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();//logout
                Intent intent = new Intent(creator_dashboard.this,MainActivity.class);
                startActivity(intent);

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(creator_dashboard.this,creatorProfile.class);
                startActivity(intent);
            }
        });

        DocumentReference documentReference = fStore.collection("creators").document(creatorId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userName.setText("Hi @"+value.getString("userName"));
            }
        });



//        DocumentReference documentReference = fStore.collection("creators").document("creatorId");
//        documentReference.addSnapshotListener(creator_dashboard.this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//
//                userName.setText(documentSnapshot.getString("userName"));
//
//
//
//
//            }
//        });

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        /*-------------------Hooks----------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /*-------------------Tool bar----------------*/

        setSupportActionBar(toolbar);




        /*-------------------Navigation driver menu----------------*/


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_close,R.string.navigation_drawer_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

//
//        FirebaseRecyclerOptions<Brand> options =
//                new FirebaseRecyclerOptions.Builder<Brand>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), Brand.class)
//                        .build();
//        adapter = new bAdapter(options);
//        recyclerView.setAdapter(adapter);

    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }



    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public void logout1(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_profile:
                Intent intent = new Intent(creator_dashboard.this,creatorProfile.class);
                startActivity(intent);
                break;

        }

        return false;
    }
}