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
import com.teamup.model.NotificationModel;
import com.teamup.utils.CommonUtils;


import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    List<NotificationModel> dataList;
    Activity context;
    LayoutInflater inflater;
    String type;
    int value;
    String nameTitle[];
    String exampleContent[];
    int imgListing[];
    int pos;
    public NotificationAdapter(Activity context, List<NotificationModel> dataList, String type) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.type=type;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(type.equalsIgnoreCase("notification"))
        value=R.layout.notification_screen;
        else if(type.equalsIgnoreCase("attendance")){
            value=R.layout.teamname_teammember;
        }
        else
         value=R.layout.mylisting_playerinfo;


        View view = inflater.inflate(value, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        if(type.equalsIgnoreCase("notification")){


            if(dataList.get(position).getType().equalsIgnoreCase("allview")){
                holder.acceptDec.setVisibility(View.VISIBLE);
                holder.viewProfile.setVisibility(View.VISIBLE);
                holder.finale_title.setText(dataList.get(position).getTitle());
                holder.approval_text.setText(dataList.get(position).getContent());
                holder.notiImage.setImageResource(dataList.get(position).getImg());
            }else if(dataList.get(position).getType().equalsIgnoreCase("noview")){
                holder.acceptDec.setVisibility(View.GONE);
                holder.viewProfile.setVisibility(View.GONE);
                holder.finale_title.setText(dataList.get(position).getTitle());
                holder.approval_text.setText(dataList.get(position).getContent());
                holder.notiImage.setImageResource(dataList.get(position).getImg());
            }else{
                holder.finale_title.setText(dataList.get(position).getTitle());
                holder.approval_text.setText(dataList.get(position).getContent());
                holder.viewProfile.setVisibility(View.VISIBLE);
                holder.acceptDec.setVisibility(View.INVISIBLE);
                holder.notiImage.setImageResource(dataList.get(position).getImg());
            }


        }else if(type.equalsIgnoreCase("attendance")){

                if(dataList.size()%2==0){
                 holder.rightIcon.setImageResource(R.mipmap.not_sure_color);
                }else{
                    holder.rightIcon.setImageResource(R.mipmap.going_color);
                }

        }
        else{


        }



    }


    @Override
    public int getItemCount() {
       return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView memberName;
        CircularImageView memberPic;
        AppCompatImageView rightIcon;

        // ads
        CircularImageView notiImage;
        TextView example_content,listingName,finale_title,approval_text,date,viewProfile,accept,decline;
        LinearLayout acceptDec;
        // calendar



        public MyViewHolder(View itemView) {
            super(itemView);

            if(type.equalsIgnoreCase("notification")){

                notiImage=itemView.findViewById(R.id.userProfilePic);
                finale_title=itemView.findViewById(R.id.finale_title);
                approval_text=itemView.findViewById(R.id.approval_text);
                date=itemView.findViewById(R.id.date);
                viewProfile=itemView.findViewById(R.id.viewProfile);
                accept=itemView.findViewById(R.id.accept);
                decline=itemView.findViewById(R.id.decline);
                acceptDec=itemView.findViewById(R.id.acceptDec);

                finale_title.setTypeface(CommonUtils.setFontTextHeader(context));
                approval_text.setTypeface(CommonUtils.setFontTextNormal(context));
                date.setTypeface(CommonUtils.setFontTextNormal(context));
                viewProfile.setTypeface(CommonUtils.setMontserratMedium(context));
                accept.setTypeface(CommonUtils.setMontserratMedium(context));
                decline.setTypeface(CommonUtils.setMontserratMedium(context));

                nameTitle=new String[]{"New Member","New Event Created","Approve New Team Member"};
                exampleContent=new String[]{"Andrew has been added to your team.","A new event has been created.","Your approval is needed to add new member to your team."};
                imgListing=new int[]{R.mipmap.user_one,R.mipmap.user_two,R.mipmap.user_four};


            }else if(type.equalsIgnoreCase("attendance")){

                memberName=itemView.findViewById(R.id.memeber_name);
                memberPic=itemView.findViewById(R.id.userProfilePic);
                rightIcon=itemView.findViewById(R.id.rightIcon);
                memberPic.setImageResource(R.mipmap.football_player);
                memberName.setTypeface(CommonUtils.setFontTextHeader(context));
            }
            else{


            }

        }

    }

}





