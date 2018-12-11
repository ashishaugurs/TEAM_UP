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
import com.teamup.model.Member;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;

import java.util.ArrayList;

public class EventMember extends BaseAdapter {
    ArrayList<Member> members;
    int[] imge;
    Activity context;

    public EventMember(Activity context, ArrayList<Member> members, String type) {
        this.context=context;
        this.members = members;
    }


    public int getCount() {
        // TODO Auto-generated method stub
        return members.size();
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
        itemView = inflater.inflate(R.layout.teamname_teammember, parent, false);
        /*Team Member*/
        TextView memberName;
        CircularImageView memberPic;
        memberName=itemView.findViewById(R.id.memeber_name);
        memberPic=itemView.findViewById(R.id.userProfilePic);
       // memberPic.setImageResource(R.mipmap.football_player);
        memberName.setTypeface(CommonUtils.setFontTextHeader(context));


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OtherDetails.class);
                intent.putExtra(FirebaseUtils.KEY_PASSED_REFERENCE,
                        FirebaseUtils
                        .getTeamMemberRef(members.get(position).getTeamID())
                        .child(members.get(position)
                        .getUid())
                        .getPath()
                        .toString());
                intent.putExtra(AppConstant.KEY_TEAM_ID, members.get(position).getTeamID());

                context.startActivity(intent);
            }
        });


        memberName.setText(members.get(position).getfirstname()+" "+members.get(position).getlastname());
        FirebaseUtils.setUserProfileImageUsingUID(memberPic, members.get(position).getUid());

        return (itemView);
    }
}
