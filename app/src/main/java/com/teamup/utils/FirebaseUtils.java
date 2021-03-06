package com.teamup.utils;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;
import com.teamup.R;
import com.teamup.model.Player;

public class FirebaseUtils {

    public static String FirebaseProfileStorageDir = "profile_pic",
    NODE_PLAYER = "players", NODE_TEAM = "teamss", NODE_TEAM_MEMBER = "team_members",
    KEY_TEAM_CREATED_BY = "createdBy",
    KEY_PHONE = "phone",
    NODE_EVENT = "events",
    KEY_PASSED_REFERENCE = "ref",
    NODE_CHAT = "chats",
    NODE_TOKEN = "tokens",
    TAG = FirebaseUtils.class.getName()
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

                                if(!player.getImage_url().isEmpty())
                                Picasso.get()
                                        .load(player.getImage_url())
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

                  getFirebaseRootRef().child(NODE_PLAYER)
                    .child(uid)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            Log.d(TAG, "onDataChange: "+dataSnapshot.getValue());

                            Player player = dataSnapshot.getValue(Player.class);

                         //   Log.d(TAG, "onDataChange: url = "+player.getImage_url());

                            if(player!=null && !player.getImage_url().isEmpty())
                                Picasso.get()
                                        .load(player.getImage_url())
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


    public static void setUserFullName(final TextView textView, String uid){


        if(isLoggedIn()){

                 getProfileReference(uid)
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


    public static void setUserFirstName(final TextView textView, String uid){


        if(isLoggedIn()){

            getProfileReference(uid)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Player player = dataSnapshot.getValue(Player.class);


                            if(player!=null)
                                textView.setText(player.getFirstname());


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

    public static DatabaseReference getProfileReference(String uid){

        return FirebaseUtils.getFirebaseRootRef()
                .child(FirebaseUtils.NODE_PLAYER)
                .child(uid);
    }

    public static DatabaseReference getMyProfileReference(){

       return FirebaseUtils.getFirebaseRootRef()
                .child(FirebaseUtils.NODE_PLAYER)
                .child(FirebaseUtils.getUId());
    }





    public static DatabaseReference getTeamNodeRef(){
        return getFirebaseRootRef()
                .child(NODE_TEAM);
    }


    public static DatabaseReference getEventRef(String teamID){

        return FirebaseUtils.getFirebaseRootRef()
                .child(FirebaseUtils.NODE_EVENT)
                .child(teamID);
    }


    public static DatabaseReference getTeamMemberRef(String teamID){

        return FirebaseUtils.getFirebaseRootRef()
                .child(FirebaseUtils.NODE_TEAM_MEMBER)
                .child(teamID);
    }

    public static DatabaseReference getChatReference(String teamID){

        return FirebaseUtils.getFirebaseRootRef()
                .child(NODE_CHAT)
                .child(teamID);
    }


    public static void storeTokenToServer(){


        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {

                        String token = instanceIdResult.getToken();
                        getFirebaseRootRef().child(NODE_TOKEN)
                                .child(getUId())
                                .setValue(token)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: Token stored for -> "+getUId());
                                    }
                                });


                    }
                });

    }

}

