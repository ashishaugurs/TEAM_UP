package com.teamup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.teamup.R;
import com.teamup.adapter.MyTeamAdapter;
import com.teamup.model.Team;
import com.teamup.utils.AppConstant;
import com.teamup.utils.FirebaseUtils;
import com.teamup.utils.OnItemClickListener;

import java.util.ArrayList;

public class MyTeam extends BaseActivity implements OnItemClickListener {

     RecyclerView common_rec;
     FloatingActionButton addnNewListing;
     String TAG = MyTeam.class.getName();
     Boolean shouldReloadList = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_team_recycle);
        context=this;
        valuesInit();
     //   setHeadValue("My Team",context);
      //  setBackText();
        setToolbar("My Team");
        listener();

    }

    private void listener() {
        addnNewListing=findViewById(R.id.add_new_listing);
        addnNewListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,CreateTeam.class));
            }
        });
    }


    MyTeamAdapter adapter;
    ArrayList<String> teamIDs = new ArrayList<>();
    ArrayList<Team> teams = new ArrayList<>();
    private void valuesInit() {

        common_rec=findViewById(R.id.common_rec);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(context);
        addLocLayout.setStackFromEnd(true);
        addLocLayout.setReverseLayout(true);

        common_rec.setLayoutManager(addLocLayout);
        common_rec.setNestedScrollingEnabled(false);


        //just to handle if the list needs a reload
//        FirebaseUtils.getTeamNodeRef()
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if(shouldReloadList)
//                                valuesInit();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//        FirebaseUtils.getFirebaseRootRef().child(FirebaseUtils.NODE_TEAM_MEMBER)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if(shouldReloadList)
//                                valuesInit();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

//        FirebaseUtils.getTeamNodeRef()
//                .orderByChild(FirebaseUtils.KEY_TEAM_CREATED_BY)
//                .equalTo(FirebaseUtils.getUId())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                        for(DataSnapshot post : dataSnapshot.getChildren()){
//                        //    Log.d(TAG, "onDataChange: "+post.getValue());
//                            teams.add(post.getValue(Team.class));
//                            teamIDs.add(post.getKey());
//                        }
//
//
//                        Log.d(TAG, "onDataChange: total created teams = "+teams.size());
//
//                        if(teams.isEmpty())
//                            teams = new ArrayList<>();
//
//                        adapter = new MyTeamAdapter(context,teams);
//                        common_rec.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//
//
//                        loadTeams();
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });


        loadTeams();

    }



    private void loadTeams(){

        shouldReloadList = false;
        teamIDs.clear();
        teams.clear();

        Log.d(TAG, "loadTeams: My UID = "+FirebaseUtils.getUId());
        adapter = new MyTeamAdapter(this);
        common_rec.setAdapter(adapter);

        common_rec.addOnItemTouchListener(new MyTeamAdapter(common_rec, MyTeam.this));

        FirebaseUtils.getFirebaseRootRef()
                .child(FirebaseUtils.NODE_TEAM_MEMBER)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


//                        teamIDs.clear();
//                        teams.clear();

                        for(final DataSnapshot post:dataSnapshot.getChildren()) {

                            Log.d(TAG, "onDataChange: "+post.getValue());

                            if(post.getValue().toString().contains(FirebaseUtils.getUId())){

                                Log.d(TAG, "onDataChange: teamID that I'm in = "+post.getKey());

                                FirebaseUtils.getTeamNodeRef()
                                        .child(post.getKey())
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                                if(dataSnapshot.exists()) {
                                                    Team team = dataSnapshot.getValue(Team.class);

                                                    teamIDs.add(post.getKey());
                                                    teams.add(team);

                                                    adapter.addTeam(team, post.getKey());
                                                    common_rec.smoothScrollToPosition(adapter.getItemCount()-1);



                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                            }


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        loadNonPlayerTeam();
    }

    private void loadNonPlayerTeam(){
        FirebaseUtils.getTeamNodeRef()
                .orderByChild(FirebaseUtils.KEY_TEAM_CREATED_BY)
                .equalTo(FirebaseUtils.getUId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                   int t = 0;
                      for(DataSnapshot post : dataSnapshot.getChildren()) {

                          if (!teamIDs.contains(post.getKey())) {

                              Log.d(TAG, "onDataChange: count = "+(++t));

                              Log.d(TAG, "onDataChange: key = "+post.getKey());
                              Team team = post.getValue(Team.class);

                              teamIDs.add(post.getKey());
                              teams.add(team);

                              adapter.addTeam(team,
                                      post.getKey());
                              common_rec.smoothScrollToPosition(adapter.getItemCount()-1);

                              Log.d(TAG, "onDataChange: when not a player = "+post.getValue());


                          }
                      }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(context, TeamNameThree.class);
        intent.putExtra(AppConstant.KEY_TEAM_ID, teamIDs.get(position));
        startActivity(intent);

        Log.d(TAG, "onItemClick: position = "+position);
        Log.d(TAG, "onItemClick: team nam = "+teams.get(position));
        Log.d(TAG, "onItemClick: team ID = "+teamIDs.get(position));

    }

    @Override
    public void onLongItemClick(View view, int position) {

    }



}