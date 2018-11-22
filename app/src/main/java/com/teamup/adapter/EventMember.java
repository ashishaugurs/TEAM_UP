package com.teamup.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.teamup.R;
import com.teamup.activity.OtherDetails;
import com.teamup.utils.CommonUtils;

import java.util.ArrayList;

public class EventMember extends BaseAdapter {
    ArrayList<String> Title=new ArrayList<>();
    int[] imge;
    Activity context;

    public EventMember(Activity context, ArrayList<String> strings, String type) {
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
        itemView = inflater.inflate(R.layout.teamname_teammember, parent, false);
        /*Team Member*/
        TextView memberName;
        CircularImageView memberPic;
        memberName=itemView.findViewById(R.id.memeber_name);
        memberPic=itemView.findViewById(R.id.userProfilePic);
        memberPic.setImageResource(R.mipmap.football_player);
        memberName.setTypeface(CommonUtils.setFontTextHeader(context));


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,OtherDetails.class));
            }
        });


        return (itemView);
    }
}
