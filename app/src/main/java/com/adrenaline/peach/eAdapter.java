package com.adrenaline.peach;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class eAdapter extends FirebaseRecyclerAdapter<Evendata,eAdapter.eViewHolder> {



    public eAdapter(@NonNull FirebaseRecyclerOptions<Evendata> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull eViewHolder eViewHolder, @SuppressLint("RecyclerView") int position, @NonNull Evendata evendata) {
        eViewHolder.name.setText(evendata.getName());
        eViewHolder.des.setText(evendata.getDes());

        eViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(eViewHolder.name.getContext());
                builder.setTitle("Are you Sure ?");
                builder.setMessage("Deleted data can't be Undo.");

                builder.setMessage("")
            }
        });

    }

    @NonNull
    @Override
    public eViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);
        return new eViewHolder(view);


    }


    public class eViewHolder extends RecyclerView.ViewHolder {

        TextView name,des;
        Button edit,delete;

        public eViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name_text);
            des = (TextView) itemView.findViewById(R.id.Event_text);
        }
    }


}
