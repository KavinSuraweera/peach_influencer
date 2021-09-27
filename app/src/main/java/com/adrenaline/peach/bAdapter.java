package com.adrenaline.peach;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class bAdapter extends FirebaseRecyclerAdapter<Brand,bAdapter.bViewHolder> {


    public bAdapter(@NonNull FirebaseRecyclerOptions<Brand> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull bAdapter.bViewHolder bViewHolder, @SuppressLint("RecyclerView") int position, @NonNull Brand brand) {
        bViewHolder.name.setText(brand.getName());
        bViewHolder.type.setText(brand.getType());
        bViewHolder.username.setText(brand.getUsername());

    }

    @NonNull
    @Override
    public bAdapter.bViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_item,parent,false);
        return new bViewHolder(view);
    }

    public class bViewHolder extends RecyclerView.ViewHolder {

        TextView username,name,type;
        CardView card;


        public bViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name_text);
            username = (TextView) itemView.findViewById(R.id.txt_username);
            name = (TextView) itemView.findViewById(R.id.Event_type);
        }
    }
}
