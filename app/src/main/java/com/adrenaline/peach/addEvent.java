package com.adrenaline.peach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class addEvent extends AppCompatActivity {
    private View decorView;
    TextView name,des;
    Button button;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private String brandId;
    DatabaseReference EventDbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        brandId = fAuth.getCurrentUser().getUid();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        button = findViewById(R.id.button);

        decorView = getWindow().getDecorView();
        name = findViewById(R.id.txt_type);
        des = findViewById(R.id.edit_text);

        EventDbref = FirebaseDatabase.getInstance().getReference().child("Events/"+brandId);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertEventData();
                Intent intent = new Intent(addEvent.this,Events.class);
                startActivity(intent);
            }

        });

    }

    private void insertEventData() {

        String Name = name.getText().toString().trim();

        String Des = des.getText().toString();
        String key = brandId;


        Evendata evendata = new Evendata(Name,Des,key);

        EventDbref.push().setValue(evendata);
        Toast.makeText(addEvent.this, "Data inserted", Toast.LENGTH_SHORT).show();
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