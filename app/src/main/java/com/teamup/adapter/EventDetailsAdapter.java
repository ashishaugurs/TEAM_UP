package com.teamup.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.teamup.R;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.OnItemClickListener;


import java.util.List;

public class EventDetailsAdapter extends RecyclerView.Adapter<EventDetailsAdapter.MyViewHolder> implements RecyclerView.OnItemTouchListener {
    List<String> dataList;
    Activity context;
    LayoutInflater inflater;
    String type;
    int value;
    GestureDetector mGestureDetector;
    private OnItemClickListener mListener;



    public EventDetailsAdapter(Activity context, List<String> dataList, String type) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.type=type;
    }

    public EventDetailsAdapter(final RecyclerView recyclerView, OnItemClickListener listener) {
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
        if(type.equalsIgnoreCase("sport")){

            value=R.layout.inflate_select_sport;

        }
        else if(type.equalsIgnoreCase("location")){

            value=R.layout.inflate_location;

        }
        else if(type.equalsIgnoreCase("unsaved")){
            value=R.layout.mylisting_playerinfo;
        }
        else{
            value=R.layout.center_events_near;
        }

        View view = inflater.inflate(value, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(type.equalsIgnoreCase("sport")){
            holder.sportName.setText(dataList.get(position));
        }
    }


    @Override
    public int getItemCount() {
       return 10;
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

        LinearLayout linearLayout;
        TextView finale_title,finale_date,game_name,location_name;
        AppCompatImageView icon_light_blue;

        /*Select Sport*/
        TextView sportName;

        /*location*/
        TextView stadiumName,cityName;

        /*Player info - unsaved*/
        TextView namePlayer,sportCat,location,ageGroup,genderType;


        public MyViewHolder(View itemView) {
            super(itemView);
            if(type.equalsIgnoreCase("sport")){

                sportName=itemView.findViewById(R.id.textView_sport);
                sportName.setTypeface(CommonUtils.setSfPro(context));


            }else if(type.equalsIgnoreCase("location")){

                cityName=itemView.findViewById(R.id.cityName);
                stadiumName=itemView.findViewById(R.id.stadiumName);
                cityName.setTypeface(CommonUtils.setSfPro(context));
                stadiumName.setTypeface(CommonUtils.setSfPro(context));
            }
            else if(type.equalsIgnoreCase("unsaved")){

                namePlayer=itemView.findViewById(R.id.playerName);
                sportCat=itemView.findViewById(R.id.sportCat);
                location=itemView.findViewById(R.id.location);
                ageGroup=itemView.findViewById(R.id.ageGroup);
                genderType=itemView.findViewById(R.id.genderType);

                namePlayer.setTypeface(CommonUtils.setFontTextHeader(context));
                sportCat.setTypeface(CommonUtils.setFontTextNormal(context));
                location.setTypeface(CommonUtils.setFontTextNormal(context));
                ageGroup.setTypeface(CommonUtils.setFontTextNormal(context));
                genderType.setTypeface(CommonUtils.setFontTextNormal(context));

            }
            else
            {
                linearLayout=itemView.findViewById(R.id.eventsLinear);
                finale_title=itemView.findViewById(R.id.finale_title);
                finale_date=itemView.findViewById(R.id.finale_date);
                game_name=itemView.findViewById(R.id.game_name);
                location_name=itemView.findViewById(R.id.location_name);
                finale_title.setTypeface(CommonUtils.setFontTextHeader(context));
                finale_date.setTypeface(CommonUtils.setFontTextNormal(context));
                game_name.setTypeface(CommonUtils.setFontTextNormal(context));
                location_name.setTypeface(CommonUtils.setFontTextNormal(context));
            }


        }

    }



}

