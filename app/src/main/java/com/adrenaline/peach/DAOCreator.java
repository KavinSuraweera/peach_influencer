package com.adrenaline.peach;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class DAOCreator {

    private DatabaseReference databaseReference;
    public DAOCreator()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Creator.class.getSimpleName());
    }

    public Task<Void> add(Creator creator)
    {

        return databaseReference.push().setValue(creator);
    }

}
