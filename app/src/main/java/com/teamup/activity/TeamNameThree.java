package com.teamup.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamup.R;
import com.teamup.adapter.EventAdapter;
import com.teamup.adapter.EventMember;
import com.teamup.adapter.MessageAdapter;
import com.teamup.model.ChatBubble;
import com.teamup.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class TeamNameThree extends BaseActivity {

    String type;
    LinearLayout bottom,btn_send;
    FrameLayout replaceFragment;
    EditText chat_text;
    boolean myMessage = true;
    private List<ChatBubble> ChatBubbles=new ArrayList<>();
    MessageAdapter msgAdapter;
    ListView listView;
//    AppCompatImageView notificationChange;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.team_name_three);
        init();
//      setBackText();
//      setHeadValue("Team name",context);
        setToolbar("Team name");
//      rightIcon();
    }


    private void rightIcon() {

        AppCompatImageView imageView=context.findViewById(R.id.notification);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.mipmap.ellipsis);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(new ContextThemeWrapper(context, R.style.DialogSlideAnim));
                dialog.setContentView(R.layout.bottom_sheet);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.BOTTOM;
                lp.windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setAttributes(lp);

                // set the custom dialog components - text, image and button
                TextView editTeam = (TextView) dialog.findViewById(R.id.edit_team);
                TextView editEvent = (TextView) dialog.findViewById(R.id.edit_event);
                TextView addNewEvent = (TextView) dialog.findViewById(R.id.add_new_event);
                TextView addNewMember = (TextView) dialog.findViewById(R.id.add_new_member);
                TextView syncCalendar = (TextView) dialog.findViewById(R.id.sync_calendar);
                TextView cancel = (TextView) dialog.findViewById(R.id.cancel_pop);

                editTeam.setTypeface(CommonUtils.setSFProDisplayRegular(context));
                editEvent.setTypeface(CommonUtils.setSFProDisplayRegular(context));
                addNewEvent.setTypeface(CommonUtils.setSFProDisplayRegular(context));
                addNewMember.setTypeface(CommonUtils.setSFProDisplayRegular(context));
                syncCalendar.setTypeface(CommonUtils.setSFProDisplayRegular(context));
                cancel.setTypeface(CommonUtils.setSFProDisplaySemibold(context));

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                editTeam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                editEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                addNewEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                addNewMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(context,AddNewMember.class));
                    }
                });
                syncCalendar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
//                        startActivity(new Intent(context,SyncCalendar.class));
                    }
                });

                dialog.show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        notificationChange.setVisibility(View.VISIBLE);
    }

    private void init() {
        try{
//            notificationChange=findViewById(R.id.notificationChange);
             listView=findViewById(R.id.list_msg);
             bottom=findViewById(R.id.bottom);
             chat_text=findViewById(R.id.chat_text);
             btn_send=findViewById(R.id.btn_send);


            final TabLayout tabLayout = findViewById(R.id.tabLayout);

            type="event_tab";


            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    switch (tabLayout.getSelectedTabPosition()){

                        case 0:
                            type="event_tab";
                            bottom.setVisibility(View.GONE);
                            eventAdapter();
                            break;

                        case 2:
                            type="team_member";
                            bottom.setVisibility(View.GONE);
                            eventMember();
                             break;

                        case 1:
                            type="team_talk";
                            bottom.setVisibility(View.VISIBLE);
                            listView.setAdapter(null);
                            messageAdapter();
                            break;
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            chat_text.setTypeface(CommonUtils.setFontTextNormal(context));
            bottom.setVisibility(View.GONE);
            sendMsgListener();
            eventAdapter();



/*            notificationChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, Notifications.class));
                }
            });*/


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void sendMsgListener() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chat_text.getText().toString().trim().equals("")) {
                    Toast.makeText(context, "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {
                    //add message to list
                    ChatBubble ChatBubble = new ChatBubble(chat_text.getText().toString(), myMessage);
                    ChatBubbles.add(ChatBubble);
                    msgAdapter.notifyDataSetChanged();
                    chat_text.setText("");
                    if (myMessage) {
                        myMessage = false;
                    } else {
                        myMessage = true;
                    }
                }
            }
        });

    }


    private void eventAdapter() {
        EventAdapter adapter = new EventAdapter(context,new ArrayList<String>(),type);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void eventMember() {
        EventMember adapter = new EventMember(context,new ArrayList<String>(),type);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void messageAdapter() {
        msgAdapter = new MessageAdapter(this, R.layout.left_chat_bubble, ChatBubbles);
        listView.setAdapter(msgAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.notification:
                 startActivity(new Intent(context,Notifications.class));
                return true;
            case R.id.editTeam:
                startActivity(new Intent(context,EditTeam.class));
                return true;
            case R.id.editEvent:
                startActivity(new Intent(context,EditEvent.class));
                return true;
            case R.id.addNewEvent:
                startActivity(new Intent(context,AddNewEvent.class));
                return true;
            case R.id.addNewMember:
                startActivity(new Intent(context,AddNewMember.class));
                return true;
            case R.id.teamNotification:
//               Toast.makeText(getApplicationContext(),"Note Option Selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.syncCalendar:
//               Toast.makeText(getApplicationContext(),"Note Option Selected",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(Build.VERSION.SDK_INT > 11) {
            invalidateOptionsMenu();
/*
            menu.findItem(R.id.option2).setVisible(false);
            menu.findItem(R.id.option4).setVisible(true);
*/
        }
        return super.onPrepareOptionsMenu(menu);
    }

}
