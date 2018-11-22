package com.teamup.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.teamup.R;
import com.teamup.utils.CommonUtils;


import java.util.List;

public class ThreeTeamAdapter extends RecyclerView.Adapter<ThreeTeamAdapter.MyViewHolder> {

    List<String> dataList;
    Activity context;
    LayoutInflater inflater;
    String type;
    int value;

    public ThreeTeamAdapter(Activity context, List<String> dataList, String type) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.type=type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(type.equalsIgnoreCase("event_tab"))
            value=R.layout.teamname_teamevent;
        else if(type.equalsIgnoreCase("team_talk"))
            value=R.layout.teamname_teamtalk_left;
        else if(type.equalsIgnoreCase("team_member")){
            value=R.layout.teamname_teammember;
        }

        View view = inflater.inflate(value, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        if(type.equalsIgnoreCase("event_tab"))
        {

        }
        else if(type.equalsIgnoreCase("team_talk"))
        {

        }
        else if(type.equalsIgnoreCase("team_member"))
        {

        }
    }


    @Override
    public int getItemCount() {

        if(type.equalsIgnoreCase("event_tab")){
            return 5;
        }else if(type.equalsIgnoreCase("team_talk")){
            return 3;
        }else
            return  1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        // my team
        CircularImageView teamImage;
        TextView teamTitle,teamType,playerStrength;
        // ads
        TextView playerName,categoryName,location,gender,dob;
        // calendar
        TextView eventTitle,eventDay,monthName,calendar_date,finale_title,finale_date,game_name,location_name;
        LinearLayout attendanceSend;
        /*Event Tab*/
        TextView trainingTitle,soccer,textTime,textLoc,not_going,going,not_sure,attendance,attendanceCopy,sendNotification;
        /*Team Member*/
        TextView memberName;
        CircularImageView memberPic;

        AppCompatImageView icon_light_blue;


        public MyViewHolder(View itemView) {
            super(itemView);

            if(type.equalsIgnoreCase("event_tab")){

                trainingTitle=itemView.findViewById(R.id.trainingTitle);
                soccer=itemView.findViewById(R.id.soccer);
                textTime=itemView.findViewById(R.id.textTime);
                textLoc=itemView.findViewById(R.id.textLoc);
                not_going=itemView.findViewById(R.id.not_going);
                going=itemView.findViewById(R.id.going);
                not_sure=itemView.findViewById(R.id.not_sure);
                attendance=itemView.findViewById(R.id.attendance);
                attendanceCopy=itemView.findViewById(R.id.attendance_copy);
                sendNotification=itemView.findViewById(R.id.send_notification);
                attendanceSend=itemView.findViewById(R.id.attendance_send);

                trainingTitle.setTypeface(CommonUtils.setFontTextHeader(context));
                soccer.setTypeface(CommonUtils.setFontTextNormal(context));
                textTime.setTypeface(CommonUtils.setFontTextNormal(context));
                textLoc.setTypeface(CommonUtils.setFontTextNormal(context));
                not_going.setTypeface(CommonUtils.setFontTextNormal(context));
                going.setTypeface(CommonUtils.setFontTextNormal(context));
                not_sure.setTypeface(CommonUtils.setFontTextNormal(context));
                attendance.setTypeface(CommonUtils.sfProMedium(context));
                attendanceCopy.setTypeface(CommonUtils.sfProMedium(context));
                sendNotification.setTypeface(CommonUtils.sfProMedium(context));

            }else if(type.equalsIgnoreCase("team_talk")){



            }else if(type.equalsIgnoreCase("team_member")){

                memberName=itemView.findViewById(R.id.memeber_name);
                memberPic=itemView.findViewById(R.id.userProfilePic);
                memberPic.setImageResource(R.mipmap.football_player);
                memberName.setTypeface(CommonUtils.setFontTextHeader(context));

            }


        }

    }



}

