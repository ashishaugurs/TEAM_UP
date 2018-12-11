package com.teamup.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pkmmte.view.CircularImageView;
import com.teamup.R;
import com.teamup.model.Team;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;
import com.teamup.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.MyViewHolder> implements RecyclerView.OnItemTouchListener {
    List<Team> teams = new ArrayList<>();
    Activity context;
    LayoutInflater inflater;
    int value;
    GestureDetector mGestureDetector;
    private OnItemClickListener mListener;
    List<String> teamIDs = new ArrayList<>();

    String TAG = MyTeamAdapter.class.getName();

    public MyTeamAdapter(Activity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    public MyTeamAdapter(final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }


    public void addTeam(Team item, String teamID){

        if(!teamIDs.contains(teamID)) {
            teams.add(item);
            teamIDs.add(teamID);
        }
        notifyDataSetChanged();
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

           value=R.layout.inflate_row_myteam;

           View view = inflater.inflate(value, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {


          holder.teamTitle.setText(teams.get(position).getName());
        Log.d(TAG, "onBindViewHolder: "+teams.get(position).getName() +" on pos = "+position);

        FirebaseUtils.getTeamMemberRef(teamIDs.get(position))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.playerStrength.setText(dataSnapshot.getChildrenCount() +" players");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }


    @Override
    public int getItemCount() {

        return teams.size() ;

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {

        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // my team
        CircularImageView teamImage;
        TextView teamTitle,teamType,playerStrength;

        // ads
        TextView playerName,categoryName,location,gender,dob;
        // calendar
        TextView eventTitle,eventDay,monthName,calendar_date,finale_title,finale_date,game_name,location_name;
        AppCompatImageView icon_light_blue;


        public MyViewHolder(View itemView) {
            super(itemView);


                teamImage=itemView.findViewById(R.id.teamPic);
                teamTitle=itemView.findViewById(R.id.teamTitle);
                teamType=itemView.findViewById(R.id.teamType);
                playerStrength=itemView.findViewById(R.id.playerStrength);

                teamImage.setImageResource(R.mipmap.football_player);
                teamTitle.setTypeface(CommonUtils.setFontTextHeader(context));
                playerStrength.setTypeface(CommonUtils.setFontTextNormal(context));
                teamType.setTypeface(CommonUtils.setFontTextNormal(context));



        }

    }



}

