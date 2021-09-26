package com.adrenaline.peach;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class pAdapter extends FirebaseRecyclerAdapter<Products,pAdapter.pViewHolder>
{


    public pAdapter(@NonNull FirebaseRecyclerOptions<Products> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull pViewHolder pViewHolder, int i, @NonNull Products products) {

        pViewHolder.name.setText(products.getName());
        //String imageUri;
        //imageUri = products.getImg();

        //Picasso.get().load(imageUri).into(pViewHolder.imageView);


    }

    @NonNull
    @Override
    public pViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new pViewHolder(view);
    }

    class pViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView imageView;


        public pViewHolder(@NonNull View itemView){
            super(itemView);
            //imageView = itemView.findViewById(R.id.img1);

            name = (TextView) itemView.findViewById(R.id.name_text);

        }

    }

}
