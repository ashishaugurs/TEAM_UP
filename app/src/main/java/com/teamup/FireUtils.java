package com.teamup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamup.utils.CommonUtils;

import java.util.HashMap;

/* Api Classes */

public class FireUtils {


    public void getSaveData(DatabaseReference myRef, final FirebaseListener listener){


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onDataChange(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                listener.onCancelled(error);
            }
        });

    }


    public void getUpdateData(DatabaseReference myRef,
                           final FirebaseListener listener,String tableName,
                           String childName,Object changeObject){

        myRef.child(tableName).child(childName).setValue(changeObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                        listener.onSuccess(aVoid);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        listener.onFailure(e);

                    }
                });

      }






}
