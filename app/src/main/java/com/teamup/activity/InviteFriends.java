package com.teamup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.utils.CommonUtils;



public class InviteFriends extends BaseActivity {

    TextView invite;
    TextView textHeader;
    TextView fbTitle;
    TextView fbDescription;
    TextView instaTitle;
    TextView designInsta;
    TextView emailTitle;
    TextView desEmail;
    TextView titleContact;
    TextView desContact;

    @Override
    protected void onResume() {
        super.onResume();
     //   visibilityGONEICon(context);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_friend);
        context=this;

        findId();
        setBlankToolbar();

        textHeader.setTypeface(CommonUtils.setFontTextHeader(context));
        fbTitle.setTypeface(CommonUtils.setFontTextHeader(context));
        fbDescription.setTypeface(CommonUtils.setFontTextNormal(context));
        instaTitle.setTypeface(CommonUtils.setFontTextHeader(context));
        designInsta.setTypeface(CommonUtils.setFontTextNormal(context));
        emailTitle.setTypeface(CommonUtils.setFontTextHeader(context));
        desEmail.setTypeface(CommonUtils.setFontTextNormal(context));
        titleContact.setTypeface(CommonUtils.setFontTextHeader(context));
        desContact.setTypeface(CommonUtils.setFontTextNormal(context));
        invite.setTypeface(CommonUtils.setFontMedium(context));
        textHeader.setText("Invite your friends\n" + "to join TeamUp community ");

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,RootActivity.class));
                finish();
            }
        });



        backclick();

    }

    private void findId() {
        invite=findViewById(R.id.invite);
        textHeader=findViewById(R.id.textView);
        fbTitle=findViewById(R.id.title_fb);
        fbDescription=findViewById(R.id.fb_des);
        instaTitle=findViewById(R.id.title_in);
        designInsta=findViewById(R.id.insta_design);
        emailTitle=findViewById(R.id.title_em);
        desEmail=findViewById(R.id.des_email);
        titleContact=findViewById(R.id.title_co);
        desContact=findViewById(R.id.des_contact);
    }


}