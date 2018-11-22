package com.teamup.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teamup.R;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;


public class PlayerDetail extends BaseActivity {
    TextView titleName,loc,soccer,anyage,gender,textPlayer,send_msg,invite;
    String type="event";
    LinearLayout btn_view;


    private void sendMsg(String title,String msgText) {

        final Dialog dialog = new Dialog(new ContextThemeWrapper(context, R.style.DialogSlideAnim));
        dialog.setContentView(R.layout.pop_up_window);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        lp.windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setAttributes(lp);

        // set the custom dialog components - text, image and button
        TextView editList = (TextView) dialog.findViewById(R.id.sent_nsg);

        TextView deleteList = (TextView) dialog.findViewById(R.id.sent_btn);
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);

        editList.setText(title);
        deleteList.setText(msgText);


        editList.setTypeface(CommonUtils.setSFProDisplaySemibold(context));
        deleteList.setTypeface(CommonUtils.setSFProDisplayRegular(context));
        cancel.setTypeface(CommonUtils.setSFProDisplaySemibold(context));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        editList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        deleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!CommonUtils.getPreferencesString(context, AppConstant.LIST_POSITION).equalsIgnoreCase("0")){
            btn_view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_detail);
        context=this;
        titleName=findViewById(R.id.earl_paddilla);
        loc=findViewById(R.id.loc);
        soccer=findViewById(R.id.soccer);
        anyage=findViewById(R.id.anyage);
        gender=findViewById(R.id.gender);
        textPlayer=findViewById(R.id.text_player);
        send_msg=findViewById(R.id.send_msg);
        invite=findViewById(R.id.invite);
        btn_view=findViewById(R.id.btn_view);


        titleName.setTypeface(CommonUtils.setFontMedium(context));
        loc.setTypeface(CommonUtils.setFontTextNormal(context));
        soccer.setTypeface(CommonUtils.setFontTextNormal(context));
        anyage.setTypeface(CommonUtils.setFontTextNormal(context));
        gender.setTypeface(CommonUtils.setFontTextNormal(context));
        textPlayer.setTypeface(CommonUtils.setFontTextNormal(context));
        send_msg.setTypeface(CommonUtils.setFontTextHeader(context));
        invite.setTypeface(CommonUtils.setFontTextHeader(context));

        send_msg.setText("Send Message");
        invite.setText("Invite to the Team");

        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  sendMsg("Send Message","Send Message");
                AlertDialog dialog = CommonUtils.getInviteDialog(context, "Send Message",
                        getString(R.string.lorem));
                        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Send", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context, "in progress", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.show();
            }
        });

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog dialog =  CommonUtils.getInviteDialog(context, "Invite to the team",
                        getString(R.string.lorem));
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "in progress", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
              //  sendMsg("Invite to the team","Send invitation");
            }
        });

     //   setHeadValue("Player Details",context);
      //  setBackText();
        setToolbar("Player Details");

    }

}