package com.teamup.utils;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.teamup.R;
import com.teamup.model.Player;

public class FirebaseUtils {

    public static String FirebaseProfileStorageDir = "profile_pic",
    PLAYERNODE = "players", TEAMNODE = "teamss",
    KEY_TEAM_CREATED_BY = "createdBy",
    KEY_PHONE = "phone"

            ;


    public static FirebaseUser getUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static boolean isLoggedIn(){
        return getUser()!=null;
    }

    public static String getUId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static void setMyProfileImage(final ImageView imageView){

        imageView.setImageResource(R.mipmap.place_holder);


        if(isLoggedIn()){


            getMyProfileReference()
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Player player = dataSnapshot.getValue(Player.class);


                            if(player!=null) {

                                if(!player.getImageUrl().isEmpty())
                                Picasso.get()
                                        .load(player.getImageUrl())
                                        .placeholder(R.mipmap.place_holder)
                                        .into(imageView);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



        }

    }


    public static void setUserProfileImageUsingUID(final ImageView imageView, String uid){

        imageView.setImageResource(R.mipmap.place_holder);
        if(isLoggedIn()){

                  getFirebaseRootRef().child(PLAYERNODE)
                    .child(uid)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Player player = dataSnapshot.getValue(Player.class);


                            if(player!=null)
                                Picasso.get()
                                        .load(player.getImageUrl())
                                        .placeholder(R.mipmap.place_holder)
                                        .into(imageView);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



        }

    }

    public static void setMyFullName(final TextView textView){


        if(isLoggedIn()){


            getMyProfileReference()
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Player player = dataSnapshot.getValue(Player.class);


                            if(player!=null)
                                textView.setText(player.getFirstname()+" "+player.getLastname());


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



        }

    }




    public static DatabaseReference getFirebaseRootRef(){

        return FirebaseDatabase.getInstance().getReference();
    }


    public static DatabaseReference getMyProfileReference(){

       return FirebaseUtils.getFirebaseRootRef()
                .child(FirebaseUtils.PLAYERNODE)
                .child(FirebaseUtils.getUId());
    }





    public static DatabaseReference getTeamNodeRef(){
        return getFirebaseRootRef()
                .child(TEAMNODE);
    }

}

