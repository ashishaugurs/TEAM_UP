package com.teamup.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.utils.CommonUtils;

public class MyExistingListing extends BaseActivity {

    TextView playerName,playerLocation,soccer,anyAge,gender,text_player;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_existing_listing_player);
        context=this;
      //  setBackText();
      //  setHeadValue("My existing listing",context);
        setToolbar("My existing listing");
        findid();
//        rightIcon();
    }

    private void findid() {
        playerName=findViewById(R.id.playerName);
        playerLocation=findViewById(R.id.playerLocation);
        soccer=findViewById(R.id.soccer);
        anyAge=findViewById(R.id.anyage);
        gender=findViewById(R.id.gender);
        text_player=findViewById(R.id.text_player);

        playerName.setTypeface(CommonUtils.setFontTextHeader(context));
        playerLocation.setTypeface(CommonUtils.setFontTextNormal(context));
        soccer.setTypeface(CommonUtils.setFontTextNormal(context));
        anyAge.setTypeface(CommonUtils.setFontTextNormal(context));
        gender.setTypeface(CommonUtils.setFontTextNormal(context));
        text_player.setTypeface(CommonUtils.setFontTextNormal(context));


    }


    private void rightIcon() {

        AppCompatImageView imageView=context.findViewById(R.id.notification);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.mipmap.ellipsis);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(new ContextThemeWrapper(context, R.style.DialogSlideAnim));
                dialog.setContentView(R.layout.edit_bottom_list);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.BOTTOM;
                lp.windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setAttributes(lp);

                // set the custom dialog components - text, image and button
                TextView editList = (TextView) dialog.findViewById(R.id.edit_list);
                TextView deleteList = (TextView) dialog.findViewById(R.id.delete_list);

                TextView cancel = (TextView) dialog.findViewById(R.id.cancel_pop);

                editList.setTypeface(CommonUtils.setSFProDisplayRegular(context));
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
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listing_data,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.editEvent:
                 CommonUtils.simpleSnackBar("Work in progress",findViewById(R.id.parentLinear));
                return true;
            case R.id.editTeam:
                CommonUtils.simpleSnackBar("Work in progress",findViewById(R.id.parentLinear));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


}
