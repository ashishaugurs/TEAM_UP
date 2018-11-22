package com.teamup.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.utils.CommonUtils;

import java.util.ArrayList;

public class EventAdapter extends BaseAdapter {
    ArrayList<String> Title=new ArrayList<>();
    int[] imge;
    Activity context;

    public EventAdapter(Activity context, ArrayList<String> strings, String type) {
        this.context=context;
        Title = null;
    }


    public int getCount() {
        // TODO Auto-generated method stub
        return 10;
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

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

        return (itemView);
    }
}
