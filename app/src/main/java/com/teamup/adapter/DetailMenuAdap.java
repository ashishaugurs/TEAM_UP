package com.teamup.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.teamup.R;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.OnItemClickListener;


import java.util.List;

public class DetailMenuAdap extends RecyclerView.Adapter<DetailMenuAdap.MyViewHolder> implements RecyclerView.OnItemTouchListener {
    List<String> dataList;
    Activity context;
    LayoutInflater inflater;
    String type;
    int value;
    GestureDetector mGestureDetector;
    private OnItemClickListener mListener;


    public DetailMenuAdap(Activity context, List<String> dataList, String type) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.type=type;
    }


    public DetailMenuAdap(final RecyclerView recyclerView, OnItemClickListener listener) {
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



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(type.equalsIgnoreCase("ads"))
            value=R.layout.local_ads;
        else if(type.equalsIgnoreCase("nearby"))
            value=R.layout.events_near_you;
        else if(type.equalsIgnoreCase("myteam")){
            value=R.layout.inflate_row_myteam;
        }
        else
            value=R.layout.events_layout;

        View view = inflater.inflate(value, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        if(type.equalsIgnoreCase("ads"))
        {

        }
        else if(type.equalsIgnoreCase("nearby"))
        {

        }
        else if(type.equalsIgnoreCase("myteam")){
            holder.teamTitle.setText(dataList.get(position));
        }
        else{
            if(position>0)
             holder.icon_light_blue.setImageResource(R.mipmap.icon_dark_blue);
        }

    }


    @Override
    public int getItemCount() {

        if(type.equalsIgnoreCase("ads")){
            return 5;
        }else if(type.equalsIgnoreCase("nearby")){
            return 3;
        }else if(type.equalsIgnoreCase("myteam")){
            return  dataList.size() ;
        }
        else{
            return 2;
        }

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

            if(type.equalsIgnoreCase("ads")){

                playerName=itemView.findViewById(R.id.playerName);
                categoryName=itemView.findViewById(R.id.category);
                location=itemView.findViewById(R.id.location);
                gender=itemView.findViewById(R.id.gender);
                dob=itemView.findViewById(R.id.dob);

                playerName.setTypeface(CommonUtils.setFontTextHeader(context));
                categoryName.setTypeface(CommonUtils.setFontTextNormal(context));
                location.setTypeface(CommonUtils.setFontTextNormal(context));
                gender.setTypeface(CommonUtils.setFontTextNormal(context));
                dob.setTypeface(CommonUtils.setFontTextNormal(context));

            }else if(type.equalsIgnoreCase("nearby")){


                finale_title=itemView.findViewById(R.id.finale_title);
                finale_date=itemView.findViewById(R.id.finale_date);
                game_name=itemView.findViewById(R.id.game_name);
                location_name=itemView.findViewById(R.id.location_name);


                finale_title.setTypeface(CommonUtils.setFontTextHeader(context));
                finale_date.setTypeface(CommonUtils.setFontTextNormal(context));
                game_name.setTypeface(CommonUtils.setFontTextNormal(context));
                location_name.setTypeface(CommonUtils.setFontTextNormal(context));



            }else if(type.equalsIgnoreCase("myteam")){

                teamImage=itemView.findViewById(R.id.teamPic);
                teamTitle=itemView.findViewById(R.id.teamTitle);
                teamType=itemView.findViewById(R.id.teamType);
                playerStrength=itemView.findViewById(R.id.playerStrength);

                teamImage.setImageResource(R.mipmap.football_player);
                teamTitle.setTypeface(CommonUtils.setFontTextHeader(context));
                playerStrength.setTypeface(CommonUtils.setFontTextNormal(context));
                teamType.setTypeface(CommonUtils.setFontTextNormal(context));

            }
            else{
                icon_light_blue=itemView.findViewById(R.id.icon_light_blue);
                eventTitle=itemView.findViewById(R.id.eventTitle);
                eventDay=itemView.findViewById(R.id.eventDay);
                monthName=itemView.findViewById(R.id.monthName);
                calendar_date=itemView.findViewById(R.id.calendar_date);
                eventTitle.setTypeface(CommonUtils.setFontTextHeader(context));
                eventDay.setTypeface(CommonUtils.setFontTextNormal(context));
                monthName.setTypeface(CommonUtils.setFontMedium(context));
                calendar_date.setTypeface(CommonUtils.setFontTextHeader(context));
            }

        }

    }



}

