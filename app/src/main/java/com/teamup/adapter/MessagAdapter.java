package com.teamup.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.teamup.R;
import com.teamup.activity.EventDetails;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.OnItemClickListener;


import java.util.List;

public class MessagAdapter extends RecyclerView.Adapter<MessagAdapter.MyViewHolder> implements RecyclerView.OnItemTouchListener {

    List<String> dataList;
    Activity context;
    LayoutInflater inflater;
    String type;
    int value;
    String nameChat[]=new String[]{"Mario Collins","Dylan Cruz","Ann Paul","Mario Collins"};
    int pChatImage[]=new int[]{R.mipmap.user_one,R.mipmap.user_two,R.mipmap.user_three,R.mipmap.user_four};
    int gChatImage[]=new int[]{R.mipmap.player,R.mipmap.user_two};
    GestureDetector mGestureDetector;
    private OnItemClickListener mListener;

    public MessagAdapter(Activity context, List<String> dataList, String type) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.type=type;
    }

    public MessagAdapter(final RecyclerView recyclerView, OnItemClickListener listener) {
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
        if(type.equalsIgnoreCase("saved") || type.equalsIgnoreCase("near_you"))
            value=R.layout.inflate_events;
        else
            value=R.layout.inflate_msg_teamtalk;

        View view = inflater.inflate(value, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(type.equalsIgnoreCase("saved")){
            holder.eventImg.setImageResource(R.mipmap.event_football);
            holder.heart_icon.setImageResource(R.mipmap.hearticon);

        }else if(type.equalsIgnoreCase("near_you")){
            holder.eventImg.setImageResource(R.mipmap.playing);
            holder.heart_icon.setImageResource(R.mipmap.heart_white);
        }
        else if(type.equalsIgnoreCase("p_chat")){

            holder.football_player.setImageResource(pChatImage[position]);
            holder.teamtalk_title.setText(nameChat[position]);

        }else if(type.equalsIgnoreCase("group_chat")){

            holder.football_player.setImageResource(R.mipmap.player);

        }


        // listeners

        if(type.equalsIgnoreCase("saved") ||
                type.equalsIgnoreCase("near_you")){

            CommonUtils.write("condition working");

            holder.cardClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommonUtils.write("click working");

                    context.startActivity(new Intent(context, EventDetails.class));
                }
            });
        }else{

            CommonUtils.write("condition not working");
        }

    }


    @Override
    public int getItemCount() {

        if(type.equalsIgnoreCase("saved") ||
                type.equalsIgnoreCase("near_you")){

            return 5;
        }else if(type.equalsIgnoreCase("p_chat")){

            return 4;

        }else if(type.equalsIgnoreCase("group_chat")){
            return 2;
        }
        else{
            return 0;
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

        CardView cardClick;
        // ads
        TextView teamtalk_title,des_team,now,qty,eventTitle,eventDay,calendar_date,monthName,locationName,eventDte;
        CircularImageView football_player;
        AppCompatImageView eventImg,heart_icon;
        // calendar


        public MyViewHolder(View itemView) {
            super(itemView);

            if(type.equalsIgnoreCase("near_you") ||
                    type.equalsIgnoreCase("saved")){

                cardClick= itemView.findViewById(R.id.card_click);
                heart_icon=itemView.findViewById(R.id.like_unlike);
                eventImg=itemView.findViewById(R.id.eventImg);
                eventTitle=itemView.findViewById(R.id.eventTitle);
                eventDay=itemView.findViewById(R.id.eventDay);
                calendar_date=itemView.findViewById(R.id.calendar_date);
                monthName=itemView.findViewById(R.id.monthName);
                locationName=itemView.findViewById(R.id.locationName);
                eventDte=itemView.findViewById(R.id.eventDate);
                eventTitle.setTypeface(CommonUtils.setFontTextHeader(context));
                eventDay.setTypeface(CommonUtils.setFontTextNormal(context));
                calendar_date.setTypeface(CommonUtils.setFontTextHeader(context));
                monthName.setTypeface(CommonUtils.setFontTextNormal(context));
                locationName.setTypeface(CommonUtils.setFontTextNormal(context));
                eventDte.setTypeface(CommonUtils.setFontTextNormal(context));

            }else{
                football_player=itemView.findViewById(R.id.football_player);
                teamtalk_title=itemView.findViewById(R.id.teamtalk_title);
                des_team=itemView.findViewById(R.id.des_team);
                now=itemView.findViewById(R.id.now);
                qty=itemView.findViewById(R.id.qty);
                teamtalk_title.setTypeface(CommonUtils.setFontTextHeader(context));
                des_team.setTypeface(CommonUtils.setFontTextNormal(context));
                now.setTypeface(CommonUtils.setFontTextNormal(context));
                qty.setTypeface(CommonUtils.setFontTextNormal(context));
              }

            }

        }

    }





