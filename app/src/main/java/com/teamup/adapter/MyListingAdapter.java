package com.teamup.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
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

public class MyListingAdapter extends RecyclerView.Adapter<MyListingAdapter.MyViewHolder> implements RecyclerView.OnItemTouchListener {
    List<String> dataList;
    Activity context;
    LayoutInflater inflater;
    String type;
    int value;
    String nameTitle[];
    String exampleContent[];
    int imgListing[];
    GestureDetector mGestureDetector;
    private OnItemClickListener mListener;

    public MyListingAdapter(Activity context, List<String> dataList, String type) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.type=type;
    }

    public MyListingAdapter(final RecyclerView recyclerView, OnItemClickListener listener) {
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


        if(type.equalsIgnoreCase("mylisting")){
            value=R.layout.my_listing;
            nameTitle=new String[]{"My Listings","Search Listings"};
            imgListing=new int[]{R.mipmap.card,R.mipmap.savelisting};
        }
        else {
            value=R.layout.search_listing;
            nameTitle=new String[]{"Player","Team","TeamUp"};
            exampleContent=new String[]{"Looking for a team to join","Looking for a player","e.g. Looking for a Gym Buddy"};
            imgListing=new int[]{R.mipmap.player,R.mipmap.team_search,R.mipmap.team_up_search};
        }
        View view = inflater.inflate(value, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listingName.setText(nameTitle[position]);
        holder.saveListLimg.setImageResource(imgListing[position]);

        if(!type.equalsIgnoreCase("mylisting")){
            holder.example_content.setText(exampleContent[position]);
        }

    }


    @Override
    public int getItemCount() {
        if(type.equalsIgnoreCase("mylisting"))
        return 2;
        else
        return  3;
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
        // ads
        CircularImageView notiImage;
        AppCompatImageView saveListLimg;
        TextView example_content,listingName,finale_title,approval_text,date,viewProfile,accept,decline;
        // calendar


        public MyViewHolder(View itemView) {
            super(itemView);
            if(type.equalsIgnoreCase("mylisting")){

                listingName=itemView.findViewById(R.id.listingName);
                saveListLimg=itemView.findViewById(R.id.saveListLimg);
                listingName.setTypeface(CommonUtils.setFontTextHeader(context));

            }
            else{

                listingName=itemView.findViewById(R.id.listingName);
                saveListLimg=itemView.findViewById(R.id.saveListLimg);
                example_content=itemView.findViewById(R.id.example_content);
                listingName.setTypeface(CommonUtils.setFontTextHeader(context));
                example_content.setTypeface(CommonUtils.setFontTextNormal(context));

            }



        }

    }

}





