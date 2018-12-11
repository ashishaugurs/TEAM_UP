package com.teamup.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.activity.Attendance;
import com.teamup.activity.EditEvent;
import com.teamup.activity.EditTeam;
import com.teamup.activity.OtherDetails;
import com.teamup.activity.TeamDetailsMap;
import com.teamup.model.Event;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;

import java.util.ArrayList;

public class EventAdapter extends BaseAdapter {
    ArrayList<Event> events ;
    int[] imge;
    Activity context;

    public EventAdapter(Activity context, ArrayList<Event> events, String type) {
        this.context=context;
        this.events = events;
    }


    public int getCount() {
        // TODO Auto-generated method stub
        return events.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View itemView;
        LinearLayout attendanceSend;
        itemView = inflater.inflate(R.layout.teamname_teamevent, parent, false);
        /*Event Tab*/
        TextView trainingTitle,soccer,textTime,textLoc,not_going,going,not_sure,attendance,attendanceCopy,sendNotification;
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

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,TeamDetailsMap.class));
            }
        });

        attendanceSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, Attendance.class));
            }
        });

        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Working on it...", Snackbar.LENGTH_LONG).show();
            }
        });

        if(!events.get(position).getCreatedBy().equals(FirebaseUtils.getUId())){
            itemView.findViewById(R.id.editEvent)
                    .setVisibility(View.GONE);
        }
        else{
            itemView.findViewById(R.id.editEvent)
                    .setVisibility(View.VISIBLE);
        }

        itemView.findViewById(R.id.editEvent)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,EditEvent.class);
                        intent.putExtra(AppConstant.KEY_TEAM_ID, events.get(position).getTeamID());
                        intent.putExtra(AppConstant.KEY_EVENT_ID, events.get(position).getEventID());
                        context.startActivity(intent);
                    }
                });

        //attendanceSend.

        return (itemView);
    }
}
