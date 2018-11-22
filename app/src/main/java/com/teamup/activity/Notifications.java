package com.teamup.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.adapter.NotificationAdapter;
import com.teamup.model.NotificationModel;
import com.teamup.utils.SwipeHelper;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends BaseActivity {

    RecyclerView common_rec;
    TextView emptyView;
    ArrayList<NotificationModel> refer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.notification_detail);
      //  setBackText();
     //   setHeadValue("Notifications",context);
        setToolbar("Notifications");
      //  listener();



        refer= new ArrayList<NotificationModel>();
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

        valuesInit();



       try{
           SwipeHelper swipeHelper = new SwipeHelper(this, common_rec) {
               @Override
               public void instantiateUnderlayButton(final RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                   underlayButtons.add(new UnderlayButton(
                           "Delete",
                           0,
                           Color.parseColor("#F63704"),
                           new UnderlayButtonClickListener() {
                               @Override
                               public void onClick(int pos) {
                                   // TODO: onDelete
                                   if(refer.size()>0){

                                       refer.remove(pos);
                                       valuesInit();
                                   }
                               }
                           }
                   ));
               }
           };
       }catch (Exception e){

       }

    }

    private void listener() {
//

        emptyView=findViewById(R.id.empty_view);
        findViewById(R.id.rightText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                common_rec.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        setRightText("Clear all",context);
    }

    private void valuesInit() {

        common_rec=findViewById(R.id.common_rec);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(context);
        common_rec.setLayoutManager(addLocLayout);
        common_rec.setNestedScrollingEnabled(false);
        final NotificationAdapter adapter = new NotificationAdapter(context,refer,"notification");
        common_rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if(refer.size()==0){
            common_rec.setVisibility(View.GONE);
//            emptyView.setVisibility(View.VISIBLE);
        }


        /*custom code for swipe*/
    }

}
