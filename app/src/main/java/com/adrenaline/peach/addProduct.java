package com.adrenaline.peach;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class addProduct extends AppCompatActivity {
    public static final String TAG = "TAG";
    private View decorView;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    EditText type,name,pip,pis,pfp,ptv,pyv,description;
    ImageView addPic;
    public Uri imageUri;
    Button button;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private String brandId;
    private FirebaseUser brand;
    String randomKey;
    String imgUri;

    DatabaseReference productDbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        decorView = getWindow().getDecorView();

        type = findViewById(R.id.txt_type);
        name = findViewById(R.id.txt_name);
        pip = findViewById(R.id.price_ip);
        pis = findViewById(R.id.price_is);
        pfp = findViewById(R.id.price_fp);
        ptv = findViewById(R.id.price_tv);
        pyv = findViewById(R.id.price_yv);
        description =findViewById(R.id.edit_text);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        addPic = findViewById(R.id.upload_pic);
        button =findViewById(R.id.button);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        brandId = fAuth.getCurrentUser().getUid();




        productDbref = FirebaseDatabase.getInstance().getReference().child("Products/"+brandId);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertBrandData();
                Intent intent = new Intent(addProduct.this,brandDashboard.class);
                startActivity(intent);
            }
        });

        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startCropActivity();
                choosePicture();

            }


        });






//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String Type = type.getText().toString().trim();
//                String Name = name.getText().toString().trim();
//                String Pip = pip.getText().toString().trim();
//                String Pis = pis.getText().toString();
//                String Pfp = pfp.getText().toString();
//                String PTv = ptv.getText().toString();
//                String Des = description.getText().toString();
//
//
//
//                //register the user in firebase
//
//
//
//                            brandId = fAuth.getCurrentUser().getUid();
//                            final String randomKey = UUID.randomUUID().toString();
//                            DocumentReference documentReference = fStore.collection("gi").document(randomKey);
//                            Map<String,Object> user = new HashMap<>();
//                            user.put("Brand",brandId);
//                            user.put("Type",Type);
//                            user.put("Name",Name);
//                            user.put("igPost",Pip);
//                            user.put("igStory",Pis);
//                            user.put("fbPost",Pfp);
//                            user.put("ttVideo",PTv);
//                            user.put("des",Des);
//
//                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d(TAG, "onSuccess: User profile is created for "+randomKey);
//                                    Intent intent = new Intent(addProduct.this,brandDashboard.class);
//                                    startActivity(intent);
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d(TAG, "onFailure: fail",e);
//                                }
//                            });
////                            startActivity(new Intent(getApplicationContext(),creatorLogin.class));
//
//
//
//
//                    }
//
//        });






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

        randomKey = UUID.randomUUID().toString();
        imgUri = "productPics/"+randomKey+"/productimg.jpg";
        StorageReference creatorpropicRef = storageReference.child(imgUri);

        creatorpropicRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded. ", Snackbar.LENGTH_LONG).show();
                        creatorpropicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Picasso.get().load(uri).into(addPic);
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

    private void insertBrandData() {
        String Type = type.getText().toString().trim();
        String Name = name.getText().toString().trim();
        String Pip = pip.getText().toString().trim();
        String Pis = pis.getText().toString();
        String Pfp = pfp.getText().toString();
        String PTv = ptv.getText().toString();
        String Pyv = pyv.getText().toString();
        String Des = description.getText().toString();
        String key = brandId;

        Products product = new Products(Type,Name,Pip,Pis,Pfp,Pyv,PTv,Des,key);

        productDbref.push().setValue(product);
        Toast.makeText(addProduct.this, "Data inserted", Toast.LENGTH_SHORT).show();
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