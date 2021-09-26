package com.adrenaline.peach;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class pAdapter extends FirebaseRecyclerAdapter<Products,pAdapter.pViewHolder>
{


    public pAdapter(@NonNull FirebaseRecyclerOptions<Products> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull pViewHolder pViewHolder, @SuppressLint("RecyclerView") int position, @NonNull Products products) {

        pViewHolder.name.setText(products.getName());
        pViewHolder.type.setText(products.getType());
        //String imageUri;
        //imageUri = products.getImg();

        //Picasso.get().load(imageUri).into(pViewHolder.imageView);

        pViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(pViewHolder.edit.getContext())
                        .setContentHolder(new ViewHolder(R.layout.pdate_popup))
                        .setExpanded(true,2100)
                        .create();

                //dialogPlus.show();

                View view =dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.txt_name);
                EditText des = view.findViewById(R.id.edit_text);
                EditText pfp = view.findViewById(R.id.price_fp);
                EditText pip = view.findViewById(R.id.price_ip);
                EditText pis = view.findViewById(R.id.price_is);
                EditText ptv = view.findViewById(R.id.price_tv);
                EditText pyv = view.findViewById(R.id.price_yv);
                EditText type= view.findViewById(R.id.txt_type);

                Button button = view.findViewById(R.id.button);
                String key = products.getImg();

                name.setText(products.getName());
                des.setText(products.getDes());
                pfp.setText(products.getPfp());
                pip.setText(products.getPip());
                pis.setText(products.getPis());
                pyv.setText(products.getPyv());
                ptv.setText(products.getPtv());
                type.setText(products.getType());


                dialogPlus.show();

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("des",des.getText().toString());
                        map.put("pfp",pfp.getText().toString());
                        map.put("pip",pip.getText().toString());
                        map.put("pis",pis.getText().toString());
                        map.put("pyv",pyv.getText().toString());
                        map.put("ptv",ptv.getText().toString());
                        map.put("type",type.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Products/"+key)
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(name.getContext(), "Updated sucessfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(name.getContext(), "error while updating", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });


            }
        });

        pViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            String key = products.getImg();
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(pViewHolder.name.getContext());
                builder.setTitle("Are you Sure ?");
                builder.setMessage("Deleted data can't be Undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Products/"+key)
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(pViewHolder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public pViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new pViewHolder(view);
    }

    class pViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,type;
        ImageView imageView;
        Button edit,delete;


        public pViewHolder(@NonNull View itemView){
            super(itemView);
            //imageView = itemView.findViewById(R.id.img1);

            name = (TextView) itemView.findViewById(R.id.name_text);
            type = (TextView) itemView.findViewById(R.id.Event_nbr);
            edit = (Button) itemView.findViewById(R.id.button4);
            delete = (Button) itemView.findViewById(R.id.button3);

        }

    }

}
