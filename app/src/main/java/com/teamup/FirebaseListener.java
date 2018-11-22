package com.teamup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface FirebaseListener {



    /* retrieve change */
    void onDataChange(DataSnapshot dataSnapshot);
    void onCancelled(DatabaseError error);

    /* update data */
    void onSuccess(Void dataSnapshot);
    void onFailure(Exception error);




}
