package com.adrenaline.peach;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class eAdapter extends FirebaseRecyclerAdapter<Evendata,eAdapter.eViewHolder> {



    public eAdapter(@NonNull FirebaseRecyclerOptions<Evendata> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull eAdapter.eViewHolder eViewHolder, @SuppressLint("RecyclerView") int position, @NonNull Evendata evendata) {
        eViewHolder.name.setText(evendata.getName());
        eViewHolder.des.setText(evendata.getDes());
    }

    @NonNull
    @Override
    public eAdapter.eViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);
        return new eViewHolder(view);
    }

    public class eViewHolder extends RecyclerView.ViewHolder {

        TextView name,des;

        public eViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name_text);
            des = (TextView) itemView.findViewById(R.id.Event_text);
        }
    }


}
