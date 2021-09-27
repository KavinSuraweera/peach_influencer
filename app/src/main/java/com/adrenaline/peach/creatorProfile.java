package com.adrenaline.peach;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.style.IconMarginSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class creatorProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    private CircleImageView creatorPic;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Button btn_logout,delete;
    


    private View decorView;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    TextView fName,lName,contactNo,userName,eMail,fname,lname,uname;
    Button edit;
    ImageView changeProfileImage;



    private String creatorId;
    private FirebaseUser user;
    AlertDialog.Builder builder;
    // lName = findViewById(R.id.view_lname);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_profile);


        fname = findViewById(R.id.usr_fname);
        fName = findViewById(R.id.fname);
        lname = findViewById(R.id.usr_lname);
        lName = findViewById(R.id.lname);
        uname = findViewById(R.id.usr_uname);
        userName = findViewById(R.id.uname);
        eMail = findViewById(R.id.uemail);
        contactNo = findViewById(R.id.ucontact);
        edit = findViewById(R.id.btn_edit);
        changeProfileImage = findViewById(R.id.btn_add_pic);
        creatorPic = findViewById(R.id.c_profile_pic);
        btn_logout = findViewById(R.id.btn_logout);
        delete = findViewById(R.id.btn_delete);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        user = fAuth.getCurrentUser();
        creatorId = fAuth.getCurrentUser().getUid();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        DocumentReference documentReference = fStore.collection("creators").document(creatorId);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();//logout
                Intent intent = new Intent(creatorProfile.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        StorageReference profileRef = storageReference.child("creatorpics/"+creatorId+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(creatorPic);
            }
        });

        builder = new AlertDialog.Builder(this);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Are you Sure ?");
                builder.setMessage("Deleted data can't be Undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fStore.collection("creators").document(creatorId)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        FirebaseAuth.getInstance().signOut();
                                        Toast.makeText(creatorProfile.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(creatorProfile.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(creatorProfile.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        });



                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(creatorProfile.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });


        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startCropActivity();
                choosePicture();

            }


        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(creatorProfile.this,editCreator.class);
                startActivity(intent);
            }
        });








        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fName.setText(documentSnapshot.getString("fName"));
                lName.setText(documentSnapshot.getString("lName"));
                lname.setText(documentSnapshot.getString("lName"));
                fname.setText(documentSnapshot.getString("fName"));
                userName.setText(documentSnapshot.getString("userName"));
                uname.setText(documentSnapshot.getString("userName"));
                contactNo.setText(documentSnapshot.getString("contactNo"));
                eMail.setText(documentSnapshot.getString("email"));




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




    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            //creatorPic.setImageURI(imageUri);
            uploadPicture();



        }
    }

    private void uploadPicture() {


        final ProgressDialog pd = new ProgressDialog(this );
        pd.setTitle("Uploading image.....");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference creatorpropicRef = storageReference.child("creatorpics/"+creatorId+"/profile.jpg");

        creatorpropicRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded. ", Snackbar.LENGTH_LONG).show();
                        creatorpropicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(creatorPic);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed Upload", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        pd.setMessage("Percentage: "+ (int) progressPercent +"%");
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


}