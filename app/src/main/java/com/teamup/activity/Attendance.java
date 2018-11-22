package com.teamup.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.adapter.NotificationAdapter;
import com.teamup.model.NotificationModel;
import com.teamup.utils.CommonUtils;

import java.util.ArrayList;

public class Attendance extends BaseActivity {

    TextView  notGOing, going, notSure;
    LinearLayout notGoingParent,goingParent,notSureParent;
    ImageView notGoingImg,goingImg,notSureImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_player);
        findId();
      //  setBackText();
        valuesInit();
        listener();
      //  setHeadValue("Tuesday soccer",context);

        setToolbar("Tuesday Soccer").setSubtitle("9 May 09:30");
    }

    private void findId() {

        notGoingImg=findViewById(R.id.not_going_img);
        goingImg=findViewById(R.id.going_img);
        notSureImg=findViewById(R.id.not_sure_img);

        notGoingParent=findViewById(R.id.not_going_parent);
        goingParent=findViewById(R.id.going_parent);
        notSureParent=findViewById(R.id.not_sure_parent);

        notGOing = findViewById(R.id.not_going);
        going = findViewById(R.id.going);
        notSure = findViewById(R.id.not_sure);

        notGOing.setTypeface(CommonUtils.setFontTextNormal(context));
        going.setTypeface(CommonUtils.setFontTextNormal(context));
        notSure.setTypeface(CommonUtils.setFontTextNormal(context));
    }

    private void valuesInit() {
       ArrayList<NotificationModel> refer=new ArrayList<NotificationModel>();

        NotificationModel model1=new NotificationModel();
        model1.setTitle("New Member");
        model1.setContent("Andrew has been added to your team.");
        model1.setImg(R.mipmap.user_one);
        model1.setType("onlyview");

        NotificationModel model2=new NotificationModel();
        model2.setTitle("New Event Created");
        model2.setContent("A new event has been created.");
        model2.setImg(R.mipmap.user_four);
        model2.setType("noview");

        NotificationModel model3=new NotificationModel();
        model3.setTitle("Approve New Team Member");
        model3.setContent("Your approval is needed to add new member to your team.");
        model3.setImg(R.mipmap.user_three);
        model3.setType("allview");



        refer.add(model1);
        refer.add(model2);
        refer.add(model3);




        RecyclerView common_rec=findViewById(R.id.common_rec);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(context);
        common_rec.setLayoutManager(addLocLayout);
        common_rec.setNestedScrollingEnabled(false);
        NotificationAdapter adapter = new NotificationAdapter(context,refer,"attendance");
        common_rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void listener() {


        notGoingParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notGoingImg.setImageResource(R.mipmap.not_going_color);
                notGOing.setTextColor(getResources().getColor(R.color.red_text));
                goingImg.setImageResource(R.mipmap.going_small);
                going.setTextColor(getResources().getColor(R.color.text_write));
                notSureImg.setImageResource(R.mipmap.note_sure);
                notSure.setTextColor(getResources().getColor(R.color.text_write));


            }
        });

        goingParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                notGoingImg.setImageResource(R.mipmap.not_going);
                notGOing.setTextColor(getResources().getColor(R.color.text_write));
                goingImg.setImageResource(R.mipmap.going_color);
                going.setTextColor(getResources().getColor(R.color.green_text));
                notSureImg.setImageResource(R.mipmap.note_sure);
                notSure.setTextColor(getResources().getColor(R.color.text_write));


            }
        });

        notSureParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notGoingImg.setImageResource(R.mipmap.not_going);
                notGOing.setTextColor(getResources().getColor(R.color.text_write));
                goingImg.setImageResource(R.mipmap.going_small);
                going.setTextColor(getResources().getColor(R.color.text_write));
                notSureImg.setImageResource(R.mipmap.not_sure_color);
                notSure.setTextColor(getResources().getColor(R.color.yellow_text));

            }
        });

    }


}
