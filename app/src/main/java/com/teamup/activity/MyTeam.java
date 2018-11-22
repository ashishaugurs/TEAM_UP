package com.teamup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.teamup.R;
import com.teamup.adapter.DetailMenuAdap;
import com.teamup.model.Team;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;
import com.teamup.utils.OnItemClickListener;

import java.util.ArrayList;

public class MyTeam extends BaseActivity implements OnItemClickListener {

     RecyclerView common_rec;
     FloatingActionButton addnNewListing;
     String TAG = MyTeam.class.getName();

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


    private void valuesInit() {

        common_rec=findViewById(R.id.common_rec);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(context);
        common_rec.setLayoutManager(addLocLayout);
        common_rec.setNestedScrollingEnabled(false);

        FirebaseUtils.getTeamNodeRef()
                .orderByChild(FirebaseUtils.KEY_TEAM_CREATED_BY)
                .equalTo(FirebaseUtils.getUId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        ArrayList<String> teamNames = new ArrayList<>();
                        for(DataSnapshot post : dataSnapshot.getChildren()){
                            Log.d(TAG, "onDataChange: "+post.getValue());
                            teamNames.add(post.getValue(Team.class).getName());
                        }

                        DetailMenuAdap adapter = new DetailMenuAdap(context,teamNames,"myteam");
                        common_rec.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        common_rec.addOnItemTouchListener(new DetailMenuAdap(common_rec,this));


    }

    @Override
    public void onItemClick(View view, int position) {

        startActivity(new Intent(context,TeamNameThree.class));

    }

    @Override
    public void onLongItemClick(View view, int position) {

    }



}