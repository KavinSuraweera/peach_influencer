package com.adrenaline.peach;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class eAdapter extends FirebaseRecyclerAdapter<Evendata,eAdapter.eViewHolder> {



    public eAdapter(@NonNull FirebaseRecyclerOptions<Evendata> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull eViewHolder eViewHolder, @SuppressLint("RecyclerView") int position, @NonNull Evendata evendata) {
        eViewHolder.name.setText(evendata.getName());
        eViewHolder.des.setText(evendata.getDes());

        eViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(eViewHolder.edit.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_event))
                        .setExpanded(true,2100)
                        .create();

                //dialogPlus.show();

                View view =dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.txt_type);
                EditText des = view.findViewById(R.id.edit_text);


                Button button = view.findViewById(R.id.button);
                String key = evendata.getKey();

                name.setText(evendata.getName());
                des.setText(evendata.getDes());



                dialogPlus.show();

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("des",des.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Events/"+key)
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

        eViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            String key = evendata.getKey();
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(eViewHolder.name.getContext());
                builder.setTitle("Are you Sure ?");
                builder.setMessage("Deleted data can't be Undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Events/"+key)
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(eViewHolder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
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
            edit = (Button) itemView.findViewById(R.id.button4);
            delete = (Button) itemView.findViewById(R.id.button3);
        }
    }


}
